package game.topdownshooter.model
import scalafx.scene.canvas.GraphicsContext
import scala.collection.mutable

// Handler class is responsible for maintaining and managing a list of game objects
// It provides methods to update, render, add, remove, and clear game objects, and handles movement flags
class Handler {
  var objects: mutable.Buffer[GameObject] = mutable.Buffer()

  // Flags for movement directions
  private var up = false
  private var down = false
  private var left = false
  private var right = false

  // Getters and setters for movement flags
  def isUp: Boolean = up
  def setUp(up: Boolean): Unit = this.up = up

  def isDown: Boolean = down
  def setDown(down: Boolean): Unit = this.down = down

  def isLeft: Boolean = left
  def setLeft(left: Boolean): Unit = this.left = left

  def isRight: Boolean = right
  def setRight(right: Boolean): Unit = this.right = right

  // Method to update (tick) all game objects
  // Calls the tick method on each game object
  def tick(): Unit = {
    objects.foreach(_.tick())
  }
  // Method to render all game objects
  // Calls the render method on each game object, passing the graphics context
  def render(gc: GraphicsContext): Unit = {
    objects.foreach(_.render(gc))
  }

  // Method to add a new game object to the list
  def addObject(tempObject: GameObject): Unit = {
    objects = objects :+ tempObject
  }

  // Method to remove a specific game object from the list
  def removeObject(tempObject: GameObject): Unit = {
    objects = objects.filterNot(_ == tempObject)
  }

  // Method to clear all game objects from the list
  def clearObjects(): Unit = {
    objects.clear()
  }
}
