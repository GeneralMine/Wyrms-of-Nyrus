package com.vetpetmon.wyrmsofnyrus;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundRegistry {

    public static SoundEvent wyrmClicks, deepwyrmclicks;
    public static SoundEvent wyrmSteps, slowwyrmsteps;
    public static SoundEvent wyrmHissTwo, wyrmannoyed;
    public static SoundEvent theVisitor;
    public static SoundEvent creepSpread, creepwyrmscream;
    public static SoundEvent myrmur, myrmurcharge;
    public static SoundEvent biter, bitercharge;
    public static SoundEvent nukeClose, nukeFar;
    public static SoundEvent wyrmroars;
    public static SoundEvent wyrmlingclicks, wyrmlinghurt, wyrmlingdeath;
    public static SoundEvent proberidle;
    public static SoundEvent nkagentidle;
    public static SoundEvent creepedhumanoid, creepedhumanoidroar;
    //Attacks
    public static SoundEvent banishment;

    public static void RegisterSounds() {
        wyrmClicks = RegisterSound("entity.wyrmClicks");
        wyrmHissTwo = RegisterSound("entity.hiss2");
        theVisitor = RegisterSound("visitormessage");
        creepSpread = RegisterSound("entity.hiveCreepSpread");
        wyrmSteps = RegisterSound("entity.wyrmSteps");
        creepwyrmscream = RegisterSound("entity.creepwyrmscream");
        deepwyrmclicks = RegisterSound("entity.deepwyrmclicks");
        slowwyrmsteps = RegisterSound("entity.slowwyrmsteps");
        nukeClose = RegisterSound("supercriticality");
        nukeFar = RegisterSound("supercriticalitydistant");
        wyrmlingclicks = RegisterSound("entity.wyrmlingclicks");
        wyrmlinghurt = RegisterSound("entity.wyrmlinghurt");
        wyrmlingdeath = RegisterSound("entity.wyrmlingdeath");
        wyrmroars = RegisterSound("entity.wyrmroars");
        proberidle = RegisterSound("entity.prober");
        myrmur = RegisterSound("entity.myrmur");
        myrmurcharge = RegisterSound("entity.myrmurcharge");
        biter = RegisterSound("entity.biter");
        bitercharge = RegisterSound("entity.biterroll");
        creepedhumanoid = RegisterSound("entity.creepedhumanoid");
        creepedhumanoidroar = RegisterSound("entity.creepedhumanoidroar");
        nkagentidle = RegisterSound("entity.nkagentidle");
        banishment = RegisterSound("entity.banishment");
        wyrmannoyed = RegisterSound("entity.wyrmannoyed");
    }

    public static SoundEvent RegisterSound(String name) {
        ResourceLocation location = new ResourceLocation(WyrmsOfNyrus.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
