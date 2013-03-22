import language.higherKinds

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

  trait Monad[M[_]] {
    def pure[A](a: A): M[A]
    def map[A, B](ma: M[A])(fn: A => B): M[B]
    def flatMap[A, B](ma: M[A])(fn: A => M[B]): M[B]
  }

  implicit val optionMonad: Monad[Option] = new Monad[Option] {
    def pure[A](a: A): Option[A] = Some(a)
    def map[A, B](ma: Option[A])(fn: A => B): Option[B] =
      ma map fn
    def flatMap[A, B](ma: Option[A])(fn: A => Option[B]): Option[B] =
      ma flatMap fn
  }

  def foldLeftM[A, B, M[_]](l: List[A], acc: B, fn: (B, A) => M[B])(implicit m: Monad[M]): M[B] =
    l match {
      case head :: tail => m.flatMap(fn(acc, head)) { foldLeftM(tail, _, fn) }
      case Nil => m.pure(acc)
    }

}