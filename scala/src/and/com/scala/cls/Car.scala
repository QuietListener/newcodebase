package and.com.scala.cls

class Car ( val year: Int){
  private var milesDriven: Int = 0;

  def miles()=milesDriven
  def drive(distance : Int): Unit ={
    milesDriven += distance;
  }
}


