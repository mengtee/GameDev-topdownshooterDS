package game.topdownshooter.model

import game.topdownshooter.view.SpriteImage
import scalafx.geometry.BoundingBox
import scalafx.scene.canvas.GraphicsContext
import scala.util.Random

// Enemy class represents a basic enemy character in the game
// It extends the GameObject abstract class and implements its behavior
class Enemy(_x: Int, _y: Int, _id: ID, handler: Handler, var _s_img: SpriteImage) extends GameObject(_x, _y, _id, _s_img) {

  // Random object to create randomness in enemy movement and appearance
  private val r: Random = new Random()
  private var choose: Int = 0
  private var hp: Int = 100
  val enemyImageIndex = r.nextInt(3) + 1 // Randomly choose between 1, 2, and 3
  val enemy_img = s_img.grabImage(enemyImageIndex, 1, 32, 32)

  // Method to update (tick) the state of the enemy
  override def tick(): Unit = {
    x += velX
    y += velY

    // Randomly change velocity to create unpredictable movement
    choose = r.nextInt(10)
    if (choose == 0) {
      velX = (r.nextInt(8) - 4)
      velY = (r.nextInt(8) - 4)
    }

    // Check collision with walls and reverse direction
    for (tempObject <- handler.`objects` if tempObject.id == ID.Wall) {
      if (bigBounds().intersects(tempObject.bounds)) {
        x += (velX * 5) * -1
        y += (velY * 5) * -1
        velX *= -1
        velY *= -1
      }
    }

    // Check collision with ammo and decrease health points
    for (tempObject <- handler.`objects` if tempObject.id == ID.Ammo) {
      if (bounds.intersects(tempObject.bounds)) {
        hp -= 50
        handler.removeObject(tempObject)
      }
    }
    // Remove the enemy if health points reach 0 or less
    if (hp <= 0) handler.removeObject(this)
  }

  // Method to render the enemy on the canvas
  override def render(gc: GraphicsContext): Unit = {
    gc.drawImage(enemy_img, x, y)
  }

  // Method to get the bounding box of the enemy for collision detection
  def bounds: BoundingBox = new BoundingBox(x, y, 32, 32)

  // Method to get a larger bounding box for wall collision detection
  def bigBounds(): BoundingBox = new BoundingBox(x - 16, y - 16, 64, 64)
}

