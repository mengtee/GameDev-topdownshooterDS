package game.topdownshooter.model

import game.topdownshooter.MainApp.showGameOverScreen
import game.topdownshooter.view.{ImageLoader, KeyInput, MouseInput, SpriteImage}
import scalafx.application.Platform
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.image.{Image, PixelReader}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text

// ShooterGame class represents the main game logic and state
// It includes the game loop, rendering logic, and interactions with other game objects

class ShooterGame{
  // Game dimensions
  var gameWidth: Double = 1000
  var gameHeight: Double = 563

  // Canvas and graphics context for rendering
  val canvas = new Canvas(gameWidth, gameHeight)
  val gc: GraphicsContext = canvas.graphicsContext2D

  // Game object handler, camera, key input, and initial ammo
  val handler = new Handler()
  val camera = new Camera(0, 0)
  val keyInput = new KeyInput(handler)
  var ammo = 100

  // Image loading and sprite handling
  val img_loader = new ImageLoader()
  val worldImage = img_loader.loadImage("/world.png")
  val sprite_sheet = img_loader.loadImage("/GameObjectImg.png")
  val ss = new SpriteImage(sprite_sheet)

  // Various images for rendering
  val floor_img = ss.grabImage(3, 2, 32, 32)
  val heart_img = ss.grabImage(6, 1, 32, 32)
  val brokenHeart_img = ss.grabImage(7, 1, 32, 32)

  // Load the initial level
  loadLevel(worldImage)

  // Mouse input handling
  val mouseInput = new MouseInput(handler, camera, ss)

  // Game loop control variables
  var isRunning = false
  var thread: Thread = _

  // Method to start the game loop
  def start(): Unit = {
    isRunning = true
    thread = new Thread(gameLoop)
    thread.start()

  }

  // Method to stop the game loop
  def stop(): Unit = {
    isRunning = false
    //    try {
    //      thread.join()
    //    } catch {
    //      case e: InterruptedException =>
    //        e.printStackTrace()
    //    }
  }

  // Game loop implementation
  // Includes logic for ticking, rendering, and frame rate control
  def gameLoop: Runnable = new Runnable {
    override def run(): Unit = {
      var lastTime = System.nanoTime()
      val amountOfTicks = 60.0
      val ns = 1000000000 / amountOfTicks
      var delta = 0.0
      var timer = System.currentTimeMillis()
      var frames = 0
      while (isRunning) {
        val now = System.nanoTime()
        delta += (now - lastTime) / ns
        lastTime = now
        while (delta >= 1) {
          tick()
          delta -= 1
        }
        render()
        Thread.sleep(50)
        frames += 1

        if (System.currentTimeMillis() - timer > 1000) {
          timer += 1000
          frames = 0
        }

      }
      //      stop()
    }
  }
  // Variable to store the player object
  var player: Shooter = _

  // Tick method to update the game state
  // Includes logic for camera tracking and game over condition
  def tick(): Unit = {
    for (i <- handler.`objects`.indices) {
      if (handler.`objects`(i).id == ID.Shooter) {
        camera.tick(handler.`objects`(i))
      }
      val obj = handler.`objects`(i)
      if (obj.id == ID.Shooter) {
        camera.tick(obj)
        player = obj.asInstanceOf[Shooter] // Assign to player variable
      }
    }


    if (player.hp <= 0) { // Check the player's health
      showGameOverScreen() // Show the Game Over screen if health is 0 or less
    }

    handler.tick()

  }

  // Pre-rendering the floor
  val floorCanvas = new Canvas(30 * 72, 30 * 72)
  val floorGc: GraphicsContext = floorCanvas.graphicsContext2D

  for (xx <- 0 until 30 * 72 by 32) {
    for (yy <- 0 until 30 * 72 by 32) {
      floorGc.drawImage(floor_img, xx, yy)
    }
  }

  // Render method to draw the current game state
  // Includes drawing of the floor, objects, hearts, and ammo
  def render(): Unit = {
    Platform.runLater {
      gc.fill = Color.Black
      gc.fillRect(0, 0, gameWidth, gameHeight)
      gc.save() // save current transformation

      // apply camera transformation
      gc.translate(-camera.x, -camera.y)

      // Draw the pre-rendered floor Canvas
      gc.drawImage(floorCanvas.snapshot(null, null), 0, 0)
      handler.render(gc)

      val fullHearts = player.hp
      val brokenHearts = 6 - fullHearts

      for (i <- 0 until fullHearts) {
        gc.drawImage(heart_img, camera.x + 5 + i * (heart_img.width.value + 5), camera.y + 5)
      }
      for (i <- 0 until brokenHearts) {
        gc.drawImage(brokenHeart_img, camera.x + 5 + (fullHearts + i) * (brokenHeart_img.width.value + 5), camera.y + 5)
      }
      // Draw the number of bullets (ammo) in the upper right corner
      gc.fill = Color.White // Set the text color
      val ammoText = s"Ammo: $ammo" // Prepare the text
      val textNode = new Text(ammoText) {
        font = gc.font // Set the same font used in the GraphicsContext
      }
      val textWidth = textNode.boundsInLocal.value.getWidth
      gc.fillText(ammoText, camera.x + canvas.width.value - textWidth - 5, camera.y + 20) // Draw the text

      gc.restore() // restore saved transformation
    }
  }

  // Method to load the level based on an image
  // Reads pixel colors to place game objects
  def loadLevel(image: Image): Unit = {
    val w = image.width.toInt
    val h = image.height.toInt
    val reader: PixelReader = image.pixelReader.get

    // Initialize 3 chasing enemies
    for (_ <- 0 until 3) {
      val randomX = scala.util.Random.nextInt(gameWidth.toInt)
      val randomY = scala.util.Random.nextInt(gameHeight.toInt)
      handler.addObject(new ChasingEnemy(randomX, randomY, ID.ChasingEnemy, handler, ss))
    }


    for {
      xx <- 0 until w
      yy <- 0 until h
    } {
      val pixel = reader.getColor(xx, yy)
      val red = (pixel.red * 255).toInt
      val green = (pixel.green * 255).toInt
      val blue = (pixel.blue * 255).toInt

      if (red == 255) {
        handler.addObject(new Wall(xx * 32, yy * 32, ID.Wall, ss))
      }

      if (blue == 255 && green == 0) {
        handler.addObject(new Shooter(xx * 32, yy * 32, ID.Shooter, handler, ammo, ss))
      }

      if (green == 255 && blue == 0) {
        handler.addObject(new Enemy(xx * 32, yy * 32, ID.Enemy, handler, ss))
      }

      if (green == 255 && blue == 255) {
        handler.addObject(new Bonus(xx * 32, yy * 32, ID.Bonus, ss))
      }
    }
  }
}