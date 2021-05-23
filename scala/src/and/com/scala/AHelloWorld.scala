package and.com.scala;

object AHelloWorld {

  def main(args: Array[String]) {
    println("HelloWorld")

    /**
     * java 基本类型对应的Scala类
     */
    //把 Scala. Int 作为capacity的类型
    val capacity: Int = 10;

    //会自动推断
    val capability1 = 20;


    val list = new java.util.ArrayList();
    list.ensureCapacity(capability1);


    /**
     * 元组返回多个值
     */

    val (a,b,c) = getPersonInfo(1)
    println(a)
    println(b)
    println(c)
    val ret = getPersonInfo(1)
    println(ret._1)


    /**
     * 多行字符串
     */

    val s1 =  """
        hello
        hello1
        world yes
        """
    println(s1);


    /**
     * 运算符重载
     * 从 技术 的 角度 来看， Scala 没有 运算符， 提及“ 运算符 重载” 时， 指的 是 重载 像+，+- 等 这样 的 符号。 在 Scala 里， 这些 实际上 是 方 法名： 运算符 利用 了 Scala 灵活 的 方式 调用 语法—— 在 Scala 里， 对象 引用 和 方法 名 之 间的 点（.） 不是 必需 的。
     */


    /**
     * == 是equal的意思
     */

    val a1 = "hello"
    val b1 = "hello"
    val c1 = new String("hello");

    println(a1 == b1)
    println(a1 == c1)
  }


  /**
   * 元组返回多个值
   */
  def getPersonInfo(primaryKey : Int):(String,String,String) = {
     ("Andy","Bobo","Kity")
  }
}