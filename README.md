## Requirements
* java-8
* scala 2.11
* sbt
* SQL Server

## Build
* ``` sbt assembly ```

## Integration test
* ``` sbt integration:test ```

## Unit test
* ``` sbt test ```

## DB creation script can be found at
* ``` src/main/resources/init.sql ```

## How to run
* sbt run --config configfile
* example config file can be found at ``` test/resources/application.conf ```
