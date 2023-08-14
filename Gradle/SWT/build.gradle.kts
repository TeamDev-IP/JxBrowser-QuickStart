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

    // This plugin automatically resolves SWT dependencies.
    id("com.diffplug.eclipse.mavencentral") version "3.40.0"
}

repositories {
    mavenCentral()
    // The repository for JxBrowser binaries.
    maven("https://europe-maven.pkg.dev/jxbrowser/releases")
}

val jxBrowserVersion by extra { "7.34.1" }

dependencies {
    // Use JxBrowser cross-platform binaries
    implementation("com.teamdev.jxbrowser:jxbrowser-cross-platform:$jxBrowserVersion")

    // Use JxBrowser SWT GUI toolkit
    implementation("com.teamdev.jxbrowser:jxbrowser-swt:$jxBrowserVersion")
}

eclipseMavenCentral {
    // Eclipse Platform v4.25 has SWT v3.121, which supports Apple Silicon,
    // but doesn't support Java 8.
    val eclipsePlatform = if (JavaVersion.current().isJava8) "4.8.0" else "4.25.0"
    release(eclipsePlatform) {
        implementation("org.eclipse.swt")
        useNativesForRunningPlatform()
    }
}

application {
    // Define the main class for the application
    mainClass.set("HelloWorld")
}

tasks.withType<JavaExec> {
    // Assign all Java system properties from
    // the command line to the JavaExec task.
    systemProperties(System.getProperties().mapKeys { it.key as String })
}
