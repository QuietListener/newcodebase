package and.com.scala.singleInstance

class Marker(val color:String) {
  override def toString: String = "mark color "+color;
}

/**
 * 单例模式
 */
object MarkerFactory{
  private val markerMap = Map(
    "red"->new Marker("red"),
    "blue"->new Marker("blue")
  )


  def getMarker(color :String )={
     if(markerMap.contains(color))
       markerMap.get(color)
     else
       null
  }
}

object main{
  def main(args: Array[String]): Unit = {
    val m = MarkerFactory.getMarker("blue")

    println(m.toString())
  }
}
