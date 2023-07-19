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

    // Apply OpenJFX plugin.
    id("org.openjfx.javafxplugin")  version "0.0.14"

    // This plugin automatically resolves SWT dependencies.
    id("com.diffplug.eclipse.mavencentral") version "3.42.2"

}

repositories {
    mavenCentral()
    // The repository for JxBrowser release binaries.
    maven {
        url = uri("https://europe-maven.pkg.dev/jxbrowser/releases")
    }
}

val jxBrowserVersion: String by extra("7.33.2")

javafx {
    version = "19.0.2.1"
    modules("javafx.controls")
}

dependencies {

    // Use JxBrowser cross-platform artifact which is added to all projects.
    implementation("com.teamdev.jxbrowser:jxbrowser-cross-platform:${jxBrowserVersion}")

    // Below are dependencies for all GUI toolkits supported by JxBrowser.
    // We add them all so that you can easily switch between GUI toolkits in this example.
    // Normally only one of these dependencies is required.

    // Use JxBrowser Swing GUI toolkit.
    implementation("com.teamdev.jxbrowser:jxbrowser-swing:${jxBrowserVersion}")

    // Use JxBrowser JavaFX GUI toolkit.
    implementation("com.teamdev.jxbrowser:jxbrowser-javafx:${jxBrowserVersion}")

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
        // "HelloOffScreen"
    )
}

eclipseMavenCentral {
    // Eclipse Platform v4.25 has SWT v3.121, which supports Apple Silicon,
    // but doesn't support Java 8.
    val eclipsePlatform = if (JavaVersion.current().isJava8()) "4.8.0" else "4.25.0"
    release(eclipsePlatform) {
        implementation("org.eclipse.swt")
        useNativesForRunningPlatform()
    }
}

val licenseKey = providers.gradleProperty("jxbrowser.license.key").get()

//System.setProperty("jxbrowser.license.key", licenseKey)

tasks.withType<JavaExec>().configureEach {
    jvmArgs(
        // For macOS to run SWT under Cocoa.
        "-XstartOnFirstThread"
    )
    systemProperty("jxbrowser.license.key", licenseKey)
}

