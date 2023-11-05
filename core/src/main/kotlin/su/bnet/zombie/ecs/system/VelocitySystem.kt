package su.bnet.zombie.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import su.bnet.zombie.ecs.component.TransformationComponent
import su.bnet.zombie.ecs.component.VelocityComponent
import su.bnet.zombie.ecs.require

class VelocitySystem : IteratingSystem(family) {
    private val tmpAcceleration = Vector2()
    private val tmpVelocity = Vector2()
    private val scalar = Vector2()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val tc = TransformationComponent.on(entity).require()
        val vc = VelocityComponent.on(entity).require()

        val acceleration = with(vc) {
            tmpAcceleration.set(force)
            val acceleration = MathUtils.clamp(tmpAcceleration.len() - friction, 0f, max.acceleration)
            tmpAcceleration.nor().scl(acceleration)
//            println("a: " + tmpAcceleration)
            tmpAcceleration//.scl(deltaTime)
        }.also {
            vc.force.set(it)
        }
        if(vc.name != "bullet") println(acceleration)
        with(vc) {
            // todo frition after zero force
            tmpVelocity.set(velocity)
            tmpVelocity.add(acceleration)
            if (velocity.len() > max.speed) tmpVelocity.nor().scl(max.speed)
//            println("v:" + tmpVelocity)
            tmpVelocity
        }.also {
            vc.velocity.set(it)
        }
        scalar.set(vc.velocity).scl(deltaTime)

        tc.position.add(scalar)
    }

    companion object {
        private val family: Family = Family.all(TransformationComponent::class.java, VelocityComponent::class.java).get()
    }
}
