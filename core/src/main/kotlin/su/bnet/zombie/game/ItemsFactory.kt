package su.bnet.zombie.game

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import su.bnet.zombie.items.Bullet
import su.bnet.zombie.items.Explosion

class ItemsFactory(
    private val bulletAtlas: TextureAtlas
) {

    fun createExplosion(position: Vector2) = Explosion.create(bulletAtlas, Explosion.Type.REGULAR, position)
    fun createSonicExplosion(position: Vector2) = Explosion.create(bulletAtlas, Explosion.Type.SONIC, position)

    fun createBullet(direction: Vector2, position: Vector2) =
        Bullet(
            bulletAtlas.createSprite(
                "icon_bullet_gold_long"
            ),
            direction = direction,
            position = position,
            explosion = ::createSonicExplosion
        )
}
