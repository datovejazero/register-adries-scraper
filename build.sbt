import sbt.Keys._

name := """register-adries-scraper"""
organization := "eu.ideata.datahub"
version := "1.0.0-SNAPSHOT"
scalaVersion := "2.11.7"


resolvers ++= Seq(
  Resolver.sonatypeRepo("public"),
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "Artifactory" at "http://maven.ideata.eu/artifactory/libs-release"
)


val akkaVersion = "2.4.10"
val slickVersion = "3.1.0"


// Change this to another test framework if you prefer
val testingDeps = Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

val parsing = Seq(
  "com.typesafe.play" % "play-json_2.11" % "2.5.8",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
)
val jodaTime = Seq(
  "joda-time" % "joda-time" % "2.9.4"
)

val commonsHttpClient = Seq("org.apache.httpcomponents" % "httpclient" % "4.5.2")

val configurationDeps = Seq(
  "com.typesafe" % "config" % "1.3.1",
  "com.github.scopt" %% "scopt" % "3.5.0"
)

val sqlServer = Seq(
  "com.microsoft.sqlserver" % "sqljdbc42" % "4.2"
)

val akka = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
)

val shapeLess = Seq(
  "com.chuusai" %% "shapeless" % "2.3.2"
)

libraryDependencies ++= testingDeps ++
  commonsHttpClient ++
  parsing ++
  jodaTime ++
  sqlServer ++
  configurationDeps ++
  akka ++
  shapeLess

lazy val ITest = config("integration") extend(Test)
lazy val root = project.in(file(".")).configs(ITest) settings(inConfig(ITest)(Defaults.testTasks): _*)

testOptions in Test := Seq(Tests.Filter(_.endsWith("Test")))
testOptions in ITest := Seq(Tests.Filter(_.endsWith("IT")))

test in assembly := Seq(Tests.Filter(_.endsWith("Test")))

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in (Compile, assembly), assembly)

publishMavenStyle := true

publishTo := {
  val artifactory = "http://maven.ideata.eu/artifactory"
  if (isSnapshot.value)
    Some("Artifactory realm" at artifactory +  "/libs-snapshot-local")
  else
    Some("Artifactory realm" at artifactory + "/libs-release-local")
}
credentials += Credentials(Path.userHome / ".sbt" / ".ideata-credentials")

