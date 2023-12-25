package game.topdownshooter.model
import game.topdownshooter.view.SpriteImage
import scalafx.geometry.BoundingBox
import scalafx.scene.canvas.GraphicsContext

// Wall class represents a wall object within the game
// It extends the GameObject class and defines the properties and behavior specific to walls
class Wall(_x: Int, _y: Int, _id: ID, _s_img: SpriteImage) extends GameObject(_x, _y, _id, _s_img) {

  // Grab the specific image for the wall block from the sprite sheet
  val block_img = s_img.grabImage(2, 2, 32, 32)

  // Tick method for updating the wall object's state
  // Walls do not have any dynamic behavior, so this method is empty
  def tick(): Unit = {}

  // Render method for drawing the wall object on the canvas
  // Draws the block image at the wall's x and y coordinates
  def render(gc: GraphicsContext): Unit = gc.drawImage(block_img, x, y)

  // Method to get the bounding box of the wall for collision detection
  // Returns a BoundingBox object representing the wall's position and size (32x32)
  override def bounds(): BoundingBox = new BoundingBox(x, y, 32, 32)
}
