package com.github.hellsingdarge.compactstatuseffects.drawModules

enum class Colour(val r: Int, val g: Int, val b: Int)
{
    RED(255, 0, 0),
    GREEN(0, 255, 0);

    fun toInt(): Int = (r shl 16) or (b shl 8) or (g shl 0)

    companion object
    {
        fun fromDuration(duration: Int): Colour
        {
            return RED
        }
    }
}
