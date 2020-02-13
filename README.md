# Gluon Client plugin for SBT

The Gluon Client plugin for sbt projects leverages GraalVM, OpenJDK and JavaFX 11+, 
by compiling into native code the Java Client application and all its required dependencies, 
so it can directly be executed as a native application on the target platform.

## Getting started

To use the plugin, apply the following steps:

### 1. Apply the plugin

Edit your project/plugins.bt file and add the plugin:
    
    addSbtPlugin("com.github.gurinderu" % "sbt-gluon-plugin" % "0.1")
    
The plugin allows some options that can be set in `configuration`, to modify the default settings, and several goals, to build and run the native application.
Don't forget to set up `graalPath` parameter.

### 2. Goals
    
Once the project is ready, the Client plugin has these main goals:    

#### `gluonBuild`

This goal does the AOT compilation. It is a very intensive and lengthy task (several minutes, depending on your project and CPU), so it should be called only when the project is ready and runs fine on a VM.

Run:

    sbt gluonBuild

The results will be available at `${TARGET_ARCH}/gvm`.

## Requirements

At this moment the plugin is in beta, and supports Linux, Mac OS X and iOS platforms for now.

![Scala CI](https://github.com/gurinderu/sbt-gluon-plugin/workflows/Scala%20CI/badge.svg?branch=master&event=push)
