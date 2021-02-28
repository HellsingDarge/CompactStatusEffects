package com.github.hellsingdarge.compactstatuseffects

import com.github.hellsingdarge.compactstatuseffects.config.ModConfig
import com.github.hellsingdarge.compactstatuseffects.drawModules.DrawModule
import com.github.hellsingdarge.compactstatuseffects.drawModules.Util
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStarted
import net.minecraft.client.MinecraftClient


@Environment(EnvType.CLIENT)
class CompactStatusEffects: ClientModInitializer
{
    override fun onInitializeClient()
    {
        AutoConfig.register(ModConfig::class.java, ::Toml4jConfigSerializer)

        ClientLifecycleEvents.CLIENT_STARTED.register(ClientStarted { client: MinecraftClient ->
            Util.textRenderer = client.textRenderer
            DrawModule.mc = client
        })
    }
}
