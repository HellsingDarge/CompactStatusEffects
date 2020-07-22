package com.github.hellsingdarge.compactstatuseffects

import com.github.hellsingdarge.compactstatuseffects.drawModules.Boxed
import com.github.hellsingdarge.compactstatuseffects.drawModules.DrawModule
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

        var increment = 41

//        val boxed = Boxed(matrixStack, minecraft, x, y, increment, statusEffects.asIterable())
        mode = Boxed(matrixStack, minecraft, x, y, increment, statusEffects.asIterable())
        mode.drawBackground()
        mode.drawSprite()
        mode.drawDescription()
    }
}