name := "reactive2"

version := "1.0"

lazy val `reactive2` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws  )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  