package com.krishna.http

import io.circe.Decoder
import org.http4s.client.Client
import zio.logging.{ Logger, Logging }
import zio.{ Has, RIO, Task, URLayer, ZLayer }

object HttpClient {

  // allows us to use our Service as a dependency.
  type HttpClient = Has[Service]

  type Response[T] = RIO[HttpClient, T]

  /*
    RIO.accessM[HttpClient] effectively accesses the environment of our effect,
    giving us Has[Service], so we call the first get to access the effect wrapped
    by Has - our Service - while the second get is the actual get request.
   */
  def get[T](resource: String, parameters: Map[String, String])
    (implicit d: Decoder[T]): Response[T] =
    RIO.accessM[HttpClient](_.get.get[T](resource, parameters))

  def getList[T](resource: String, parameters: Map[String, String])
    (implicit d: Decoder[T]): Response[Seq[T]] = {
    RIO.accessM[HttpClient](_.get.get[Seq[T]](resource, parameters))
  }

  /*
    method will create a ZLayer, which is very similar to ZIO data type
    def http4s: ZLayer[Has[Client[Task]], Nothing, HttpClient] = ???

    use type aliases to make ZLayer more expressive as well.
    Knowing our layer can’t fail, we can use URLayer instead of ZLayer

    this layer makes our HttpClient available
   */
  def http4s: URLayer[Logging with Has[Client[Task]], HttpClient] = {
    ZLayer.fromServices[Logger[String], Client[Task], Service] {
      (logger, htt4sClient) => Http4s(logger, htt4sClient)
    }
  }
}
