package com.github.hellsingdarge.compactstatuseffects.drawModules

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffectUtil

class NoName(matrixStack: MatrixStack, minecraft: MinecraftClient, x: Int, y: Int, effects: Iterable<StatusEffectInstance>):
        DrawModule(matrixStack, minecraft, x, y, effects)
{
    private val config = modConfig.noNameConfig
    private var xOffset = 32 + modConfig.margin
    private var xIncrement = 32
    private var yIncrement = 41
    private var maxNum = config.maxEffectsNumber

    override fun drawBackground()
    {
        minecraft.textureManager.bindTexture(BACKGROUND_TEXTURE)
        var i = y
        var j = x

        repeat(effects.count()) { index ->
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)
            drawTexture(matrixStack, j - xOffset, i, 0, 0, 33, 41)

            i += yIncrement
            j = x - ((index + 1) / maxNum) * xIncrement
            if ((index + 1) % maxNum == 0) i = y
        }
    }

    override fun drawSprite()
    {
        var i = y
        var j = x

        effects.forEachIndexed { index, instance ->
            val effect = instance.effectType
            val sprite = spriteManager.getSprite(effect)
            minecraft.textureManager.bindTexture(sprite.atlas.id)
            drawSprite(matrixStack, j + 8 - xOffset, i + 7, zOffset, 18, 18, sprite)

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
            val duration = StatusEffectUtil.durationToString(instance, 1.0f)

            if (instance.isPermanent)
            {
                // infinity symbol instead of "**:**"
                fontRenderer.drawWithShadow(matrixStack, "\u221e", j + 13f - xOffset, i + 27f, 0x7F7F7F)
            }
            else
            {
                if (duration.length == 5)
                {
                    fontRenderer.drawWithShadow(matrixStack, duration, j + 4f - xOffset, i + 27f, 0x7F7F7F)
                }
                else
                {
                    fontRenderer.drawWithShadow(matrixStack, duration, j + 7f - xOffset, i + 27f, 0x7F7F7F)
                }
            }

            i += yIncrement
            j = x - ((index + 1) / maxNum) * xIncrement
            if ((index + 1) % maxNum == 0) i = y
        }
    }
}