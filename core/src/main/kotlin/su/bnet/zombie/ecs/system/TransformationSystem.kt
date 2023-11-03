package su.bnet.zombie.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.component.TransformationComponent
import su.bnet.zombie.ecs.require

class TransformationSystem : IteratingSystem(family) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val tc = TransformationComponent.on(entity).require()
        val rc = RenderComponent.on(entity).require()
        rc.sprite.apply {
            // todo avoid dirty
            rotation = tc.angle
            //setPosition(tc.position.x, tc.position.y) // old
            setOriginBasedPosition(tc.position.x, tc.position.y)    // check
            setSize(tc.size.x, tc.size.y)
            setScale(tc.scale.x, tc.scale.y)
        }
    }



    companion object {
        private val family = Family.all(
            TransformationComponent::class.java,
        ).get()
    }
}
