package decorator

/**
  * Formatters
  */
trait Formatter {
  def visit(block: Block)(text: String): String

  def visit(empty: Empty)(text: String): String

  def visit(entityName: EntityName)(text: String): String

  def visit(twitterUserName: TwitterUserName)(text: String): String

  def visit(link: Link)(text: String): String

  def substr(entity: Entity, text: String): String = {
    text.substring(entity.begin, entity.end)
  }
}

/**
  * Implementation for Formatters
  */
object Formatter extends Formatter {

  override def visit(block: Block)(text: String): String = text

  override def visit(empty: Empty)(text: String): String = ""

  override def visit(entityName: EntityName)(text: String): String = s"""<strong>${substr(entityName, text)}</strong>"""

  override def visit(twitterUserName: TwitterUserName)(text: String): String = {
    val str = substr(twitterUserName, text)
    s"""<a href=”http://twitter.com/$str”>$str</a>"""
  }

  override def visit(link: Link)(text: String): String = {
    val str = substr(link, text)
    s"""<a href=”$str”>$str</a>"""
  }
}
