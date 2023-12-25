package game.topdownshooter.view

import game.topdownshooter.MainApp
import scalafxml.core.macros.sfxml

// GameInstructionController class is responsible for handling user interactions within the game instruction screen
// It provides methods to proceed to the game or go back to the main menu
@sfxml
class GameInstructionController {

  // Method to proceed to the game
  // Calls the MainApp's initializeGame method to start the game after the instructions have been shown
  def proceedToGame(): Unit = {
    MainApp.initializeGame()
  }

  // Method to go back to the main menu
  // Calls the MainApp's showMenuPage method to switch to the main menu
  def backToMenu(): Unit ={
    MainApp.showMenuPage()
  }
}
