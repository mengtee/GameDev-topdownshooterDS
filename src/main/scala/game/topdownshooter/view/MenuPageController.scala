package game.topdownshooter.view

import game.topdownshooter.MainApp
import javafx.fxml.FXML
import scalafx.scene.text.Text
import scalafx.animation.TranslateTransition
import scalafxml.core.macros.sfxml
import scalafx.util.Duration
import scalafx.event.ActionEvent

@sfxml
class MenuPageController() {

  @FXML
  var animatedText: Text = _


  def play(actionEvent: ActionEvent): Unit = {
    MainApp.showGameInterface()
  }

}

