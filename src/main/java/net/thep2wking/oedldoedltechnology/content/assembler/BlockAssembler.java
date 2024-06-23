package net.thep2wking.oedldoedltechnology.content.assembler;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.thep2wking.oedldoedlcore.util.ModToolTypes;
import net.thep2wking.oedldoedltechnology.api.factory.BlockFactoryBase;
import net.thep2wking.oedldoedltechnology.util.handler.GuiHandler;

public class BlockAssembler extends BlockFactoryBase {
	public BlockAssembler(String modid, String name, CreativeTabs tab, Material material, SoundType sound,
			MapColor mapColor, int harvestLevel, ModToolTypes toolType, float hardness, float resistance,
			int lightLevel) {
		super(modid, name, tab, material, sound, mapColor, harvestLevel, toolType, hardness, resistance, lightLevel);
		GameRegistry.registerTileEntity(TileAssembler.class, this.getRegistryName());
	}

	@Override
	public TileEntity getTileEntity() {
		return new TileAssembler();
	}

	@Override
	protected int getGuiId() {
		return GuiHandler.ASSEMBLER;
	}
}