name := "reactive2"

version := "1.0"

lazy val `reactive2` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc, cache, ws, specs2 % Test,
  "org.twitter4j" % "twitter4j-core" % "4.0.4",
  "org.twitter4j" % "twitter4j-stream" % "4.0.4",
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.1.7" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

unmanagedSourceDirectories in Test += baseDirectory.value / "gatling"

routesGenerator := InjectedRoutesGenerator

