package game.topdownshooter.view

import game.topdownshooter.MainApp
import scalafxml.core.macros.sfxml
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.application.Platform

@sfxml
class RootLayoutController {

  // Method to show the game's settings
  // TODO: This method needs to be implemented to show the settings dialog or pane
  def showSettings(): Unit = {
    // TODO: Implement code to show settings dialog or pane
  }

  // Method to exit the game
  // Calls Platform.exit() to close the application
  def exitGame(): Unit = {
    Platform.exit()
  }

  // Method to show information about the game
  // Displays an alert with details about the game, including title, developer, and version
  def showAbout(): Unit = {
    val alert = new Alert(AlertType.Information) {
      initOwner(MainApp.stage)
      title = "About"
      headerText = "Top Down Shooter Game"
      contentText = "Game developed by Tee Meng Kiat. Version 1.0."
    }

    alert.showAndWait()
  }

}
