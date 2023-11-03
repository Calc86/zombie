package su.bnet.zombie.ecs.events

import com.badlogic.ashley.core.Entity

enum class System {
    ENGINE
}

sealed class Events

open class GameEvents(val to: String) : Events()

class Hello(to: String) : GameEvents(to)

open class SystemEvents(val to: System) : Events()

class Remove(val entity: Entity) : SystemEvents(System.ENGINE)
