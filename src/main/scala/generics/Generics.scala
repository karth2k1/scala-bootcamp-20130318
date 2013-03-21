package generics

object Generics {

  class Dog
  class Puppy extends Dog

  trait Producer[+A] {
    def get: A = ???
  }
  trait Consumer[-A] {
    def put(value: A): Unit = ???
  }
  class Box[A] extends Producer[A] with Consumer[A]

  val dogGetBox: Producer[Dog] = new Box()
  val dogPutBox: Consumer[Dog] = new Box()
  val puppyGetBox: Producer[Puppy] = new Box()
  val puppyPutBox: Consumer[Puppy] = new Box()

  def getDog(box: Producer[Dog]): Dog = box.get
  def putDog(box: Consumer[Dog]): Unit = box.put(new Dog)

  def getPuppy(box: Producer[Puppy]): Puppy = box.get
  def putPuppy(box: Consumer[Puppy]): Unit = box.put(new Puppy)

  getDog(dogGetBox)
  getDog(puppyGetBox) // -- makes sense
  putDog(dogPutBox)
  // putDog(puppyPutBox) -- doesn't make sense

  getPuppy(puppyGetBox)
  // getPuppy(dogBox) -- doesn't make sense
  putPuppy(puppyPutBox)
  putPuppy(dogPutBox) // -- makes sense

}