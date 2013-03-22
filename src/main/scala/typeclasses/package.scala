import language.higherKinds

package object typeclasses {

  /*** SAFE EQUALITY (not really a type class) ***/

  implicit class SafeEqual[A](lhs: A) {
    def ===(rhs: A): Boolean = lhs == rhs
  }

  /*** MONOID ***/

  trait Monoid[A] {
    def id: A
    def append(lhs: A, rhs: A): A
  }

  implicit class MonoidOps[A](lhs: A)(implicit m: Monoid[A]) {
    def +(rhs: A): A = m.append(lhs, rhs)
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

  /*** MONAD ***/

  trait Monad[M[_]] {
    def pure[A](a: A): M[A]
    def map[A, B](ma: M[A])(fn: A => B): M[B]
    def flatMap[A, B](ma: M[A])(fn: A => M[B]): M[B]
  }

  implicit class MonadOps[M[_], A](ma: M[A])(implicit m: Monad[M]) {
    def map[B](fn: A => B): M[B] = m.map(ma)(fn)
    def flatMap[B](fn: A => M[B]): M[B] = m.flatMap(ma)(fn)
  }

  type Id[A] = A

  implicit val idMonad: Monad[Id] = new Monad[Id] {
    def pure[A](a: A): A = a
    def map[A, B](a: A)(fn: A => B): B = fn(a)
    def flatMap[A, B](a: A)(fn: A => B): B = fn(a)
  }

  implicit val optionMonad: Monad[Option] = new Monad[Option] {
    def pure[A](a: A): Option[A] = Some(a)
    def map[A, B](ma: Option[A])(fn: A => B): Option[B] =
      ma map fn
    def flatMap[A, B](ma: Option[A])(fn: A => Option[B]): Option[B] =
      ma flatMap fn
  }

  /*** FOLDABLE ***/

  trait Foldable[F[_]] {
    def foldLeftM[A, B, M[_]](fa: F[A], acc: B)(fn: (B, A) => M[B])(implicit m: Monad[M]): M[B]

    def foldLeft[A, B](fa: F[A], acc: B)(fn: (B, A) => B): B =
      foldLeftM[A, B, Id](fa, acc)(fn)

    def monoidFoldLeft[A](fa: F[A])(implicit m: Monoid[A]): A =
      foldLeft(fa, m.id)(m.append)
  }

  implicit class FoldableOps[F[_], A](fa: F[A])(implicit f: Foldable[F]) {
    def foldLeftM[B, M[_]](acc: B)(fn: (B, A) => M[B])(implicit m: Monad[M]): M[B] = f.foldLeftM(fa, acc)(fn)
    def foldLeft[B](acc: B)(fn: (B, A) => B): B = f.foldLeft(fa, acc)(fn)
    def monoidFoldLeft(implicit m: Monoid[A]): A = f.monoidFoldLeft(fa)
  }

  implicit val listFoldable: Foldable[List] = new Foldable[List] {
    def foldLeftM[A, B, M[_]](l: List[A], acc: B)(fn: (B, A) => M[B])(implicit m: Monad[M]): M[B] =
      l match {
        case head :: tail => fn(acc, head) flatMap { foldLeftM(tail, _)(fn) }
        case Nil => m.pure(acc)
      }
  }

  /*** JSON ***/

  trait Json[A] {
    def toJson(a: A): String
    def fromJson(json: String): Option[A]
  }

  implicit class JsonOps[A](a: A)(implicit ev: Json[A]) {
    def toJson: String = ev.toJson(a)
  }

  def fromJson[A](json: String)(implicit ev: Json[A]): Option[A] =
    ev.fromJson(json)

  implicit val intJson: Json[Int] = new Json[Int] {
    def toJson(a: Int): String =
      a.toString
    def fromJson(json: String): Option[Int] =
      try {
        Some(json.toInt)
      } catch {
        case _: NumberFormatException => None
      }
  }

  implicit val stringJson: Json[String] = new Json[String] {
    def toJson(a: String): String = ???
    def fromJson(json: String): Option[String] = ???
  }








}