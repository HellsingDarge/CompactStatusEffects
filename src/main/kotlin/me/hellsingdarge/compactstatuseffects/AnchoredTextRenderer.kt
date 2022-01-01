package me.hellsingdarge.compactstatuseffects

import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack

class AnchoredTextRenderer(private val textRenderer: TextRenderer)
{
    // same as TextRenderer.draw, but here for completeness
    fun drawLeftAlign(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false, fontSize: Int = textRenderer.fontHeight)
    {
        drawAnchor(matrixStack, xPivot, yPivot, 0xFF0000)

        draw(matrixStack, text, xPivot, yPivot - fontSize, colour, withShadow, fontSize)
    }

    fun drawCentreAlign(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false, fontSize: Int = textRenderer.fontHeight)
    {
        drawAnchor(matrixStack, xPivot, yPivot, 0x00FF00)

        val xPos = xPivot - textRenderer.getWidth(text) / 2 * fontSize / textRenderer.fontHeight
        draw(matrixStack, text, xPos, yPivot - fontSize, colour, withShadow, fontSize)
    }

    fun drawRightAlign(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false, fontSize: Int = textRenderer.fontHeight)
    {
        drawAnchor(matrixStack, xPivot, yPivot, 0x0000FF)

        val xPos = xPivot - textRenderer.getWidth(text) * fontSize / textRenderer.fontHeight
        draw(matrixStack, text, xPos, yPivot - fontSize, colour, withShadow, fontSize)
    }

    private fun draw(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false, fontSize: Int = textRenderer.fontHeight)
    {
        drawAnchor(matrixStack, xPivot, yPivot, 0xFFFFFF)

        val fontScale = fontSize / textRenderer.fontHeight.toFloat()
        val vertexConsumer = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)

        matrixStack.push()
        matrixStack.scale(fontScale, fontScale, 1f)

        textRenderer.draw(
            text,
            xPivot / fontScale,
            yPivot / fontScale,
            colour,
            withShadow,
            matrixStack.peek().positionMatrix,
            vertexConsumer,
            false,
            0,
            0xF00F0,
            textRenderer.isRightToLeft
        )
        vertexConsumer.draw()

        matrixStack.pop()
    }

    fun drawAnchor(matrixStack: MatrixStack, x: Float, y: Float, colour: Int)
    {
        if (false)
        {
            val size = 1
            val length = 5
            val debugColour = (0.6f * 255).toInt().shl(24) + colour
            // Horizontal
            DrawableHelper.fill(matrixStack, (x - length).toInt(), (y - size).toInt(), (x + length).toInt(), (y + size).toInt(), debugColour)
            // Vertical
            DrawableHelper.fill(matrixStack, (x - size).toInt(), (y - length).toInt(), (x + size).toInt(), (y + length).toInt(), debugColour)

            val mc = MinecraftClient.getInstance()
            val mouse = mc.mouse
            val mouseX = mouse.x * mc.window.scaledWidth / mc.window.width
            val mouseY = mouse.y * mc.window.scaledHeight / mc.window.height

            if (mouseX in (x - length)..(x + length) && mouseY in (y - length)..(y + length))
                drawCoords(matrixStack, x - length - 1, y + length + 1)
        }
    }

    private fun drawCoords(matrixStack: MatrixStack, x: Float, y: Float)
    {
        val fontSize = 5
        val fontScale = fontSize / textRenderer.fontHeight.toFloat()
        val text = "(%.1f, %.1f)".format(x, y)
        val xPos = x - textRenderer.getWidth(text) * fontScale
        val vertexConsumer = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)
        matrixStack.push()
        matrixStack.scale(fontScale, fontScale, 1f)
        textRenderer.draw(
            text,
            xPos / fontScale,
            y / fontScale,
            0xAAAAAA,
            true,
            matrixStack.peek().positionMatrix,
            vertexConsumer,
            false,
            0,
            0xF00F0,
            false
        )
        vertexConsumer.draw()
        matrixStack.pop()
    }
}