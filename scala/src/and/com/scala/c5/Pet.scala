package and.com.scala.c5

class Pet {

}

class Dog extends Pet{

}


object main22 {
  //可接受Pet或者Pet的子类
  def workWithPets[T <: Pet](pets: Array[T]): Unit ={

  }

  def main(args: Array[String]): Unit = {
    val pet = new Pet();
    val dogs = Array(new Dog())
    workWithPets(dogs)

  }

}



