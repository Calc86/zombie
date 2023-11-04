package su.bnet.zombie.utility

class Delay(
    var delay: Float,
    private val onEnd: () -> Unit = {},
    private val onRun: () -> Unit
) {
    private var currentDelay = 0f

    private val isLock
        get() = currentDelay > 0f

    fun reset(reset: Float = 0f) {
        currentDelay = reset
    }

    fun update(deltaTime: Float) {
        currentDelay -= deltaTime
        if (currentDelay < 0f) {    // <= or < - plus one frame and onEnd routine
            currentDelay = 0f
            onEnd.invoke()
        }
    }

    fun run() {
        if (isLock) return
        currentDelay = delay
        onRun.invoke()
    }
}
