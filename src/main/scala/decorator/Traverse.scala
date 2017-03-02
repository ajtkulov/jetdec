package decorator

/**
  * Traverse companion object
  */
object Traverse {
  def handle(text: String)(markup: Seq[Entity])(implicit formatter: Formatter): String = {
    if (markup.nonEmpty) {
      val entities = Empty(0) +: markup.sortBy(x => x.begin) :+ Empty(text.length)
      val res = entities.zipWithIndex.drop(1).flatMap { case (item, idx) =>
        val formatted = item.accept(formatter)(text)

        List(text.substring(entities(idx - 1).end, item.begin), formatted)
      }
      res.mkString("")
    } else {
      text
    }
  }
}
