ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version := "0.1.0"
ThisBuild / organization := "com.shuzau"
ThisBuild / organizationName := "Huzaus"

name := "counter"

ThisBuild / scalacOptions := Seq("-unchecked",
  "-deprecation",
  "-encoding",
  "utf8",
  "-feature",
  "literal-types",
  "-Xfatal-warnings",
  "-Ymacro-annotations")

ThisBuild / libraryDependencies += compilerPlugin("org.typelevel" %% "kind-projector" % "0.11.3" cross CrossVersion.full)

lazy val `counter` = (project in file("."))
  .settings(discoveredMainClasses in Compile ++= (discoveredMainClasses in (`api`, Compile)).value)
  .dependsOn(`api`)
  .aggregate(`domain`, `api`)

lazy val `domain` = (project in file("domain"))

lazy val `api` = (project in file("api"))
  .settings()
  .dependsOn(`domain` % "compile->compile;test->test")