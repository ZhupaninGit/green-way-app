val publicationName = "kotlin-obd-api"

plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "com.github.eltonvs"
version = "1.3.0"



dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
/*
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
*/

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }
    }
}
