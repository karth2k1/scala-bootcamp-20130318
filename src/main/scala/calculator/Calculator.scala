package calculator

import typeclasses._

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

  def step(stack: List[Expression], token: String): Option[List[Expression]] =
    token match {
      case Number(num) =>
        Some(NumberExpression(num) :: stack)
      case Operator(op) =>
        stack match {
          case rhs :: lhs :: tail => Some(OperationExpression(lhs, rhs, op) :: tail)
          case _ => None
        }
      case _ => None
    }

  def parse(expression: String): Option[Expression] = {
    val tokens = expression.split(" ")
    val stack = foldLeftM(tokens.toList, List.empty[Expression], step)
    stack map { _.head }
  }

  def calculate(expression: Expression): Int =
    expression match {
      case OperationExpression(lhs, rhs, op) => op(calculate(lhs), calculate(rhs))
      case NumberExpression(num) => num
    }

  def main(args: Array[String]): Unit =
    args match {
      case Array(expression) =>
        parse(expression) match {
          case Some(tree) => println(calculate(tree))
          case None => println("invalid expression")
        }
      case _ => println("Usage: Calculator <expression>")
    }

}