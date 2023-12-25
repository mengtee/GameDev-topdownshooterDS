package game.topdownshooter.model

import game.topdownshooter.view.SpriteImage
import scalafx.geometry.BoundingBox
import scalafx.scene.canvas.GraphicsContext

// ChasingEnemy class represents an enemy character that chases the player (Shooter) in the game
// It extends the GameObject abstract class and implements its behavior
class ChasingEnemy(_x: Int, _y: Int, _id: ID, handler: Handler, var _s_img: SpriteImage) extends GameObject(_x, _y, _id, _s_img) {
  private var hp: Int = 100
  val enemy_img = s_img.grabImage(1, 2, 32, 32)

  // Method to update (tick) the state of the chasing enemy
  override def tick(): Unit = {

    // Find the player (Shooter) object in the game
    val shooter = handler.objects.find(_.id == ID.Shooter)
    shooter.foreach { player =>
      val diffX = player.x - x
      val diffY = player.y - y
      val distance = math.sqrt(diffX * diffX + diffY * diffY)

      // Determine the velocity to move towards the player
      velX = ((diffX / distance) * 3).toFloat
      velY = ((diffY / distance) * 3).toFloat

      // Tentative new position
      val newX = x + velX
      val newY = y + velY

      // Check if the new position would collide with a wall
      val collidesWithWall = handler.objects.exists {
        case obj if obj.id == ID.Wall => new BoundingBox(newX, newY, 32, 32).intersects(obj.bounds)
        case _ => false
      }

      // If there is no collision, move to the new position
      if (!collidesWithWall) {
        x = newX
        y = newY
      } else {
        // Otherwise, try to find a new direction to move in
        val alternateDirections = List(
          (velY, velX), // Rotate 90 degrees
          (-velY, -velX), // Rotate 180 degrees
          (velY * -1, velX * -1) // Rotate -90 degrees
        )

        // Try each alternate direction and use the first one that doesn't collide
        alternateDirections.find {
          case (altX, altY) =>
            !handler.objects.exists {
              case obj if obj.id == ID.Wall => new BoundingBox(x + altX, y + altY, 32, 32).intersects(obj.bounds)
              case _ => false
            }
        }.foreach {
          case (altX, altY) =>
            x += altX
            y += altY
        }
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

  // Method to render the chasing enemy on the canvas
  override def render(gc: GraphicsContext): Unit = {
    gc.drawImage(enemy_img, x, y)
  }

  // Method to get the bounding box of the chasing enemy for collision detection
  def bounds: BoundingBox = new BoundingBox(x, y, 32, 32)

  // Method to get a larger bounding box for wall collision detection
  def bigBounds(): BoundingBox = new BoundingBox(x - 16, y - 16, 64, 64)

}