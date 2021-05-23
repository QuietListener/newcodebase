package and.com.scala.c5

import java.util._

import and.com.scala.c5.main1.getClass;

class Test51{
  def printMethodInfo(methodName: String): Unit ={
    println("Return type of " + methodName +" is "
      + getClass().getDeclaredMethod(methodName).getReturnType().getName)
  }


  def method1(){6} //没有=不会推断返回值，默认为void
  def method2()={6}
  def method3() = 6
  def method4:Double = 6



  //接受变长参数
  def max(values: Int*) = {

    println(values)
    values.foldLeft(values(0)){Math.max}
  }

}

object main1 {
  def main(args: Array[String]): Unit = {
    val t51 = new Test51()

    t51.printMethodInfo("method1")
    t51.printMethodInfo("method2")
    t51.printMethodInfo("method3")
    t51.printMethodInfo("method4")

    val m1= t51.max(1,2,6,3)
    println(m1);

    //下面要报错
    val numbers = Array(2,4,1,5)
    //println(t51.max(numbers))

    t51.max(numbers:_*)

  }
}
