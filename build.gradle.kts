plugins {
    kotlin("jvm") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("org.jetbrains.dokka") version "1.5.0"
    `maven-publish`
}

group = "kr.kro.minestar"
version = "1.0.0"

tasks {
    compileKotlin{
        kotlinOptions.jvmTarget = "16"
    }
    javadoc {
        options.encoding = "UTF-8"
    }

    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    create<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaHtml")
        from("$buildDir/dokka/html")
    }
}

publishing {
    publications {
        create<MavenPublication>("${rootProject.name}-api") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            repositories {
                maven {
                    name = "MavenCentral"
                    val releasesRepoUrl = "https://repo.projecttl.net/repository/maven-releases/"
                    val snapshotsRepoUrl = "https://repo.projecttl.net/repository/maven-snapshots/"
                    url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

                    credentials.runCatching {
                        username = project.properties["username"] as String?
                        password = project.properties["password"] as String?
                    }
                }

                pom {
                    name.set(rootProject.name)
                    description.set("This is minecraft default project")
                    url.set("https://github.com/MineStarAS/MineCraftDefaultProject")
                    licenses {
                        license {
                            name.set("GNU GENERAL PUBLIC LICENSE Version 3")
                            url.set("https://www.gnu.org/licenses/gpl-3.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("MineStarAS")
                            name.set("MineStar")
                            email.set("band1019@naver.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:https://github.com/MineStarAS/MineCraftDefaultProject.git")
                        developerConnection.set("scm:git:https://github.com/MineStarAS/MineCraftDefaultProject.git")
                        url.set("https://github.com/MineStarAS/MineCraftDefaultProject.git")
                    }
                }
            }
        }
    }
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots"
    }
    maven("https://repo.projecttl.net/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("net.kyori:adventure-api:4.7.0")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")

    //project_TL

    //MineStar
}

