package su.bnet.zombie.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import su.bnet.zombie.ecs.component.ActComponent
import su.bnet.zombie.ecs.require

class ActSystem : IteratingSystem(Family.all(ActComponent::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        ActComponent.on(entity).require().onAct.invoke(deltaTime)
    }
}
