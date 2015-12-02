java-rest-server-template
=====================

```
<dependency>
	<groupId>com.github.toto-castaldi.services.restServer</groupId>
    <artifactId>core</artifactId>
    <version>1.1</version>
</dependency>


```

Skip sign in maven
`mvn -DperformSign=false `

Deploy snapshot
`mvn clean deploy `

Deploy release
`mvn clean deploy -P release-sign-artifacts`
