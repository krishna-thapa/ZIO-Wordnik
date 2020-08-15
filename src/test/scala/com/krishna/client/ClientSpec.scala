package com.krishna.client

import zio.test._
import zio.clock.{ Clock, nanoTime }
import Assertion._

/*
  https://zio.dev/docs/howto/howto_test_effects
 */
object ClientSpec extends DefaultRunnableSpec {

  def spec = suite("all tests")(
    testClientResponse,
    suite2,
    suite3
  )

  val resource = ""
  val apiToken: Map[String, String] = Map("api_key" -> "API.key")

  val testClientResponse: Spec[Clock, TestFailure[Nothing], TestSuccess] = suite("Response")(
    testM("with 400") {

/*    val result = get[Option[WordOfTheDay]](resource, apiToken).orElseSucceed(None)

      for {
        res <- result
        _ <- putStrLn(res.toString)
      } yield assert(res.isDefined)(isFalse)*/

      assertM(nanoTime)(isGreaterThanEqualTo(0L))
    },
    testM("s1.t2") {
      assertM(nanoTime)(isGreaterThanEqualTo(0L))
    }
  )

  val suite2: Spec[Clock, TestFailure[Nothing], TestSuccess] = suite("suite2")(
    testM("s2.t1") {
      assertM(nanoTime)(isGreaterThanEqualTo(0L))
    },
    testM("s2.t2") {
      assertM(nanoTime)(isGreaterThanEqualTo(0L))
    },
    testM("s2.t3") {
      assertM(nanoTime)(isGreaterThanEqualTo(0L))
    }
  )

  val suite3: Spec[Clock, TestFailure[Nothing], TestSuccess] = suite("suite3")(
    testM("s3.t1") {
      assertM(nanoTime)(isGreaterThanEqualTo(0L))
    }
  )
}
