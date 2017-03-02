package decorator

object Main extends App {
  override def main(args: Array[String]): Unit = {
    val input = Markup(
      """Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile""",
      Block(0, 47,
        List(
          EntityName(14, 22),
          EntityName(0, 5),
          TwitterUserName(56, 67),
          Link(37, 54)
        )))

    val res = Traverse.handle(input.text)(input.markup.children)(Formatter)
    println(res)
  }
}
