package cookbook

trait Normalizable {
  def toGrams: Gram = Gram(0)
}

sealed trait Measure {
  def value: Int = 0
}

case class Gram(override val value: Int) extends Measure with Normalizable {
  override def toGrams: Gram = this
}

case class Kilogram(override val value: Int) extends Measure with Normalizable {
  override def toGrams: Gram = Gram(value * 1000)
}

case class Recipe(ingredients: List[String], directions: List[String])

case class Cookbook(recipes: Map[String, Recipe])
