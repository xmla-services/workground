# Documentation
### CatalogName : complex_ExpressiveNames
## Olap Context Details:
## Schemas:
### Schema ExpressiveNames : 

    
        Expressive names schema.
    
  
### Public Dimensions:

    Dimension1, Dimension2, Dimension3

##### Dimension "Dimension1":

Hierarchies:

    D1H1

##### Hierarchy D1H1:

Tables: "D1H1L1Table"

Levels: "D1H1L1"

###### Level "D1H1L1" :

    column(s): D1H1L1

##### Dimension "Dimension2":

Hierarchies:

    D2H1, D2H2

##### Hierarchy D2H1:

Tables: "D2H1L1Table"

Levels: "D2H1L1"

###### Level "D2H1L1" :

    column(s): D2H1L1

##### Hierarchy D2H2:

Tables: "D2H2L2Table"

Levels: "D2H2L1, D2H2L2"

###### Level "D2H2L1" :

    column(s): D2H2L1

###### Level "D2H2L2" :

    column(s): D2H2L2

##### Dimension "Dimension3":

Hierarchies:

    D3H1, D3H3

##### Hierarchy D3H1:

Tables: "D3H1L1Table"

Levels: "D3H1L1"

###### Level "D3H1L1" :

    column(s): D3H1L1

##### Hierarchy D3H3:

Tables: "D3H3L3Table,D3H3L2Table,D3H3L1Table"

Levels: "D3H3L1, D3H3L2, D3H3L3"

###### Level "D3H3L1" :

    column(s): D3H3L1

###### Level "D3H3L2" :

    column(s): D3H3L2

###### Level "D3H3L3" :

    column(s): D3H3L3

---
### Cubes :

    Cube 1

---
#### Cube "Cube 1":

    Test Cube

##### Table: "Cube1Fact"

##### Dimensions:
##### Dimension: "Dimension1 -> Dimension1":

##### Dimension: "Dimension2 -> Dimension2":

##### Dimension: "Dimension3 -> Dimension3":

### Cube "Cube 1" diagram:

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
c0["Cube 1"]{
M Measure1 ""D Dimension1 ""
D Dimension2 ""
D Dimension3 ""
}
d00[Dimension1] {
H D1H1 "Hierarchy 1 Dimension 1"
}
h000[D1H1] {
L D1H1L1 "Level 1 Dimension 1 Hierarchy1"
}
d01[Dimension2] {
H D2H1 "Hierarchy 1 Dimension 2"
H D2H2 "Hierarchy 2 Dimension 2"
}
h010[D2H1] {
L D2H1L1 "Level 1 Hierarchy 1 Dimension 2"
}
h011[D2H2] {
L D2H2L1 "Level 1 Hierarchy 2 Dimension 2"
L D2H2L2 "Level 2 Hierarchy 2 Dimension 2"
}
d02[Dimension3] {
H D3H1 "Hierarchy 1 Dimension 3"
H D3H3 "Hierarchy 1 Dimension 3"
}
h020[D3H1] {
L D3H1L1 "Level 1 Hierarchy1 Dimension 3"
}
h021[D3H3] {
L D3H3L1 "Level 1 Hierarchy3 Dimension 3"
L D3H3L2 "Level 2 Hierarchy3 Dimension 3"
L D3H3L3 "Level 3 Hierarchy3 Dimension 3"
}
"c0" ||--|| "d00" : "D1-D1H1L1"
"d00" ||--|| "h000" : "D1H1L1-D1H1L1"
"c0" ||--|| "d01" : "D2-D2H1L1"
"d01" ||--|| "h010" : "D2H1L1-D2H1L1"
"c0" ||--|| "d01" : "D2-D2H2L2"
"d01" ||--|| "h011" : "D2H2L2-D2H2L1"
"d01" ||--|| "h011" : "D2H2L2-D2H2L2"
"c0" ||--|| "d02" : "D3-D3H1L1"
"d02" ||--|| "h020" : "D3H1L1-D3H1L1"
"c0" ||--|| "d02" : "D3-D3H3L3"
"d02" ||--|| "h021" : "D3H3L3-D3H3L1"
"d02" ||--|| "h021" : "D3H3L3-D3H3L2"
"d02" ||--|| "h021" : "D3H3L3-D3H3L3"
```
---
### Cube Matrix for ExpressiveNames:
```mermaid
quadrantChart
title Cube Matrix
x-axis small level number --> high level number
y-axis Low row count --> High row count
quadrant-1 Complex
quadrant-2 Deep
quadrant-3 Simple
quadrant-4 Wide

Cube Cube 1: [0.4000, 0.0000]
```
---
### Database :
---
```mermaid
---
title: Diagram;
---
erDiagram
Cube1Fact{
null D1
null D2
null D3
null M1
}
D1H1L1Table{
null D1H1L1
null D1H1L1_NAME
null D1H1L1_Ordinal
}
D2H1L1Table{
null D2H1L1
null D2H1L1_NAME
null D2H1L1_Ordinal
}
D2H2L2Table{
null D2H2L2
null D2H2L1
null D2H2L2_NAME
null D2H2L1_NAME
null D2H2L2_Ordinal
null D2H2L1_Ordinal
}
D3H1L1Table{
null D3H1L1
null D3H1L1_NAME
null D3H1L1_Ordinal
}
D3H2L1Table{
null D3H2L1
null D3H2L1_NAME
null D3H2L1_Ordinal
}
D3H2L2Table{
null D3H2L2
null D3H2L1_id
null D3H2L2_NAME
null D3H2L2_Ordinal
}
D3H3L1Table{
null D3H3L1
null D3H3L1_NAME
null D3H3L1_Ordinal
}
D3H3L2Table{
null D3H3L2
null D3H3L1_id
null D3H3L2_NAME
null D3H3L2_Ordinal
}
D3H3L3Table{
null D3H3L3
null D3H3L2_id
null D3H3L3_NAME
null D3H3L3_Ordinal
}

"Cube1Fact" ||--o{ "D1H1L1Table" : "D1-D1H1L1"
"Cube1Fact" ||--o{ "D2H1L1Table" : "D2-D2H1L1"
"Cube1Fact" ||--o{ "D2H2L2Table" : "D2-D2H2L2"
"Cube1Fact" ||--o{ "D3H1L1Table" : "D3-D3H1L1"
"Cube1Fact" ||--o{ "D3H3L3Table" : "D3-D3H3L3"
"D3H3L3Table" ||--o{ "D3H3L2Table" : "D3H3L2_id-D3H3L2"
"D3H3L2Table" ||--o{ "D3H3L1Table" : "D3H3L1_id-D3H3L1"
```
---
## Validation result for schema ExpressiveNames
## <span style='color: blue;'>WARNING</span> : 
|Type|   |
|----|---|
|SCHEMA|Level: Type should be set for D2H1L1|
|SCHEMA|Level: Type should be set for D3H3L3|
|SCHEMA|Level: Type should be set for D3H3L2|
|SCHEMA|Level: Type should be set for D2H2L2|
|SCHEMA|Level: Type should be set for D3H3L1|
|SCHEMA|Level: Type should be set for D2H2L1|
|SCHEMA|Level: Type should be set for D1H1L1|
|SCHEMA|Level: Type should be set for D3H1L1|
|DATABASE|Table: Schema must be set|
