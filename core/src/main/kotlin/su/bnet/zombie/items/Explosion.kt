package su.bnet.zombie.items

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite
import su.bnet.zombie.ecs.component.ActComponent
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.component.TransformationComponent
import su.bnet.zombie.game.screens.GameScreen

class Explosion(
    textures: Array<TextureAtlas.AtlasRegion>,
    duration: Float,
    playMode: PlayMode = PlayMode.LOOP
): TtlItem(duration) {
    private val frameDuration = duration / textures.size
    private val animation = Animation<TextureRegion>(frameDuration, textures, playMode)
    private var sprite = AnimatedSprite(animation).apply {
        isCenterFrames = true
        isUseFrameRegionSize = true
    }
    private val rc = RenderComponent(sprite)
    private val tc = TransformationComponent()
//    private val ac = ActComponent(::onAct)

    init {
        entity.apply {
            add(rc)
            add(tc)
//            add(ac)
        }
    }

    override fun onAct(deltaTime: Float) {
        super.onAct(deltaTime)
        sprite.update(deltaTime)
        tc.apply {
//            size.set(sprite.width * GameScreen.World.unitScale, sprite.height * GameScreen.World.unitScale)
            size.set(sprite.width, sprite.height)
            scale.set(GameScreen.World.unitScale, GameScreen.World.unitScale)
            sprite.setSize(size.x, size.y)
            sprite.setPosition(0f, 0f)
            sprite.setOriginCenter()
//            println("=(${sprite.x}, ${sprite.y}, ${sprite.width}, ${sprite.height})")
        }
    }

    enum class Type(
        val sets: String,
        val duration: Float,
    ) {
        REGULAR("regularExplosion", 2f),
        SONIC("sonicExplosion", 1.5f)
    }

    companion object {
        fun create(atlas: TextureAtlas, type: Type) = Explosion(atlas.findRegions(type.sets), type.duration)
    }
}
