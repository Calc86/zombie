package su.bnet.zombie.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import ktx.ashley.get
import ktx.ashley.mapperFor

class MovementComponent(
    val velocity: Vector2 = Vector2(),
    val maxVelocity: Float = 0f
) : Component {
    companion object {
        val mapper = mapperFor<MovementComponent>()

        fun on(entity: Entity) = entity[mapper]
    }
}
