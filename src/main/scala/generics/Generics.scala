package generics

object Generics {

  class Dog
  class Puppy extends Dog

  case class Box[A](var contents: A)

  val dogBox: Box[Dog] = Box(new Dog)
  val puppyBox: Box[Puppy] = Box(new Puppy)

  def getDog[A <: Dog](box: Box[A]): A = box.contents
  def putDog(box: Box[Dog]): Unit = box.contents = new Dog

  def getPuppy(box: Box[Puppy]): Puppy = box.contents
  def putPuppy[A >: Puppy](box: Box[A]): Unit = box.contents = new Puppy

  getDog(dogBox)
  getDog(puppyBox) // -- makes sense
  putDog(dogBox)
  // putDog(puppyBox) -- doesn't make sense

  getPuppy(puppyBox)
  // getPuppy(dogBox) -- doesn't make sense
  putPuppy(puppyBox)
  putPuppy(dogBox) // -- makes sense

}