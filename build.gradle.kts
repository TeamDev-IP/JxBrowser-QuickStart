/*
 *  Copyright 2023, TeamDev. All rights reserved.
 *
 *  Redistribution and use in source and/or binary forms, with or without
 *  modification, must retain the above copyright notice and the following
 *  disclaimer.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    application

    // Two plugins below are for JavaFX and SWT support.
    // Normally only one of them is required because an application uses only one GUI toolkit.
    // We add both plugins so that you can easily switch between GUI toolkits in this example.

    // Apply OpenJFX plugin for JavaFX support.
    // https://plugins.gradle.org/plugin/org.openjfx.javafxplugin.
    id("org.openjfx.javafxplugin")  version "0.0.14"

    // This plugin automatically resolves SWT dependencies.
    // https://plugins.gradle.org/plugin/com.diffplug.eclipse.mavencentral.
    id("com.diffplug.eclipse.mavencentral") version "3.42.2"
}

repositories {
    mavenCentral()
    // The repository for JxBrowser release binaries.
    maven {
        url = uri("https://europe-maven.pkg.dev/jxbrowser/releases")
    }
}

java {
    toolchain {
        // Even though JxBrowser supports Java 8, we use Java 11 to build this project
        // with more up-to-date tools.
        //
        // If you'd like Quick Start example which uses Java 8, please refer to this tag:
        // https://github.com/TeamDev-IP/JxBrowser-QuickStart/releases/tag/v7.33.2.
        //
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}


// This block configures the OpenJFX plugin, which is necessary for JavaFX support.
javafx {
    // The version of the OpenJFX library which starts supporting Apple Silicon.
    // Find your version at https://gluonhq.com/products/javafx/.
    version = "19.0.2.1"
    modules("javafx.controls")
}

dependencies {

    // Get the latest version from the product page at https://teamdev.com/jxbrowser/.
    val jxBrowserVersion = "7.33.2"

    // Use JxBrowser cross-platform artifact which is added to all projects.
    implementation("com.teamdev.jxbrowser:jxbrowser-cross-platform:$jxBrowserVersion")

    // Below are dependencies for all GUI toolkits supported by JxBrowser.
    // We add them all so that you can easily switch between GUI toolkits in this example.
    // Usually only one of these dependencies is required for a GUI toolkit used in a project.

    // Use JxBrowser Swing GUI toolkit.
    implementation("com.teamdev.jxbrowser:jxbrowser-swing:$jxBrowserVersion")

    // Use JxBrowser JavaFX GUI toolkit.
    implementation("com.teamdev.jxbrowser:jxbrowser-javafx:$jxBrowserVersion")

    // Use JxBrowser SWT GUI toolkit.
    implementation("com.teamdev.jxbrowser:jxbrowser-swt:${jxBrowserVersion}")
}

application {

    // The main class for the application.
    mainClass.set(
        "HelloSwing"

        // Alternative main classes for other GUI toolkits:
        // "HelloJavaFX"
        // "HelloSwt"

        // The main class for the off-screen rendering mode example:
        // "HelloOffscreen"
    )
}

// This block configures the Eclipse Maven Central plugin, which is necessary for SWT support.
eclipseMavenCentral {
    silenceEquoIDE()
    // Eclipse Platform v4.25 has SWT v3.121, which supports Apple Silicon,
    // but doesn't support Java 8.
    val eclipsePlatform = if (JavaVersion.current().isJava8()) "4.8.0" else "4.25.0"
    release(eclipsePlatform) {
        implementation("org.eclipse.swt")
        useNativesForRunningPlatform()
    }
}

// Obtain the JxBrowser license key from the `gradle.properties` file.
val licenseKey = LicenseKey()
System.setProperty(licenseKey.propertyName, licenseKey.value)

tasks.withType<JavaExec>().configureEach {
    jvmArgs(
        // For macOS to run SWT under Cocoa.
        "-XstartOnFirstThread"
    )

    systemProperties(System.getProperties().mapKeys { (key, _) -> key.toString() })
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get()
        )
    }
}

/**
 * This class obtains JxBrowser license key from `gradle.properties` file of this project.
 */
class LicenseKey {
    val propertyName = "jxbrowser.license.key"
    val value: String by lazy {
        val licenseKey = providers.gradleProperty(propertyName).getOrElse("")
        if (licenseKey.isNullOrBlank()) {
            error(
                """
                No JxBrowser license key specified. Please specify the key in `gradle.properties` like this:
                
                $propertyName=YOUR_LICENSE_KEY
                
                You can get the trial license key from https://www.teamdev.com/jxbrowser#evaluate.
                """.trimIndent()
            )
        }
        licenseKey
    }
}
