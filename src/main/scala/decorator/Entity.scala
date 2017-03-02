package decorator

/**
  * Decorator entity
  */
sealed trait Entity {
  def begin: Int

  def end: Int

  def accept(formatter: Formatter)(text: String): String
}

/**
  * Entity name
  *
  * @param begin begin shift
  * @param end   end shift
  */
case class EntityName(begin: Int, end: Int) extends Entity {
  override def accept(formatter: Formatter)(text: String): String = formatter.visit(this)(text)
}

/**
  * Empty decorator
  *
  * @param position shift
  */
case class Empty(position: Int) extends Entity {
  override def begin: Int = position

  override def end: Int = position

  override def accept(formatter: Formatter)(text: String): String = formatter.visit(this)(text)
}

/**
  * Twitter user name decorator
  *
  * @param begin begin shift
  * @param end   end shift
  */
case class TwitterUserName(begin: Int, end: Int) extends Entity {
  override def accept(formatter: Formatter)(text: String): String = formatter.visit(this)(text)
}

/**
  * Link decorator
  *
  * @param begin begin shift
  * @param end   end shift
  */
case class Link(begin: Int, end: Int) extends Entity {
  override def accept(formatter: Formatter)(text: String): String = formatter.visit(this)(text)
}

/**
  * Block decorator
  *
  * @param begin    begin shift
  * @param end      end shift
  * @param children children decorators
  */
case class Block(begin: Int, end: Int, children: Seq[Entity]) extends Entity {
  override def accept(formatter: Formatter)(text: String): String = formatter.visit(this)(text)
}

/**
  * Markup
  *
  * @param text   raw text
  * @param markup block decorator
  */
case class Markup(text: String, markup: Block) {}
