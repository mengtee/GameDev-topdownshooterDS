package game.topdownshooter.view

import game.topdownshooter.MainApp.shooterGame
import game.topdownshooter.model.{Ammo, Camera, Handler, ID}
import scalafx.scene.input.MouseEvent

// MouseInput class is responsible for handling mouse interactions within the game, specifically mouse-press events
// It works in conjunction with the Handler and Camera classes to detect mouse clicks and interact with game objects
class MouseInput(handler: Handler, camera: Camera, var ss: SpriteImage) {

  // Mouse press event handler
  // This function is triggered when the mouse is pressed within the game canvas
  // It calculates the mouse position, checks if the Shooter object is clicked and has ammo,
  // and then adds an Ammo object to the handler at the clicked position
  val mousePressedHandler: (MouseEvent) => Unit = (event: MouseEvent) => {
    // Calculate the absolute mouse position by adding the camera's position
    val mx = (event.x + camera.x).toInt
    val my = (event.y + camera.y).toInt

    // Iterate through the objects in the handler
    for (i <- handler.`objects`.indices) {
      val tempObject = handler.`objects`(i)

      // If the object is a Shooter and there is available ammo
      if (tempObject.id == ID.Shooter && shooterGame.ammo >= 1) {
        // Add an Ammo object at the Shooter's position, directed towards the clicked position
        handler.addObject(new Ammo(tempObject.x + 16, tempObject.y + 24, ID.Ammo, ss, handler, mx, my))

        // Decrease the ammo count by 1
        shooterGame.ammo -= 1
      }
    }
  }
}
