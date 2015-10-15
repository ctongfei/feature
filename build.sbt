name := "feature"

organization := "me.tongfei"

version := "0.3.0-SNAPSHOT"

scalaVersion := "2.11.7"

isSnapshot := true

resolvers += Resolver.sonatypeRepo("snapshots")

// libraryDependencies += "me.tongfei" %% "poly-algebra" % "0.2.10-SNAPSHOT" changing()

scalacOptions in ThisBuild ++= Seq("-optimise", "-Yclosure-elim", "-Yinline")

autoAPIMappings := true

scalacOptions in (Compile, doc) += "-diagrams"

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomExtra :=
  <url>http://github.com/ctongfei/feature</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>http://opensource.org/licenses/MIT</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:ctongfei/feature.git</url>
      <connection>scm:git:git@github.com:ctongfei/feature.git</connection>
    </scm>
    <developers>
      <developer>
        <id>ctongfei</id>
        <name>Tongfei Chen</name>
        <url>http://tongfei.me/</url>
      </developer>
    </developers>

