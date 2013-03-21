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
      Reader { e => fn(run(e)) }
    def flatMap[B](fn: A => Reader[E, B]): Reader[E, B] =
      Reader { e => fn(run(e)).run(e) }
  }

  /*** STATE ***/

  case class State[S, A](run: S => (S, A)) {
    def map[B](fn: A => B): State[S, B] =
      State { s0 =>
        val (s1, a) = run(s0)
        (s1, fn(a))
      }
    def flatMap[B](fn: A => State[S, B]): State[S, B] =
      State { s0 =>
        val (s1, a) = run(s0)
        fn(a).run(s1)
      }
  }
}