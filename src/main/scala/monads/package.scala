package object monads {

  /*** FAILURE ***/
  
  sealed trait Option[+A] {
    def get: A
    def map[B](fn: A => B): Option[B]
    def flatMap[B](fn: A => Option[B]): Option[B]
  }

  case class Some[+A](get: A) extends Option[A] {
    def map[B](fn: A => B): Option[B] = Some(fn(get))
    def flatMap[B](fn: A => Option[B]): Option[B] = fn(get)
  }

  case object None extends Option[Nothing] {
    def get: Nothing = throw new NoSuchElementException
    def map[B](fn: Nothing => B): Option[B] = None
    def flatMap[B](fn: Nothing => Option[B]): Option[B] = None
  }

  /*** DELAYED COMPUTATION ***/
  
  case class Future[A](get: () => A) {
    def map[B](fn: A => B): Future[B] =
      Future { () => fn(get()) }
    def flatMap[B](fn: A => Future[B]): Future[B] =
      Future { () => fn(get()).get() }
  }

  /*** DEPENDENCY INJECTION ***/

  case class Reader[E, A](run: E => A) {
    def map[B](fn: A => B): Reader[E, B] =
      ??? // see Future.map
    def flatMap[B](fn: A => Reader[E, B]): Reader[E, B] =
      ??? // see Future.flatMap
  }

}