package and.com.scala.singleInstance

class Marker1 private (val color:String) {

  println("creating " + this)
  override def toString: String = "mark color "+color;
}

/**
 * 单例模式
 */
object Marker1{
  private val markerMap = Map(
    "red"->new Marker("red"),
    "blue"->new Marker("blue")
  )


  //相当于金泰变量
  def primaryColors = "red,blue"

  //调用 Marker("blue")就调这个
  def apply(color: String)= {
    if(markerMap.contains(color))
      markerMap(color)
    else
      null
  }

  
  def getMarker(color :String )={
     if(markerMap.contains(color))
       markerMap.get(color)
     else
       null
  }
}

object main{
  def main(args: Array[String]): Unit = {
    val m = Marker1.getMarker("blue")

    println(m.toString())

    val m1 = Marker1("red")
    println(m1)

    println(Marker1.primaryColors)
  }
}
