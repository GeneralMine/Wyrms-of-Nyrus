package com.vetpetmon.wyrmsofnyrus;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundRegistry {

    public static SoundEvent wyrmClicks;
    public static SoundEvent wyrmHissTwo;
    public static SoundEvent theVisitor;
    public static SoundEvent creepSpread;
    public static SoundEvent wyrmSteps;

    public static void RegisterSounds() {
        wyrmClicks = RegisterSound("entity.wyrmClicks");
        wyrmHissTwo = RegisterSound("entity.hiss2");
        theVisitor = RegisterSound("visitormessage");
        creepSpread = RegisterSound("entity.hiveCreepSpread");
        wyrmSteps = RegisterSound("entity.wyrmSteps");
    }

    public static SoundEvent RegisterSound(String name) {
        ResourceLocation location = new ResourceLocation(wyrmsofnyrus.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
