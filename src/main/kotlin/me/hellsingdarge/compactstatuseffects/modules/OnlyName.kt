package me.hellsingdarge.compactstatuseffects.modules

import me.hellsingdarge.compactstatuseffects.Utils
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

class OnlyName(matrixStack: MatrixStack, uiX: Int, uiY: Int, bgWidth: Int, effects: Iterable<StatusEffectInstance>, isRecipeBookOpen: Boolean):
    DrawModule(matrixStack, uiX, uiY, bgWidth, effects, isRecipeBookOpen)
{
    override val width: Int get() = Constants.OnlyName.width
    override val height: Int get() = Constants.OnlyName.height
    override val config = modConfig.onlyName
    override val yIncrement = if (!config.squash) height else height - 5
    override val maxNum = if (!config.squash) config.effectsPerColumn else config.effectsPerColumn + 3

    override fun draw()
    {
        drawBackground { x, y ->
            drawTexture(matrixStack, x - xOffset, y, 0, 41, width, height)
        }

        drawDescription { x, y, instance ->
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

            textRenderer.drawLeftAlign(matrixStack, effectName, x + 5f - xOffset, y + 15f, colour, true)

            onHover(x, y) { mouseX, mouseY ->
                textRenderer.drawLeftAlign(matrixStack, Utils.effectDurationToStr(instance), mouseX.toFloat(), mouseY.toFloat(), withShadow = true)
            }
        }
    }
}