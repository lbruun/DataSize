# DataSize Utils
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.lbruun/datasize/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.lbruun/datasize)
[![javadoc](https://javadoc.io/badge2/net.lbruun/datasize/javadoc.svg)](https://javadoc.io/doc/net.lbruun/datasize) 

Minimal no-dep library dedicated to print human-readable byte sizes.



### Features

* Blazingly fast
* Supports both decimal calculation (1 kilobyte = 1000 bytes) as well as binary calculation (1 kikibyte = 1024 bytes).
* Configurable:
    * Number of decimals (per unit type, for example for Megabytes print with 3 decimals)
    * Suffix (per unit type, for example for Megabytes it may be ` MB`)
    * Decimal separator. Typically either `.` or `,`.
* Includes many preset suffixes:   SI, ISO-80000, Customary, GNU, etc.    
* Can be used with Java 8 onwards.





### Documentation

For in-depth documentation see [JavaDoc](https://javadoc.io/doc/net.lbruun/datasize).


### Usage

Library is available on Maven Central.

```xml
<dependency>
    <groupId>net.lbruun</groupId>
    <artifactId>datasize</artifactId>
    <version>  --LATEST--  </version>
</dependency>
```



#### Code examples

Using the convenience methods:
```java
DataSize.asStringBinary (2_000_000L);  // produces "1.9 MiB"
DataSize.asStringDecimal(2_000_000L);  // produces "2.0 MB"
```

Full control:
```java

// Increase decimals for the 'mega' unit
DataSizeUnitDecimals decimals = DataSizeUnitDecimals.builder()
                .withMegabyteDecimals(3)
                .build();

DataSize.asString(
    2_000_000L,
    true,             // use binary (true) or decimal (false)
    DataSizeUnitSuffixes.SUFFIXES_GNU,    // which suffixes to use
    '.',              // decimal separator
    decimals          // number of decimals, by unit
    );     
    // produces "1.907M"

```

