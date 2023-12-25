package game.topdownshooter.model

import game.topdownshooter.view.SpriteImage
import scalafx.geometry.BoundingBox
import scalafx.scene.canvas.GraphicsContext
import scala.util.Random

// The Bonus class represents a bonus item in the game that the player can collect.
// Bonus items can have different effects, such as increasing the player's health or ammo.
class Bonus(_x: Float, _y: Float, _id: ID, var _s_img: SpriteImage) extends GameObject(_x, _y, _id, _s_img) {
  // Randomly choose an image index to determine the appearance of the bonus item.
  private val r: Random = new Random()
  val ImageIndex = r.nextInt(2) + 1
  val bonus_img = s_img.grabImage(8,ImageIndex, 32, 32)

  // The tick method is empty as the bonus item does not have any behavior that needs to be updated each tick.
  override def tick(): Unit = {}

  // Render the bonus item by drawing its image at its current position.
  override def render(gc: GraphicsContext): Unit = {
    gc.drawImage(bonus_img, x, y)
  }

  // Define the bounding box for collision detection. The size is set to 8x8.
  override def bounds(): BoundingBox = new BoundingBox(x, y, 8, 8)
}
