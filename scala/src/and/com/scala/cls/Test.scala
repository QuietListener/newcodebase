package and.com.scala.cls

object Test {

  def main(args: Array[String]): Unit = {
    val car = new Car(2020)
    println("Car Made in :"+car.year)
    car.drive(10);
    println("Drive for "+car.miles()+" miles")


    val persion = new Persion("andy","dofree")
    println(persion)
    val persion1 = new Persion("andy","dofree","yes")
    println(persion1)

    println(persion1.lastName);
  }

}
