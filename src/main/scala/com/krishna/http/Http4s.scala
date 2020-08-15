package com.krishna.http

import io.circe.Decoder
import org.http4s.Uri
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import zio.{ IO, Task, ZIO }
import zio.interop.catz._
import zio.logging.Logger

// An instance of this class can’t be created outside the http package
// the instance will be provided through our ZLayer
private[http] final case class Http4s(logger: Logger[String], client: Client[Task])
  extends Service
  with Http4sClientDsl[Task] {

  def get[T](resource: String, parameters: Map[String, String])
    (implicit d: Decoder[T]): Task[T] = {

    val resourceUri = Uri(path = rootUrl + resource).withQueryParams(parameters)
    logger.info(s"GET Request: $resourceUri") *>
      client
        .expect[T](resourceUri.toString())
        .foldM(
          e => logger.error(s"Request failed: bad request: ${e.getMessage}") *> ZIO.fail(e),
          ZIO.succeed(_)
        )
  }
}


/*
Note:
  http4s is built on top of the Cats Effect stack: https://zio.dev/docs/interop/interop_catseffect
 */