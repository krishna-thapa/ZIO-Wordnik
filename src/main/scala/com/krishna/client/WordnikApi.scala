package com.krishna.client

import com.krishna.http.HttpClient.{ Response, get, getList }
import com.krishna.domain.{ WordOfTheDay, WordPronunciation }

object WordnikApi extends ClientMethods {

  def getWOD: Response[WordOfTheDay] = {
    val resource: String = "words.json/wordOfTheDay"
    get[WordOfTheDay](resource, apiToken)
  }

  def getWordPronunciation(word: String): Response[Seq[WordPronunciation]] = {
    val resource: String = s"word.json/$word/pronunciations"
    getList[WordPronunciation](resource, apiToken)
      .orElseSucceed(Seq.empty)
  }
}
