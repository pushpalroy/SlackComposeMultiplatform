plugins {
  kotlin("jvm") version "1.7.20"
  application
}

group = "dev.baseio.slackserver"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
  // grpc
  implementation("io.grpc:grpc-netty-shaded:1.49.2")
  implementation(project(":generate-proto"))

  //mongodb
  implementation("org.litote.kmongo:kmongo:4.7.1")
  implementation("org.litote.kmongo:kmongo-async:4.7.1")
  implementation("org.litote.kmongo:kmongo-coroutine:4.7.1")


  //jwt
  implementation("io.jsonwebtoken:jjwt-api:0.11.5")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
  runtimeOnly("io.jsonwebtoken:jjwt-orgjson:0.11.5")

  //passwords
  implementation("at.favre.lib:bcrypt:0.9.0")

  kotlin.sourceSets.getByName("main")
    .kotlin
    .srcDirs(projectDir.parentFile
      .resolve("generate-proto/build/generated/source/proto/main/kotlin")
      .canonicalPath,projectDir.parentFile
      .resolve("generate-proto/build/generated/source/proto/main/grpckt")
      .canonicalPath,projectDir.parentFile
      .resolve("generate-proto/build/generated/source/proto/main/grpc")
      .canonicalPath,projectDir.parentFile
      .resolve("generate-proto/build/generated/source/proto/main/java")
      .canonicalPath)
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

application {
  mainClass.set("MainKt")
}