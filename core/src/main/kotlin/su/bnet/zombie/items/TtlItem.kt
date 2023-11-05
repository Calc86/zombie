package su.bnet.zombie.items

import com.badlogic.ashley.core.Entity
import su.bnet.zombie.ecs.component.ActComponent
import su.bnet.zombie.ecs.component.EventComponent
import su.bnet.zombie.ecs.events.Remove

open class TtlItem(
    private val ttl: Float,
) {
    val entity = Entity()
    protected val ec = EventComponent()
    private var life = 0f
    protected val ac = ActComponent(::onAct)

//    private inner class TtlActComponent: ActComponent(::onAct)

    init {
        entity.apply {
            add(ac)
            add(ec)
        }
    }

    protected open fun onAct(deltaTime: Float) {
        println("act")
        life += deltaTime
        if (life >= ttl) ec.send(Remove(entity))
    }
}
