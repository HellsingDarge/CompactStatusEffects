package me.hellsingdarge.compactstatuseffects.modules

import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffectUtil

object HudTimer
{
    private val mc: MinecraftClient by lazy { MinecraftClient.getInstance() }

    @JvmStatic
    fun draw(matrices: MatrixStack, effects: Collection<StatusEffectInstance>)
    {
        var i = 0
        var j = 0

        effects.forEach { inst ->
            if (inst.shouldShowIcon())
            {
                val fontSize = 3
                var k = mc.window.scaledWidth
                var l = 1 + 16

                if (inst.effectType.isBeneficial)
                {
                    k -= 25 * i
                    i++
                }
                else
                {
                    k -= 25 * j
                    l += 26
                    j++
                }

                Util.drawRightAlign(matrices, StatusEffectUtil.durationToString(inst, 1.0f), k - 3f, l.toFloat(), withShadow = true, fontSize = fontSize)
            }
        }
    }
}