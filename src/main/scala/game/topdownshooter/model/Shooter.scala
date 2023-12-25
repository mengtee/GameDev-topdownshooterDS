package game.topdownshooter.model

import game.topdownshooter.MainApp.shooterGame
import game.topdownshooter.view.SpriteImage
import scalafx.geometry.BoundingBox
import scalafx.scene.canvas.GraphicsContext

import scala.util.Random

// Shooter class represents the main player character in the game
// It extends the GameObject class and defines properties and behavior specific to the shooter character
class Shooter(_x: Float, _y: Float, _id: ID, handler: Handler, var ammo: Int, var _s_img: SpriteImage) extends GameObject(_x, _y, _id, _s_img) {
  val shooter_img = s_img.grabImage(4, 1, 32, 41)
  var hp: Int = 6

  // Method to update the shooter's state
  def tick(): Unit = {
    // Update position based on velocity
    x += velX.toInt
    y += velY.toInt
    collision()

    (handler.isUp, handler.isDown, handler.isRight, handler.isLeft) match {
      case (true, _, _, _) => velY = -5
      case (_, true, _, _) => velY = 5
      case (_, _, true, _) => velX = 5
      case (_, _, _, true) => velX = -5
      case _ => {
        velY = 0; velX = 0
      }
    }
  }

  // Variables to manage invincibility period after being hit
  private var lastHitTime: Long = 0
  private val invincibilityPeriod: Long = 1000

  // Handle collisions and movement
  private def collision(): Unit = {
    val objectsToRemove = scala.collection.mutable.ListBuffer[GameObject]()

    for (tempObject <- handler.`objects`) {
      if (tempObject.id == ID.Wall) {
        if (bounds.intersects(tempObject.bounds)) {
          x += (velX * -1).toInt
          y += (velY * -1).toInt
        }
      }
      if (tempObject.id == ID.Bonus) {
        if (bounds.intersects(tempObject.bounds)) {
          val rand = new Random()
          if (rand.nextInt(3) == 0) {
            // 1/3 chance to increase health
            if (hp < 6) hp += 1 // Assuming the maximum hp is 6
          } else {
            // 2/3 chance to increase bullets
            shooterGame.ammo += 10
          }
          handler.removeObject(tempObject) // Remove the crate
        }
      }
      if (tempObject.id == ID.Enemy | tempObject.id ==ID.ChasingEnemy) {
        if (bounds.intersects(tempObject.bounds)) {
          val currentTime = System.currentTimeMillis()
          if (currentTime - lastHitTime > invincibilityPeriod) {
            hp -= 1
            lastHitTime = currentTime
          }
        }
      }
    }

    objectsToRemove.foreach(handler.removeObject)

  }

  // Method to render the shooter object on the canvas
  def render(gc: GraphicsContext): Unit = {
    println(s"drawing rect at $x $y")
    gc.drawImage(shooter_img, x, y)

  }
  // Method to get the bounding box of the shooter for collision detection
  def bounds: BoundingBox = new BoundingBox(x, y, 32, 32)
}
