plugins {
    java
    kotlin("jvm") version "1.4.32"
}

group = "session.manager"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

//tasks.getByName<Jar>("Jar") {
//    manifest {
//        attributes["Main-Class"] = "MovieQuizBackendKt"
//    }
//}

tasks.getByName<Test>("test") {
    useJUnitPlatform()


        maxHeapSize = "4096m"
//        jvmArgs = "-XX:MaxPermSize=256m"


}