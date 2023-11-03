package su.bnet.zombie.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.maps.MapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import ktx.ashley.get
import ktx.ashley.mapperFor

class TransformationComponent(
    val position: Vector2 = Vector2(),
    val size: Vector2 = Vector2(),
    val scale: Vector2 = Vector2(1f, 1f),
    var angle: Float = 0f
) : Component {
    companion object {
        val mapper = mapperFor<TransformationComponent>()

        fun on(entity: Entity) = entity[mapper]
    }
}
