package com.krishna.client

import com.krishna.http.HttpClient.{ Response, get, getList }
import com.krishna.domain.{ WordAudio, WordOfTheDay, WordPronunciation }
import zio.RIO

object WordnikApi extends ClientMethods {

  def getWOD: Response[Option[WordOfTheDay]] = {
    val resource: String = "words.json/wordOfTheDay"
    // Going to make success even if it fails with response of None
    get[Option[WordOfTheDay]](resource, apiToken).orElseSucceed(None)
  }

  def getWordPronunciation(word: String): Response[Seq[WordPronunciation]] = {
    val resource: String = s"word.json/$word/pronunciations"
    getList[WordPronunciation](resource, apiToken)
      // Return success with Empty sequence if the API fails
      .orElseSucceed(Seq.empty)
  }

  def getWordAudio(word: String): Response[Option[WordAudio]] = {
    val resource: String = s"word.json/$word/audio"
    getList[WordAudio](resource, apiToken).foldM(
      // Return success with None if the API fails
      _ => RIO.succeed(None),
      s => RIO.succeed(s.headOption)
    )
  }
}
