package and.com.scala.c5

import java.util._;

object Test {

}

object main {
  def main(args: Array[String]): Unit = {

    var list1: List[Int] = new ArrayList[Int]();
    var list2 = new ArrayList[Int]()


    list2 add 1
    list2 add 2

    var total = 0
    for (index <- 0 until list2.size()) {
      total += list2.get(index)
    }
    println("total is " + total);




    var list3 = new ArrayList[Int]()
    var list4 = new ArrayList(); //ArrayList[Nothing]  Nothing是所有类的子类

    //报错
    //list3 = list4;

    var list5 = new ArrayList[Int]()
    var list6 = new ArrayList[Any](); //ArrayList[Nothing]  Nothing是所有类的其类

    //list6 = list5;//error


    var ref1 : Int = 1
    var ref2 : Any = null
    ref2 = ref1; //ok


    /**
     * Option
     */

    def commendOrPractise( input:String)={
      if(input == "test") Some("good") else None
    };


    var list33 = new ArrayList[String]()

    list33 add "test"
    list33 add "ee"
    for(i <- 0 until list33.size()){
      val input = list33.get(i);
      val comment = commendOrPractise(input)
      println("input " +input + " comment "+ comment.getOrElse("found no comments"));
    }



  }
}
