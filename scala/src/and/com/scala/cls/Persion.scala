package and.com.scala.cls

class Persion (val firstName: String, val lastName:String) {
  //_表示这种列席的默认值，String默认为null
  private var position: String = _
  println("creating "+ toString())

  /**
   * 副构造方法
   * @param firstName
   * @param lastName
   * @param positionHeld
   */
  def this(firstName:String,lastName:String,positionHeld:String){
    this(firstName,lastName);
    position = positionHeld;
  }

  override def toString: String = {
    return firstName+" "+lastName+" holds "+position + " posiiton"
  }
}
