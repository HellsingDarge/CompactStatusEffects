package me.hellsingdarge.compactstatuseffects.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = "compactstatuseffects")
class ModConfig: ConfigData, ModMenuApi
{
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*>
    {
        return ConfigScreenFactory { parent -> AutoConfig.getConfigScreen(this::class.java, parent).get() }
    }

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    val module: Module = Module.NO_NAME

    @ConfigEntry.Gui.CollapsibleObject
    val noName = NoNameConfig()

    @ConfigEntry.Gui.CollapsibleObject
    val onlyName = OnlyNameConfig()

    @ConfigEntry.Gui.CollapsibleObject
    val noSprite = NoSpriteConfig()

    enum class Module(val str: String)
    {
        NO_NAME("NO NAME"),
        NO_SPRITE("NO SPRITE"),
        ONLY_NAME("ONLY NAME");

        override fun toString() = str
    }

    class NoSpriteConfig: ConfigData, IConfigCommon
    {
        @ConfigEntry.BoundedDiscrete(min = -3, max = 2)
        override val uiOffset: Int = 0

        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        override val margin: Int = 10

        override val squash: Boolean = false

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        override val effectsPerColumn: Int = 5
    }

    class NoNameConfig: ConfigData, IConfigCommon
    {
        @ConfigEntry.BoundedDiscrete(min = -3, max = 2)
        override val uiOffset: Int = 0

        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        override val margin: Int = 10

        override val squash: Boolean = false

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        override val effectsPerColumn: Int = 4

        val showLevel: Boolean = true
        val levelInArabic: Boolean = false
    }

    class OnlyNameConfig: ConfigData, IConfigCommon
    {
        @ConfigEntry.BoundedDiscrete(min = -3, max = 2)
        override val uiOffset: Int = 0

        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        override val margin: Int = 10

        @ConfigEntry.BoundedDiscrete(min = 1, max = 15)
        override val effectsPerColumn: Int = 8

        override val squash: Boolean = false

        @ConfigEntry.ColorPicker
        val permanentColour: Int = 0xFFFFFF

        @ConfigEntry.ColorPicker
        val wontExpireSoonColour: Int = 0x00B009

        @ConfigEntry.ColorPicker
        val soonToExpireColour: Int = 0xD4CD00

        @ConfigEntry.ColorPicker
        val expiringColour: Int = 0xD40000

        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = 0, max = 5 * 60)
        val expiringBound: Int = 20

        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = 0, max = 10 * 60)
        val soonToExpireBound: Int = 60
    }
}