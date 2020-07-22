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

    override fun drawBackground()
    {
        minecraft.textureManager.bindTexture(BOX_TEXTURE)
        var i = y

        repeat(effects.count()) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)
            drawTexture(matrixStack, x - 40, i, 0, 14, 31, 52)
            i += offset
        }
    }

    override fun drawSprite()
    {
        var i = y

        effects.forEach {
            val effect = it.effectType
            val sprite = spriteManager.getSprite(effect)
            minecraft.textureManager.bindTexture(sprite.atlas.id)
            drawSprite(matrixStack, x + 7 - 40, i + 7, zOffset, 18, 18, sprite)
            i += offset
        }
    }

    override fun drawDescription()
    {
        var i = y

        effects.forEach { instance ->
            val duration = StatusEffectUtil.durationToString(instance, 1.0f)
//            fontRenderer.drawWithShadow(matrixStack, "\u221e", x + 12f, i + 20f, 0x7F7F7F)
            fontRenderer.drawWithShadow(matrixStack, duration, x + 12f - 46, i + 27f, 0x7F7F7F)
            i += offset
        }
    }
}