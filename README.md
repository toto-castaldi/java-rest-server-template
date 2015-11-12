java-rest-server-template
=====================

```
<dependency>
	<groupId>com.github.toto-castaldi.services.restServer</groupId>
    <artifactId>core</artifactId>
    <version>1.0</version>
</dependency>
```

Skip sign in maven
`mvn -DperformSign=true `
Deploy snapshot
`mvn clean deploy`
Deploy release
`mvn clean deploy -P ossrh`