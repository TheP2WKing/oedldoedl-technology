package net.thep2wking.oedldoedltechnology.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.thep2wking.oedldoedlcore.util.ModToolTypes;
import net.thep2wking.oedldoedltechnology.OedldoedlTechnology;
import net.thep2wking.oedldoedltechnology.content.assembler.BlockAssembler;
import net.thep2wking.oedldoedltechnology.content.constructor.BlockConstructor;

public class ModBlocks {
	public static final Block CONSTRUCTOR = new BlockConstructor(OedldoedlTechnology.MODID, "constructor", OedldoedlTechnology.TAB, Material.IRON, SoundType.METAL, MapColor.IRON, 1, ModToolTypes.PICKAXE, 5.0F, 5.0F, 0);

	public static final Block ASSEMBLER = new BlockAssembler(OedldoedlTechnology.MODID, "assembler", OedldoedlTechnology.TAB, Material.IRON, SoundType.METAL, MapColor.IRON, 1, ModToolTypes.PICKAXE, 5.0F, 5.0F, 0);
}