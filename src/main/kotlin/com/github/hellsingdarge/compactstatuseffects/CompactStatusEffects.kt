package com.github.hellsingdarge.compactstatuseffects

import com.github.hellsingdarge.compactstatuseffects.config.ModConfig
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment


@Environment(EnvType.CLIENT)
class CompactStatusEffects: ClientModInitializer
{
    override fun onInitializeClient()
    {
        AutoConfig.register(ModConfig::class.java, ::Toml4jConfigSerializer)
    }
}
