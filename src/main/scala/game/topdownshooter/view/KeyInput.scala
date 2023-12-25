package game.topdownshooter.view
import game.topdownshooter.model.Handler
import scalafx.scene.input.{KeyCode, KeyEvent}


// KeyInput class is responsible for handling keyboard interactions within the game
// It detects key press and key release events to control the direction of movement
class KeyInput(handler: Handler) {

  // Method to handle key press events
  // It maps specific keys (W, A, S, D, and arrow keys) to directional movement
  // and sets the corresponding direction to true in the handler
  def handleKeyPressed(event: KeyEvent): Unit = {
    event.code match {
      case KeyCode.W | KeyCode.UP    => handler.setUp(true)
      case KeyCode.S | KeyCode.DOWN  => handler.setDown(true)
      case KeyCode.A | KeyCode.LEFT  => handler.setLeft(true)
      case KeyCode.D | KeyCode.RIGHT => handler.setRight(true)
      case _ =>
    }
  }

  // Method to handle key release events
  // It maps specific keys to directional movement
  // and sets the corresponding direction to false in the handler, stopping the movement
  def handleKeyReleased(event: KeyEvent): Unit = {
    event.code match {
      case KeyCode.W | KeyCode.UP    => handler.setUp(false)
      case KeyCode.S | KeyCode.DOWN  => handler.setDown(false)
      case KeyCode.A | KeyCode.LEFT  => handler.setLeft(false)
      case KeyCode.D | KeyCode.RIGHT => handler.setRight(false)
      case _ =>
    }
  }
}