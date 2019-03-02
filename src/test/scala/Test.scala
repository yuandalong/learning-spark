import org.apache.commons.codec.digest.DigestUtils

/**
  * Created by ydl on 2017/3/14.
  */
object Test extends App {
  val md5 = DigestUtils.md5Hex("123")
  println(md5)
}
