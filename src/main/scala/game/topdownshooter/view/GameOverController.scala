package game.topdownshooter.view

import scalafx.scene.control.Button
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml
import game.topdownshooter.MainApp
import scalafx.scene.text.Text

// GameOverController class is responsible for handling the game over screen's user interactions
// It provides methods to restart the game or go back to the main menu
@sfxml
class GameOverController(
                          private val gameOverPane: AnchorPane, // AnchorPane containing the game over UI elements
                          private val gameOverText: Text,       // Text element for displaying game over message
                          private val replayButton: Button,     // Button to replay the game
                          private val backToMenu: Button        // Button to go back to the main menu
                        ) {

  // Method to restart the game
  // Calls the MainApp's initializeGame method to reset the game state and start a new game
  def restartGame(): Unit = {
    MainApp.initializeGame()
  }

  // Method to handle the back-to-menu button action
  // Calls the MainApp's showMenuPage method to switch to the main menu
  def handleMenuButtonAction(): Unit = {
    MainApp.showMenuPage()
  }
}
