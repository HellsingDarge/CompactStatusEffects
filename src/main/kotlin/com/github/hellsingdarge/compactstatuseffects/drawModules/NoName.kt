package com.github.hellsingdarge.compactstatuseffects.drawModules

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.resource.language.I18n
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffectUtil

class NoName(matrixStack: MatrixStack, x: Int, y: Int, effects: Iterable<StatusEffectInstance>):
        DrawModule(matrixStack, x, y, effects)
{
    private val config = modConfig.noName
    private val xOffset = 32 + config.margin
    private val xIncrement = 32
    private val yIncrement = 41
    private val maxNum = config.effectsPerColumn

    override fun drawBackground() {
        textureManager.bindTexture(backgroundTexture)
        var i = x
        var j = y

        repeat(effects.count()) { index ->
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)
            drawTexture(matrixStack, i - xOffset, j, 0, 0, 33, 41)

            i = x - ((index + 1) / maxNum) * xIncrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = y
        }
    }

    override fun drawSprite()
    {
        var i = y
        var j = x

        effects.forEachIndexed { index, instance ->
            val effect = instance.effectType
            val sprite = spriteManager.getSprite(effect)
            textureManager.bindTexture(sprite.atlas.id)
            drawSprite(matrixStack, j + 8 - xOffset, i + 7, zOffset, 18, 18, sprite)

            j = x - ((index + 1) / maxNum) * xIncrement
            i += yIncrement
            if ((index + 1) % maxNum == 0) i = y
        }
    }

    override fun drawDescription()
    {
        var i = x
        var j = y

        effects.forEachIndexed { index, instance ->
            val duration = StatusEffectUtil.durationToString(instance, 1.0f)

            if (instance.isPermanent)
            {
                Util.drawCentreAlign(matrixStack, "∞", i + 17f - xOffset, j + 27f, 0x7F7F7F)
            }
            else
            {
                Util.drawCentreAlign(matrixStack, duration, i + 17f - xOffset, j + 27f, 0x7F7F7F, true)
            }

            if (config.showLevel && instance.amplifier in 1..9)
            {
                val level: String = if (config.levelInArabic)
                {
                    (instance.amplifier + 1).toString()
                }
                else
                {
                    I18n.translate("enchantment.level." + (instance.amplifier + 1))
                }

                Util.drawRightAlign(matrixStack, level, i + 30f - xOffset, j + 18f, withShadow = true)
            }

            i = x - ((index + 1) / maxNum) * xIncrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = y
        }
    }
}