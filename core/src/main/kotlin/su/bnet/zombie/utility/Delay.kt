package su.bnet.zombie.utility

class Delay(
    private val delay: Float,
    private val onRun: () -> Unit
) {
    private var currentDelay = 0f

    private val isLock
        get() = currentDelay > 0f

    fun update(deltaTime: Float) {
        currentDelay -= deltaTime
        if (currentDelay <= 0f) currentDelay = 0f
    }

    fun run() {
        if (isLock) return
        currentDelay = delay
        onRun.invoke()
    }
}
