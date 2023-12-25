package game.topdownshooter.view

import scalafx.scene.image.{Image, WritableImage}

// SpriteImage class is responsible for managing sprites within an image
// It takes an Image object and provides a method to extract specific sprites based on coordinates and dimensions
class SpriteImage(image: Image) {
  // Get the pixel reader from the given image
  // This allows us to read the pixel data of the image to extract specific parts
  private val pixelReader = image.pixelReader.get

  // Method to grab a specific sprite (sub-image) from the main image
  // col and row define the column and row of the sprite in a grid, where each sprite is assumed to be 32x32 pixels
  // width and height define the size of the sprite to be extracted
  // Returns a WritableImage containing the extracted sprite
  def grabImage(col: Int, row: Int, width: Int, height: Int): WritableImage = {
    println(s"Grabbing image at col=$col, row=$row, width=$width, height=$height")

    // Create a new WritableImage by reading the pixel data from the specified coordinates and dimensions
    // Note: Since the indexing starts at 1, we subtract 32 to get the correct starting position
    new WritableImage(pixelReader, (col * 32) - 32, (row * 32) - 32, width, height)
  }
}
