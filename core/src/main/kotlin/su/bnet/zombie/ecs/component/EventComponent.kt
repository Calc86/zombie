package su.bnet.zombie.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import ktx.ashley.get
import ktx.ashley.mapperFor
import su.bnet.zombie.ecs.events.Events

class EventComponent(
    val name: String = "",
    val onEvent: ((Events, Entity) -> Unit) = { _, _ -> }
) : Component {
    val events = mutableListOf<Events>()

    val isReceiver = name.isNotBlank()

    fun send(event: Events) {
        events.add(event)
    }

    fun clear() = events.clear()

    companion object {
        val mapper = mapperFor<EventComponent>()

        fun on(entity: Entity) = entity[mapper]
    }

}
