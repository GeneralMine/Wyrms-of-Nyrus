package com.vetpetmon.wyrmsofnyrus.handlers;

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.Constants;
import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.WyrmVariables;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.ai.gestalt.GestaltHostMind;
import com.vetpetmon.wyrmsofnyrus.entity.nonwyrms.EntityNKAgent;
import com.vetpetmon.wyrmsofnyrus.evo.EvoPoints;
import com.vetpetmon.wyrmsofnyrus.invasion.*;
import com.vetpetmon.wyrmsofnyrus.locallib.ChatUtils;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Constants.ModID)
public class WoNHandler {

    public static int infamyDecay=5;


    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(WyrmsOfNyrus.MODID))
        {
            ConfigManager.sync(WyrmsOfNyrus.MODID, Config.Type.INSTANCE);
            WyrmsOfNyrus.logger.info("Configuration loaded or changed.");
        }
    }
    
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        //if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 10) WyrmsOfNyrus.logger.info("[WONHANDLER] onWorldTick was called successfully.");
        // GESTALT
        if (AI.gestaltUseInfamy && GestaltHostMind.getAttentionLevel(world) > 1) {
            if ((GestaltHostMind.getAttentionLevel(world) == 100)) {
                if (RNG.getIntRangeInclu(1, (AI.gestaltInfamyDecayChance/(1 + (GestaltHostMind.getAttentionLevel(world)/3)))) == 1){
                    if (infamyDecay == 0) GestaltHostMind.decreaseAttentionLevel(world);
                    else infamyDecay--;
                    //WyrmsOfNyrus.logger.info("Infamy decay countdown: " + infamyDecay);
                    // Next line of code looks extremely evil, but I promise it's completely fine, you don't need curly braces for everything! (:
                    if (!world.playerEntities.isEmpty()) for (EntityPlayer player: world.playerEntities) {
                        if(AI.maxInfamySummonsPods) InvasionEvent.invasionEvent(player,world);
                        world.playSound(null, player.getPosition(), SoundRegistry.maxinfamy, SoundCategory.HOSTILE, 4, 0.5F);
                    }
                }
            }
            else if (RNG.getIntRangeInclu(1, (AI.gestaltInfamyDecayChance/(1 + (GestaltHostMind.getAttentionLevel(world)/2)))) == 1) {
                GestaltHostMind.decreaseAttentionLevel(world);
                infamyDecay = 5;
            }
        }
        // EVOLUTION
        EvoPoints.decay(world);
        // INVASION
        if (Invasion.invasionEnabled) InvasionStatus.executescript(world);

        // EVENTS

        // EXPLANATION OF GHSA-xwmh-4hmj-2vw4 & patch:
        // THIS IS PART 2. This patch moves the invasion checks and events from onPlayerTick to onWorldTick.
        // onWorldTick is only invoked by the server-side jar, not client-side, as the client jar does NOT handle the world functions.
        // GHSA-xwmh-4hmj-2vw4 happened because it's also the CLIENT that was invoking invasion events, when only the server
        // should've been invoking the events in the first place.
        //
        // Without immediate access to the player entity, we have to look at the logged playerEntities in the world.
        // Singleplayer will ALWAYS have 1 player active in it. At least, it should, otherwise something went horribly wrong.
        // But in Multiplayer, the server may call for an event but NOT have any players, and instead, the code will try to look
        // for null values. This will cause NullPointerException errors, and most likely crash the server. Let's fix that, shall we?
        //
        // We first check if there's even a player in the world in the first place, by using Minecraft's playerEntities.isEmpty() getter,
        // which will return false if there is at least one player currently connected to the server. Thus, we check if isEmpty() does not
        // return true. If it returns false, then we move into the code segments within the condition.
        //
        // Now, in singleplayer, there will always be 1 player. (At least, there should be 1. If not, we are in big trouble!)
        // world.rand.nextInt() always returns at least 1, so if world.playerEntities.size() == 1, then it will always pick the player in
        // singleplayer. In multiplayer, world.playerEntities.size() >= 1, as 0 is filtered out in the conditional test anyway.
        // This means that if there are multiple players connected to the server at once, players have a 1 in N chance of having an event
        // take place in their area, N being the total amount of players on the server. After we find this (un)lucky player, we set a
        // temporary class as the variable, with the selected player being the object.
        //
        // Something-something, later versions insert MP-exclusive event features/mechanics here.
        //
        // After all of that is done, the mod will now run almost as identical as pre-patched versions do, but with
        // GHSA-xwmh-4hmj-2vw4 completely fixed (in theory... It should be fixed.)
        if (!world.playerEntities.isEmpty()) {
            if (world.getDifficulty() != EnumDifficulty.PEACEFUL && world.provider.isSurfaceWorld()) { //Fixes events trying to happen in peaceful mode and events happening outside of the nether
                EntityPlayer chosenPlayer = world.playerEntities.get(world.rand.nextInt(world.playerEntities.size())); //Pick random player, always turns at least 1, in singleplayer, the one player is always selected for events
                int x = (int) chosenPlayer.posX;
                int y = (int) chosenPlayer.posY;
                int z = (int) chosenPlayer.posZ;
                boolean invasionActive = WyrmVariables.WorldVariables.get(world).invasionStarted;

                if (!chosenPlayer.isDead && Invasion.invasionEnabled && (!chosenPlayer.world.isRemote && event.phase == TickEvent.Phase.END)) { // Check to make sure this player actually exists in the world LOL
                    if (!invasionActive && Invasion.invasionStartsNaturally) {
                        if (InvasionScheduler.detectDayChange(world)) {
                            VisitorEvent.visitorEvent(false, world, x, y, z);
                        }
                    }
                    if (invasionActive) {
                        if (InvasionScheduler.runSchedule(world)) {
                            InvasionEvent.invasionEvent(chosenPlayer, world);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onChatMessage(final ServerChatEvent event) {
        String code = WyrmVariables.WorldVariables.get(event.getPlayer().getEntityWorld()).distressCode, input = event.getMessage();
        EntityPlayerMP player = event.getPlayer();
        if (input.equals(code) && player.isEntityAlive()) {
            World world = player.getEntityWorld();
            EntityNKAgent spawn = new EntityNKAgent(world);
            BlockPos playerPos = player.getPosition();
            if (WyrmVariables.WorldVariables.get(world).pastDistressCall == 0) {
                WyrmVariables.WorldVariables.get(world).pastDistressCall = 1;
                spawn.setLocationAndAngles(playerPos.getX() + 0.5, playerPos.getY() + 10, playerPos.getZ() + 0.5, world.rand.nextFloat() * 360F, 0.0F);
                if (!world.isRemote) world.spawnEntity(spawn);
                world.addWeatherEffect(new EntityLightningBolt(spawn.world, spawn.posX, spawn.posY, spawn.posZ, true));
                if (player.getName().equals("Vetpetmon"))
                    player.sendMessage(new TextComponentString(ChatUtils.PURPLE + "<???> I've got your back."));
                else if (!WyrmVariables.WorldVariables.get(world).invasionStarted)
                    player.sendMessage(new TextComponentString(ChatUtils.PURPLE + "<???> ∴⍑ᔑℸ ̣ ℸ ̣ ⍑ᒷǁ∷ᒷ リ\uD835\uDE79ℸ ̣ ⍑ᒷ∷ᒷ ǁᒷℸ ̣ ∴⍑ᔑℸ ̣ ⟍̅\uD835\uDE79 ǁ\uD835\uDE79⚍ ∴ᔑリℸ ̣ "));
                else if (InvasionPoints.getDifficulty(world) == 1.0)
                    player.sendMessage(new TextComponentString(ChatUtils.PURPLE + "<???> ∷ᒷᔑꖎꖎǁ ǁ\uD835\uDE79⚍ リᒷᒷ⟍̅ ᒲǁ ⍑ᒷꖎ!¡ ℸ ̣ ⍑¦ᓭ ᒷᔑ∷ꖎǁ ǁ\uD835\uDE79⚍ ᓭ⍑\uD835\uDE79⚍ꖎ⟍̅ ¦リ⍊ᒷᓭℸ ̣ ¦リ ᓭ\uD835\uDE79ᒲᒷ ℸ ̣ ∷ᔑ!¡ᓭ."));
                WyrmVariables.WorldVariables.get(world).distressCode = WyrmVariables.WorldVariables.generateDistressCode(new Random(), "_...", 20);
                WyrmVariables.WorldVariables.get(world).syncData(world);
                event.setCanceled(true); //HIDE THE PLAYER'S MESSAGE!
            }
        }
    }
}
