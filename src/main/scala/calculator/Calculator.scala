package calculator

object Calculator {

  object Number {
    def unapply(token: String): Option[Int] =
      try {
        Some(token.toInt)
      } catch {
        case e: NumberFormatException => None
      }
  }

  type Operator = (Int, Int) => Int

  object Operator {
    val operators = Map[String, Operator](
      "+" -> { _ + _ },
      "-" -> { _ - _ },
      "*" -> { _ * _ },
      "/" -> { _ / _ })
    def unapply(token: String): Option[Operator] =
      operators.get(token)
  }

  def step(stack: List[Int], token: String): List[Int] =
    token match {
      case Number(num) =>
        num :: stack
      case Operator(op) =>
        stack match {
          case rhs :: lhs :: tail => op(lhs, rhs) :: tail
          case _ => throw new IllegalArgumentException("not enough operands")
        }
      case _ =>
        throw new IllegalArgumentException("invalid token: " + token)
    }

  def calculate(expression: String): Int = {
    val tokens = expression.split(" ")
    val stack = tokens.foldLeft(List.empty[Int]) { step }
    stack.head
  }

  def main(args: Array[String]): Unit =
    args match {
      case Array(expression) => println(calculate(expression))
      case _ => println("Usage: Calculator <expression>")
    }

}