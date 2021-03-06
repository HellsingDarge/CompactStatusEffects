package me.hellsingdarge.compactstatuseffects.mixins;


import me.hellsingdarge.compactstatuseffects.CustomEffectsDisplay;
import me.hellsingdarge.compactstatuseffects.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T>
{
    public AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, title);
    }

    @Redirect(method = "drawStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;drawStatusEffectBackgrounds(Lnet/minecraft/client/util/math/MatrixStack;IILjava/lang/Iterable;)V"))
    void drawBackground(AbstractInventoryScreen ais, MatrixStack matrixStack, int i, int j, Iterable<StatusEffectInstance> effects)
    {
        CustomEffectsDisplay customEffectsDisplay = new CustomEffectsDisplay(matrixStack, x, y, effects);
        customEffectsDisplay.draw();
    }

    @Redirect(method = "applyStatusEffectOffset", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;x:I", opcode = Opcodes.PUTFIELD, ordinal = 1))
    void redirectOffset(AbstractInventoryScreen abstractInventoryScreen, int value)
    {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        switch (config.getModule())
        {
            case NO_NAME:
                this.x = (this.width - this.backgroundWidth) / 2 + config.getNoName().getUiOffset() * 20;
                break;
            case NO_SPRITE:
                this.x = (this.width - this.backgroundWidth) / 2 + config.getNoSprite().getUiOffset() * 20;
                break;
            case ONLY_NAME:
                this.x = (this.width - this.backgroundWidth) / 2 + config.getOnlyName().getUiOffset() * 20;
                break;
        }
    }

    @Inject(method = "drawStatusEffectSprites", at = @At("HEAD"), cancellable = true)
    void drawSprite(MatrixStack matrixStack, int i, int j, Iterable<StatusEffectInstance> iterable, CallbackInfo ci)
    {
        ci.cancel();
    }

    @Inject(method = "drawStatusEffectDescriptions", at = @At("HEAD"), cancellable = true)
    void drawDescriptions(MatrixStack matrixStack, int i, int j, Iterable<StatusEffectInstance> iterable, CallbackInfo ci)
    {
        ci.cancel();
    }
}
