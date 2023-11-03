package su.bnet.zombie.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import su.bnet.zombie.ecs.component.MovementComponent
import su.bnet.zombie.ecs.component.TransformationComponent
import su.bnet.zombie.ecs.require

class MovementSystem : IteratingSystem(Family.all(TransformationComponent::class.java, MovementComponent::class.java).get()) {
    private val scalar = Vector2()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val tc = TransformationComponent.on(entity).require()
        val mc = MovementComponent.on(entity).require()

        scalar.set(mc.velocity).nor().scl(mc.maxVelocity).scl(deltaTime)
        //scalar.set(mc.velocity).nor().scl(mc.maxVelocity).scl(deltaTime)
        tc.position.add(scalar)
    }
}
