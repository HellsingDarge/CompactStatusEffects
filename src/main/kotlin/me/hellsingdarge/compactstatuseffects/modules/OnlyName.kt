package me.hellsingdarge.compactstatuseffects.modules

import com.mojang.blaze3d.systems.RenderSystem
import me.hellsingdarge.compactstatuseffects.TextRendererHelper
import me.hellsingdarge.compactstatuseffects.Utils
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

class OnlyName(matrixStack: MatrixStack, x: Int, y: Int, effects: Iterable<StatusEffectInstance>):
    DrawModule(matrixStack, x, y, effects)
{
    override val width: Int
        get() = 100
    override val height: Int
        get() = 19
    override val config = modConfig.onlyName
    override val xOffset = width + config.margin - 1
    override val xDecrement = width - 1
    override val yIncrement = if (!config.squash) height else height - 5
    override val maxNum = if (!config.squash) config.effectsPerColumn else config.effectsPerColumn + 3

    override fun drawBackground()
    {
        textureManager.bindTexture(backgroundTexture)
        var i = x
        var j = y

        repeat(effects.count()) { index ->
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)
            drawTexture(matrixStack, i - xOffset, j, 0, 41, width, height)

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

            val effectName = instance.getName()

            TextRendererHelper.drawLeftAlign(matrixStack, effectName, i + 5f - xOffset, j + 15f, colour, true)

            onHover(i, j) { mouseX, mouseY ->
                TextRendererHelper.drawLeftAlign(matrixStack, Utils.effectDurationToStr(instance), mouseX.toFloat(), mouseY.toFloat(), withShadow = true)
            }

            i = x - ((index + 1) / maxNum) * xDecrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = y
        }
    }
}