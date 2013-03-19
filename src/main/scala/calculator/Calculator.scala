package calculator

import collection.mutable.Stack

object Calculator {

  def main(args: Array[String]): Unit = args match {
    case Array(expression) =>
      // split expression into tokens
      val tokens = expression.split(" ")

      val stack = new Stack[Int]

      // for each token ...
      for (token <- tokens)
        token match {
          // if it's an operator ...
          case "+" | "-" | "*" | "/" =>
            val rhs = stack.pop()
            val lhs = stack.pop()
            token match {
              case "+" => stack.push(lhs + rhs)
              case "-" => stack.push(lhs - rhs)
              case "*" => stack.push(lhs * rhs)
              case "/" => stack.push(lhs / rhs)
            }

          // if it's maybe a number ...
          case _ => try {
            val num = token.toInt
            stack.push(num)
          } catch {
            case e: NumberFormatException =>
              throw new IllegalArgumentException("invalid token", e)
          }
        }
      println(stack.pop())

    case _ => println("Usage: Calculator <expression>")
  }

}