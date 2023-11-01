package su.bnet.zombie.game

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import su.bnet.zombie.ecs.component.MovementComponent
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.component.TransformationComponent

class Player(
    entity: Entity,
    sprite: Sprite,
) {
    private val rc = RenderComponent(sprite)
    private val tc = TransformationComponent(
        position = Vector2(300f, 300f),     // todo
        size = Vector2(sprite.width, sprite.height), // todo
        scale = Vector2(0.5f, 0.5f) // todo
    )
//    private val vc = VelocityComponent(
//        force = Vector2(0f, -10f),
//        velocity = Vector2(-100f, 0f),
//    )
    private val mc = MovementComponent(
        Vector2(-50f, 0f)
    )

    init {
        entity.apply {
            add(rc)
            add(tc)
            //add(vc)
            add(mc)
        }
    }
}
