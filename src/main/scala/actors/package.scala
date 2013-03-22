import util.Random

import java.util.concurrent._
import collection.mutable

package object actors {

  def fib(n: Int): BigInt = n match {
    case 0 | 1 => 1
    case _ => fib(n - 1) + fib(n - 2)
  }

  def randomFib: BigInt = fib(Random nextInt 35)

  class Logger extends Runnable {
    private[this] val messages = mutable.Queue.empty[String]

    def log(message: String): Unit = ???
    def run(): Unit = ???
  }

  def main(args: Array[String]): Unit = {
    val pool = Executors.newFixedThreadPool(10)

    for (_ <- 1 to 20) {
      val task = new Runnable {
        def run(): Unit = println(randomFib)
      }
      pool execute task
    }

    pool.shutdown()
  }

}