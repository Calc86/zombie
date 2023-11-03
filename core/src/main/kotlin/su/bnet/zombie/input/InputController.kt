package su.bnet.zombie.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import ktx.app.KtxInputAdapter

class InputController: KtxInputAdapter {
    init {
        Gdx.input.isButtonPressed(Input.Buttons.LEFT)
    }

}
