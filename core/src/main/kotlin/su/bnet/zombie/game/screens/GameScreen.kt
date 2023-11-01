package su.bnet.zombie.game.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import su.bnet.zombie.ecs.system.MovementSystem
import su.bnet.zombie.ecs.system.VelocitySystem
import su.bnet.zombie.ecs.system.RenderSystem
import su.bnet.zombie.ecs.system.TransformationSystem
import su.bnet.zombie.game.Player

class GameScreen : KtxScreen {
    private val camera = OrthographicCamera()
//    private val viewport = FitViewport(16f, 9f, camera)
    private val image = Texture("logo.png".toInternalFile(), true).apply { setFilter(
        Texture.TextureFilter.Linear,
        Texture.TextureFilter.Linear
    ) }
    private val batch = SpriteBatch()
    private val engine = Engine()
    private val ts = TransformationSystem()
    private val vs = VelocitySystem()
    private val ms = MovementSystem()
    private val rs = RenderSystem(camera, batch)

    override fun resize(width: Int, height: Int) {
//        viewport.update(width, height)
    }

    override fun show() {
        camera.setToOrtho(false, 16f, 9f)
        camera.update();
//        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)
//        camera.update()
        val pe = Entity()
        val player = Player(pe, Sprite(image))
        engine.addEntity(pe)
        engine.addSystem(ts)
        engine.addSystem(vs)
        engine.addSystem(ms)
        engine.addSystem(rs)
    }

    override fun render(delta: Float) {
        clearScreen(red = 1f, green = 0f, blue = 0f)
        engine.update(delta)
    }

    override fun dispose() {
        image.disposeSafely()
        batch.disposeSafely()
    }

    object World {
        val unitScale = 1/64f
    }
}