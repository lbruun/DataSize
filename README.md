# DataSize Utils
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.lbruun/datasize/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.lbruun/datasize)
[![javadoc](https://javadoc.io/badge2/net.lbruun/datasize/javadoc.svg)](https://javadoc.io/doc/net.lbruun/datasize) 

Minimal no-dep library dedicated to print human-readable byte sizes.



### Features

* Blazingly fast
* Small. Adds about 10KB to your project.
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
    true,                                 // use binary (true) or decimal (false)
    DataSizeUnitSuffixes.SUFFIXES_GNU,    // suffixes to use
    '.',                                  // decimal separator
    decimals                              // number of decimals, by unit
    );     
    // produces "1.907M"

```

### Pre-defined suffix sets

A number of pre-defined suffix sets are included:

| For use <br>with<br>calc. type | Suffixes<br>set | Description |
| ---------------- | ------ | --- |
| BINARY  | [SUFFIXES_ISO80000](https://javadoc.io/doc/net.lbruun/datasize/latest/net/lbruun/datasize/DataSizeUnitSuffixes.html#SUFFIXES_ISO80000) | ISO 80000 / International Electrotechnical Commission (IEC) |
| BINARY  | [SUFFIXES_CUSTOMARY](https://javadoc.io/doc/net.lbruun/datasize/latest/net/lbruun/datasize/DataSizeUnitSuffixes.html#SUFFIXES_CUSTOMARY) | Unit suffixes known as customary. These are used for example by the Microsoft Windows operating system. |
| BINARY  | [SUFFIXES_GNU](https://javadoc.io/doc/net.lbruun/datasize/latest/net/lbruun/datasize/DataSizeUnitSuffixes.html#SUFFIXES_GNU) | Unit suffixes used by GNU/Linux `ls -h` command. This is a very dense format with no space between the digits and the suffix.  |
| DECIMAL | [SUFFIXES_SI](https://javadoc.io/doc/net.lbruun/datasize/latest/net/lbruun/datasize/DataSizeUnitSuffixes.html#SUFFIXES_SI) | International System of Units (SI) |
| DECIMAL | [SUFFIXES_GNU_SI](https://javadoc.io/doc/net.lbruun/datasize/latest/net/lbruun/datasize/DataSizeUnitSuffixes.html#SUFFIXES_GNU_SI) | Unit suffixes used by GNU/Linux `ls --si` command. This is a very dense format with no space between the digits and the suffix. |
|         | [(builder)](https://javadoc.io/doc/net.lbruun/datasize/latest/net/lbruun/datasize/DataSizeUnitSuffixes.html#builder()) | Roll your own |



## Performance

The core fomatting routine is optimized for speed by:

* Avoiding `String.format`, `DecimalFormat` and the like.
* Avoiding costly `Math` functions. Only does integer arithmetics.
* Favor integer comparison over integer multiplication

Honestly, none of this matters if you are formatting a display of 50 files. But it matters if you are formatting a display of 50,000 files.

Also, for accuracy, the routine avoids floting point completely. We don't want those mysterious
rounding errors!


## Alternatives

- `org.springframework.util.unit.DataSize`. From what I can tell it can only parse, not format.

- [Apache Commons IO - FileUtils class](https://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/FileUtils.html#byteCountToDisplaySize-long-). Does too heavy rounding, everything becomes GBs. Also not clear to me if it does both binary or decimal calculation .. or indeed which one it does?

