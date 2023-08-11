Grabber design

Schema:
```
<?xml version="1.0" encoding="UTF-8"?>
<Schema name="Bevölkerung">


<Cube name="Bevölkerung">
  <Table name="einwohner"/>

  <Dimension name="Jahr" foreignKey="JAHR">
    <Hierarchy hasAll="false" name="Jahr" primaryKey="year" defaultMember="2023">
    <Table name="year"/>
    <Level name="Jahr" column="year" ordinalColumn="ordinal"/>
    </Hierarchy>
  </Dimension>

  <Dimension name="statistischer Bezirk" foreignKey="STATBEZ">
    <Hierarchy hasAll="true" name="Stadt - Planungsraum - statistischer Bezirk" primaryKey="gid" primaryKeyTable="statbez">
    <Join leftKey="plraum" rightKey="gid">
       <Table name="statbez"/>
       <Join leftKey="townid" rightKey="id">
          <Table name="plraum"/>
          <Table name="town"/>
       </Join>      
    </Join>      
    <Level name="Stadt" column="name"  table="town">
          <Property name="GeoJson" column="geojson" type="String"/>
    </Level>          
    <Level name="Planungsraum" column="gid" captionColumn="" nameColumn="plraum" type="Integer" table="plraum">
      <Property name="uuid" column="uuid"/>
      <Property name="GeoJson" column="geojson" type="String"/>
    </Level>
    <Level name="Statistischer Bezirk" column="gid" nameColumn="statbez_name" type="Integer"  table="statbez">
      <Property name="uuid" column="uuid"/>
      <Property name="GeoJson" column="geojson" type="String"/>
     </Level>
    </Hierarchy>
  </Dimension>

  <Dimension name="Geschlecht" foreignKey="KER_GESCH">
    <Hierarchy hasAll="true" name="Geschlecht (m/w/d)" primaryKey="key">
      <Table name="gender"/>
    <Level name="Geschlecht" column="key" nameColumn="name"/>
    </Hierarchy>
  </Dimension>

  <Dimension name="Alter" foreignKey="AGE">

    <Hierarchy hasAll="true" name="Alter (Einzeljahrgänge)" primaryKey="Age">
      <Table name="AgeGroups"/>
      <Level name="Alter" column="Age"/>
    </Hierarchy>
    
    <Hierarchy hasAll="true" name="Altersgruppen (Standard)" primaryKey="Age">
      <Table name="AgeGroups"/>
      <Level name="Altersgruppe" column="H1" ordinalColumn="H1_Order"/>
      <Level name="Alter" column="Age"/>
    </Hierarchy>

    <Hierarchy hasAll="true" name="Altersgruppen (Kinder)" primaryKey="Age">
      <Table name="AgeGroups"/>
      <Level name="Altersgruppe" column="H2" ordinalColumn="H2_Order"/>
      <Level name="Alter" column="Age"/>
    </Hierarchy>
    
    
    <Hierarchy hasAll="true" name="Altersgruppen (Systematik RKI)" primaryKey="Age">
      <Table name="AgeGroups"/>
      <Level name="Altersgruppe" column="H7" ordinalColumn="H7_Order"/>
      <Level name="Alter" column="Age"/>
    </Hierarchy>
    
    <Hierarchy hasAll="true" name="Altersgruppen (Covidstatistik)" primaryKey="Age">
      <Table name="AgeGroups"/>
      <Level name="Altersgruppe" column="H8" ordinalColumn="H8_Order"/>
      <Level name="Alter" column="Age"/>
    </Hierarchy>
    
    <Hierarchy hasAll="true" name="Altersgruppen (10-Jahres-Gruppen)" primaryKey="Age">
      <Table name="AgeGroups"/>
      <Level name="Altersgruppe" column="H9" ordinalColumn="H9_Order"/>
      <Level name="Alter" column="Age"/>
    </Hierarchy>

  </Dimension>
```

  <Measure aggregator="sum" name="Einwohnerzahl" column="Anzahl"/>
 </Cube>

</Schema>
Dictionary tables:

In the schema we have:

| Dimension  |            |            |            |
|----------  |:----------:|:----------:|-----------:|
|            | Hierarchy1 | Level1     | Property1  |  
|            |            | Level2     | Property2  |
|            |            |  ...       | ...        |
|            |            | Leveln     | Propertyn  |
|            | Hierarchy2 | Level1     | Property1  |  
|            |            | Level2     | Property2  |
|            |            |  ...       | ...        |
|            |            | Leveln     | Propertyn  |
|    -       |    -       |   -        |     -      |
|            | Hierarchyn | Level1     | Property1  |  
|            |            | Level2     | Property2  |
|            |            |  ...       | ...        |
|            |            | Leveln     | Propertyn  |
| Measure1..n|            |            |            |




Levels this is dictionaries tables in database. Name of  dictionaries tables depends of names of cub, dimension, hierarchy, level.  For example: “cubName_dimensionName_hierarchyName_levelName”

Level have “key” and “caption”.  This is columns  names of dictionary table .  Also level be able have “properties”. This is additional columns names  of   dictionary tables.
Level be able have parent level (level2 → level1). This is additional column name  of   dictionary tables.

MDXQuery for get data from xmla server for dictionary table:

```
with
member [Measures].[Key] as
[statistischer Bezirk.Stadt - Planungsraum - statistischer Bezirk].Properties( "Key" )
-- key
member [Measures].[Parent Key] as
[statistischer Bezirk.Stadt - Planungsraum - statistischer Bezirk].Parent.Properties( "Key" )
-- parent key
member [Measures].[Caption] as
[statistischer Bezirk.Stadt - Planungsraum - statistischer Bezirk].Properties( "Caption" )
-- caption
member [Measures].[GeoJson] as
[statistischer Bezirk.Stadt - Planungsraum - statistischer Bezirk].Properties( "GeoJson" )
-- property  geoJson
select
{[Measures].[Key], [Measures].[Parent Key], [Measures].[Caption], [Measures].[GeoJson]} on 0,
[statistischer Bezirk.Stadt - Planungsraum - statistischer Bezirk].[Stadt].Members on 1
--[statistischer Bezirk.Stadt - Planungsraum - statistischer Bezirk].[Planungsraum].Members on 1
from [Bevölkerung]
```

Last level have connection with table of facts.  “Key” of Last level this is column in table of fact.

Additional dictionary this is  dictionary of source. This table “Source” have two columns (source_id (Long), source (String))

Tables of fact:

Every source have cubes. Every cubes are table of fact in target database.

Restriction: Same name of cube in source this is same name of tables fact in target database.

Tables of fact have connection to last level of hierarchies  and measure.
Columns of table of fact are “key” column of last levels  hierarchies. For example.
```
      <Hierarchy hasAll="true" name="Altersgruppen (Systematik RKI)" primaryKey="Age">
        <Table name="AgeGroups"/>
        <Level name="Altersgruppe" column="H7" ordinalColumn="H7_Order"/>
        <Level name="Alter" column="Age"/>
      </Hierarchy>
```
Table of fact have “Alter” column  in table of fact. Name of column depends of names of dimension, hierarchy, level. Every  Hierarchy have column in table of fact with last level “key” as data.  Fact table also have “source_id” column with source id key and connection to “source” dictionary table .


MDXQuery for get data from xmla server for table of fact:
```
DRILLTHROUGH SELECT FROM [Bevölkerung]

RETURN
-- last level in hierarchy 1
[Alter.Alter (Einzeljahrgänge)].[Alter],
-- last level in hierarchy 2
[Alter.Altersgruppen (10-Jahres-Gruppen)].[Alter],
-- last level in hierarchy 3
[Alter.Altersgruppen (Covidstatistik)].[Alter],
-- last level in hierarchy 4
[Alter.Altersgruppen (Kinder)].[Alter],
-- last level in hierarchy 5
[Alter.Altersgruppen (Standard)].[Alter],
-- last level in hierarchy 6
[Alter.Altersgruppen (Systematik RKI)].[Alter],
-- last level in hierarchy 7
[Geschlecht.Geschlecht (m/w/d)].[Geschlecht],
-- last level in hierarchy 8
[Jahr].[Jahr],
-- last level in hierarchy 9
[statistischer Bezirk.Stadt - Planungsraum - statistischer Bezirk].[Statistischer Bezirk],
-- mesure 1
[Measures].[Einwohnerzahl]
```
