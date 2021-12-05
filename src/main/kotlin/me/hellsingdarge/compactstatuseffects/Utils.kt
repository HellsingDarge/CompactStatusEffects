package me.hellsingdarge.compactstatuseffects

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.util.StringHelper
import net.minecraft.util.math.MathHelper

object Utils
{
    fun effectDurationToStr(effect: StatusEffectInstance): String
    {
        return if (effect.isPermanent)
        {
            "∞"
        }
        else
        {
            val i = MathHelper.floor(effect.duration.toFloat())
            StringHelper.formatTicks(i)
        }
    }
}