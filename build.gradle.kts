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
    implementation("io.grpc:grpc-kotlin-stub:1.4.1")
    implementation("io.grpc:grpc-stub:1.46.0")
    implementation("io.grpc:grpc-bom:1.63.2")
    implementation("com.google.protobuf:protoc:3.24.0")
    implementation("com.google.protobuf:protobuf-java:3.24.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.24.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.0"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.53.0"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
                create("grpckt")
            }
            it.builtins {
                create("kotlin")
            }
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
}

plugins.withId("org.jetbrains.kotlin.jvm") {
    dependencies {
        "api"("com.google.protobuf:protobuf-kotlin")
    }
}