package com.github.hellsingdarge.compactstatuseffects.drawModules

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.resource.language.I18n
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

class OnlyName(matrixStack: MatrixStack, minecraft: MinecraftClient, x: Int, y: Int, effects: Iterable<StatusEffectInstance>):
        DrawModule(matrixStack, minecraft, x, y, effects)
{
    private val xOffset = 100
    private val xIncrement = 99
    private val yIncrement = 19
    private val maxNum = 7

    override fun drawBackground()
    {
        minecraft.textureManager.bindTexture(BACKGROUND_TEXTURE)
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
                    0xFFFFFF // white
                }
                instance.duration < 400 ->
                {
                    0xD40000 // red
                }
                instance.duration in 400..1200 ->
                {
                    0xD4CD00 // yellow
                }
                else ->
                {
                    0x00B009 // green
                }
            }

            var effectName = I18n.translate(instance.effectType.translationKey, *arrayOfNulls(0))

            if (instance.amplifier in 1..9)
            {
                effectName = effectName + ' ' + I18n.translate("enchantment.level.${instance.amplifier + 1}", *arrayOfNulls(0))
            }

            fontRenderer.drawWithShadow(matrixStack, effectName, j + 5f - xOffset, i + 6f, colour)

            i += yIncrement
            j = x - ((index + 1) / maxNum) * xIncrement
            if ((index + 1) % maxNum == 0) i = y
        }
    }
}