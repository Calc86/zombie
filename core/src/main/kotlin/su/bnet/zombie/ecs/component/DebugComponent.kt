package su.bnet.zombie.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.maps.MapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import ktx.ashley.get
import ktx.ashley.mapperFor

open class DebugComponent : Component {
    var debugName = "debug"
}
