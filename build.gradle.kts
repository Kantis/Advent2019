buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.61"))
    }
}

plugins {
    kotlin("jvm") version "1.3.61"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.commons:commons-csv:1.7")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}


