mvn clean source:jar jar:jar javadoc:jar deploy -Dmaven.test.skip=true -DrepositoryId=sonatype-nexus-staging
