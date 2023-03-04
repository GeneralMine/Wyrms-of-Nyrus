package com.vetpetmon.wyrmsofnyrus.advancements;

import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class Advancements {
    public static Advancement playerroot;
    public static Advancement howdidwegethere,killitwithfire,probed,nottoday,witness,theycandothat;
    public static void init(MinecraftServer serv){
        net.minecraft.advancements.AdvancementManager adv = serv.getAdvancementManager();

        playerroot = adv.getAdvancement(new ResourceLocation(WyrmsOfNyrus.MODID, "root"));
        howdidwegethere = adv.getAdvancement(new ResourceLocation(WyrmsOfNyrus.MODID, "how_did_we_get_here"));
        killitwithfire = adv.getAdvancement(new ResourceLocation(WyrmsOfNyrus.MODID, "killitwithfire"));
        probed = adv.getAdvancement(new ResourceLocation(WyrmsOfNyrus.MODID, "probed"));
        nottoday = adv.getAdvancement(new ResourceLocation(WyrmsOfNyrus.MODID, "nottoday"));
        witness = adv.getAdvancement(new ResourceLocation(WyrmsOfNyrus.MODID, "witness"));
        theycandothat = adv.getAdvancement(new ResourceLocation(WyrmsOfNyrus.MODID, "theycandothat"));
    }
    public static void grantAchievement(EntityPlayerMP player, Advancement a){
        if(a == null){
            WyrmsOfNyrus.logger.error("Tried to give a player a null advancement. Blame Modrome.");
            return;
        }
        for(String s : player.getAdvancements().getProgress(a).getRemaningCriteria()){
            player.getAdvancements().grantCriterion(a, s);
        }
    }
    public static boolean hasAdvancement(EntityPlayer player, Advancement a){
        if(a == null){
            WyrmsOfNyrus.logger.error("Tried to check for an advancement, but no such advancement exists!");
            return false;
        }
        if(player instanceof EntityPlayerMP){
            return ((EntityPlayerMP)player).getAdvancements().getProgress(a).isDone();
        }
        return false;
    }
}
