package me.hellsingdarge.compactstatuseffects

import me.hellsingdarge.compactstatuseffects.config.ModConfig
import me.hellsingdarge.compactstatuseffects.modules.Constants
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen

class StatusEffectsExclusions: REIClientPlugin
{
    private val player by lazy { MinecraftClient.getInstance().player!! }
    private val config by lazy { AutoConfig.getConfigHolder(ModConfig::class.java).config }

    override fun registerExclusionZones(zones: ExclusionZones)
    {
        zones.register(AbstractInventoryScreen::class.java) { screen ->
            val (module, pair) = when (config.module)
            {
                ModConfig.Module.NO_NAME -> Pair(config.noName, Constants.NoName)
                ModConfig.Module.NO_SPRITE -> Pair(config.noSprite, Constants.NoSprite)
                ModConfig.Module.ONLY_NAME -> Pair(config.onlyName, Constants.OnlyName)
            }
            if (module.leftSide)
                return@register emptyList()

            val effectsCount = player.statusEffects.size
            if (effectsCount == 0)
                return@register emptyList()

            val x = screen.x + screen.backgroundWidth + module.margin

            List(effectsCount / module.effectsPerColumn + 1) {
                Rectangle(x + pair.width * it, screen.y, pair.width, pair.height * module.effectsPerColumn + 1)
            }
        }
    }
}