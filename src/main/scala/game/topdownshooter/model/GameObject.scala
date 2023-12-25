package game.topdownshooter.model
import game.topdownshooter.view.SpriteImage
import scalafx.geometry.{BoundingBox, Bounds}
import scalafx.scene.canvas.GraphicsContext

// GameObject is an abstract class representing a general game object within the game world
// It includes common properties and methods that all game objects must implement
abstract class GameObject(var x: Float, var y: Float, var id: ID, var s_img: SpriteImage ) {

  // Velocity of the object in the x and y directions
  var velX: Float = 0
  var velY: Float = 0

  // Method to update (tick) the state of the object
  // It must be implemented by concrete subclasses
  def tick(): Unit

  // Method to render the object on the canvas
  // It must be implemented by concrete subclasses and takes a GraphicsContext as a parameter
  def render(g: GraphicsContext): Unit

  // Method to get the bounding box of the object for collision detection
  // It must be implemented by concrete subclasses and returns a BoundingBox
  def bounds: BoundingBox
}
