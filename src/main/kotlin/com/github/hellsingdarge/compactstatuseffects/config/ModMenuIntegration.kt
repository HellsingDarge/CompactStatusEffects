package com.github.hellsingdarge.compactstatuseffects.config

import io.github.prospector.modmenu.api.ConfigScreenFactory
import io.github.prospector.modmenu.api.ModMenuApi
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig

class ModMenuIntegration: ModMenuApi
{
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*>
    {
        return ConfigScreenFactory { parent -> AutoConfig.getConfigScreen(ModConfig::class.java, parent).get() }
    }
}