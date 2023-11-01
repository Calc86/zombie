package su.bnet.zombie.game

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import su.bnet.zombie.ecs.component.MovementComponent
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.component.TransformationComponent
import su.bnet.zombie.game.screens.GameScreen

class Player(
    entity: Entity,
    sprite: Sprite,
) {
    private val rc = RenderComponent(sprite)
    private val tc = TransformationComponent(
        size = Vector2(sprite.width * GameScreen.World.unitScale, sprite.height * GameScreen.World.unitScale),
//        scale = Vector2(GameScreen.World.unitScale, GameScreen.World.unitScale)
    )

    private val mc = MovementComponent(
        //Vector2(-50f, 0f)
    )

    init {
        entity.apply {
            add(rc)
            add(tc)
            add(mc)
        }
    }
}
