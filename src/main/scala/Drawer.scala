import java.awt.Color
import java.awt.geom._
import java.awt.image.BufferedImage

object Drawer {
  def drawImage(cities: List[City], generation: Int, fitness: Double): BufferedImage = {
    val width = 800
    val height = 900
    val image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()

    graphics.setPaint(Color.white)
    graphics.fill(new Rectangle2D.Float(0f, 0f, width, height))

    graphics.setPaint(Color.blue)
    cities.zip(cities.drop(1) :+ cities(0)).map(a => {
      graphics.draw(new Line2D.Float(a._1.x, a._1.y, a._2.x,a._2.y))
    })

    graphics.setPaint(Color.red)
    cities.map(c => {
      graphics.fill(new Ellipse2D.Float(c.x - 5f, c.y - 5f, 10f, 10f))
    })

    graphics.setPaint(Color.black)
    graphics.drawString("Gen: " + generation + " fitness: " + fitness, 0, 850)

    image
  }
}
