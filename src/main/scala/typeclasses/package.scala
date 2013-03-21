package object typeclasses {

	trait Monoid[A] {
    def id: A
    def append(lhs: A, rhs: A): A
  }

  // TODO:
  // 1. implement Monoid instances for Int and String
  // 2. implement monoidFold

  implicit val intMonoid: Monoid[Int] = ???
  implicit val stringMonoid: Monoid[String] = ???

  def monoidFold[A](l: List[A])(implicit m: Monoid[A]): A = ???

}