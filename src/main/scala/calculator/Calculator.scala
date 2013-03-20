package calculator

object Calculator {

  sealed trait Expression
  case class OperationExpression(lhs: Expression, rhs: Expression, op: Operator) extends Expression
  case class NumberExpression(value: Int) extends Expression

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

  def step(stack: List[Expression], token: String): List[Expression] =
    token match {
      case Number(num) =>
        NumberExpression(num) :: stack
      case Operator(op) =>
        stack match {
          case rhs :: lhs :: tail => OperationExpression(lhs, rhs, op) :: tail
          case _ => throw new IllegalArgumentException("not enough operands")
        }
      case _ =>
        throw new IllegalArgumentException("invalid token: " + token)
    }

  def parse(expression: String): Expression = {
    val tokens = expression.split(" ")
    val stack = tokens.foldLeft(List.empty[Expression]) { step }
    stack.head
  }

  def calculate(expression: Expression): Int =
    expression match {
      case OperationExpression(lhs, rhs, op) => op(calculate(lhs), calculate(rhs))
      case NumberExpression(num) => num
    }

  def main(args: Array[String]): Unit =
    args match {
      case Array(expression) =>
        val tree = parse(expression)
        println(calculate(tree))
      case _ => println("Usage: Calculator <expression>")
    }

}