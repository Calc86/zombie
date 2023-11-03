package su.bnet.zombie.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import ktx.ashley.get
import ktx.ashley.mapperFor
import su.bnet.zombie.input.Inputs

class GameInputComponent(
    val onKeyProcess: ((Set<Inputs>) -> Boolean)? = null,
    val onMouseMove: ((Vector2) -> Boolean)? = null,
): Component {
    companion object {
        val mapper = mapperFor<GameInputComponent>()

        fun on(entity: Entity) = entity[mapper]
    }
}
