plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "kr.kro.minestar"
version = "1.0.0"

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
            name = "sonatype-oss-snapshots"
        }
        maven("https://repo.projecttl.net/repository/maven-public/")
    }

    dependencies {
        compileOnly(kotlin("stdlib"))
        compileOnly("net.kyori:adventure-api:4.10.1")
        compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
        compileOnly("com.arcaniax:HeadDatabase-API:1.3.1")
        implementation("org.reflections:reflections:0.10.2")//패키지

        //project_TL
        implementation("net.projecttl:project-economy-api:2.1.3")

        //MineStar
        implementation("kr.kro.minestar:Utility-API:1.0.0")
    }
}
