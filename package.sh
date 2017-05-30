cd ModelicaPublicApiGenerator
mvn clean package
cd ../VersionChecker
mvn clean package
cd ..
cp ModelicaPublicApiGenerator/target/ModelicaPublicApiGenerator-1.0.0-jar-with-dependencies.jar UsageExample/libs/ModelicaPublicApiGenerator-1.0.0.jar
cp VersionChecker/target/VersionChecker-1.0.0-jar-with-dependencies.jar UsageExample/libs/VersionChecker-1.0.0.jar
