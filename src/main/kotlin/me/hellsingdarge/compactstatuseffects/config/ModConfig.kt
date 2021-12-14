package me.hellsingdarge.compactstatuseffects.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = "compactstatuseffects")
class ModConfig: ConfigData
{
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    val module: Module = Module.NO_NAME

    @ConfigEntry.Gui.CollapsibleObject
    val noName = NoNameConfig()

    @ConfigEntry.Gui.CollapsibleObject
    val noSprite = NoSpriteConfig()

    @ConfigEntry.Gui.CollapsibleObject
    val onlyName = OnlyNameConfig()

    enum class Module
    {
        NO_NAME,
        NO_SPRITE,
        ONLY_NAME;
    }

    class NoSpriteConfig: ConfigData, IConfigCommon
    {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 10)
        override val margin: Int = 10
            get() = field * 2

        override val squash: Boolean = false

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        override val effectsPerColumn: Int = 5

        @ConfigEntry.Gui.Tooltip
        override val oldSide: Boolean = true
    }

    class NoNameConfig: ConfigData, IConfigCommon
    {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 10)
        override val margin: Int = 10
            get() = field * 2

        override val squash: Boolean = false

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        override val effectsPerColumn: Int = 4

        @ConfigEntry.Gui.Tooltip
        override val oldSide: Boolean = true

        val showLevel: Boolean = true
        val levelInArabic: Boolean = false
    }

    class OnlyNameConfig: ConfigData, IConfigCommon
    {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 10)
        override val margin: Int = 10
            get() = field * 2

        @ConfigEntry.BoundedDiscrete(min = 1, max = 15)
        override val effectsPerColumn: Int = 8

        @ConfigEntry.Gui.Tooltip
        override val oldSide: Boolean = true

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