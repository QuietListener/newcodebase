package and.com.scala.c6

import java.util._

object HightClassFunc {

  def totla(num:Int, codeBlock:Int=>Int):Int={

    var result = 0
    for(i <- 1 to num){
        result += codeBlock(i);
      }

    return result;
  };


  def inject(attr: Array[Int],initial:Int,operator:(Int,Int)=>Int):Int={

    var carryOver=initial;
    attr.foreach(element=>{carryOver = operator(carryOver,element)});
    return carryOver
  }

  //Curry
  def inject1(attr: Array[Int],initial:Int)(operator:(Int,Int)=>Int):Int={

    var carryOver=initial;
    attr.foreach(element=>{carryOver = operator(carryOver,element)});
    return carryOver
  }


  def injectOperator(a:Int,b:Int):Int={
    a+b
  }


  //偏应用函数
  def  log(date :Date,msg:String): Unit ={
    println(date+" ---" +msg);
  }


  def main(args: Array[String]): Unit = {

    var t1 = totla(3,i=>i);
    println(t1);
    t1 = totla(3,i=> if(i%2==0) 1 else 0);
    println(t1);

    val a1 = Array(1,2,3,2)
    val ret1 = inject(a1,0,(n1:Int,n2:Int)=>{n1+n2});
    println(ret1);

    //直接传入函数
    val ret1_1 = inject(a1,0,injectOperator);
    println(ret1_1);

    val ret2 = inject1(a1,0){(n1:Int,n2:Int)=>{n1+n2}}
    println(ret2);


    //偏应用函数
    val date = new Date()
    log(date,"msg1")
    log(date,"msg2")

    //将一个值绑定到date上，使用_使得第二个参数未被绑定
    val logWithDeteBound = log(new Date(),_:String);
    logWithDeteBound("msg11");
    logWithDeteBound("msg22");
  }
}
