import sbt._
import sbt.Keys._
import com.typesafe.sbt.SbtScalariform
import scalariform.formatter.preferences._

object Settings {
  lazy val buildOrganization = "gr.jkl"
  lazy val buildScalaVersion = Version.scala

  def buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    scalaVersion := buildScalaVersion,
    crossVersion := CrossVersion.binary)

  def reformSettings = SbtScalariform.scalariformSettings ++ Seq(
    SbtScalariform.ScalariformKeys.preferences := FormattingPreferences.
      setPreference(DoubleIndentClassDeclaration, true).
      setPreference(MultilineScaladocCommentsStartOnFirstLine, true).
      setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true). 
      setPreference(AlignSingleLineCaseStatements, true))

  def defaultSettings = buildSettings ++ reformSettings ++ Seq(
    resolvers ++= DefaultOptions.resolvers(true) :+ ("Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"),
    scalacOptions in Compile ++= Seq("-encoding", "UTF-8", "-target:jvm-1.6", "-deprecation", "-feature", "-unchecked"),
    parallelExecution in Test := false,
    testOptions in Test += Tests.Argument("-oDF"),
    scalacOptions in (Compile, doc) ++= Seq("-groups"),
    shellPrompt <<= version(v => DefaultOptions.shellPrompt(v)))
}

object Version {
  lazy val scala = "2.10.0"
  lazy val uid   = "1.1.1-SNAPSHOT"
  lazy val play  = "2.1-RC2"
}

object Dependency {
  lazy val uid      = "gr.jkl" %% "uid"       % Version.uid
  lazy val play     = "play"   %% "play"      % Version.play
  lazy val playTest = "play"   %% "play-test" % Version.play % "test"
}

object Dependencies {
  import Dependency._

  lazy val core = Seq(uid, play, playTest)
}

object UIDBuild extends Build {
  import Settings._

  lazy val developers = Seq(Developer("nevang", "Nikolas Evangelopoulos", Some("https://github.com/nevang")))

  def extraSettings = Seq( 
    startYear := Some(2012),
    organizationName := "jkl",
    organizationHomepage := None,
    homepage <<= scmInfo ( _.map(_.browseUrl)),
    licenses <<= homepage ( _.map( h => "Simplified BSD License" -> url(h + "/master/LICENSE")).toSeq),
    Publish.developers :=developers)

  lazy val uid = Project(
    id = "playUid",
    base = file("."),
    settings = defaultSettings ++ extraSettings ++ Publish.settings ++ GitHub.settings ++ Seq(
      description := "Play plugin for 64-bit id generation",
      libraryDependencies ++= Dependencies.core,
      scalacOptions in (Compile, doc) <++= version map (v => DefaultOptions.scaladoc("UID", v))))
}
