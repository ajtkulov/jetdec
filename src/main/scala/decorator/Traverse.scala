package decorator

/**
  * Traverse companion object
  */
object Traverse {
  def handle(text: String)(markup: Seq[Entity])(implicit formatter: Formatters): String = {
    if (markup.nonEmpty) {
      val entities = Empty(0) +: markup.sortBy(x => x.begin) :+ Empty(text.length)
      val res = entities.zipWithIndex.drop(1).flatMap { case (item, idx) =>
        val decor = item match {
          case e: Empty => formatter.emptyFormatter.format(e)(text)
          case l: Link => formatter.linkFormatter.format(l)(text)
          case e: EntityName => formatter.entityNameFormatter.format(e)(text)
          case b: Block => formatter.blockFormatter.format(b)(text)
          case t: TwitterUserName => formatter.twitterUserNameFormatter.format(t)(text)
        }

        List(text.substring(entities(idx - 1).end, item.begin), decor)
      }
      res.mkString("")
    } else {
      text
    }
  }
}
