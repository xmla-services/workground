# Documentation
### CatalogName : complex_SteelWheel
## Olap Context Details:
## Schemas:
### Schema SteelWheels : 
### Public Dimensions:

    

---
### Cubes :

    SteelWheelsSales

---
#### Cube "SteelWheelsSales":

    

##### Table: "orderfact"

##### Dimensions:
##### Dimension "Markets":

Hierarchies:

    Hierarchy0

##### Hierarchy Hierarchy0:

Tables: "customer_w_ter"

Levels: "Territory, Country, State Province, City"

###### Level "Territory" :

    column(s): TERRITORY

###### Level "Country" :

    column(s): COUNTRY

###### Level "State Province" :

    column(s): STATE

###### Level "City" :

    column(s): CITY

##### Dimension "Customers":

Hierarchies:

    Hierarchy0

##### Hierarchy Hierarchy0:

Tables: "customer_w_ter"

Levels: "Customer"

###### Level "Customer" :

    column(s): CUSTOMERNAME

##### Dimension "Product":

Hierarchies:

    

##### Hierarchy :

Tables: "products"

Levels: "Line, Vendor, Product"

###### Level "Line" :

    column(s): PRODUCTLINE

###### Level "Vendor" :

    column(s): PRODUCTVENDOR

###### Level "Product" :

    column(s): PRODUCTNAME

##### Dimension "Time":

Hierarchies:

    Hierarchy0

##### Hierarchy Hierarchy0:

Tables: "time"

Levels: "Years, Quarters, Months"

###### Level "Years" :

    column(s): YEAR_ID

###### Level "Quarters" :

    column(s): QTR_NAME

###### Level "Months" :

    column(s): MONTH_NAME

##### Dimension "Order Status":

Hierarchies:

    Hierarchy0

##### Hierarchy Hierarchy0:

Tables: ""

Levels: "Type"

###### Level "Type" :

    column(s): STATUS

### Cube "SteelWheelsSales" diagram:

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
c0["SteelWheelsSales"]{
M Quantity ""M Sales ""D Markets ""
D Customers ""
D Product ""
D Time ""
D Order Status ""
}
d00[Markets] {
H null ""
}
h000[null] {
L Territory ""
L Country ""
L State Province ""
L City ""
}
d01[Customers] {
H null ""
}
h010[null] {
L Customer ""
}
d02[Product] {
H  ""
}
h020[] {
L Line ""
L Vendor ""
L Product ""
}
d03[Time] {
H null ""
}
h030[null] {
L Years ""
L Quarters ""
L Months ""
}
d04[Order Status] {
H null ""
}
h040[null] {
L Type ""
}
"c0" ||--|| "d00" : "CUSTOMERNUMBER-CUSTOMERNUMBER"
"d00" ||--|| "h000" : "CUSTOMERNUMBER-TERRITORY"
"d00" ||--|| "h000" : "CUSTOMERNUMBER-COUNTRY"
"d00" ||--|| "h000" : "CUSTOMERNUMBER-STATE"
"d00" ||--|| "h000" : "CUSTOMERNUMBER-CITY"
"c0" ||--|| "d01" : "CUSTOMERNUMBER-CUSTOMERNUMBER"
"d01" ||--|| "h010" : "CUSTOMERNUMBER-CUSTOMERNAME"
"c0" ||--|| "d02" : "PRODUCTCODE-PRODUCTCODE"
"d02" ||--|| "h020" : "PRODUCTCODE-PRODUCTLINE"
"d02" ||--|| "h020" : "PRODUCTCODE-PRODUCTVENDOR"
"d02" ||--|| "h020" : "PRODUCTCODE-PRODUCTNAME"
"c0" ||--|| "d03" : "TIME_ID-TIME_ID"
"d03" ||--|| "h030" : "TIME_ID-YEAR_ID"
"d03" ||--|| "h030" : "TIME_ID-QTR_NAME"
"d03" ||--|| "h030" : "TIME_ID-MONTH_NAME"
"c0" ||--|| "d04" : "STATUS-STATUS"
"d04" ||--|| "h040" : "STATUS-STATUS"
```
---
### Cube Matrix for SteelWheels:
```mermaid
quadrantChart
title Cube Matrix
x-axis small level number --> high level number
y-axis Low row count --> High row count
quadrant-1 Complex
quadrant-2 Deep
quadrant-3 Simple
quadrant-4 Wide

Cube SteelWheelsSales: [0.6000, 0.0000]
```
---
### Database :
---
```mermaid
---
title: Diagram;
---
erDiagram
customer_w_ter{
null CUSTOMERNUMBER
null CUSTOMERNAME
null CONTACTLASTNAME
null CONTACTFIRSTNAME
null PHONE
null ADDRESSLINE1
null ADDRESSLINE2
null CITY
null STATE
null POSTALCODE
null COUNTRY
null EMPLOYEENUMBER
null CREDITLIMIT
null TERRITORY
}
orderfact{
null ORDERNUMBER
null PRODUCTCODE
null QUANTITYORDERED
null PRICEEACH
null ORDERLINENUMBER
null TOTALPRICE
null ORDERDATE
null REQUIREDDATE
null SHIPPEDDATE
null STATUS
null COMMENTS
null CUSTOMERNUMBER
null TIME_ID
null QTR_ID
null MONTH_ID
null YEAR_ID
}
products{
null PRODUCTCODE
null PRODUCTNAME
null PRODUCTLINE
null PRODUCTSCALE
null PRODUCTVENDOR
null PRODUCTDESCRIPTION
null QUANTITYINSTOCK
null BUYPRICE
null MSRP
}
time{
null TIME_ID
null MONTH_ID
null QTR_ID
null YEAR_ID
null MONTH_NAME
null MONTH_DESC
null QTR_NAME
null QTR_DESC
}

"orderfact" ||--o{ "customer_w_ter" : "CUSTOMERNUMBER-CUSTOMERNUMBER"
"orderfact" ||--o{ "customer_w_ter" : "CUSTOMERNUMBER-CUSTOMERNUMBER"
"orderfact" ||--o{ "products" : "PRODUCTCODE-PRODUCTCODE"
"orderfact" ||--o{ "time" : "TIME_ID-TIME_ID"
```
---
## Validation result for schema SteelWheels
## <span style='color: red;'>ERROR</span> : 
|Type|   |
|----|---|
|SCHEMA|Hierarchy: Table field must be empty in dimension with name Product|
## <span style='color: blue;'>WARNING</span> : 
|Type|   |
|----|---|
|SCHEMA|Level: Type should be set for Years|
|SCHEMA|Level: Type should be set for Product|
|SCHEMA|Level: Type should be set for City|
|SCHEMA|Level: Type should be set for State Province|
|SCHEMA|Level: Type should be set for Territory|
|SCHEMA|Level: Type should be set for Months|
|SCHEMA|Level: Type should be set for Line|
|SCHEMA|Level: Type should be set for Customer|
|SCHEMA|Level: Type should be set for Vendor|
|SCHEMA|Level: Type should be set for Country|
|SCHEMA|Level: Type should be set for Type|
|SCHEMA|Level: Type should be set for Quarters|
|DATABASE|Table: Schema must be set|
