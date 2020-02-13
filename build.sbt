name := "sbt-gluon-plugin"

organization := "com.github.gurinderu"

version := "0.1"

scalaVersion := "2.12.10"

enablePlugins(SbtPlugin)

libraryDependencies += "com.gluonhq" % "substrate" % "0.0.13"

resolvers += "Gluon Snapshots" at "https://nexus.gluonhq.com/nexus/content/repositories/public-snapshots"


