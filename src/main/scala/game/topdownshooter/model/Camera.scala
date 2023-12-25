package game.topdownshooter.model

import game.topdownshooter.MainApp
import game.topdownshooter.MainApp.shooterGame

// The Camera class is responsible for controlling the view of the game area.
// It allows the game to follow the player (Shooter) and shift the view accordingly.
class Camera(var x: Float, var y: Float) {

  // The tick method updates the camera's position based on the target GameObject (e.g., the player).
  // It smoothly moves the camera towards the player's position, keeping the player centered on the screen.
  def tick(gameObject: GameObject): Unit = {
    // Calculate the new x position of the camera, moving it closer to the player's x position
    x += (((gameObject.x - x) - shooterGame.gameWidth / 2) * 0.05f).toFloat

    // Calculate the new y position of the camera, moving it closer to the player's y position
    y += (((gameObject.y - y) - shooterGame.gameHeight / 2) * 0.05f).toFloat
  }
}
