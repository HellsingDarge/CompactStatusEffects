package me.hellsingdarge.compactstatuseffects

import me.hellsingdarge.compactstatuseffects.config.ModConfig
import me.hellsingdarge.compactstatuseffects.modules.DrawModule
import me.hellsingdarge.compactstatuseffects.modules.NoName
import me.hellsingdarge.compactstatuseffects.modules.NoSprite
import me.hellsingdarge.compactstatuseffects.modules.OnlyName
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

class CustomEffectsDisplay(
    private val matrixStack: MatrixStack,
    private val uiX: Int,
    private val uiY: Int,
    private val backgroundWidth: Int,
    private val statusEffects: Iterable<StatusEffectInstance>
)
{
    fun draw()
    {
        val mode: DrawModule = when (AutoConfig.getConfigHolder(ModConfig::class.java).config.module)
        {
            ModConfig.Module.NO_NAME -> NoName(matrixStack, uiX, uiY, backgroundWidth, statusEffects)
            ModConfig.Module.NO_SPRITE -> NoSprite(matrixStack, uiX, uiY, backgroundWidth, statusEffects)
            ModConfig.Module.ONLY_NAME -> OnlyName(matrixStack, uiX, uiY, backgroundWidth, statusEffects)
        }

        mode.draw()
    }
}