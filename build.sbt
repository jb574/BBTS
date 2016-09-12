name := """hello-akka"""

version := "1.0"

scalaVersion := "2.11.8"
sbtVersion := "0.13.6"

lazy val akkaVersion = "2.4.0"


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "com.google.guava" % "guava" % "17.0"
)


mainClass in assembly := Some("controller.Main")

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")


fork in Test := false

fork in run := false