package com.cout970.magneticraft.gui.client.components

import com.cout970.magneticraft.IVector2
import com.cout970.magneticraft.gui.client.core.DrawableBox
import com.cout970.magneticraft.gui.client.core.IComponent
import com.cout970.magneticraft.gui.client.core.IGui
import com.cout970.magneticraft.util.vector.Vec2d
import com.cout970.magneticraft.util.vector.contains
import com.cout970.magneticraft.util.vector.vec2Of
import net.minecraft.util.ResourceLocation
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11


/**
 * Created by cout970 on 2017/07/29.
 */
class CompScrollBar(
        override val pos: IVector2,
        override val size: IVector2 = vec2Of(12, 88),
        val texture: ResourceLocation) : IComponent {

    override lateinit var gui: IGui

    companion object {
        val sliderSize = vec2Of(12, 15)
    }

    var tracking = false
    var currentScroll = 0
    var section = 0.0
    var maxScroll = 19

    init {
        recalculateSections()
    }

    fun getScroll(): Int {
        return Math.round(currentScroll.toFloat() / section).toInt()
    }

    fun recalculateSections() {
        section = ((size.yi - sliderSize.yf) / maxScroll).toDouble()
    }

    override fun drawFirstLayer(mouse: Vec2d, partialTicks: Float) {
        gui.bindTexture(texture)
        GL11.glColor4f(1f, 1f, 1f, 1f)

        gui.drawTexture(DrawableBox(
                screen = Pair(gui.pos + pos + vec2Of(0, currentScroll), sliderSize),
                texture = Pair(vec2Of(232, 0), sliderSize),
                textureSize = vec2Of(256, 256)
        ))

        if (Mouse.isButtonDown(0)) {
            onMouseClick(mouse, 0)
        } else {
            tracking = false
        }
    }

    override fun onMouseClick(mouse: Vec2d, mouseButton: Int): Boolean {
        if (mouseButton == 0) {
            if (mouse in Pair(gui.pos + pos, size)) {
                tracking = true
            }
            if (tracking) {
                currentScroll = mouse.yi - pos.yi - gui.pos.yi - 8
                clampScroll()
            }
        }
        return super.onMouseClick(mouse, mouseButton)
    }

    override fun onWheel(amount: Int) {
        if (tracking) {
            return
        }
        currentScroll -= (amount * section).toInt()
        clampScroll()
    }

    fun clampScroll() {
        currentScroll = Math.min(Math.max(0, currentScroll), size.yi - sliderSize.yi)
    }
}