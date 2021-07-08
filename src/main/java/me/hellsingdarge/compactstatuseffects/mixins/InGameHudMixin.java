package me.hellsingdarge.compactstatuseffects.mixins;


import com.google.common.collect.Ordering;
import me.hellsingdarge.compactstatuseffects.modules.HudTimer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Collection;

@Mixin(InGameHud.class)
public class InGameHudMixin
{
    @Inject(
            method = "renderStatusEffectOverlay",
            slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V")),
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void afterRun(MatrixStack matrices, CallbackInfo ci, Collection<StatusEffectInstance> collection)
    {
        HudTimer.draw(matrices, Ordering.natural().reverse().sortedCopy(collection));
    }
}
