package com.krishna.http

import io.circe.Decoder
import zio.Task

trait Service {
  protected val rootUrl: String = "https://api.wordnik.com/v4/"

  /**
   * @param resource : String
   * @param parameters parameters: Map[String, String]
   * url in the format "resource?key=value"
   *  implicit Decoder[T]: used to decode the json result into T.
   * @return zio.Task[T], a type alias for ZIO[Any, Throwable, T], which represents an
   *  effect that has no requirements, and may fail with a Throwable value, or succeed with a T.
   */
  def get[T](resource: String, parameters: Map[String, String])
    (implicit d: Decoder[T]): Task[T]
}