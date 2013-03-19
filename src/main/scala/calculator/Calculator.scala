package calculator

import collection.mutable.Stack

object Calculator {

  object Number {
    def unapply(token: String): Option[Int] =
      try {
        Some(token.toInt)
      } catch {
        case e: NumberFormatException => None
      }
  }

  sealed trait Operator {
    def operate(lhs: Int, rhs: Int): Int
  }

  object Operator {
    val operators = Map(
      "+" -> Add,
      "-" -> Subtract,
      "*" -> Multiply,
      "/" -> Divide)
    def unapply(token: String): Option[Operator] =
      operators.get(token)
  }

  object Add extends Operator {
    def operate(lhs: Int, rhs: Int): Int = lhs + rhs
  }

  object Subtract extends Operator {
    def operate(lhs: Int, rhs: Int): Int = lhs - rhs
  }

  object Multiply extends Operator {
    def operate(lhs: Int, rhs: Int): Int = lhs * rhs
  }

  object Divide extends Operator {
    def operate(lhs: Int, rhs: Int): Int = lhs / rhs
  }

  def calculate(expression: String): Int = {
    val tokens = expression.split(" ")
    val stack = new Stack[Int]

    for (token <- tokens)
      token match {
        case Number(num) =>
          stack.push(num)
        case Operator(op) =>
          val rhs = stack.pop()
          val lhs = stack.pop()
          stack.push(op.operate(lhs, rhs))
        case _ =>
          throw new IllegalArgumentException("invalid token: " + token)
      }

    stack.pop()
  }

  def main(args: Array[String]): Unit =
    args match {
      case Array(expression) => println(calculate(expression))
      case _ => println("Usage: Calculator <expression>")
    }

}