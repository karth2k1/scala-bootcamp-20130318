package cookbook

class Recipe(val ingredients: List[String], val directions: List[String])

object Recipe {
  def apply(ingredients: List[String], directions: List[String]) =
    new Recipe(ingredients, directions)
  
  def unapply(recipe: Recipe): Option[(List[String], List[String])] =
    Some((recipe.ingredients, recipe.directions))
}

class Cookbook(val recipes: Map[String, Recipe])

object Cookbook {
  def apply(recipes: Map[String, Recipe]): Cookbook =
    new Cookbook(recipes)
  def empty: Cookbook =
    new Cookbook(Map.empty)

  def unapply(cookbook: Cookbook): Option[Map[String, Recipe]] =
    Some(cookbook.recipes)
}