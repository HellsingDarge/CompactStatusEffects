package com.github.hellsingdarge.compactstatuseffects.drawModules

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.resource.language.I18n
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffectUtil

class NoSprite(matrixStack: MatrixStack, minecraft: MinecraftClient, x: Int, y: Int, effects: Iterable<StatusEffectInstance>):
        DrawModule(matrixStack, minecraft, x, y, effects)
{
    private val config = modConfig.noSpriteConfig
    private val xOffset = 110
    private val xIncrement = 99
    private val yIncrement = 31
    private val maxNum = config.maxEffectsNumber

    override fun drawBackground()
    {
        minecraft.textureManager.bindTexture(BACKGROUND_TEXTURE)
        var i = y
        var j = x

        repeat(effects.count()) { index ->
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)
            drawTexture(matrixStack, j - xOffset, i, 0, 60, 100, 31)

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
            var effectName = I18n.translate(instance.effectType.translationKey, *arrayOfNulls(0))

            if (instance.amplifier in 1..9)
            {
                effectName = effectName + ' ' + I18n.translate("enchantment.level." + (instance.amplifier + 1), *arrayOfNulls(0))
            }

            fontRenderer.drawWithShadow(matrixStack, effectName, j + 6f - xOffset, (i + 6).toFloat(), 0xFFFFFF)
            val duration = StatusEffectUtil.durationToString(instance, 1.0f)

            if (instance.isPermanent)
            {
                // infinity symbol instead of "**:**"
                fontRenderer.drawWithShadow(matrixStack, "\u221e", j + 5f - xOffset, i + 16f, 0x7F7F7F)
            }
            else
            {
                fontRenderer.drawWithShadow(matrixStack, duration, j + 5f - xOffset, i + 16f, 0x7F7F7F)
            }

            i += yIncrement
            j = x - ((index + 1) / maxNum) * xIncrement
            if ((index + 1) % maxNum == 0) i = y
        }
    }
}