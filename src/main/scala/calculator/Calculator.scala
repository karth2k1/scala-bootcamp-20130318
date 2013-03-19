package calculator

import collection.mutable.Stack

object Calculator {

  def handleNumber(token: String, stack: Stack[Int]): Boolean =
    try {
      stack.push(token.toInt)
      true
    } catch {
      case e: NumberFormatException => false
    }

  def handleOperator(token: String, stack: Stack[Int]): Boolean =
    token match {
      case "+" | "-" | "*" | "/" =>
        val rhs = stack.pop()
        val lhs = stack.pop()
        token match {
          case "+" =>
            stack.push(lhs + rhs)
            true
          case "-" =>
            stack.push(lhs - rhs)
            true
          case "*" =>
            stack.push(lhs * rhs)
            true
          case "/" =>
            stack.push(lhs / rhs)
            true
        }
      case _ => false
    }

  def calculate(expression: String): Int = {
    val tokens = expression.split(" ")
    val stack = new Stack[Int]

    for (token <- tokens)
      if (!handleNumber(token, stack) && !handleOperator(token, stack))
        throw new IllegalArgumentException("invalid token: " + token)

    stack.pop()
  }

  def main(args: Array[String]): Unit =
    args match {
      case Array(expression) => println(calculate(expression))
      case _ => println("Usage: Calculator <expression>")
    }

}