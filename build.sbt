import play.core.PlayVersion

val nexusPreviewHost: String = System.getProperty("hmrc.repo.host", "https://nexus-preview.tax.service.gov.uk")

lazy val govUkTaxIntegrationTests: Project = Project("scalatest-test", file("."))
  .settings(version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.11.8",
    resolvers ++= Seq("hmrc-snapshots" at nexusPreviewHost + "/content/repositories/hmrc-snapshots",
      "hmrc-releases" at nexusPreviewHost + "/content/repositories/hmrc-releases",
      "typesafe-releases" at nexusPreviewHost + "/content/repositories/typesafe-releases",
      Resolver.url("hmrc-sbt-plugin-releases", url("https://dl.bintray.com/hmrc/sbt-plugin-releases"))(Resolver.ivyStylePatterns)),
    libraryDependencies ++= Seq(
      "org.pegdown" % "pegdown" % "1.4.2" % "test",
      "net.lightbody.bmp" % "browsermob-core" % "2.1.1",
      "org.scalactic" %% "scalactic" % "3.0.4",
      "com.typesafe.play" %% "play-test" % PlayVersion.current,
      "org.scalatest" %% "scalatest" % "3.0.4",
      "com.fasterxml.jackson.core" % "jackson-annotations" % "2.7.0",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.7.2",
      "com.fasterxml.jackson.core" % "jackson-core" % "2.7.4",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.7.2",
      "org.seleniumhq.selenium" % "selenium-java" % "3.5.3",
      "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0-M1" % "test",
      "com.google.guava" % "guava" % "23.0"),

    parallelExecution in Test := false,
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports/html-report"),
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oDF"))