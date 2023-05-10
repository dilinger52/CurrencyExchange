plugins {
    id("org.springframework.boot") version ("2.5.0")
    id("io.spring.dependency-management") version ("1.0.11.RELEASE")
    id("java")
}

group = "org.dilinger"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.springframework.boot:spring-boot-starter-web:2.2.2.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.2.2.RELEASE")
    implementation("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.7.1")
    implementation ("io.springfox:springfox-swagger2:3.0.0")
    implementation ("io.springfox:springfox-swagger-ui:3.0.0")
}

tasks.test {
    useJUnitPlatform()
}