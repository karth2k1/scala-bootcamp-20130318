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

  // TODO this actor should
  //   1. compute a fibonacci number
  //   2. log the result
  //   3. somehow signal that it's ready to compute another
  // also it should be shutdown-able
  class Fib extends Actor {
    def receive(message: Any): Unit = ???
  }

  def main(args: Array[String]): Unit = {
    val pool = Executors.newCachedThreadPool
    pool execute Logger

    while (true) {
      val task = new Runnable {
        def run(): Unit = Logger ! randomFib
      }
      pool execute task
    }

    pool.shutdown()
  }

}