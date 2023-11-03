package su.bnet.zombie.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import ktx.app.KtxInputAdapter
import su.bnet.zombie.ecs.component.GameInputComponent
import su.bnet.zombie.ecs.require
import su.bnet.zombie.input.Inputs
import java.lang.IllegalStateException

class GameInputSystem : IteratingSystem(Family.all(GameInputComponent::class.java).get()), KtxInputAdapter {
    private val inputMap = mutableSetOf<Inputs>()
    private val mousePosition = Vector2()
    private var mouseDown: Vector2? = null

    override fun update(deltaTime: Float) {
        for (entity in entities) {
            if (processEntityKey(entity)) break
        }
    }

    private fun processEntityKey(entity: Entity): Boolean {
        val ic = GameInputComponent.on(entity).require()
        return ic.onKeyProcess(inputMap)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        throw IllegalStateException("processEntity")
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        mousePosition.set(screenX.toFloat(), screenY.toFloat()) // todo to world coordinates
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        mouseDown = Vector2(screenX.toFloat(), screenY.toFloat()) // todo to world coordinates
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        mouseDown = null
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
