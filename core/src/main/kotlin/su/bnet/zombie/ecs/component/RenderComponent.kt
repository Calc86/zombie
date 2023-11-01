package su.bnet.zombie.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import ktx.ashley.get
import ktx.ashley.mapperFor

class RenderComponent(var sprite: Sprite, val zOrder: Int = 0,) : Component {
    companion object {
        private val mapper = mapperFor<RenderComponent>()

        fun on(entity: Entity) = entity[mapper]
    }
}
