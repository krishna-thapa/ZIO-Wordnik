package com.krishna.client

import com.krishna.domain.{ WordAudio, WordOfTheDay, WordPronunciation }
import com.krishna.http.HttpClient.Response
import com.typesafe.config.{ Config, ConfigFactory }

trait ClientMethods {

  val config: Config = ConfigFactory.load("application.conf")

  val apiToken: Map[String, String] = Map("api_key" -> config.getString("API.key"))

  def getWOD: Response[Option[WordOfTheDay]]

  def getWordPronunciation(word: String): Response[Seq[WordPronunciation]]

  def getWordAudio(word: String): Response[Option[WordAudio]]
}
