package su.bnet.zombie.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import ktx.graphics.use
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.require
import su.bnet.zombie.game.screens.GameScreen


class RenderSystem(
    private val camera: OrthographicCamera,
//    private val viewport: Viewport,
    private val batch: Batch
) : SortedIteratingSystem(family, comparator) {
    private val map: TiledMap
    private val renderer: OrthogonalTiledMapRenderer
    init {
        map = TmxMapLoader().load("atlas/preview.tmx")
        renderer = OrthogonalTiledMapRenderer(map, GameScreen.GameWorld.unitScale, batch)
        renderer.setView(camera)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.sprite.draw(batch)
//        println("(${entity.sprite.x}, ${entity.sprite.y}, ${entity.sprite.width}, ${entity.sprite.height})")
    }

    override fun update(deltaTime: Float) {
        camera.update()
        renderer.setView(camera)
        renderer.render()
        batch.use(camera) {
            super.update(deltaTime)
        }
    }

    private val Entity.sprite
        get() = RenderComponent.on(this).require().sprite

    class RenderComparator : Comparator<Entity> {
        override fun compare(o1: Entity, o2: Entity): Int {
            // todo
            return 0
        }
    }

    companion object {
        private val family = Family.all(RenderComponent::class.java).get()
        private val comparator = RenderComparator()
    }
}
