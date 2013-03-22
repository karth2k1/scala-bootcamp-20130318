package object typeclasses {

	trait Monoid[A] {
    def id: A
    def append(lhs: A, rhs: A): A
  }

  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    def id: Int = 0
    def append(lhs: Int, rhs: Int): Int = lhs + rhs
  }

  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    def id: String = ""
    def append(lhs: String, rhs: String): String = lhs + rhs
  }

  implicit def listMonoid[A]: Monoid[List[A]] = new Monoid[List[A]] {
    def id: List[A] = List.empty[A]
    def append(lhs: List[A], rhs: List[A]): List[A] = lhs ::: rhs
  }

  def monoidFold[A](l: List[A])(implicit m: Monoid[A]): A =
    l.foldLeft(m.id)(m.append)

}