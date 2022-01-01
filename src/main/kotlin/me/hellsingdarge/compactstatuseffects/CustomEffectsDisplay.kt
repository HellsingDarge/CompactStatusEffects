package me.hellsingdarge.compactstatuseffects

import me.hellsingdarge.compactstatuseffects.config.ModConfig
import me.hellsingdarge.compactstatuseffects.modules.DrawModule
import me.hellsingdarge.compactstatuseffects.modules.NoName
import me.hellsingdarge.compactstatuseffects.modules.NoSprite
import me.hellsingdarge.compactstatuseffects.modules.OnlyName
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

class CustomEffectsDisplay
{
    fun draw(matrixStack: MatrixStack, uiX: Int, uiY: Int, backgroundWidth: Int, statusEffects: Iterable<StatusEffectInstance>, isRecipeBookOpen: Boolean)
    {
        val mode: DrawModule = when (AutoConfig.getConfigHolder(ModConfig::class.java).config.module)
        {
            ModConfig.Module.NO_NAME -> NoName(matrixStack, uiX, uiY, backgroundWidth, statusEffects, isRecipeBookOpen)
            ModConfig.Module.NO_SPRITE -> NoSprite(matrixStack, uiX, uiY, backgroundWidth, statusEffects, isRecipeBookOpen)
            ModConfig.Module.ONLY_NAME -> OnlyName(matrixStack, uiX, uiY, backgroundWidth, statusEffects, isRecipeBookOpen)
        }

        mode.draw()
    }
}