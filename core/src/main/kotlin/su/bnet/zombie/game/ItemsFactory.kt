package su.bnet.zombie.game

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import su.bnet.zombie.items.Explosion

class ItemsFactory(
    private val bulletAtlas: TextureAtlas
) {

    fun createExplosion() = Explosion.create(bulletAtlas, Explosion.Type.REGULAR)
    fun createSonicExplosion() = Explosion.create(bulletAtlas, Explosion.Type.SONIC)
}
