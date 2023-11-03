package su.bnet.zombie.input

import com.badlogic.gdx.Input

enum class Inputs(val keysCode: List<Int>) {
    LEFT(listOf(Input.Keys.LEFT, Input.Keys.A)),
    RIGHT(listOf(Input.Keys.RIGHT, Input.Keys.D)),
    UP(listOf(Input.Keys.UP, Input.Keys.W)),
    DOWN(listOf(Input.Keys.DOWN, Input.Keys.S)),
    FIRE(listOf(Input.Keys.SPACE)),
    ACTION(listOf(Input.Keys.E)),
}
