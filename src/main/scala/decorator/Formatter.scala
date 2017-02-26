package decorator

/**
  * Formatter
  *
  * @tparam T sub-type of decorator entity
  */
trait Formatter[T <: Entity] {
  def format(entity: T)(text: String): String

  def value(entity: T)(text: String): String = {
    text.substring(entity.begin, entity.end)
  }
}

/**
  * Formatters
  */
trait Formatters {

  abstract class TagFormatter[T <: Entity] extends Formatter[T] {
    val tag: String

    override def format(entity: T)(text: String): String = {
      val substr = value(entity)(text)
      s"""<${tag}>${substr}</${tag}>"""
    }
  }

  implicit val entityNameFormatter: Formatter[EntityName]
  implicit val linkFormatter: Formatter[Link]
  implicit val blockFormatter: Formatter[Block]
  implicit val twitterUserNameFormatter: Formatter[TwitterUserName]

  implicit val emptyFormatter = new Formatter[Empty] {
    override def format(entity: Empty)(text: String): String = ""
  }
}

/**
  * Implementation for Formatters
  */
object Formatters extends Formatters {
  implicit val entityNameFormatter = new TagFormatter[EntityName] {
    override val tag = "strong"
  }

  implicit val linkFormatter = new Formatter[Link] {
    override def format(entity: Link)(text: String): String = {
      val substr = value(entity)(text)
      s"""<a href=”${substr}”>${substr}</a>"""
    }
  }

  implicit val blockFormatter = new Formatter[Block] {
    override def format(entity: Block)(text: String): String = text
  }

  implicit val twitterUserNameFormatter = new Formatter[TwitterUserName] {
    override def format(entity: TwitterUserName)(text: String): String = {
      val substr = value(entity)(text)
      s"""<a href=”http://twitter.com/${substr}”>${substr}</a>"""
    }
  }
}
