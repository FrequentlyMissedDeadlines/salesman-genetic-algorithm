import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.stream.{FileImageOutputStream, ImageOutputStream}
import scala.util.Random

object GeneticsSolver {
  def solve(cities: Array[City], generations: Int, population: Int): Array[City] = {
    var individuals = (0 until population).toList.map(_ => Random.shuffle(cities.toList).toArray)
    var bestFit = Double.MaxValue

    val output: ImageOutputStream = new FileImageOutputStream(new File("images/evolution.gif"))
    val writer: GifSequenceWriter = new GifSequenceWriter(output, BufferedImage.TYPE_INT_RGB, 1000, true)

    for (gen <- 0 until generations) {
      individuals = individuals.sortBy(score)
      val bestScore = score(individuals(0))
      if (bestScore < bestFit) {
        bestFit = bestScore
        writer.writeToSequence(Drawer.drawImage(individuals(0).toList, gen, bestFit))
        //ImageIO.write(Drawer.drawImage(individuals(0).toList, gen, bestFit), "PNG", new File("images/generation_" + gen + ".png"))
      }
      val best = individuals.take(population / 3)
      val mutants = best.map(mutation)
      val children = best.map(a => merge(a, best(Random.between(0, best.size))))
      individuals = best ::: mutants ::: children
    }

    writer.close();
    output.close();
    individuals.minBy(score)
  }

  def merge(a: Array[City], b: Array[City]): Array[City] = {
    val res = a.slice(0, a.size / 2)
    res ++ b.filter(x => res.forall(r => r.id != x.id))
  }

  def score(cities: Array[City]): Double = {
    cities.zip(cities.drop(1) :+ cities(0)).map(a => a._1.distance(a._2)).reduce((a,b) => a + b)
  }

  def mutation(cities: Array[City]): Array[City] = {
    val res = cities.clone
    val i = Random.between(0, cities.size)
    val j = Random.between(0, cities.size)

    val tmp = res(i)
    res(i) = res(j)
    res(j) = tmp

    res
  }
}