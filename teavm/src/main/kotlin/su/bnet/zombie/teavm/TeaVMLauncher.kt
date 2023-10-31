@file:JvmName("TeaVMLauncher")

package su.bnet.zombie.teavm

import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration
import com.github.xpenatan.gdx.backends.teavm.TeaApplication
import su.bnet.zombie.ZombieGame

/** Launches the TeaVM/HTML application. */
fun main() {
    val config = TeaApplicationConfiguration("canvas").apply {
        width = 640
        height = 480
    }
    TeaApplication(ZombieGame(), config)
}
