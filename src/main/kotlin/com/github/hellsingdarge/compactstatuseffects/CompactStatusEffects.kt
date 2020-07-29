package com.github.hellsingdarge.compactstatuseffects

import com.github.hellsingdarge.compactstatuseffects.config.ModConfig
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry
import me.sargunvohra.mcmods.autoconfig1u.gui.registry.api.GuiRegistryAccess
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer
import me.sargunvohra.mcmods.autoconfig1u.util.Utils.getUnsafely
import me.sargunvohra.mcmods.autoconfig1u.util.Utils.setUnsafely
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder
import me.shedaniel.clothconfig2.api.ModifierKeyCode
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.text.TranslatableText
import java.lang.reflect.Field


@Environment(EnvType.CLIENT)
class CompactStatusEffects: ClientModInitializer
{
    override fun onInitializeClient()
    {
        AutoConfig.register(ModConfig::class.java, ::Toml4jConfigSerializer)

        val guiRegistry = AutoConfig.getGuiRegistry(ModConfig::class.java)
        guiRegistry.registerPredicateProvider({ i13n: String?, field: Field, config: Any?, defaults: Any?, _: GuiRegistryAccess? ->

            if (field.isAnnotationPresent(ConfigEntry.Gui.Excluded::class.java)) return@registerPredicateProvider emptyList()
            val entry = ConfigEntryBuilder.create().startModifierKeyCodeField(TranslatableText(i13n), getUnsafely(field, config, ModifierKeyCode.unknown())).setModifierDefaultValue { getUnsafely(field, defaults) }.setModifierSaveConsumer { newValue: ModifierKeyCode? -> setUnsafely(field, config, newValue) }.build()
            listOf(entry)
        })
        { field: Field -> field.type == ModifierKeyCode::class.java }
    }
}
