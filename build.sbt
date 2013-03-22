name := "sandbox"

scalaVersion := "2.10.1"

scalacOptions += "-feature"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.1.2",
  "org.specs2"        %% "specs2"     % "1.14"   % "test",
  "org.scalacheck"    %% "scalacheck" % "1.10.0" % "test"
)