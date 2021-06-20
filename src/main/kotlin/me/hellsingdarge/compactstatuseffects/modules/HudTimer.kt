package me.hellsingdarge.compactstatuseffects.modules

import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffectUtil

object HudTimer
{
    @JvmStatic
    fun draw(matrices: MatrixStack, effects: Collection<StatusEffectInstance>)
    {
        var i = 0
        var j = 0

        effects.forEach { inst ->
            if (inst.shouldShowIcon())
            {
                val fontSize = 9
                var k = MinecraftClient.getInstance().window.scaledWidth + 24
                var l = 1 + 16

                if (inst.effectType.isBeneficial)
                {
                    ++i
                    k -= 25 * i
                }
                else
                {
                    ++j
                    k -= 25 * j
                    l += 26
                }

                Util.drawRightAlign(matrices, StatusEffectUtil.durationToString(inst, 1.0f), k.toFloat(), l.toFloat(), withShadow = true, fontSize = fontSize)

            }
        }
    }
}