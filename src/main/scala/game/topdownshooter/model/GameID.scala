package game.topdownshooter.model

// ID is a sealed trait representing the unique identification for different types of game objects
// It is used to categorize the various game objects within the game world, allowing for specific behavior and interactions
sealed trait ID

object ID {
  // Shooter object represents the main player character
  case object Shooter extends ID

  // Wall object represents a solid obstacle in the game world
  case object Wall extends ID

  // Ammo object represents ammunition that can be used by the shooter
  case object Ammo extends ID

  // Enemy object represents a basic enemy type
  case object Enemy extends ID

  // ChasingEnemy object represents a type of enemy that chases the player
  case object ChasingEnemy extends ID

  // Bonus object represents a bonus or power-up that can be collected by the player
  case object Bonus extends ID
}
