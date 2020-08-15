package com.krishna.client

import com.krishna.domain.{ WordOfTheDay, WordPronunciation }
import com.krishna.http.HttpClient.Response

trait ClientMethods {

  val apiToken: Map[String, String] = Map("api_key" -> "u1m1rcn6yqik1ti4wt0mb7ltqcz2gtp0xnbnacly5l05mgiis")

  def getWOD: Response[WordOfTheDay]

  def getWordPronunciation(word: String): Response[Seq[WordPronunciation]]
}
