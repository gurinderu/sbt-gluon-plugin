package com.gluonhq

import sbt.settingKey

trait GluonKeys {
  lazy val graalPath = settingKey[String]("Graal path")
  lazy val javaFxVersion = settingKey[String]("JavaFX version")
  lazy val nativeImageArgs = settingKey[Seq[String]]("Native image args")
  lazy val javaFxOsName = settingKey[String]("OS Name")
}
