package actors

import java.util.concurrent.{ BlockingQueue, LinkedBlockingQueue }

trait Actor extends Runnable {
  private[this] var isShutdown = false
  private[this] val messages: BlockingQueue[Any] =
    new LinkedBlockingQueue[Any]

	final def tell(message: Any): Unit =
    messages.put(message)

  final def !(message: Any): Unit = tell(message)

  final def run(): Unit =
    while (!isShutdown) receive(messages.take())

  protected[this] def shutdown(): Unit =
    isShutdown = true

  protected[this] def receive(message: Any): Unit
}
