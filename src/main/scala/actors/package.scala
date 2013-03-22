import util.Random

import java.util.concurrent._

package object actors {

  def fib(n: Int): BigInt = n match {
    case 0 | 1 => 1
    case _ => fib(n - 1) + fib(n - 2)
  }

  def randomFib: BigInt = fib(Random nextInt 35)

  object Logger extends Runnable {
    private val messages: BlockingQueue[String] =
      new LinkedBlockingQueue[String]

    def log(message: String): Unit = ???
    def run(): Unit =
      while (true) ???
  }

  def main(args: Array[String]): Unit = {
    val pool = Executors.newFixedThreadPool(10)
    pool execute Logger

    for (_ <- 1 to 20) {
      val task = new Runnable {
        def run(): Unit = Logger log randomFib.toString
      }
      pool execute task
    }

    // pool.shutdown()
  }

}