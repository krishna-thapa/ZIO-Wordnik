package com.krishna

import com.krishna.http.HttpClient
import com.krishna.client.WordnikApi
import com.krishna.domain.WordOfTheDayResponse
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import zio._
import zio.console.putStrLn
import zio.interop.catz._
import zio.logging.Logging

import scala.concurrent.ExecutionContext.Implicits

object Main extends App {

  override def run(args: List[String]): ZIO[ZEnv, Nothing, Int] = {

    val program: ZIO[ZEnv, Throwable, Unit] = for {
      /*
        Requires a ZLayer that produces an HttpClient, which has Client[Task] as its own dependency.
       */
      htt4sClient <- makeHttpClient
      _ <- makeProgram(htt4sClient)
    } yield ()

    program.foldM(
      e => putStrLn(s"Execution failed: ${e.getMessage}") *> ZIO.succeed(1),
      _ => ZIO.succeed(0)
    )
  }

  /*
     create the Client[Task] as a managed resource first
   */
  private def makeHttpClient: UIO[TaskManaged[Client[Task]]] =
    ZIO.runtime[Any].map {
      implicit rts =>
        BlazeClientBuilder
          .apply[Task](Implicits.global)
          .resource
          .toManaged
    }

  private def makeProgram(http4sClient: TaskManaged[Client[Task]]): RIO[ZEnv, Unit] = {
    val loggerLayer = Logging.console(
      format = (_, logEntry) => logEntry,
      rootLoggerName = Some("wordnik-client:")
    )

    /*
      Sort out the layers:
     */
    val httpClientLayer = http4sClient.toLayer.orDie
    // feed our program with the new dependency(loggerLayer). The change is in the layer provided
    val http4sClientLayer = (loggerLayer ++ httpClientLayer) >>> HttpClient.http4s

    val program = for {
      wordOfDay <- WordnikApi.getWOD

      isWoDDefined: Boolean = wordOfDay.isDefined

      pronunciation <- if (isWoDDefined) {
        WordnikApi.getWordPronunciation(wordOfDay.get.word)
      } else ZIO.succeed(Seq.empty)

      audio <- if (isWoDDefined) {
        WordnikApi.getWordAudio(wordOfDay.get.word)
      } else ZIO.succeed(None)

      // Have to return this class in Inspirational-quote-api project
      result = WordOfTheDayResponse(wordOfDay, pronunciation, audio)
      _ <- putStrLn("Result case class: " + result.toString)
    } yield ()

    /*
      finally, provide our program with the required layer
     */
    program.provideSomeLayer[ZEnv](http4sClientLayer)

  }
}