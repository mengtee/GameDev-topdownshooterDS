package game.topdownshooter.model
import game.topdownshooter.view.SpriteImage
import scalafx.geometry.BoundingBox
import scalafx.scene.canvas.GraphicsContext

import scala.util.Random

// The Ammo class represents a projectile (ammo) in the game, fired by the player's character.
class Ammo(_x: Float, _y: Float, _id: ID, _s_img: SpriteImage, handler: Handler, mx: Int, my: Int) extends GameObject(_x, _y, _id, _s_img) {
  // Determine the velocity of the ammo based on the target coordinates (mx, my).
  var velX1: Float = ((mx - x) / 10)
  var velY1: Float = ((my - y) / 10)
  private val r: Random = new Random()

  // Randomly select an image for the ammo from a specific range of rows and columns.
  val ammo_row = r.nextInt(2)+ 1
  val ammo_col = r.nextInt(2)+ 5
  val ammo_img = s_img.grabImage(ammo_col, ammo_row, 32, 32)

  // Update the ammo's position based on its velocity.
  override def tick(): Unit = {
    x += velX1
    y += velY1

    // Check for collisions with walls and remove the ammo if it hits one.
    for (tempObject <- handler.`objects`.toList) {
      if (tempObject.id == ID.Wall) {
        if (bounds().intersects(tempObject.bounds)) {
          handler.removeObject(this)
        }
      }
    }
  }

  // Render the ammo by drawing its image at its current position.
  override def render(gc: GraphicsContext): Unit = {
    gc.drawImage(ammo_img, x, y, 8, 8)
  }

  // Define the bounding box for collision detection. The size is set to 8x8.
  override def bounds(): BoundingBox = new BoundingBox(x, y, 8, 8)
}
