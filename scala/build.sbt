name := "GildedRose"

version := "1.0"

scalaVersion := "2.13.1"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers += DefaultMavenRepository
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
