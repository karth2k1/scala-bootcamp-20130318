import util.Random

import java.util.concurrent._

package object actors {

  def fib(n: Int): BigInt = n match {
    case 0 | 1 => 1
    case _ => fib(n - 1) + fib(n - 2)
  }

  def randomFib: BigInt = fib(Random nextInt 35)

  case object Shutdown

  object Logger extends Actor {
    def receive(message: Any): Unit =
      message match {
        case Shutdown => shutdown()
        case _ => println(message)
      }
  }

  case object Fib
  class Fib extends Actor {
    def receive(message: Any): Unit =
      message match {
        case Shutdown => shutdown()
        case Fib =>
          Logger ! randomFib
          this ! Fib
      }
  }

  def main(args: Array[String]): Unit = {
    val pool = Executors.newCachedThreadPool

    val actors = Logger +: (for (_ <- 1 to 10) yield {
      val fib = new Fib
      fib ! Fib
      fib
    })

    actors foreach { pool execute _ }
    readLine()
    actors foreach { _ ! Shutdown }

    pool.shutdown()
  }

}