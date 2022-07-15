case class City(x: Int, y: Int, id: Int) {
  def distance(other: City): Double = {
    Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))
  }
}
