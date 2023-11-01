package su.bnet.zombie.ecs.system

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import ktx.ashley.get
import ktx.graphics.use
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.require

class RenderSystem(private val batch: Batch) : SortedIteratingSystem(family, comparator) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.sprite.draw(batch)
    }

    override fun update(deltaTime: Float) {
        batch.use {
            super.update(deltaTime)
        }
    }

    private val Entity.sprite
        get() = RenderComponent.on(this).require().sprite

    class RenderComparator : Comparator<Entity> {
        override fun compare(o1: Entity, o2: Entity): Int {
            // todo
            return 0
        }
    }

    companion object {
        private val family = Family.all(RenderComponent::class.java).get()
        private val comparator = RenderComparator()
    }
}
