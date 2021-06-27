package me.hellsingdarge.compactstatuseffects.modules

import me.hellsingdarge.compactstatuseffects.config.IConfigCommon
import me.hellsingdarge.compactstatuseffects.config.ModConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.resource.language.I18n
import net.minecraft.client.texture.StatusEffectSpriteManager
import net.minecraft.client.texture.TextureManager
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.util.Identifier

abstract class DrawModule(
    protected val matrixStack: MatrixStack,
    protected val x: Int,
    protected val y: Int,
    protected val effects: Iterable<StatusEffectInstance>
): DrawableHelper()
{
    protected abstract val width: Int
    protected abstract val height: Int
    protected abstract val config: IConfigCommon
    protected abstract val xOffset: Int
    protected abstract val xDecrement: Int
    protected abstract val yIncrement: Int
    protected abstract val maxNum: Int

    protected val modConfig: ModConfig = AutoConfig.getConfigHolder(ModConfig::class.java).config
    protected val backgroundTexture = Identifier("compactstatuseffects:textures/atlas.png")
    protected val spriteManager: StatusEffectSpriteManager = mc.statusEffectSpriteManager
    protected val textureManager: TextureManager = mc.textureManager

    open fun drawBackground()
    {
    }

    open fun drawSprite()
    {
    }

    open fun drawDescription()
    {
    }

    protected fun StatusEffectInstance.getName(): String
    {
        var effectName = I18n.translate(this.effectType.translationKey, *arrayOfNulls(0))

        if (this.amplifier in 1..9)
        {
            effectName += ' ' + I18n.translate("enchantment.level." + (this.amplifier + 1), *arrayOfNulls(0))
        }

        return effectName
    }


    protected inline fun onHover(x: Int, y: Int, block: (mouseX: Int, mouseY: Int) -> Unit)
    {
        val mouseX = (mc.mouse.x * mc.window.scaledWidth / mc.window.width).toInt()
        val mouseY = (mc.mouse.y * mc.window.scaledHeight / mc.window.height).toInt()

        if (mouseX in (x - xDecrement - config.margin + 1)..(x - config.margin - 1) && mouseY in (y + 1)..(y + yIncrement - 1))
        {
            block(mouseX, mouseY)
        }
    }

    companion object
    {
        val mc: MinecraftClient = MinecraftClient.getInstance()
    }
}