package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class KinderSounds {

    public static SoundEvent INCUBATOR_SOUND = SoundEvent.of (new Identifier (KinderMod.MOD_ID, "incubator"));

    public static void registerSounds (){
        Registry.register (Registries.SOUND_EVENT, new Identifier (KinderMod.MOD_ID, "incubator"), INCUBATOR_SOUND);
    }
}
