import java.io.File
import javax.imageio.ImageIO
import scala.util.Random

object Salesman extends App {
  val N = if (args.length == 0) 20 else args(0).toInt
  val M = if (args.length <= 1) 400 else args(1).toInt
  val G = if (args.length <= 2) 300 else args(2).toInt
  val rand = new Random(System.currentTimeMillis())
  val cities = (for(i <- 0 until N) yield City(M + rand.nextInt() % M, M + rand.nextInt() % M, i)).toSet.toArray

  val solution = GeneticsSolver.solve(cities, G, 30)
  val fitness = GeneticsSolver.score(solution)
  println(fitness)

  ImageIO.write(Drawer.drawImage(solution.toList, G, fitness), "PNG", new File("images/solution.png"))
}
