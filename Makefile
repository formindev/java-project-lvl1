.DEFAULT_GOAL := build-run

build-run: build run

run:
	java -jar ./target/java-project-lvl1-1.0-SNAPSHOT-jar-with-dependencies.jar

clean:
	rm -rf ./target

build: ./mvnw clean package

update:
	./mvnw versions:update-properties
	./mvnw versions:display-plugin-updates