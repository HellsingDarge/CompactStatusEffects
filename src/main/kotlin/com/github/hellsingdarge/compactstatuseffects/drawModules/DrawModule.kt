package com.github.hellsingdarge.compactstatuseffects.drawModules

import com.github.hellsingdarge.compactstatuseffects.config.ModConfig
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
    protected var modConfig: ModConfig = AutoConfig.getConfigHolder(ModConfig::class.java).config
    protected val backgroundTexture = Identifier("compactstatuseffects:textures/atlas.png")
    protected val spriteManager: StatusEffectSpriteManager = mc.statusEffectSpriteManager
    protected val textureManager: TextureManager = mc.textureManager

    open fun drawBackground() {}
    open fun drawSprite() {}
    open fun drawDescription() {}

    companion object
    {
        lateinit var mc: MinecraftClient
    }
}