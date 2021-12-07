package me.hellsingdarge.compactstatuseffects.mixins;


import me.hellsingdarge.compactstatuseffects.CustomEffectsDisplay;
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

//    @Redirect(method = "applyStatusEffectOffset", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;x:I", opcode = Opcodes.PUTFIELD, ordinal = 1))
//    void redirectOffset(AbstractInventoryScreen abstractInventoryScreen, int value)
//    {
//        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
//        ModConfig.Module module = config.getModule();
//
//        int offset = switch (module)
//                {
//                    case NO_NAME -> config.getNoName().getUiOffset() * 20;
//                    case NO_SPRITE -> config.getNoSprite().getUiOffset() * 20;
//                    case ONLY_NAME -> config.getOnlyName().getUiOffset() * 20;
//                };
//
//        this.x = (this.width - this.backgroundWidth) / 2 + offset;
//    }

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
}
