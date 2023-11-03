package su.bnet.zombie.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import su.bnet.zombie.ecs.component.EventComponent
import su.bnet.zombie.ecs.events.*
import su.bnet.zombie.ecs.require
import java.lang.IllegalStateException

class EventSystem(private val engine: Engine) : IteratingSystem(Family.all(EventComponent::class.java).get()) {
    override fun update(deltaTime: Float) {
        val senders = entities.map { entity ->
            entity to EventComponent.on(entity).require()
        }
        val events = senders.map { (e, c) ->
            c.events.map { e to it }.also { c.clear() }
        }.flatten()
        val receivers = senders.map { it.second }.filter { c -> c.isReceiver }
        events.forEach { (entity, event) -> handleEvent(entity, event, receivers) }
//        println("senders " + senders.size)
//        println("events " + events.size)
//        println("receivers " + receivers.size)
    }

    private fun handleEvent(sender: Entity, event: Events, receivers: List<EventComponent>) {
        when (event) {
            is GameEvents -> receivers
                .filter { it.name == event.to }
                .forEach { it.onEvent.invoke(event, sender) }

            is SystemEvents -> handleSystemEvent(sender, event)
        }
    }

    private fun handleSystemEvent(sender: Entity, event: SystemEvents) {
        when (event) {
            is Remove -> engine.removeEntity(sender)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        throw IllegalStateException("processEntity")
    }
}
