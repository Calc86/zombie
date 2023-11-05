package su.bnet.zombie.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import ktx.ashley.get
import ktx.ashley.mapperFor

open class ActComponent(val onAct: (deltaTime: Float) -> Unit) : Component {
    companion object {
        val mapper = mapperFor<ActComponent>()

        fun on(entity: Entity) = entity[mapper]
    }
}
