package me.hellsingdarge.compactstatuseffects.modules

import me.hellsingdarge.compactstatuseffects.config.IConfigCommon
import me.hellsingdarge.compactstatuseffects.config.ModConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
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

    companion object
    {
        val mc: MinecraftClient = MinecraftClient.getInstance()
    }
}