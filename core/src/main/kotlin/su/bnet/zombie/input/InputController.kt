package su.bnet.zombie.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import ktx.app.KtxInputAdapter

class InputController: KtxInputAdapter {
    val inputMap = mutableMapOf<Inputs, Float>()
    init {
        inputMap.apply {
            Inputs.entries.forEach { put(it, 0f) }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        return super.keyDown(keycode)
    }

    override fun keyUp(keycode: Int): Boolean {
        return super.keyUp(keycode)
    }
}
