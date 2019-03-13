# kintone-java-sdk

> The SDK of kintone REST API client use for kintone-java-sdk

## Create a project

- on cygwin

```bashshell
$ mvn -B archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=sample -DartifactId=sample
```

## Edit Pom.xml

- Pop.xml added Kintone-java-sdk.jar dependency

```bashshell

$    <dependency>
$      <groupId>com.cybozu.kintone</groupId>
$      <artifactId>Kintone-java-sdk</artifactId>
$      <version>0.2.0</version>
$    </dependency>
```

## Library download

- on cygwin

```bashshell
$ mvn dependency:copy-dependencies -DoutputDirectory=lib
$ cd lib
```

## References

- [kintone-java-sdk](https://kintone.github.io/kintone-java-sdk/)


## License


MIT

## Copyright

Copyright(c) Cybozu, Inc.
