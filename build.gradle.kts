import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
	groovy
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.freefair.lombok") version "8.13.1"
	id("com.diffplug.spotless") version "7.0.4"
	id("com.github.ben-manes.versions") version "0.52.0"
}

group = "net.frey"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-mysql")


	runtimeOnly("com.mysql:mysql-connector-j")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.spockframework:spock-core:2.4-M6-groovy-4.0")
	testImplementation("org.spockframework:spock-spring:2.4-M6-groovy-4.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

fun isNonStable(version: String): Boolean {
	val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
	val regex = "^[0-9,.v-]+(-r)?$".toRegex()
	val isStable = stableKeyword || regex.matches(version)
	return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
	rejectVersionIf {
		isNonStable(candidate.version)
	}
}

tasks.wrapper {
	gradleVersion = "8.14.2"
	distributionType = Wrapper.DistributionType.ALL
}

spotless {
	java {
		palantirJavaFormat()
		removeUnusedImports()
	}
}

tasks.named("compileJava") {
	dependsOn("spotlessApply")
}