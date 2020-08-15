package com.krishna

import io.circe.{ Decoder, HCursor }

package object domain {

  /*
    Response for the word of the day
   */
  case class WordOfTheDay(
    word: String,
    definitions: Seq[Definitions],
    examples: Seq[Examples],
    note: Option[String]
  )

  object WordOfTheDay {

    implicit val decodeWordOfTheDay: Decoder[WordOfTheDay] = (c: HCursor) => for {
      word <- c.downField("word").as[String]
      definitions <- c.downField("definitions").as[Seq[Definitions]]
      examples <- c.downField("examples").as[Seq[Examples]]
      note <- c.downField("note").as[Option[String]]
    } yield {
      WordOfTheDay(word, definitions, examples, note)
    }
  }

  /*
    Contains the definition of a word with part of speech
   */
  case class Definitions(
    text: Option[String],
    partOfSpeech: Option[String]
  )

  object Definitions {
    implicit val decodeDefinitions: Decoder[Definitions] = (c: HCursor) => for {
      text <- c.downField("text").as[Option[String]]
      partOfSpeech <- c.downField("partOfSpeech").as[Option[String]]
    } yield {
      Definitions(text, partOfSpeech)
    }
  }

  case class Examples(
    url: Option[String],
    text: Option[String]
  )

  /*
    Contains the examples used for the word with the source url
   */
  object Examples {
    implicit val decodeExamples: Decoder[Examples] = (c: HCursor) => for {
      url <- c.downField("url").as[Option[String]]
      text <- c.downField("text").as[Option[String]]
    } yield {
      Examples(url, text)
    }
  }

  /*
    Class for the pronunciation of the word
   */
  case class WordPronunciation (
    raw: String,
    rawType: String
  )

  object WordPronunciation {
    implicit val decodeWordPronunciation: Decoder[WordPronunciation] = (c: HCursor) => for {
      raw <- c.downField("raw").as[String]
      rawType <- c.downField("rawType").as[String]
    } yield {
      WordPronunciation(raw, rawType)
    }
  }
}
