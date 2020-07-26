package com.github.hellsingdarge.compactstatuseffects.mixins;


import com.github.hellsingdarge.compactstatuseffects.CustomEffectsDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

@Mixin(AbstractInventoryScreen.class)
public class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T>
{
    @Shadow
    protected boolean drawStatusEffects;

    public AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, title);
    }

    @Overwrite
    public void drawStatusEffects(MatrixStack matrixStack)
    {
        CustomEffectsDisplay customEffectsDisplay = new CustomEffectsDisplay(matrixStack, client, x, y, client.player.getStatusEffects());
        customEffectsDisplay.draw();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY)
    {

    }
}
