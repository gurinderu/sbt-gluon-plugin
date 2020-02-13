package com.gluonhq

import java.io.File
import java.nio.file.Paths

import com.gluonhq.substrate.model.Triplet
import com.gluonhq.substrate.{ProjectConfiguration, SubstrateDispatcher}
import sbt.Keys._
import sbt.{Def, _}

import scala.collection.JavaConverters._

object ClientPlugin extends AutoPlugin {

  object autoImport extends GluonTasks with GluonKeys {

  }

  import autoImport._

  def clientConfig(): Def.Initialize[Task[ProjectConfiguration]] = Def.task {
    val mainClassValue = (mainClass in Compile).value.get

    val clientConfig = new ProjectConfiguration(mainClassValue)
    clientConfig.setGraalPath(Paths.get(graalPath.value))
    clientConfig.setTarget(Triplet.fromCurrentOS)
    clientConfig.setAppId(organization.value + "." + name.value)
    clientConfig.setAppName(name.value)
    clientConfig.setCompilerArgs(nativeImageArgs.value.asJava)
    clientConfig
  }

  override val projectSettings = Seq(
    javaFxVersion := "13.0.1",
    gluonCompile := {
      val directory = baseDirectory.value.toPath
      val dispatcher = new SubstrateDispatcher(directory, clientConfig().value)
      val classpath = (fullClasspath in Compile)
        .value
        .map(_.data)
        .map(_.getAbsolutePath)
        .mkString(File.pathSeparator)
      val result = dispatcher.nativeCompile(classpath)
      require(result, "Compilation failed.")
    },
    gluonLink := {
      val directory = baseDirectory.value.toPath
      val classpath = (fullClasspath in Compile)
        .value
        .map(_.data)
        .map(_.getAbsolutePath)
        .mkString(File.pathSeparator)
      val dispatcher = new SubstrateDispatcher(directory, clientConfig().value)
      val result = dispatcher.nativeLink(classpath)
      require(result, "Linking failed.")
    },
    gluonBuild := Def.sequential(gluonCompile, gluonLink).value,
    javaFxOsName := {
      System.getProperty("os.name") match {
        case n if n.startsWith("Linux") => "linux"
        case n if n.startsWith("Mac") => "mac"
        case n if n.startsWith("Windows") => "win"
        case _ => throw new Exception("Unknown platform!")
      }
    },
    nativeImageArgs := Seq.empty
  )

}
