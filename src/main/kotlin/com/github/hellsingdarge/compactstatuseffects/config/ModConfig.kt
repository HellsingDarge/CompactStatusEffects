package com.github.hellsingdarge.compactstatuseffects.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = "compactstatuseffects")
class ModConfig: ConfigData
{
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    var module: Module = Module.NO_NAME

    @ConfigEntry.Gui.CollapsibleObject
    val noName = NoNameConfig()

    @ConfigEntry.Gui.CollapsibleObject
    val onlyName = OnlyNameConfig()

    @ConfigEntry.Gui.CollapsibleObject
    val noSprite = NoSpriteConfig()

    enum class Module {
        NO_NAME,
        NO_SPRITE,
        ONLY_NAME,
    }

    class NoSpriteConfig : ConfigData, IConfigCommon {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        override var margin: Int = 10

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        override var effectsPerColumn: Int = 5
    }

    class NoNameConfig : ConfigData, IConfigCommon {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        override var margin: Int = 10

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        override var effectsPerColumn: Int = 4

        var showLevel: Boolean = true
        var levelInArabic: Boolean = false
    }

    class OnlyNameConfig : ConfigData, IConfigCommon {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        override var margin: Int = 10

        @ConfigEntry.BoundedDiscrete(min = 1, max = 15)
        override var effectsPerColumn: Int = 8

        @ConfigEntry.ColorPicker
        var permanentColour: Int = 0xFFFFFF

        @ConfigEntry.ColorPicker
        var wontExpireSoonColour: Int = 0x00B009

        @ConfigEntry.ColorPicker
        var soonToExpireColour: Int = 0xD4CD00

        @ConfigEntry.ColorPicker
        var expiringColour: Int = 0xD40000

        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = 0, max = 5 * 60)
        var expiringBound: Int = 20

        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = 0, max = 10 * 60)
        var soonToExpireBound: Int = 60
    }
}