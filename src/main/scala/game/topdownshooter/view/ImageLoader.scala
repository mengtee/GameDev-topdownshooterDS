package game.topdownshooter.view
import scalafx.scene.image.Image

// ImageLoader class is responsible for loading images from a given path
// It provides a method to load an image and handles any exceptions that may occur during the loading process
class ImageLoader {

  // Variable to hold the loaded image
  private var image: Image = _

  // Method to load an image from the specified path
  // Returns the loaded Image object
  // If an exception occurs during loading, an error message is printed and the method returns null
  def loadImage(path: String): Image = {
    try {
      // Attempt to load the image from the given path
      image = new Image(getClass.getResource(path).toString)
      println(s"Image loaded successfully from path: $path")
      image
    } catch {
      // Handle any exceptions that may occur during loading
      case ex: Exception =>
        println(s"Error loading image from path: $path")
        ex.printStackTrace()
        image
    }
  }
}
