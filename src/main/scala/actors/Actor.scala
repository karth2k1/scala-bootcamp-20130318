package actors

trait Actor extends Runnable {
	def tell(message: Any): Unit = ???
  def run(): Unit = ???

  def receive(message: Any): Unit
}