import Dependencies.Libraries._

resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = (project in file("."))
  .settings(
    organization := "com.krishna",
    name := "ZIO-Wordnik",
    version := "0.1",
    scalaVersion := "2.13.3",
    maxErrors := 3,
    libraryDependencies ++= Seq(
      zio,
      zioStreams,
      zioTestSbt,

      zioInteropCats,
      zioLogging,
      logback,

      http4sDsl,
      http4sClient,
      http4sCirce,

      circeCore,
      circeParser,

      typesafeConfig
    ),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )

scalacOptions --= Seq(
  "-Xfatal-warnings"
)