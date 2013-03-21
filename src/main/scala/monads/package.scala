package object monads {

  sealed trait Option[+A] {
    def get: A
    def map[B](fn: A => B): Option[B]
    def flatMap[B](fn: A => Option[B]): Option[B]
  }

  case class Some[+A](get: A) extends Option[A] {
    def map[B](fn: A => B): Option[B] = ???
    def flatMap[B](fn: A => Option[B]): Option[B] = ???
  }

  case object None extends Option[Nothing] {
    def get: Nothing = throw new NoSuchElementException
    def map[B](fn: Nothing => B): Option[B] = ???
    def flatMap[B](fn: Nothing => Option[B]): Option[B] = ???
  }

}