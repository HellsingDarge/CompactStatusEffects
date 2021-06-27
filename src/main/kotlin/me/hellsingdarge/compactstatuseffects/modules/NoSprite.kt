package me.hellsingdarge.compactstatuseffects.modules

import com.mojang.blaze3d.systems.RenderSystem
import me.hellsingdarge.compactstatuseffects.TextRendererHelper
import me.hellsingdarge.compactstatuseffects.Utils
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

class NoSprite(matrixStack: MatrixStack, x: Int, y: Int, effects: Iterable<StatusEffectInstance>):
    DrawModule(matrixStack, x, y, effects)
{
    override val width: Int
        get() = 100
    override val height: Int
        get() = 31
    override val config = modConfig.noSprite
    override val xOffset = width + config.margin - 1
    override val xDecrement = width - 1
    override val yIncrement = if (!config.squash) height else height - 7
    override val maxNum = if (!config.squash) config.effectsPerColumn else config.effectsPerColumn + 1

    override fun drawBackground()
    {
        textureManager.bindTexture(backgroundTexture)
        var i = x
        var j = y

        repeat(effects.count()) { index ->
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)
            drawTexture(matrixStack, i - xOffset, j, 0, 60, width, height)

            i = x - ((index + 1) / maxNum) * xDecrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = y
        }
    }

    override fun drawDescription()
    {
        var i = x
        var j = y

        effects.forEachIndexed { index, instance ->
            val effectName = instance.getName()

            TextRendererHelper.drawLeftAlign(matrixStack, effectName, i + 5f - xOffset, j + 15f, 0xFFFFFF, true)
            TextRendererHelper.drawLeftAlign(matrixStack, Utils.effectDurationToStr(instance), i + 5f - xOffset, j + 25f, 0x7F7F7F, true)

            i = x - ((index + 1) / maxNum) * xDecrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = y
        }
    }
}