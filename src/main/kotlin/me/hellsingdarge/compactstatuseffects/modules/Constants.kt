package me.hellsingdarge.compactstatuseffects.modules

// Ugly, but I can't be bothered to rewrite the class for this to be accessible
// Probably a todo (eventually)
object Constants
{
    data class WidthHeightPair(val width: Int, val height: Int)

    val NoName = WidthHeightPair(33, 41)
    val NoSprite = WidthHeightPair(100, 31)
    val OnlyName = WidthHeightPair(100, 19)
}