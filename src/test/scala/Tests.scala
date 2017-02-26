import decorator._
import org.scalatest.FunSuite

class Tests extends FunSuite {
  implicit val format: Formatters = Formatters

  test("Decorator - main") {
    val text = """Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile"""
    val input = Markup(
      text,
      Block(0, text.length,
        List(
          EntityName(14, 22),
          EntityName(0, 5),
          TwitterUserName(56, 67),
          Link(37, 54)
        )))

    val res = Traverse.handle(input.text)(input.markup.children)

    assert(res == """<strong>Obama</strong> visited <strong>Facebook</strong> headquarters: <a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a> @<a href=”http://twitter.com/elversatile”>elversatile</a>""")
  }

  test("Decorator - Prefix/suffix") {
    val text = """Prefix Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile suffix"""
    val input = Markup(
      text,
      Block(0, text.length,
        List(
          EntityName(7 + 14, 7 + 22),
          EntityName(7 + 0, 7 + 5),
          TwitterUserName(7 + 56, 7 + 67),
          Link(7 + 37, 7 + 54)
        )))

    val res = Traverse.handle(input.text)(input.markup.children)

    assert(res == """Prefix <strong>Obama</strong> visited <strong>Facebook</strong> headquarters: <a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a> @<a href=”http://twitter.com/elversatile”>elversatile</a> suffix""")
  }

  test("Decorator - empty") {
    val text = """Prefix Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile suffix"""
    val input = Markup(
      text,
      Block(0, text.length,
        List(
        )))

    val res = Traverse.handle(input.text)(input.markup.children)

    assert(res == """Prefix Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile suffix""")
  }

  test("Decorator - One item - first") {
    val text = """Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile"""
    val input = Markup(
      text,
      Block(0, text.length,
        List(
          EntityName(0, 5)
        )))

    val res = Traverse.handle(input.text)(input.markup.children)

    assert(res == """<strong>Obama</strong> visited Facebook headquarters: http://bit.ly/xyz @elversatile""")
  }

  test("Decorator - One item - last") {
    val text = """Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile"""
    val input = Markup(
      text,
      Block(0, text.length,
        List(
          TwitterUserName(56, 67)
        )))

    val res = Traverse.handle(input.text)(input.markup.children)

    assert(res == """Obama visited Facebook headquarters: http://bit.ly/xyz @<a href=”http://twitter.com/elversatile”>elversatile</a>""")
  }
}