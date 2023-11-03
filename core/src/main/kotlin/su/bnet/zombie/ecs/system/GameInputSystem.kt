package su.bnet.zombie.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import ktx.app.KtxInputAdapter
import su.bnet.zombie.ecs.component.GameInputComponent
import su.bnet.zombie.ecs.require
import su.bnet.zombie.input.Inputs
import java.lang.IllegalStateException

class GameInputSystem(private val camera: Camera) : IteratingSystem(Family.all(GameInputComponent::class.java).get()), KtxInputAdapter {
    private val inputMap = mutableSetOf<Inputs>()
    private val tmpMouseXY = Vector3()
    private val mousePosition = Vector2()
    //private var mouseDown: Vector2? = null

    override fun update(deltaTime: Float) {
        // key process
        for (entity in entities) {
            if (processEntityKey(entity)) break
        }
        // mouse process
        for (entity in entities) {
            if(processMouseMove(entity)) break
        }
    }

    private fun processEntityKey(entity: Entity): Boolean {
        val ic = GameInputComponent.on(entity).require()
        return ic.onKeyProcess?.invoke(inputMap) ?: false
    }

    private fun processMouseMove(entity: Entity): Boolean {
        val ic = GameInputComponent.on(entity).require()
        return ic.onMouseMove?.invoke(mousePosition) ?: false
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        throw IllegalStateException("processEntity")
    }

    private fun updateMousePosition(screenX: Int, screenY: Int) {
        camera.unproject(tmpMouseXY.set(screenX.toFloat(), screenY.toFloat(), 0f))
        mousePosition.set(tmpMouseXY.x, tmpMouseXY.y)
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        updateMousePosition(screenX, screenY)
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        updateMousePosition(screenX, screenY)
        inputMap.add(Inputs.ACTION)
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        updateMousePosition(screenX, screenY)
        inputMap.remove(Inputs.ACTION)
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        Inputs.entries.forEach { input ->
            input.keysCode.forEach { k ->
                if (k == keycode)
                    inputMap.add(input)
            }
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        Inputs.entries.forEach { input ->
            input.keysCode.forEach { k ->
                if (k == keycode) inputMap.remove(input)
            }
        }
        return true
    }
}
