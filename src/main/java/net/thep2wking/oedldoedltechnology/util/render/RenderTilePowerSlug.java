package net.thep2wking.oedldoedltechnology.util.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thep2wking.oedldoedltechnology.content.block.BlockPowerSlug;
import net.thep2wking.oedldoedltechnology.content.block.TilePowerSlug;
import net.thep2wking.oedldoedltechnology.util.ModPowerSlugColor;
import net.thep2wking.oedldoedltechnology.util.model.ModelPowerSlug;

@SideOnly(Side.CLIENT)
public class RenderTilePowerSlug extends TileEntitySpecialRenderer<TilePowerSlug> {
    private final ModelPowerSlug MODEL = new ModelPowerSlug();

    @Override
    public void render(TilePowerSlug te, double x, double y, double z, float partialTicks, int destroyStage,
            float alpha) {
        GlStateManager.pushMatrix();

        // Translate to the correct position
        GlStateManager.translate(x, y, z);

        // Get the facing direction of the block
        EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockPowerSlug.FACING);

        // Get the color of the block
        ModPowerSlugColor color = te.getColor();
        ResourceLocation TEXTURE = new ResourceLocation("oedldoedltechnology:textures/blocks/" + color + "_power_slug.png");

        // Apply transformations based on the facing direction
        switch (facing) {
            case UP:
                GlStateManager.translate(0.5F, 1.5F, 0.5F);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                break;
            case DOWN:
                GlStateManager.translate(0.5F, -0.5F, 0.5F);
                break;
            case NORTH:
                GlStateManager.translate(0.5F, 0.5F, -0.5F);
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                break;
            case SOUTH:
                GlStateManager.translate(0.5F, 0.5F, 1.5F);
                GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                break;
            case WEST:
                GlStateManager.translate(-0.5F, 0.5F, 0.5F);
                GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
                break;
            case EAST:
                GlStateManager.translate(1.5F, 0.5F, 0.5F);
                GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                break;
        }

        // Apply rotation based on tile entity's rotation
        GlStateManager.rotate(te.getRotation(), 0.0F, 1.0F, 0.0F);

        // Bind the texture and render the model
        this.bindTexture(TEXTURE);
        this.MODEL.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        // Pop the matrix to reset transformations
        GlStateManager.popMatrix();
    }
}