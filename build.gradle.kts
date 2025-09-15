import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    id("com.epages.restdocs-api-spec") version "0.19.0"
}

group = "org.psy"
version = "0.0.1-SNAPSHOT"
description = "demo"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis") //redis
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("com.mysql:mysql-connector-j:8.2.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5") //jwt
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    // JAX-B dependencies for JDK 9+
    implementation("javax.xml.bind:jaxb-api:2.3.1") //jwt 관련

    implementation("org.jetbrains.kotlin:kotlin-allopen:1.8.0") // kotlin entity open 관련
    implementation ("org.springframework.boot:spring-boot-starter-validation") // validation
    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")

    //spring security 관련
    implementation("org.springframework.boot:spring-boot-starter-security")


    implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")

    //test
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc") //restdocs
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.0") //mockmvc
    testImplementation("io.mockk:mockk:1.12.0") //mockk
}

//2.0 k2 컴파일러 활성화
kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}
sourceSets {
    main {
//        java.srcDirs("src/main/java")
        kotlin.srcDirs("src/main/kotlin")
    }
}

tasks {
    getByName<BootJar>("bootJar") {
        mainClass.set("org.psy.demo.KotlinDemoApplication")
    }
}
//default
tasks.withType<KotlinCompile> {
    kotlinOptions {
        // JSR-305 설정은 필요에 따라 조정
//        freeCompilerArgs += "-Xjsr305=strict" // Java 상호 운용성이 중요하지 않다면 제거 가능
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openapi3 {
    title = "demo API"
    setServer("https://demo.net")
    description = "Swagger API Documentation for demo"
    version = "0.1.0"
    format = "yaml" // or json
}

tasks.register<Copy>("copyOasToSwagger") {
    delete("src/main/resources/static/swagger-ui/openapi3.yaml") // 기존 OAS 파일 삭제
    from("$buildDir/api-spec/openapi3.yaml") // 복제할 OAS 파일 지정
    into("src/main/resources/static/swagger-ui/.") // 타겟 디렉터리로 파일 복제
    dependsOn("openapi3") // openapi3 Task가 먼저 실행되도록 설정
}