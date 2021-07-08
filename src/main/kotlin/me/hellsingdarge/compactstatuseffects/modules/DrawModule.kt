package me.hellsingdarge.compactstatuseffects.modules

import com.mojang.blaze3d.systems.RenderSystem
import me.hellsingdarge.compactstatuseffects.AnchoredTextRenderer
import me.hellsingdarge.compactstatuseffects.config.IConfigCommon
import me.hellsingdarge.compactstatuseffects.config.ModConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.resource.language.I18n
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.StatusEffectSpriteManager
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.util.Identifier

abstract class DrawModule(
    protected val matrixStack: MatrixStack,
    protected val uiX: Int,
    protected val uiY: Int,
    protected val effects: Iterable<StatusEffectInstance>
): DrawableHelper()
{
    protected val xOffset: Int get() = width + config.margin - 1
    protected val xDecrement: Int get() = width - 1

    protected val modConfig: ModConfig = AutoConfig.getConfigHolder(ModConfig::class.java).config
    protected val backgroundTexture = Identifier("compactstatuseffects:textures/atlas.png")
    protected val spriteManager: StatusEffectSpriteManager = mc.statusEffectSpriteManager
    protected val textRenderer = AnchoredTextRenderer(mc.textRenderer)

    protected abstract val width: Int
    protected abstract val height: Int
    protected abstract val config: IConfigCommon
    protected abstract val yIncrement: Int
    protected abstract val maxNum: Int

    open fun draw()
    {
    }

    protected inline fun drawBackground(body: (x: Int, y: Int) -> Unit)
    {
        RenderSystem.setShaderTexture(0, backgroundTexture)
        var i = uiX
        var j = uiY

        repeat(effects.count()) { index ->
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)

            body(i, j)

            i = uiX - ((index + 1) / maxNum) * xDecrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = uiY
        }
    }

    protected inline fun drawSprite(body: (x: Int, y: Int, sprite: Sprite) -> Unit)
    {
        var i = uiX
        var j = uiY

        effects.forEachIndexed { index, instance ->
            val effect = instance.effectType
            val sprite = spriteManager.getSprite(effect)
            RenderSystem.setShaderTexture(0, sprite.atlas.id)

            body(i, j, sprite)

            i = uiX - ((index + 1) / maxNum) * xDecrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = uiY
        }
    }

    protected inline fun drawDescription(body: (x: Int, y: Int, instance: StatusEffectInstance) -> Unit)
    {
        var i = uiX
        var j = uiY

        effects.forEachIndexed { index, instance ->
            body(i, j, instance)

            i = uiX - ((index + 1) / maxNum) * xDecrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = uiY
        }
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
        val mc: MinecraftClient by lazy { MinecraftClient.getInstance() }
    }
}