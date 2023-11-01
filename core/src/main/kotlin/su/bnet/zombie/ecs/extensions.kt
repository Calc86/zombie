package su.bnet.zombie.ecs

import com.badlogic.ashley.core.Component

inline fun <reified T: Component> T?.require() =
    requireNotNull(this) { "no component ${T::class.java} found" }

