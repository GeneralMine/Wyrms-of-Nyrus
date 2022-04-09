package com.vetpetmon.wyrmsofnyrus;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class SoundRegistry {

    //public static List<SoundEvent> ALL_SOUNDS = new ArrayList<SoundEvent>();

    public static SoundEvent wyrmClicks;
    public static SoundEvent wyrmHissTwo;

    public static void RegisterSounds() {
        wyrmClicks = RegisterSound("entity.wyrmClicks");
        wyrmHissTwo = RegisterSound("entity.hiss2");
    }

    public static SoundEvent RegisterSound(String name) {
        ResourceLocation location = new ResourceLocation(wyrmsofnyrus.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
