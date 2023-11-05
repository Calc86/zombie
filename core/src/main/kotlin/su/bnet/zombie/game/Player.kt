package su.bnet.zombie.game

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import su.bnet.zombie.ecs.component.*
import su.bnet.zombie.ecs.events.Add
import su.bnet.zombie.ecs.events.Events
import su.bnet.zombie.ecs.events.Hello
import su.bnet.zombie.game.screens.GameScreen
import su.bnet.zombie.input.Inputs
import su.bnet.zombie.utility.Delay

class Player(
    entity: Entity,
    sprite: Sprite,
    private val factory: ItemsFactory
) {
    private val rc = RenderComponent(sprite)
    private val tc = TransformationComponent(
        size = Vector2(sprite.width, sprite.height),
        scale = Vector2(GameScreen.World.unitScale, GameScreen.World.unitScale),
    ).apply {
        sprite.setSize(size.x, size.y)
        sprite.setOriginCenter()
        angle = 70f // for test
    }

    private val mc = MovementComponent(
        maxVelocity = 5f
    )

    private val ic = GameInputComponent(::onKey, ::onMouseMove)
    private val ec = EventComponent("player", ::onEvent)
    private val ac = ActComponent(::onAct)

    private val lookAt = Vector2()
    private val fire = Delay(0.001f, onRun = ::onFire)

    val position
        get() = tc.position

    init {
        entity.apply {
            add(rc)
            add(tc)
            add(mc)
            add(ic)
            add(ec)
            add(ac)
        }
    }

    private fun onKey(keys: Set<Inputs>): Boolean {
        //println(keys)
        when {
            keys.contains(Inputs.LEFT) -> mc.velocity.x = -mc.maxVelocity
            keys.contains(Inputs.RIGHT) -> mc.velocity.x = mc.maxVelocity
            else -> mc.velocity.x = 0f
        }

        when {
            keys.contains(Inputs.DOWN) -> mc.velocity.y = -mc.maxVelocity
            keys.contains(Inputs.UP) -> mc.velocity.y = mc.maxVelocity
            else -> mc.velocity.y = 0f
        }

        if (keys.contains(Inputs.FIRE)) fire.run()

        return true
    }

    private fun onMouseMove(mouse: Vector2): Boolean {
        lookAt.set(mouse).sub(tc.position)
        tc.angle = lookAt.angleDeg()
        return false
    }

    private fun onEvent(event: Events, from: Entity) {
        println("event $event, from $from")
    }

    private fun onAct(deltaTime: Float) {
        fire.update(deltaTime)
    }

    private fun onFire() {
//        ec.send(Add(factory.createExplosion().entity))
        ec.send(Add(factory.createBullet(Vector2(lookAt).rotateDeg(-90f), tc.position).entity))
        ec.send(Hello("player"))
    }
}
