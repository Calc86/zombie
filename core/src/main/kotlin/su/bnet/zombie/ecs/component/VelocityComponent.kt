package su.bnet.zombie.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import ktx.ashley.get
import ktx.ashley.mapperFor

@Deprecated("use movement")
class VelocityComponent(
    val velocity: Vector2 = Vector2(),
    val force: Vector2 = Vector2(),
    val friction: Float = 0f,
    val max: Max = Max(),
    val name:String = "",
) : Component {
    class Max(
        var speed: Float = Float.MAX_VALUE,
        var acceleration: Float = Float.MAX_VALUE
    )

    companion object {
        val mapper = mapperFor<VelocityComponent>()

        fun on(entity: Entity) = entity[mapper]
    }
}
