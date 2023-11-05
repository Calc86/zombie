package su.bnet.zombie.game.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import su.bnet.zombie.ecs.system.*
import su.bnet.zombie.game.ItemsFactory
import su.bnet.zombie.game.Player
import su.bnet.zombie.items.Explosion
import su.bnet.zombie.utility.MyAnimation


class GameScreen : KtxScreen {
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(World.Screen.width, World.Screen.height, camera)
    private val image = Texture("logo.png".toInternalFile(), true).apply {
        setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        )
    }
    private val personAtlas = TextureAtlas(Gdx.files.internal("atlas/person.atlas"))
    private val fireAtlas = TextureAtlas(Gdx.files.internal("atlas/fire.atlas"))
    private val playerSprite = personAtlas.createSprite("hitman1_gun").apply {
        //setOriginCenter()
        //rotation = 70f
    }

    private val batch = SpriteBatch()
    private val engine = Engine()


    private val gis = GameInputSystem(camera)
    private val es = EventSystem(engine)
    private val act = ActSystem()
    private val ts = TransformationSystem()
    //private val vs = VelocitySystem()
    private val ms = MovementSystem()
    private val rs = RenderSystem(camera, batch)

    private val creator = ItemsFactory(fireAtlas)

    lateinit var player: Player
    lateinit var blow: MyAnimation
    lateinit var explosion: Explosion

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun show() {
        camera.setToOrtho(false, World.Screen.width, World.Screen.height)
        camera.update();
//        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)
//        camera.update()
        Gdx.input.inputProcessor = gis;
        val pe = Entity()

        player = Player(pe, playerSprite, creator)
        explosion = creator.createSonicExplosion()
        blow = MyAnimation(Sprite(image), fireAtlas.findRegions("regularExplosion"), 2f)

        engine.addEntity(pe)
//        engine.addEntity(blow.entity)
//        engine.addEntity(explosion.entity)

        engine.addSystem(gis)
        engine.addSystem(act)
        engine.addSystem(es)
        engine.addSystem(ts)
        //engine.addSystem(vs)
        engine.addSystem(ms)
        engine.addSystem(rs)
    }

    override fun render(delta: Float) {
        clearScreen(red = 1f, green = 0f, blue = 0f)
        camera.position.set(player.position.x, player.position.y, 0f)
        engine.update(delta)
    }

    override fun dispose() {
        image.disposeSafely()
        batch.disposeSafely()
    }

    object World {
        const val unitScale = 1 / 64f

        object Screen {
            const val width = 16f
            const val height = 9f
//            const val width = 9f / 2
//            const val height = 16f / 2
        }

    }
}
