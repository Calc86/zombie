package su.bnet.zombie

import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.async.KtxAsync
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.require
import su.bnet.zombie.game.screens.FirstScreen
import su.bnet.zombie.game.screens.GameScreen

class ZombieGame : KtxGame<KtxScreen>() {
    override fun create() {
        KtxAsync.initiate()

//        addScreen(FirstScreen())
//        setScreen<FirstScreen>()
        addScreen(GameScreen())
        setScreen<GameScreen>()
    }
}

