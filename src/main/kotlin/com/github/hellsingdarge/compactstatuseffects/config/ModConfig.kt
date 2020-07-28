package com.github.hellsingdarge.compactstatuseffects.config

import me.sargunvohra.mcmods.autoconfig1u.ConfigData
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment

@Config(name = "compactstatuseffects")
class ModConfig: ConfigData
{
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    var module: Module = Module.BOXED

    @ConfigEntry.Category("boxed_config")
    @ConfigEntry.Gui.TransitiveObject
    val boxedConfig = BoxedConfig()

    @ConfigEntry.Category("onlyname_config")
    @ConfigEntry.Gui.TransitiveObject
    val onlyNameConfig = OnlyNameConfig()

    enum class Module
    {
        BOXED,
        NOSPRITE,
        ONLYNAME,
    }

    @Config(name = "module_boxed")
    class BoxedConfig: ConfigData
    {
        @Comment("Maximum number of displayed effects before new column is added")
        var maxNumber: Int = 4
    }

    @Config(name = "module_onlyname")
    class OnlyNameConfig: ConfigData
    {
        @ConfigEntry.ColorPicker
        var permanentColour = 0xFFFFFF

        @ConfigEntry.ColorPicker
        var wontExpireSoonColour = 0x00B009

        @ConfigEntry.ColorPicker
        var soonToExpireColour = 0xD4CD00

        @ConfigEntry.ColorPicker
        var expiringColour = 0xD40000

        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = 0, max = 5 * 60)
        var expiringBound = 20

        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = 0, max = 10 * 60)
        var soonToExpireBound = 60
    }
}