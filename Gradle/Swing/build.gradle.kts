/*
 *  Copyright 2024, TeamDev. All rights reserved.
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

    kotlin("jvm") version "2.0.0"

    // Apply the application plugin to add support for building a CLI application
    application

    // Provides convenience methods for adding JxBrowser dependencies into a project
    id("com.teamdev.jxbrowser") version "1.2.1"
}

jxbrowser {
    version = "8.2.1"
}

repositories {
    mavenCentral()
}

dependencies {
    // Use JxBrowser cross-platform binaries
    implementation(jxbrowser.crossPlatform)

    // Use JxBrowser Swing GUI toolkit
    implementation(jxbrowser.swing)

    // Use JxBrowser Kotlin DSL
    implementation(jxbrowser.kotlin)
}

application {
    // Define the main class for the application
    mainClass.set("HelloSwing")
}

tasks.withType<JavaExec> {
    // Assign all Java system properties from
    // the command line to the JavaExec task.
    systemProperties(System.getProperties().mapKeys { it.key as String })
}
