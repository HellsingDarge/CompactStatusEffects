package me.hellsingdarge.compactstatuseffects.modules

import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack

object Util
{
    private val textRenderer: TextRenderer by lazy { MinecraftClient.getInstance().textRenderer }

    // same as TextRenderer.draw, but here for completeness
    fun drawLeftAlign(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false)
    {
        val vertexConsumer = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)
        textRenderer.draw(
            text,
            xPivot,
            yPivot,
            colour,
            withShadow,
            matrixStack.peek().model,
            vertexConsumer,
            false,
            0xF7F7F7,
            0xF000F0,
            textRenderer.isRightToLeft
        )
        vertexConsumer.draw()
    }

    fun drawCentreAlign(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false)
    {
        val vertexConsumer = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)
        val xPos = xPivot - textRenderer.getWidth(text) / 2
        textRenderer.draw(
            text,
            xPos,
            yPivot,
            colour,
            withShadow,
            matrixStack.peek().model,
            vertexConsumer,
            false,
            0,
            0xF000F0,
            textRenderer.isRightToLeft
        )
        vertexConsumer.draw()
    }

    fun drawRightAlign(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false)
    {
        val vertexConsumer = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)
        val xPos = xPivot - textRenderer.getWidth(text)
        textRenderer.draw(
            text,
            xPos,
            yPivot,
            colour,
            withShadow,
            matrixStack.peek().model,
            vertexConsumer,
            false,
            0,
            0xF000F0,
            textRenderer.isRightToLeft
        )
        vertexConsumer.draw()
    }
}