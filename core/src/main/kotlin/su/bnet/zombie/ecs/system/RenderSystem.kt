package su.bnet.zombie.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.*
import ktx.graphics.use
import ktx.tiled.shape
import ktx.tiled.type
import su.bnet.zombie.ecs.component.RenderComponent
import su.bnet.zombie.ecs.require
import su.bnet.zombie.game.screens.GameScreen


class RenderSystem(
    private val camera: OrthographicCamera,
//    private val viewport: Viewport,
    private val batch: Batch,
    private val map: TiledMap
) : SortedIteratingSystem(family, comparator) {
    private val projectionMatrix = Matrix4()
    private val shapeRenderer = ShapeRenderer()

    //    private val map: TiledMap
    private val renderer: OrthogonalTiledMapRenderer =
        object : OrthogonalTiledMapRenderer(map, GameScreen.GameWorld.unitScale, batch) {
            override fun renderObject(o: MapObject) {
                drawShape(o.shape, shapeRenderer)
            }
        }

    private fun drawShape(shape: Shape2D, renderer: ShapeRenderer) {
        when (shape) {
            is Rectangle -> renderer.rect(shape.x, shape.y, shape.width, shape.height)
            is Ellipse -> renderer.ellipse(shape.x, shape.y, shape.width, shape.height)
            is Polygon -> renderer.polygon(shape.transformedVertices)
            is Polyline -> renderer.polyline(shape.transformedVertices)
            else -> {
                println("unknown shape $shape")
            }
        }
    }

    init {
//        map = TmxMapLoader().load("atlas/preview.tmx")
        renderer.setView(camera)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.sprite.draw(batch)
//        println("(${entity.sprite.x}, ${entity.sprite.y}, ${entity.sprite.width}, ${entity.sprite.height})")
    }

    private fun updateMatrix() {
        projectionMatrix.set(camera.combined).scale(GameScreen.GameWorld.unitScale, GameScreen.GameWorld.unitScale, 1f)
    }

    override fun update(deltaTime: Float) {
        camera.update()
        updateMatrix()
        //shapeRenderer.setProjectionMatrix(Matrix4(camera.combined))
        renderer.setView(camera)
        shapeRenderer.use(ShapeRenderer.ShapeType.Line, projectionMatrix) {
            renderer.render()
        }

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
