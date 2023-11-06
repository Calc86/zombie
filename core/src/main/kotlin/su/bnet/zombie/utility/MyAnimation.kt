package su.bnet.zombie.utility

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite
import su.bnet.zombie.ecs.component.ActComponent
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.component.TransformationComponent
import su.bnet.zombie.game.screens.GameScreen

class MyAnimation(
    val s: Sprite,
    textures: Array<TextureAtlas.AtlasRegion>,
    duration: Float,
    playMode: PlayMode = PlayMode.LOOP
) {
    private val frameDuration = duration / textures.size
    private val animation = Animation<TextureRegion>(frameDuration, textures, playMode)
    private var sprite = AnimatedSprite(animation).apply {
        isCenterFrames = true
        isUseFrameRegionSize = true
    }
    private val rc = RenderComponent(sprite)
    private val ac = ActComponent(::onAct)
    private val tc = TransformationComponent()

    val entity = Entity().apply {
        add(rc)
        add(tc)
        add(ac)
//        tc.debugName = "anim"
    }

    private fun onAct(dt: Float) {
        sprite.update(dt)
        tc.apply {
//            size.set(sprite.width * GameScreen.World.unitScale, sprite.height * GameScreen.World.unitScale)
            size.set(sprite.width, sprite.height)
            scale.set(GameScreen.GameWorld.unitScale, GameScreen.GameWorld.unitScale)
            sprite.setSize(size.x, size.y)
            sprite.setPosition(0f, 0f)
            sprite.setOriginCenter()
//            println("=(${sprite.x}, ${sprite.y}, ${sprite.width}, ${sprite.height})")
        }
    }
}
