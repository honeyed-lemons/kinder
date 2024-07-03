package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class KinderSounds {

    public static SoundEvent INCUBATOR_SOUND = SoundEvent.createVariableRangeEvent(new ResourceLocation(KinderMod.MOD_ID, "incubator"));
    public static SoundEvent INCUBATOR_DONE_SOUND = SoundEvent.createVariableRangeEvent(new ResourceLocation(KinderMod.MOD_ID, "incubator_done"));

    public static void registerSounds() {
        Registry.register(BuiltInRegistries.SOUND_EVENT, new ResourceLocation(KinderMod.MOD_ID, "incubator"), INCUBATOR_SOUND);
        Registry.register(BuiltInRegistries.SOUND_EVENT, new ResourceLocation(KinderMod.MOD_ID, "incubator_done"), INCUBATOR_DONE_SOUND);

    }
}
