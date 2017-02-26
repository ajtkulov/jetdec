package decorator

/**
  * Decorator entity
  */
sealed trait Entity {
  def begin: Int

  def end: Int
}

/**
  * Entity name
  *
  * @param begin begin shift
  * @param end   end shift
  */
case class EntityName(begin: Int, end: Int) extends Entity {}

/**
  * Empty decorator
  *
  * @param position shift
  */
case class Empty(position: Int) extends Entity {
  override def begin: Int = position

  override def end: Int = position
}

/**
  * Twitter user name decorator
  *
  * @param begin begin shift
  * @param end   end shift
  */
case class TwitterUserName(begin: Int, end: Int) extends Entity {}

/**
  * Link decorator
  *
  * @param begin begin shift
  * @param end   end shift
  */
case class Link(begin: Int, end: Int) extends Entity {}

/**
  * Block decorator
  *
  * @param begin    begin shift
  * @param end      end shift
  * @param children children decorators
  */
case class Block(begin: Int, end: Int, children: Seq[Entity]) extends Entity {}

/**
  * Markup
  *
  * @param text   raw text
  * @param markup block decorator
  */
case class Markup(text: String, markup: Block) {}
