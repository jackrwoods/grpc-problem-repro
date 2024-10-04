import com.google.protobuf.gradle.*

group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
    `java-library`
    id("java")
    id("idea")
    id("com.google.protobuf") version("0.9.4")
    kotlin("jvm") version "2.0.20"
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}


dependencies {
    implementation("io.grpc:grpc-netty:1.46.0")
    implementation("io.grpc:grpc-protobuf:1.53.0")
    implementation("io.grpc:grpc-kotlin-stub:1.2.0")
    implementation("io.grpc:grpc-stub:1.46.0")
    implementation("io.grpc:grpc-bom:1.63.2")
    implementation("com.google.protobuf:protoc:3.24.0")
    implementation("com.google.protobuf:protobuf-java:3.24.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.24.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.7"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.49.0"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-Xlint:-deprecation")
        options.compilerArgs.add("-Xlint:-static")
        options.isDeprecation = false
    }
    withType<ProcessResources>().configureEach {
        exclude("**/*.proto")
    }
    withType<Jar> {
        dependsOn("generateProto")
    }
    withType<GenerateProtoTask>().configureEach {
        plugins {
            id("grpc")
        }
    }
}

plugins.withId("org.jetbrains.kotlin.jvm") {
    dependencies {
        "api"("com.google.protobuf:protobuf-kotlin")
    }
    protobuf {
        plugins {
            id("grpckt") {
                artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
            }
        }
    }
    tasks {
        withType<GenerateProtoTask>().configureEach {
            builtins {
                id("kotlin")
            }
        }
    }
}