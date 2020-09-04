package com.github.hellsingdarge.compactstatuseffects.drawModules

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.resource.language.I18n
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

class OnlyName(matrixStack: MatrixStack, x: Int, y: Int, effects: Iterable<StatusEffectInstance>):
        DrawModule(matrixStack, x, y, effects)
{
    private val config = modConfig.onlyNameConfig
    private val xOffset = 99 + modConfig.margin
    private val xIncrement = 99
    private val yIncrement = 19
    private val maxNum = config.maxEffectsNumber

    override fun drawBackground()
    {
        textureManager.bindTexture(BACKGROUND_TEXTURE)
        var i = y
        var j = x

        repeat(effects.count()) { index ->
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)
            drawTexture(matrixStack, j - xOffset, i, 0, 41, 100, 19)

            i += yIncrement
            j = x - ((index + 1) / maxNum) * xIncrement
            if ((index + 1) % maxNum == 0) i = y
        }
    }

    override fun drawDescription()
    {
        var i = y
        var j = x

        effects.forEachIndexed { index, instance ->
            val colour = when
            {
                instance.isPermanent ->
                {
                    config.permanentColour
                }
                instance.duration < config.expiringBound * 20 ->
                {
                    config.expiringColour
                }
                instance.duration in (config.expiringBound * 20)..(config.soonToExpireBound * 20) ->
                {
                    config.soonToExpireColour
                }
                else ->
                {
                    config.wontExpireSoonColour
                }
            }

            var effectName = I18n.translate(instance.effectType.translationKey, *arrayOfNulls(0))

            if (instance.amplifier in 1..9)
            {
                effectName = effectName + ' ' + I18n.translate("enchantment.level.${instance.amplifier + 1}", *arrayOfNulls(0))
            }

            Util.drawLeftAlign(matrixStack, effectName, j + 5f - xOffset, i + 6f, colour, true)

            i += yIncrement
            j = x - ((index + 1) / maxNum) * xIncrement
            if ((index + 1) % maxNum == 0) i = y
        }
    }
}