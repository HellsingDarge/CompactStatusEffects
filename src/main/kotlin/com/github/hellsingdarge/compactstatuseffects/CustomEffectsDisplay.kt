package com.github.hellsingdarge.compactstatuseffects

import com.github.hellsingdarge.compactstatuseffects.drawModules.DrawModule
import com.github.hellsingdarge.compactstatuseffects.drawModules.NoSprite
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

class CustomEffectsDisplay(
        private val matrixStack: MatrixStack,
        private val minecraft: MinecraftClient,
        private val x: Int,
        private val y: Int,
        private val statusEffects: MutableCollection<StatusEffectInstance>
)
{
    fun draw()
    {
        lateinit var mode: DrawModule

//        val boxed = Boxed(matrixStack, minecraft, x, y, increment, statusEffects.asIterable())
//        mode = Boxed(matrixStack, minecraft, x, y, statusEffects.asIterable())
//        mode = OnlyName(matrixStack, minecraft, x, y, statusEffects.asIterable())
        mode = NoSprite(matrixStack, minecraft, x, y, statusEffects.asIterable())
        mode.drawBackground()
        mode.drawSprite()
        mode.drawDescription()
    }
}