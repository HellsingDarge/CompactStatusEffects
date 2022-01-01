package me.hellsingdarge.compactstatuseffects.modules

import me.hellsingdarge.compactstatuseffects.Utils
import net.minecraft.client.resource.language.I18n
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

class NoName(matrixStack: MatrixStack, uiX: Int, uiY: Int, bgWidth: Int, effects: Iterable<StatusEffectInstance>, isRecipeBookOpen: Boolean):
    DrawModule(matrixStack, uiX, uiY, bgWidth, effects, isRecipeBookOpen)
{
    override val width: Int get() = Constants.NoName.width
    override val height: Int get() = Constants.NoName.height
    override val config = modConfig.noName
    override val yIncrement = if (!config.squash) height else height - 17
    override val maxNum = if (!config.squash) config.effectsPerColumn else config.effectsPerColumn + 3

    override fun draw()
    {
        drawBackground { x, y ->
            drawTexture(matrixStack, x - xOffset, y, 0, 0, width, height)
        }

        drawSprite { x, y, sprite ->
            drawSprite(matrixStack, x + 8 - xOffset, y + 7, zOffset, 18, 18, sprite)
        }

        drawDescription { x, y, instance ->
            if (config.squash)
                textRenderer.drawCentreAlign(matrixStack, Utils.effectDurationToStr(instance), x + 17f - xOffset, y + 10f, 0x7F7F7F, true)
            else
                textRenderer.drawCentreAlign(matrixStack, Utils.effectDurationToStr(instance), x + 17f - xOffset, y + 36f, 0x7F7F7F, true)

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

                if (config.squash)
                    textRenderer.drawRightAlign(matrixStack, level, x + 30f - xOffset, y + 10f, withShadow = true)
                else
                    textRenderer.drawRightAlign(matrixStack, level, x + 30f - xOffset, y + 27f, withShadow = true)
            }

            onHover(x, y) { mouseX, mouseY ->
                val effectName = instance.getName()

                textRenderer.drawLeftAlign(matrixStack, effectName, mouseX.toFloat(), mouseY.toFloat(), withShadow = true)
            }
        }
    }
}