package com.github.hellsingdarge.compactstatuseffects.drawModules

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffectUtil
import net.minecraft.util.Identifier

class Boxed(matrixStack: MatrixStack, minecraft: MinecraftClient, x: Int, y: Int, offset: Int, effects: Iterable<StatusEffectInstance>):
        DrawModule(matrixStack, minecraft, x, y, offset, effects)
{
    private val BOX_TEXTURE = Identifier("compactstatuseffects:textures/atlas.png")
    private val xOffset = 35
    private val xIncrement = 30

    override fun drawBackground()
    {
        minecraft.textureManager.bindTexture(BOX_TEXTURE)
        var i = y
        var j = x

        repeat(effects.count()) { index ->
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)
            drawTexture(matrixStack, j - 40, i, 0, 14, 31, 52)

            i += yIncrement
            j = x - ((index + 1) / 4) * xIncrement
            if ((index + 1) % 4 == 0) i = y
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
            drawSprite(matrixStack, j + 2 - xOffset, i + 7, zOffset, 18, 18, sprite)

            i += yIncrement
            j = x - ((index + 1) / 4) * xIncrement
            if ((index + 1) % 4 == 0) i = y
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
                fontRenderer.drawWithShadow(matrixStack, "\u221e", j - xOffset + 7f, i + 27f, 0x7F7F7F)
            }
            else
            {
                if (duration.length == 5)
                {
                    fontRenderer.drawWithShadow(matrixStack, duration, j - 2f - xOffset, i + 27f, 0x7F7F7F)
                }
                else
                {
                    fontRenderer.drawWithShadow(matrixStack, duration, j + 1f - xOffset, i + 27f, 0x7F7F7F)
                }
            }

            i += yIncrement
            j = x - ((index + 1) / 4) * xIncrement
            if ((index + 1) % 4 == 0) i = y
        }
    }
}