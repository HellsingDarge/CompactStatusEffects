package com.github.hellsingdarge.compactstatuseffects.drawModules

import com.github.hellsingdarge.compactstatuseffects.config.ModConfig
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
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

    open fun drawBackground() {}
    open fun drawSprite() {}
    open fun drawDescription() {}

    companion object
    {
        @JvmStatic
        protected val BACKGROUND_TEXTURE = Identifier("compactstatuseffects:textures/atlas.png")
        lateinit var spriteManager: StatusEffectSpriteManager
        lateinit var textureManager: TextureManager
    }
}