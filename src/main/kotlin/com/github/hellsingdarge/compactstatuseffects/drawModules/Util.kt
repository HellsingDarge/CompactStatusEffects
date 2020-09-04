package com.github.hellsingdarge.compactstatuseffects.drawModules

import net.minecraft.client.font.TextRenderer
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack

object Util
{
    // same as TextRenderer.draw, but here for completeness
    fun drawLeftAlign(textRenderer: TextRenderer, matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, withShadow: Boolean = false)
    {
        val vertexConsumer = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)
        textRenderer.draw(
                text,
                xPivot,
                yPivot,
                0xFFFFFF,
                withShadow,
                matrixStack.peek().model,
                vertexConsumer,
                false,
                0xF7F7F7,
                0,
                textRenderer.isRightToLeft
        )
        vertexConsumer.draw()
    }

    fun drawCentreAlign(textRenderer: TextRenderer, matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, withShadow: Boolean = false)
    {
        val vertexConsumer = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)
        val xPos = xPivot - textRenderer.getWidth(text) / 2
        textRenderer.draw(
                text,
                xPos,
                yPivot,
                0xFFFFFF,
                withShadow,
                matrixStack.peek().model,
                vertexConsumer,
                false,
                0,
                0,
                textRenderer.isRightToLeft
        )
        vertexConsumer.draw()
    }

    fun drawRightAlign(textRenderer: TextRenderer, matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, withShadow: Boolean = false)
    {
        val vertexConsumer = VertexConsumerProvider.immediate(Tessellator.getInstance().buffer)
        val xPos = xPivot - textRenderer.getWidth(text)
        textRenderer.draw(
                text,
                xPos,
                yPivot,
                0xFFFFFF,
                withShadow,
                matrixStack.peek().model,
                vertexConsumer,
                false,
                0,
                0,
                textRenderer.isRightToLeft
        )
        vertexConsumer.draw()
    }
}