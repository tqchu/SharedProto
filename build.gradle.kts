plugins {
    java
    `java-library`
    `maven-publish`
    signing
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "org.ebook_searching"
version = "0.0.1"

publishing {
    publications {
        create<MavenPublication>("qdcSharedProto") {
            from(components["java"])

            artifactId = "proto"

            pom {
                name.set("Shared Proto")
                description.set("A mono shared proto definition for QDC")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("chu")
                        name.set("Truong Quang Chu")
                        email.set("truongquangchu.tqc@gmail.com")
                    }
                    developer {
                        id.set("dat")
                        name.set("Le Van Dat")
                        email.set("lvd.levandat@gmail.com")
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/tqchu/SharedProto")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.google.protobuf:protobuf-java:3.23.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
