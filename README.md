# Description
[![build](https://github.com/subash-m/Spring-Boot-Olingo-OData-V4.01/actions/workflows/maven.yml/badge.svg)](https://github.com/subash-m/Spring-Boot-Olingo-OData-V4.01/actions/workflows/maven.yml)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE.txt)
![GitHub last commit](https://img.shields.io/github/last-commit/subash-m/Spring-Boot-Olingo-OData-V4.01)

Sample project to show integration of Spring Boot with Apache Olingo to create OData V4.01 API

## Prerequisites

* maven
* Java JDK 1.8

## To run the project

Execute the following command from the project folder

```maven
mvn spring-boot:run
```

## To check the results

Open browser and try the following URLs,

### Service Document

```URL
http://localhost:8080/OData/V1.0/
```

The expected result is the Service Document which displays our *EntityContainerInfo*

```xml
<app:service xmlns:atom="http://www.w3.org/2005/Atom" xmlns:app="http://www.w3.org/2007/app" xmlns:metadata="http://docs.oasis-open.org/odata/ns/metadata" metadata:context="$metadata">
	<app:workspace>
    	<atom:title>OData.Demo.Container</atom:title>
        <app:collection href="Products" metadata:name="Products">
        	<atom:title>Products</atom:title>
        </app:collection>
    </app:workspace>
</app:service>
```

### Metadata Document

```
http://localhost:8080/OData/V1.0/$metadata
```

The expected result is the Metadata Document that displays our *Schema*, *EntityType*, *EntityContainer* and *EntitySet*

```xml
<edmx:Edmx xmlns:edmx="http://docs.oasis-open.org/odata/ns/edmx" Version="4.0">
    <edmx:DataServices>
        <Schema xmlns="http://docs.oasis-open.org/odata/ns/edm" Namespace="OData.Demo">
            <EntityType Name="Product">
                <Key>
                	<PropertyRef Name="ID"/>
                </Key>
                <Property Name="ID" Type="Edm.Int32"/>
                <Property Name="Name" Type="Edm.String"/>
                <Property Name="Description" Type="Edm.String"/>
            </EntityType>
            <EntityContainer Name="Container">
            	<EntitySet Name="Products" EntityType="OData.Demo.Product"/>
            </EntityContainer>
        </Schema>
    </edmx:DataServices>
</edmx:Edmx>
```

### Query / EntitySet

```
http://localhost:8080/OData/V1.0/Products?$format=JSON
```

The expected result is the hardcoded list of product entries, which we have coded in our processor implementation

```json
{
    "@odata.context": "$metadata#Products",
    "value": [
        {
            "ID": 1,
            "Name": "Notebook Basic 15",
            "Description": "Notebook Basic, 1.7GHz - 15 XGA - 1024MB DDR2 SDRAM - 40GB"
        },
        {
            "ID": 2,
            "Name": "1UMTS PDA",
            "Description": "Ultrafast 3G UMTS/HSDPA Pocket PC, supports GSM network"
        },
        {
            "ID": 3,
            "Name": "Ergo Screen",
            "Description": "19 Optimum Resolution 1024 x 768 @ 85Hz, resolution 1280 x 960"
        }
    ]
}
```

## Reference Credits

* Official Apache Olingo tutorial section
  * https://olingo.apache.org/doc/odata4/tutorials/read/tutorial_read.html
* Rohit Ghatol's repository
  * https://github.com/rohitghatol/spring-boot-Olingo-oData
