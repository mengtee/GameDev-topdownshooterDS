package game.topdownshooter
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Scene, canvas => sfxCanvas, image => sfxImage, input => sfxInput, paint => sfxPaint, text => sfxText}
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import game.topdownshooter.model._
import scalafx.scene.input.KeyEvent

object MainApp extends JFXApp {
  // Load the FXML file for the main layout
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load[jfxs.layout.BorderPane]()
  val roots: jfxs.layout.BorderPane = loader.getRoot[jfxs.layout.BorderPane]()

  // Create the main stage with the loaded scene
  stage = new PrimaryStage {
    title = "Top Down Shooter Game"
    scene = new Scene(roots) {
      stylesheets += getClass.getResource("view/styles.css").toExternalForm
    }
  }

  // Create an instance of the ShooterGame
  val shooterGame = new ShooterGame()

  // Show the menu page
  def showMenuPage(): Unit = {
    println("showMenuPage method called.")
    shooterGame.stop()
    val resource = getClass.getResource("view/MenuPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  // Show the game interface/instructions
  def showGameInterface(): Unit = {
    val resource = getClass.getResource("view/GameInstruction.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  // Show the menu page initially
  showMenuPage()

  // Display the stage
  stage.show()

  // Initialize the game with initial settings
  def initializeGame(): Unit = {
    // Reset the game state and load the level
    shooterGame.stop()
    shooterGame.handler.clearObjects()
    shooterGame.ammo = 100
    shooterGame.camera.x = 0
    shooterGame.camera.y = 0
    shooterGame.loadLevel(shooterGame.worldImage)

    // Update the scene with game canvas and event handlers
    val gameScene = stage.scene()
    roots.setCenter(shooterGame.canvas)
    shooterGame.canvas.onMousePressed = shooterGame.mouseInput.mousePressedHandler
    gameScene.onKeyPressed = (event: KeyEvent) => shooterGame.keyInput.handleKeyPressed(event)
    gameScene.onKeyReleased = (event: KeyEvent) => shooterGame.keyInput.handleKeyReleased(event)
    shooterGame.start()
    shooterGame.canvas.requestFocus()

    // Update the canvas size on window resize
    stage.scene.width.onChange { (_, _, newValue) =>
      shooterGame.gameWidth = newValue.doubleValue
      shooterGame.canvas.width = shooterGame.gameWidth
    }

    stage.scene.height.onChange { (_, _, newValue) =>
      shooterGame.gameHeight = newValue.doubleValue
      shooterGame.canvas.height = shooterGame.gameHeight
    }
  }

  // Show the game over screen and stop the game loop
  def showGameOverScreen(): Unit = {
    println("showGameOverScreen method called.")
    shooterGame.stop()
    Platform.runLater {
      val resource = getClass.getResource("view/GameOver.fxml")
      val loader = new FXMLLoader(resource, NoDependencyResolver)
      loader.load()
      val gameOverRoots = loader.getRoot[jfxs.layout.AnchorPane]
      this.roots.setCenter(gameOverRoots)
    }
  }
}

