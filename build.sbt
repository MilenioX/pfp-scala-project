import Dependencies._

ThisBuild / scalaVersion     := "2.13.11"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.mundox"
ThisBuild / organizationName := "mundox"

lazy val root = (project in file("."))
  .settings(
    name := "pfp-scala-project",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core"              % "2.6.1",
      "org.typelevel" %% "cats-effect"            % "3.1.1",
      "org.typelevel" %% "cats-mtl"               % "1.2.1",
      "org.typelevel" %% "log4cats-slf4j"         % "2.3.1",
      "co.fs2"        %% "fs2-core"               % "3.0.3",
      "dev.optics"    %% "monocle-core"           % "3.0.0",
      "dev.optics"    %% "monocle-macro"          % "3.0.0",
      "io.estatico"   %% "newtype"                % "0.4.4",
      "eu.timepit"    %% "refined"                % "0.9.25",
      "eu.timepit"    %% "refined-cats"           % "0.9.25",
      "tf.tofu"       %% "derevo-cats"            % "0.12.5",
      "tf.tofu"       %% "derevo-cats-tagless"    % "0.12.5",
      "tf.tofu"       %% "derevo-circe-magnolia"  % "0.12.5",
      "tf.tofu"       %% "tofu-core-higher-kind"  % "0.10.2",
      "org.typelevel" %% "squants"                % "1.8.3",
      "com.github.cb372" %% "cats-retry"          % "3.1.0",
      "org.http4s" %% s"http4s-dsl" % "0.23.1",
      "org.http4s" %% s"http4s-ember-client" % "0.23.1",
      "org.http4s" %% s"http4s-ember-server" % "0.23.1",
      "org.http4s" %% s"http4s-circe" % "0.23.1"
    ),
    addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full),
    scalacOptions ++= Seq(
      "-Ymacro-annotations", "-Wconf:cat=unused:info"
    )
  )