package calculator

import collection.mutable.Stack

object Calculator {

  /*
   * 1. get the expression from the command line argument
   * 2. tokenize the string (split on whitespace)
   * 3. for each token:
   *    - if it's a number, push on the stack
   *    - if it's an operator, pop operands and push result
   *    - if it's garbage, throw exception
   * 4. print result, located on top of stack
   */

  def main(args: Array[String]): Unit = args match {
    case Array(expression) =>
      // split expression into tokens
      val tokens = expression.split(" ")

      // for each token ...
      for (token <- tokens)
        token match {
          // if it's an operator ...
          case "+" | "-" | "*" | "/" => println("operator!!")

          // if it's maybe a number ...
          case _ => try {
            val num = token.toInt
            println("number!!")
          } catch {
            case _: NumberFormatException => println("garbage!!")
          }
        }
    case _ => println("Usage: Calculator <expression>")
  }

}