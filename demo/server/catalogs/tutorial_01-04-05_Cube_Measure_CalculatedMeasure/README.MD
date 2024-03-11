# Documentation
### CatalogName : tutorial_01-04-05_Cube_Measure_CalculatedMeasure
## Olap Context Details:
## Schemas:
### Schema Minimal_Cubes_With_Two_Measures_Plus_One_Calculated_Measure : 

		
		A mininmal cube with a simple measure-based calculated member
		
		.
		
		
		
  
### Public Dimensions:

    

---
### Cubes :

    CubeTwoMeasuresPlusOneCalculatedMeasure

---
#### Cube "CubeTwoMeasuresPlusOneCalculatedMeasure":

    

##### Table: "Fact"

##### Dimensions:
### Cube "CubeTwoMeasuresPlusOneCalculatedMeasure" diagram:

---

```mermaid
%%{init: {
"theme": "default",
"themeCSS": [
    ".er.entityBox {stroke: black;}",
    ".er.attributeBoxEven {stroke: black;}",
    ".er.attributeBoxOdd {stroke: black;}",
    "[id^=entity-c] .er.entityBox { fill: lightgreen;} ",
    "[id^=entity-d] .er.entityBox { fill: powderblue;} ",
    "[id^=entity-h] .er.entityBox { fill: pink;} "
]
}}%%
erDiagram
c0["CubeTwoMeasuresPlusOneCalculatedMeasure"]{
M Measure1-Sum ""M Measure2-Count ""}
```
---
### Cube Matrix for Minimal_Cubes_With_Two_Measures_Plus_One_Calculated_Measure:
```mermaid
quadrantChart
title Cube Matrix
x-axis small level number --> high level number
y-axis Low row count --> High row count
quadrant-1 Complex
quadrant-2 Deep
quadrant-3 Simple
quadrant-4 Wide

Cube CubeTwoMeasuresPlusOneCalculatedMeasure: [0.0000, 0.0000]
```
---
## Validation result for schema Minimal_Cubes_With_Two_Measures_Plus_One_Calculated_Measure
## <span style='color: red;'>ERROR</span> : 
|Type|   |
|----|---|
|SCHEMA|Cube with name CubeTwoMeasuresPlusOneCalculatedMeasure must contain Dimensions|
|DATABASE|Cube with name CubeTwoMeasuresPlusOneCalculatedMeasure must contain measures|
|DATABASE|Fact table Fact does not exist in database .|
