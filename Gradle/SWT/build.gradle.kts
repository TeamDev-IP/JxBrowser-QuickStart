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

import org.apache.tools.ant.taskdefs.condition.Os
import org.apache.tools.ant.taskdefs.condition.Os.FAMILY_MAC

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    application
}

repositories {
    mavenCentral()
    // The repository for JxBrowser binaries.
    maven("https://europe-maven.pkg.dev/jxbrowser/releases")
}

dependencies {
    val jxBrowserVersion = "7.36"

    // Use JxBrowser cross-platform binaries
    implementation("com.teamdev.jxbrowser:jxbrowser-cross-platform:$jxBrowserVersion")

    // Use JxBrowser SWT GUI toolkit
    implementation("com.teamdev.jxbrowser:jxbrowser-swt:$jxBrowserVersion")

    implementation(Swt.toolkitDependency)
}

Swt.configurePlatformDependency(project)

application {
    // Define the main class for the application
    mainClass.set("HelloWorld")
}

tasks.withType<JavaExec> {
    if (Os.isFamily(FAMILY_MAC)) {
        jvmArgs(
            // For macOS to run SWT under Cocoa.
            "-XstartOnFirstThread"
        )
    }

    // Assign all Java system properties from
    // the command line to the JavaExec task.
    systemProperties(System.getProperties().mapKeys { it.key as String })
}

