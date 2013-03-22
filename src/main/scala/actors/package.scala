import util.Random

import akka.actor._

package object actors {

  def fib(n: Int): BigInt = n match {
    case 0 | 1 => 1
    case _ => fib(n - 1) + fib(n - 2)
  }

  def randomFib: BigInt = fib(Random nextInt 35)

  class Logger extends Actor {
    def receive = {
      case message => println(message)
    }
  }

  case object Fib
  class Fib(logger: ActorRef) extends Actor {
    def receive = {
      case Fib =>
        logger ! randomFib
        self ! Fib
    }
  }

  /*
   * TODO
   *   0 update build.sbt with akka, and restart
   *     (maybe delete existing actor, or rename)
   *   1 replace pool with actor system
   *   2 update existing actors (ActorRef)
   */
  def main(args: Array[String]): Unit = {
    val system = ActorSystem()
    val logger = system.actorOf(Props[Logger], "logger")

    for {
      i   <- 1 to 10
      fib  = system.actorOf(Props(new Fib(logger)), s"fib-$i")
    } fib ! Fib

    readLine()
    system.shutdown
  }

}