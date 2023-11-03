package su.bnet.zombie.game

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import su.bnet.zombie.ecs.component.GameInputComponent
import su.bnet.zombie.ecs.component.MovementComponent
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.component.TransformationComponent
import su.bnet.zombie.game.screens.GameScreen
import su.bnet.zombie.input.Inputs

class Player(
    entity: Entity,
    sprite: Sprite,
) {
    private val rc = RenderComponent(sprite)
    private val tc = TransformationComponent(
        size = Vector2(sprite.width * GameScreen.World.unitScale, sprite.height * GameScreen.World.unitScale),
    ).apply {
        sprite.setSize(size.x, size.y)
        sprite.setOriginCenter()
        angle = 70f // for test
    }

    private val mc = MovementComponent(
        maxVelocity = 5f
    )

    private val ic = GameInputComponent(::onKey)

    val position
        get() = tc.position

    init {
        entity.apply {
            add(rc)
            add(tc)
            add(mc)
            add(ic)
        }
    }

    private fun onKey(keys: Set<Inputs>): Boolean {
        //println(keys)
        when {
            keys.contains(Inputs.LEFT) -> mc.velocity.x = -mc.maxVelocity
            keys.contains(Inputs.RIGHT) -> mc.velocity.x = mc.maxVelocity
            else ->  mc.velocity.x = 0f
        }

        when {
            keys.contains(Inputs.DOWN) -> mc.velocity.y = -mc.maxVelocity
            keys.contains(Inputs.UP) -> mc.velocity.y = mc.maxVelocity
            else -> mc.velocity.y = 0f
        }

        return true
    }
}
