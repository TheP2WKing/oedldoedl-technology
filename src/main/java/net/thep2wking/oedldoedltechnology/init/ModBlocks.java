package net.thep2wking.oedldoedltechnology.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.thep2wking.oedldoedlcore.util.ModToolTypes;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.api.projectpart.ModBlockProjectPartBase;
import net.thep2wking.oedldoedltechnology.content.assembler.BlockAssembler;
import net.thep2wking.oedldoedltechnology.content.block.BlockPowerSlug;
import net.thep2wking.oedldoedltechnology.content.constructor.BlockConstructor;
import net.thep2wking.oedldoedltechnology.util.ModPowerSlugColor;

public class ModBlocks {
	public static final Block CONSTRUCTOR = new BlockConstructor(OedldoedlTechnology.MODID, "constructor", OedldoedlTechnology.TAB, Material.IRON, SoundType.METAL, MapColor.IRON, 1, ModToolTypes.PICKAXE, 5.0F, 5.0F, 0);

	public static final Block ASSEMBLER = new BlockAssembler(OedldoedlTechnology.MODID, "assembler", OedldoedlTechnology.TAB, Material.IRON, SoundType.METAL, MapColor.IRON, 1, ModToolTypes.PICKAXE, 5.0F, 5.0F, 0);
	
	public static final Block BLUE_POWER_SLUG = new BlockPowerSlug(OedldoedlTechnology.MODID, "blue_power_slug", OedldoedlTechnology.TAB, ModPowerSlugColor.BLUE, Material.CLAY, SoundType.SLIME, MapColor.BLUE_STAINED_HARDENED_CLAY, 0, ModToolTypes.NO_TOOL, 0.5F, 0.5F, 8);
	public static final Block YELLOW_POWER_SLUG = new BlockPowerSlug(OedldoedlTechnology.MODID, "yellow_power_slug", OedldoedlTechnology.TAB, ModPowerSlugColor.YELLOW, Material.CLAY, SoundType.SLIME, MapColor.YELLOW_STAINED_HARDENED_CLAY, 0, ModToolTypes.NO_TOOL, 0.5F, 0.5F, 8);
	public static final Block PURPLE_POWER_SLUG = new BlockPowerSlug(OedldoedlTechnology.MODID, "purple_power_slug", OedldoedlTechnology.TAB, ModPowerSlugColor.PURPLE, Material.CLAY, SoundType.SLIME, MapColor.PURPLE_STAINED_HARDENED_CLAY, 0, ModToolTypes.NO_TOOL, 0.5F, 0.5F, 8);

	public static final Block MODULAR_FRAME = new ModBlockProjectPartBase(OedldoedlTechnology.MODID, "modular_frame", OedldoedlTechnology.TAB, Material.IRON, SoundType.METAL, MapColor.IRON, 0, ModToolTypes.PICKAXE, 3.0f, 3.0f, 0);
	public static final Block HEAVY_MODULAR_FRAME = new ModBlockProjectPartBase(OedldoedlTechnology.MODID, "heavy_modular_frame", OedldoedlTechnology.TAB, Material.IRON, SoundType.METAL, MapColor.OBSIDIAN, 0, ModToolTypes.PICKAXE, 5.0f, 5.0f, 0);
}