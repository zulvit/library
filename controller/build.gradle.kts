plugins {
    id("java")
}

group = "ru.zulvit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("name.falgout.jeffrey.testing.junit5:guice-extension:1.2.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("org.mockito:mockito-core:4.8.1")
    // import project models
    implementation(project(":models"))
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10")
    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    implementation("org.xerial:sqlite-jdbc:3.39.3.0")
    // https://mvnrepository.com/artifact/com.intellij/annotations
    implementation("com.intellij:annotations:12.0")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0-rc2")
    // https://mvnrepository.com/artifact/com.google.inject/guice
    implementation("com.google.inject:guice:5.1.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}