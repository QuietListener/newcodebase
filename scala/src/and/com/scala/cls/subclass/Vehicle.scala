package and.com.scala.cls.subclass

class Vehicle (val id: Int, val year : Int){
  override def toString() : String = "ID: " + id+ " Year: " + year
}


class Ship(override val id :Int, override val year:Int,var fuelLevel:Int) extends Vehicle(id,year){
  override def toString: String = {
    super.toString()+" Fuel level:"+fuelLevel;
  }
}
