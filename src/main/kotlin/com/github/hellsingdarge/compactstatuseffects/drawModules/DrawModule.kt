package com.github.hellsingdarge.compactstatuseffects.drawModules

import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.texture.StatusEffectSpriteManager
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance

abstract class DrawModule(
        protected val matrixStack: MatrixStack,
        protected val minecraft: MinecraftClient,
        protected val x: Int,
        protected val y: Int,
        protected val offset: Int,
        protected val effects: Iterable<StatusEffectInstance>
): DrawableHelper()
{
    protected val spriteManager: StatusEffectSpriteManager = minecraft.statusEffectSpriteManager
    protected val fontRenderer: TextRenderer = minecraft.textRenderer

    open fun drawBackground() {}
    open fun drawSprite() {}
    open fun drawDescription() {}
}