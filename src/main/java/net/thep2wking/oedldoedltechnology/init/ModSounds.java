package net.thep2wking.oedldoedltechnology.init;

import net.minecraft.util.SoundEvent;
import net.thep2wking.oedldoedlcore.api.sound.ModSoundEventBase;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;

public class ModSounds {
	public static final SoundEvent RAILGUN_SHOT = new ModSoundEventBase(OedldoedlTechnology.MODID, "railgun_shot");
	public static final SoundEvent RAILGUN_IMPACT = new ModSoundEventBase(OedldoedlTechnology.MODID, "railgun_impact");

	public static final SoundEvent UP_N_ATOMIZER_SHOT = new ModSoundEventBase(OedldoedlTechnology.MODID, "up_n_atomizer_shot");
	public static final SoundEvent UP_N_ATOMIZER_IMPACT = new ModSoundEventBase(OedldoedlTechnology.MODID, "up_n_atomizer_impact");

	public static final SoundEvent REPUBLICAN_SPACE_RANGER_IDLE = new ModSoundEventBase(OedldoedlTechnology.MODID, "republican_space_ranger_idle");
	public static final SoundEvent REPUBLICAN_SPACE_RANGER_DEATH = new ModSoundEventBase(OedldoedlTechnology.MODID, "republican_space_ranger_death");
}