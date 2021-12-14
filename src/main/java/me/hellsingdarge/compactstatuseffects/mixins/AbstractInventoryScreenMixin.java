package me.hellsingdarge.compactstatuseffects.mixins;


import me.hellsingdarge.compactstatuseffects.CustomEffectsDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

@SuppressWarnings("rawtypes")
@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T>
{
    public AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, title);
    }

    @Redirect(
            method = "drawStatusEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;drawStatusEffectBackgrounds(Lnet/minecraft/client/util/math/MatrixStack;IILjava/lang/Iterable;Z)V"
            )
    )
    void redirectDrawBackground(AbstractInventoryScreen ais, MatrixStack matrixStack, int x, int height, Iterable<StatusEffectInstance> effects, boolean wide)
    {
        CustomEffectsDisplay customEffectsDisplay = new CustomEffectsDisplay(matrixStack, x, this.y, this.backgroundWidth, effects);
        customEffectsDisplay.draw();
    }

    @Inject(method = "drawStatusEffectSprites", at = @At("HEAD"), cancellable = true)
    void onDrawSprite(MatrixStack matrices, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide, CallbackInfo ci)
    {
        ci.cancel();
    }

    @Inject(method = "drawStatusEffectDescriptions", at = @At("HEAD"), cancellable = true)
    void onDrawDescriptions(MatrixStack matrixStack, int i, int j, Iterable<StatusEffectInstance> iterable, CallbackInfo ci)
    {
        ci.cancel();
    }

    @ModifyConstant(
            method = "drawStatusEffects",
            constant = @Constant(intValue = 33),
            slice = @Slice(from = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;drawStatusEffectSprites(Lnet/minecraft/client/util/math/MatrixStack;IILjava/lang/Iterable;Z)V"
            ))
    )
    int onDrawDescriptionNarrow(int constant)
    {
        return -100000;
    }
}
