package su.bnet.zombie.items

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import su.bnet.zombie.ecs.component.MovementComponent
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.component.TransformationComponent
import su.bnet.zombie.ecs.component.VelocityComponent
import su.bnet.zombie.ecs.events.Add
import su.bnet.zombie.game.screens.GameScreen

class Bullet(
    sprite: Sprite,
    velocity: Float = 7f,
    direction: Vector2,
    position: Vector2,
    ttl: Float = 0.71f,
    private val explosion: (position: Vector2) -> Explosion
) : TtlItem(MathUtils.random(ttl - ttl * 0.5f, ttl + ttl * 0.5f)) {
    private val rc = RenderComponent(sprite)
    private val tc = TransformationComponent(
        position = Vector2(position),
        angle = direction.angleDeg(),
        size = Vector2(sprite.width, sprite.height),
        scale = Vector2(GameScreen.World.unitScale, GameScreen.World.unitScale),
    )
    private val vc = MovementComponent(
        velocity = Vector2(0f, velocity).rotateDeg(MathUtils.random(direction.angleDeg() - 15, direction.angleDeg() + 15)),
        maxVelocity = velocity
    )

    init {
        entity.apply {
            add(rc)
            add(tc)
            add(vc)
        }
    }

    override fun onDie() {
        ec.send(Add(explosion.invoke(tc.position).entity))
    }
}
