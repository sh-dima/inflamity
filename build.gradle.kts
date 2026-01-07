//import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.undefinedcreations.nova.ServerType

plugins {
    alias(libs.plugins.kotlin)
//    alias(libs.plugins.shadow)

    alias(libs.plugins.paper)
    alias(libs.plugins.nova)

    alias(libs.plugins.yml)
}

repositories {
    mavenCentral()
}

dependencies {
    library(kotlin("stdlib"))
//    library(libs.commands)
//    library(libs.config)
//    implementation(libs.metrics)

    paperweight.paperDevBundle("1.21.8-R0.1-SNAPSHOT")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

group = "org.example"

version = ProcessBuilder("git", "describe", "--tags", "--always", "--dirty")
    .directory(project.projectDir)
    .start()
    .inputStream
    .bufferedReader()
    .readText()
    .trim()

tasks {
    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true

        filePermissions {
            user.read = true
            user.write = true
            user.execute = false

            group.read = true
            group.write = false
            group.execute = false

            other.read = true
            other.write = false
            other.execute = false
        }

        dirPermissions {
            user.read = true
            user.write = true
            user.execute = true

            group.read = true
            group.write = false
            group.execute = true

            other.read = false
            other.write = false
            other.execute = true
        }
    }

    runServer {
        val version = project.findProperty("minecraft.version") as? String ?: "1.21.10"
        val serverSoftware = (project.findProperty("minecraft.software") as? String ?: "papermc")
        val metricsEnabled = (project.findProperty("metrics") as? String)?.toBoolean() == true

        val run = File(project.projectDir, "run/${serverSoftware}/${version}")

        serverFolder(run)
        serverType(ServerType.valueOf(serverSoftware.uppercase()))

        minecraftVersion(version)

        acceptMojangEula()

        doFirst {
            val metricsConfig = run.resolve("plugins/bStats/config.yml")
            metricsConfig.parentFile.mkdirs()
            metricsConfig.writeText("""
                enabled: $metricsEnabled
                logFailedRequests: true

            """.trimIndent())

            val properties = run.resolve("server.properties")
            if (!properties.exists()) {
                properties.parentFile.mkdirs()
                properties.writeText("""
                        gamemode=creative
                        generate-structures=false
                        generator-settings={"biome":"minecraft:plains","layers":[{"block":"minecraft:air","height":127},{"block":"minecraft:barrier","height":1}]}
                        level-type=minecraft\:flat
                        spawn-protection=0
                        allow-flight=true

                    """.trimIndent()
                )
            }

            val bukkit = run.resolve("bukkit.yml")
            if (!bukkit.exists()) {
                bukkit.parentFile.mkdirs()
                bukkit.writeText("""
                    spawn-limits:
                    monsters: 0
                    animals: 0
                    water-animals: 0
                    water-ambient: 0
                    water-underground-creature: 0
                    axolotls: 0
                    ambient: 0
                """.trimIndent())
            }
        }
    }

//    withType<ShadowJar> {
//        from("assets/text/licenses") {
//            into("licenses")
//        }
//
//        archiveClassifier = ""
//
//        enableAutoRelocation = true
//        relocationPrefix = "${project.group}.${project.name}.dependencies"
//
//        minimizeJar = true
//    }
//
//    jar {
//        enabled = false
//    }
}

listOf(
//    tasks.shadowJar,
    tasks.jar,
    tasks.kotlinSourcesJar,
).forEach {
    it {
        from("README.md")
        from("LICENSE")
    }
}

bukkit {
    name = "Template"
    description = "A template for Minecraft Paper plugins"

    main = "$group.${project.name}.Plugin"
    apiVersion = "1.20.6"
    version = project.version.toString()

    authors = listOf(
        "Esoteric Enderman"
    )

    website = "https://gitlab.com/esoteric-templates/templates/template-minecraft-plugin"
}
