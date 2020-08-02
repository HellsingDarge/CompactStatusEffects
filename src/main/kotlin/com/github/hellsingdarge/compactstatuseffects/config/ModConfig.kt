package com.github.hellsingdarge.compactstatuseffects.config

import me.sargunvohra.mcmods.autoconfig1u.ConfigData
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry

@Config(name = "compactstatuseffects")
class ModConfig: ConfigData
{
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    var module: Module = Module.NONAME

    @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
    var margin: Int = 10

    @ConfigEntry.Gui.CollapsibleObject
    val noNameConfig = NoNameConfig()

    @ConfigEntry.Gui.CollapsibleObject
    val onlyNameConfig = OnlyNameConfig()

    @ConfigEntry.Gui.CollapsibleObject
    val noSpriteConfig = NoSpriteConfig()

    enum class Module
    {
        NONAME,
        NOSPRITE,
        ONLYNAME,
    }

    @Config(name = "module_nosprite")
    class NoSpriteConfig: ConfigData
    {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        var maxEffectsNumber: Int = 5
    }

    @Config(name = "module_boxed")
    class NoNameConfig: ConfigData
    {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        var maxEffectsNumber: Int = 4
    }

    @Config(name = "module_onlyname")
    class OnlyNameConfig: ConfigData
    {
        @ConfigEntry.BoundedDiscrete(min = 1, max = 15)
        var maxEffectsNumber: Int = 8

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