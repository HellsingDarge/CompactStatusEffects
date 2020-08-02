package com.github.hellsingdarge.compactstatuseffects

import com.github.hellsingdarge.compactstatuseffects.config.ModConfig
import com.github.hellsingdarge.compactstatuseffects.drawModules.DrawModule
import com.github.hellsingdarge.compactstatuseffects.drawModules.NoName
import com.github.hellsingdarge.compactstatuseffects.drawModules.NoSprite
import com.github.hellsingdarge.compactstatuseffects.drawModules.OnlyName
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

class CustomEffectsDisplay(
        private val matrixStack: MatrixStack,
        private val minecraft: MinecraftClient,
        private val x: Int,
        private val y: Int,
        private val statusEffects: Iterable<StatusEffectInstance>
)
{
    fun draw()
    {
        val mode: DrawModule = when (AutoConfig.getConfigHolder(ModConfig::class.java).config.module)
        {
            ModConfig.Module.NONAME -> NoName(matrixStack, minecraft, x, y, statusEffects)
            ModConfig.Module.NOSPRITE -> NoSprite(matrixStack, minecraft, x, y, statusEffects)
            ModConfig.Module.ONLYNAME -> OnlyName(matrixStack, minecraft, x, y, statusEffects)
        }

        mode.drawBackground()
        mode.drawSprite()
        mode.drawDescription()
    }
}