package me.hellsingdarge.compactstatuseffects.modules

import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

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
                val fontSize = 6
                var k = mc.window.scaledWidth
                var l = 24

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

                Util.drawRightAlign(matrices, Util.effectDurationToStr(inst), k - 3.25f, l - 0.25f, withShadow = true, fontSize = fontSize, colour = 0xD3D3D3)
            }
        }
    }
}