import sbt._

object Dependencies {

  object Versions {
    val tapirVersion = "0.17.12"
    val zioVersion   = "1.0.5"
    val circe        = "0.13.0"
    val sttp         = "3.1.1"
  }

  lazy val logBack        = "ch.qos.logback" % "logback-core" % "1.2.3"
  lazy val logBackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"
  lazy val slf4j          = "org.slf4j" % "slf4j-api" % "1.7.30"
  lazy val scalaTest      = "org.scalatest" %% "scalatest" % "3.2.6" % Test
  lazy val scalaCheck     = "org.scalatestplus" %% "scalacheck-1-14" % "3.1.0.0" % Test
  lazy val diffx          = "com.softwaremill.diffx" %% "diffx-scalatest" % "0.3.29" % Test

  lazy val newType = "io.estatico" %% "newtype" % "0.4.4"
  lazy val cats    = "org.typelevel" %% "cats-core" % "2.1.1"
  lazy val zio     = "dev.zio" %% "zio" % Versions.zioVersion

  lazy val zioInteropCats = "dev.zio" %% "zio-interop-cats" % "2.3.1.0"

  lazy val testDependencies: Seq[ModuleID] = Seq(scalaTest, scalaCheck, diffx)

  lazy val commonDependencies: Seq[ModuleID] =
    Seq(cats, zio, newType, logBack, logBackClassic, slf4j)

  lazy val tapirDependencies: Seq[ModuleID] = Seq("com.softwaremill.sttp.tapir" %% "tapir-zio",
                                                  "com.softwaremill.sttp.tapir" %% "tapir-zio-http4s-server",
                                                  "com.softwaremill.sttp.tapir" %% "tapir-json-circe",
                                                  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-http4s",
                                                  "com.softwaremill.sttp.tapir" %% "tapir-redoc-http4s",
                                                  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs",
                                                  "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml").map(_ % Versions.tapirVersion)

  val circleDependencies: Seq[ModuleID] = Seq("io.circe" %% "circe-core",
                                              "io.circe" %% "circe-generic",
                                              "io.circe" %% "circe-generic-extras",
                                              "io.circe" %% "circe-parser").map(_ % Versions.circe)


  lazy val http4sCircle: ModuleID = "org.http4s" %% "http4s-circe" % "0.21.8" % Test

}