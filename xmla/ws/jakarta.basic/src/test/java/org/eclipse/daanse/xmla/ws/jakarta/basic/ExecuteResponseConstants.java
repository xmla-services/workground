package org.eclipse.daanse.xmla.ws.jakarta.basic;

public class ExecuteResponseConstants {

    public static final String DRILL_THROUGH_MAX_ROWS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element minOccurs="0" name="Store_x0020_Country" sql:field="Store Country" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Store_x0020_State" sql:field="Store State" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Store_x0020_City" sql:field="Store City" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Store_x0020_Name" sql:field="Store Name" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Store_x0020_Sqft" sql:field="Store Sqft" type="xsd:int"/>
                                        <xsd:element minOccurs="0" name="Store_x0020_Type" sql:field="Store Type" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Year" sql:field="Year" type="xsd:int"/>
                                        <xsd:element minOccurs="0" name="Quarter" sql:field="Quarter" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Month" sql:field="Month" type="xsd:int"/>
                                        <xsd:element minOccurs="0" name="Week" sql:field="Week" type="xsd:int"/>
                                        <xsd:element minOccurs="0" name="Day" sql:field="Day" type="xsd:int"/>
                                        <xsd:element minOccurs="0" name="Product_x0020_Family" sql:field="Product Family" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Product_x0020_Department" sql:field="Product Department" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Product_x0020_Category" sql:field="Product Category" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Product_x0020_Subcategory" sql:field="Product Subcategory" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Brand_x0020_Name" sql:field="Brand Name" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Product_x0020_Name" sql:field="Product Name" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Media_x0020_Type" sql:field="Media Type" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Promotion_x0020_Name" sql:field="Promotion Name" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="State_x0020_Province" sql:field="State Province" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="City" sql:field="City" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Name" sql:field="Name" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Name_x0020__x0028_Key_x0029_" sql:field="Name (Key)" type="xsd:int"/>
                                        <xsd:element minOccurs="0" name="Education_x0020_Level" sql:field="Education Level" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Gender" sql:field="Gender" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Marital_x0020_Status" sql:field="Marital Status" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Yearly_x0020_Income" sql:field="Yearly Income" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="Unit_x0020_Sales" sql:field="Unit Sales" type="xsd:decimal"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <Store_x0020_Country>86</Store_x0020_Country>
                                <Store_x0020_State>86</Store_x0020_State>
                                <Store_x0020_City>86</Store_x0020_City>
                                <Store_x0020_Name>86</Store_x0020_Name>
                                <Store_x0020_Sqft>86</Store_x0020_Sqft>
                                <Store_x0020_Type>86</Store_x0020_Type>
                                <Year>86</Year>
                                <Quarter>86</Quarter>
                                <Month>86</Month>
                                <Week>86</Week>
                                <Day>86</Day>
                                <Product_x0020_Family>86</Product_x0020_Family>
                                <Product_x0020_Department>86</Product_x0020_Department>
                                <Product_x0020_Category>86</Product_x0020_Category>
                                <Product_x0020_Subcategory>86</Product_x0020_Subcategory>
                                <Brand_x0020_Name>86</Brand_x0020_Name>
                                <Product_x0020_Name>86</Product_x0020_Name>
                                <Media_x0020_Type>86</Media_x0020_Type>
                                <Promotion_x0020_Name>86</Promotion_x0020_Name>
                                <State_x0020_Province>86</State_x0020_Province>
                                <City>86</City>
                                <Name>86</Name>
                                <Name_x0020__x0028_Key_x0029_>86</Name_x0020__x0028_Key_x0029_>
                                <Education_x0020_Level>86</Education_x0020_Level>
                                <Gender>86</Gender>
                                <Marital_x0020_Status>86</Marital_x0020_Status>
                                <Yearly_x0020_Income>86</Yearly_x0020_Income>
                                <Unit_x0020_Sales>86</Unit_x0020_Sales>
                            </row>
                            <row>
                                <Store_x0020_Country xsi:type="xsd:string">USA</Store_x0020_Country>
                                <Store_x0020_State xsi:type="xsd:string">CA</Store_x0020_State>
                                <Store_x0020_City xsi:type="xsd:string">San Francisco</Store_x0020_City>
                                <Store_x0020_Name xsi:type="xsd:string">Store 14</Store_x0020_Name>
                                <Store_x0020_Sqft xsi:type="xsd:int">22478</Store_x0020_Sqft>
                                <Store_x0020_Type xsi:type="xsd:string">Small Grocery</Store_x0020_Type>
                                <Year xsi:type="xsd:int">1997</Year>
                                <Quarter xsi:type="xsd:string">Q1</Quarter>
                                <Month xsi:type="xsd:int">3</Month>
                                <Week xsi:type="xsd:int">11</Week>
                                <Day xsi:type="xsd:int">6</Day>
                                <Product_x0020_Family xsi:type="xsd:string">Drink</Product_x0020_Family>
                                <Product_x0020_Department xsi:type="xsd:string">Beverages</Product_x0020_Department>
                                <Product_x0020_Category xsi:type="xsd:string">Drinks</Product_x0020_Category>
                                <Product_x0020_Subcategory xsi:type="xsd:string">Flavored Drinks</Product_x0020_Subcategory>
                                <Brand_x0020_Name xsi:type="xsd:string">Fabulous</Brand_x0020_Name>
                                <Product_x0020_Name xsi:type="xsd:string">Fabulous Strawberry Drink</Product_x0020_Name>
                                <Media_x0020_Type xsi:type="xsd:string">No Media</Media_x0020_Type>
                                <Promotion_x0020_Name xsi:type="xsd:string">No Promotion</Promotion_x0020_Name>
                                <State_x0020_Province xsi:type="xsd:string">CA</State_x0020_Province>
                                <City xsi:type="xsd:string">Berkeley</City>
                                <Name xsi:type="xsd:string">Achari Harp</Name>
                                <Name_x0020__x0028_Key_x0029_ xsi:type="xsd:int">4617</Name_x0020__x0028_Key_x0029_>
                                <Education_x0020_Level xsi:type="xsd:string">High School Degree</Education_x0020_Level>
                                <Gender xsi:type="xsd:string">M</Gender>
                                <Marital_x0020_Status xsi:type="xsd:string">M</Marital_x0020_Status>
                                <Yearly_x0020_Income xsi:type="xsd:string">$110K - $130K</Yearly_x0020_Income>
                                <Unit_x0020_Sales xsi:type="xsd:decimal">3</Unit_x0020_Sales>
                            </row>
                            <row>
                                <Store_x0020_Country xsi:type="xsd:string">USA</Store_x0020_Country>
                                <Store_x0020_State xsi:type="xsd:string">CA</Store_x0020_State>
                                <Store_x0020_City xsi:type="xsd:string">San Francisco</Store_x0020_City>
                                <Store_x0020_Name xsi:type="xsd:string">Store 14</Store_x0020_Name>
                                <Store_x0020_Sqft xsi:type="xsd:int">22478</Store_x0020_Sqft>
                                <Store_x0020_Type xsi:type="xsd:string">Small Grocery</Store_x0020_Type>
                                <Year xsi:type="xsd:int">1997</Year>
                                <Quarter xsi:type="xsd:string">Q1</Quarter>
                                <Month xsi:type="xsd:int">3</Month>
                                <Week xsi:type="xsd:int">11</Week>
                                <Day xsi:type="xsd:int">6</Day>
                                <Product_x0020_Family xsi:type="xsd:string">Food</Product_x0020_Family>
                                <Product_x0020_Department xsi:type="xsd:string">Baked Goods</Product_x0020_Department>
                                <Product_x0020_Category xsi:type="xsd:string">Bread</Product_x0020_Category>
                                <Product_x0020_Subcategory xsi:type="xsd:string">Sliced Bread</Product_x0020_Subcategory>
                                <Brand_x0020_Name xsi:type="xsd:string">Sphinx</Brand_x0020_Name>
                                <Product_x0020_Name xsi:type="xsd:string">Sphinx Wheat Bread</Product_x0020_Name>
                                <Media_x0020_Type xsi:type="xsd:string">No Media</Media_x0020_Type>
                                <Promotion_x0020_Name xsi:type="xsd:string">No Promotion</Promotion_x0020_Name>
                                <State_x0020_Province xsi:type="xsd:string">CA</State_x0020_Province>
                                <City xsi:type="xsd:string">Berkeley</City>
                                <Name xsi:type="xsd:string">Achari Harp</Name>
                                <Name_x0020__x0028_Key_x0029_ xsi:type="xsd:int">4617</Name_x0020__x0028_Key_x0029_>
                                <Education_x0020_Level xsi:type="xsd:string">High School Degree</Education_x0020_Level>
                                <Gender xsi:type="xsd:string">M</Gender>
                                <Marital_x0020_Status xsi:type="xsd:string">M</Marital_x0020_Status>
                                <Yearly_x0020_Income xsi:type="xsd:string">$110K - $130K</Yearly_x0020_Income>
                                <Unit_x0020_Sales xsi:type="xsd:decimal">3</Unit_x0020_Sales>
                            </row>
                            <row>
                                <Store_x0020_Country xsi:type="xsd:string">USA</Store_x0020_Country>
                                <Store_x0020_State xsi:type="xsd:string">CA</Store_x0020_State>
                                <Store_x0020_City xsi:type="xsd:string">San Francisco</Store_x0020_City>
                                <Store_x0020_Name xsi:type="xsd:string">Store 14</Store_x0020_Name>
                                <Store_x0020_Sqft xsi:type="xsd:int">22478</Store_x0020_Sqft>
                                <Store_x0020_Type xsi:type="xsd:string">Small Grocery</Store_x0020_Type>
                                <Year xsi:type="xsd:int">1997</Year>
                                <Quarter xsi:type="xsd:string">Q1</Quarter>
                                <Month xsi:type="xsd:int">3</Month>
                                <Week xsi:type="xsd:int">11</Week>
                                <Day xsi:type="xsd:int">6</Day>
                                <Product_x0020_Family xsi:type="xsd:string">Food</Product_x0020_Family>
                                <Product_x0020_Department xsi:type="xsd:string">Deli</Product_x0020_Department>
                                <Product_x0020_Category xsi:type="xsd:string">Meat</Product_x0020_Category>
                                <Product_x0020_Subcategory xsi:type="xsd:string">Bologna</Product_x0020_Subcategory>
                                <Brand_x0020_Name xsi:type="xsd:string">Red Spade</Brand_x0020_Name>
                                <Product_x0020_Name xsi:type="xsd:string">Red Spade Beef Bologna</Product_x0020_Name>
                                <Media_x0020_Type xsi:type="xsd:string">No Media</Media_x0020_Type>
                                <Promotion_x0020_Name xsi:type="xsd:string">No Promotion</Promotion_x0020_Name>
                                <State_x0020_Province xsi:type="xsd:string">CA</State_x0020_Province>
                                <City xsi:type="xsd:string">Berkeley</City>
                                <Name xsi:type="xsd:string">Achari Harp</Name>
                                <Name_x0020__x0028_Key_x0029_ xsi:type="xsd:int">4617</Name_x0020__x0028_Key_x0029_>
                                <Education_x0020_Level xsi:type="xsd:string">High School Degree</Education_x0020_Level>
                                <Gender xsi:type="xsd:string">M</Gender>
                                <Marital_x0020_Status xsi:type="xsd:string">M</Marital_x0020_Status>
                                <Yearly_x0020_Income xsi:type="xsd:string">$110K - $130K</Yearly_x0020_Income>
                                <Unit_x0020_Sales xsi:type="xsd:decimal">3</Unit_x0020_Sales>
                            </row>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String DRILL_THROUGH_RESPONSE = """
            <?xml version="1.0"?>
            <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                    <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                        <return>
                            <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                    <xsd:element name="root">
                                        <xsd:complexType>
                                            <xsd:sequence>
                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                            </xsd:sequence>
                                        </xsd:complexType>
                                    </xsd:element>
                                    <xsd:simpleType name="uuid">
                                        <xsd:restriction base="xsd:string">
                                            <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                        </xsd:restriction>
                                    </xsd:simpleType>
                                    <xsd:complexType name="row">
                                        <xsd:sequence>
                                            <xsd:element minOccurs="0" name="Store_x0020_Country" sql:field="Store Country" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Store_x0020_State" sql:field="Store State" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Store_x0020_City" sql:field="Store City" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Store_x0020_Name" sql:field="Store Name" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Store_x0020_Sqft" sql:field="Store Sqft" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Store_x0020_Type" sql:field="Store Type" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Year" sql:field="Year" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Quarter" sql:field="Quarter" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Month" sql:field="Month" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Week" sql:field="Week" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Day" sql:field="Day" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Product_x0020_Family" sql:field="Product Family" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Product_x0020_Department" sql:field="Product Department" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Product_x0020_Category" sql:field="Product Category" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Product_x0020_Subcategory" sql:field="Product Subcategory" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Brand_x0020_Name" sql:field="Brand Name" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Product_x0020_Name" sql:field="Product Name" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Media_x0020_Type" sql:field="Media Type" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Promotion_x0020_Name" sql:field="Promotion Name" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="State_x0020_Province" sql:field="State Province" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="City" sql:field="City" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Name" sql:field="Name" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Name_x0020__x0028_Key_x0029_" sql:field="Name (Key)" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Education_x0020_Level" sql:field="Education Level" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Gender" sql:field="Gender" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Marital_x0020_Status" sql:field="Marital Status" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Yearly_x0020_Income" sql:field="Yearly Income" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Sales_x0020_Count" sql:field="Sales Count" type="xsd:int"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:schema>
                                <row>
                                    <Store_x0020_Country>2</Store_x0020_Country>
                                    <Store_x0020_State>2</Store_x0020_State>
                                    <Store_x0020_City>2</Store_x0020_City>
                                    <Store_x0020_Name>2</Store_x0020_Name>
                                    <Store_x0020_Sqft>2</Store_x0020_Sqft>
                                    <Store_x0020_Type>2</Store_x0020_Type>
                                    <Year>2</Year>
                                    <Quarter>2</Quarter>
                                    <Month>2</Month>
                                    <Week>2</Week>
                                    <Day>2</Day>
                                    <Product_x0020_Family>2</Product_x0020_Family>
                                    <Product_x0020_Department>2</Product_x0020_Department>
                                    <Product_x0020_Category>2</Product_x0020_Category>
                                    <Product_x0020_Subcategory>2</Product_x0020_Subcategory>
                                    <Brand_x0020_Name>2</Brand_x0020_Name>
                                    <Product_x0020_Name>2</Product_x0020_Name>
                                    <Media_x0020_Type>2</Media_x0020_Type>
                                    <Promotion_x0020_Name>2</Promotion_x0020_Name>
                                    <State_x0020_Province>2</State_x0020_Province>
                                    <City>2</City>
                                    <Name>2</Name>
                                    <Name_x0020__x0028_Key_x0029_>2</Name_x0020__x0028_Key_x0029_>
                                    <Education_x0020_Level>2</Education_x0020_Level>
                                    <Gender>2</Gender>
                                    <Marital_x0020_Status>2</Marital_x0020_Status>
                                    <Yearly_x0020_Income>2</Yearly_x0020_Income>
                                    <Sales_x0020_Count>2</Sales_x0020_Count>
                                </row>
                                <row>
                                    <Store_x0020_Country xsi:type="xsd:string">USA</Store_x0020_Country>
                                    <Store_x0020_State xsi:type="xsd:string">CA</Store_x0020_State>
                                    <Store_x0020_City xsi:type="xsd:string">San Francisco</Store_x0020_City>
                                    <Store_x0020_Name xsi:type="xsd:string">Store 14</Store_x0020_Name>
                                    <Store_x0020_Sqft xsi:type="xsd:int">22478</Store_x0020_Sqft>
                                    <Store_x0020_Type xsi:type="xsd:string">Small Grocery</Store_x0020_Type>
                                    <Year xsi:type="xsd:int">1997</Year>
                                    <Quarter xsi:type="xsd:string">Q1</Quarter>
                                    <Month xsi:type="xsd:int">2</Month>
                                    <Week xsi:type="xsd:int">9</Week>
                                    <Day xsi:type="xsd:int">20</Day>
                                    <Product_x0020_Family xsi:type="xsd:string">Drink</Product_x0020_Family>
                                    <Product_x0020_Department xsi:type="xsd:string">Beverages</Product_x0020_Department>
                                    <Product_x0020_Category xsi:type="xsd:string">Pure Juice Beverages</Product_x0020_Category>
                                    <Product_x0020_Subcategory xsi:type="xsd:string">Juice</Product_x0020_Subcategory>
                                    <Brand_x0020_Name xsi:type="xsd:string">Skinner</Brand_x0020_Name>
                                    <Product_x0020_Name xsi:type="xsd:string">Skinner Apple Juice</Product_x0020_Name>
                                    <Media_x0020_Type xsi:type="xsd:string">No Media</Media_x0020_Type>
                                    <Promotion_x0020_Name xsi:type="xsd:string">No Promotion</Promotion_x0020_Name>
                                    <State_x0020_Province xsi:type="xsd:string">CA</State_x0020_Province>
                                    <City xsi:type="xsd:string">Concord</City>
                                    <Name xsi:type="xsd:string">Nina Medina</Name>
                                    <Name_x0020__x0028_Key_x0029_ xsi:type="xsd:int">2948</Name_x0020__x0028_Key_x0029_>
                                    <Education_x0020_Level xsi:type="xsd:string">High School Degree</Education_x0020_Level>
                                    <Gender xsi:type="xsd:string">M</Gender>
                                    <Marital_x0020_Status xsi:type="xsd:string">S</Marital_x0020_Status>
                                    <Yearly_x0020_Income xsi:type="xsd:string">$30K - $50K</Yearly_x0020_Income>
                                    <Sales_x0020_Count xsi:type="xsd:int">1259</Sales_x0020_Count>
                                </row>
                                <row>
                                    <Store_x0020_Country xsi:type="xsd:string">USA</Store_x0020_Country>
                                    <Store_x0020_State xsi:type="xsd:string">CA</Store_x0020_State>
                                    <Store_x0020_City xsi:type="xsd:string">San Francisco</Store_x0020_City>
                                    <Store_x0020_Name xsi:type="xsd:string">Store 14</Store_x0020_Name>
                                    <Store_x0020_Sqft xsi:type="xsd:int">22478</Store_x0020_Sqft>
                                    <Store_x0020_Type xsi:type="xsd:string">Small Grocery</Store_x0020_Type>
                                    <Year xsi:type="xsd:int">1997</Year>
                                    <Quarter xsi:type="xsd:string">Q3</Quarter>
                                    <Month xsi:type="xsd:int">9</Month>
                                    <Week xsi:type="xsd:int">39</Week>
                                    <Day xsi:type="xsd:int">19</Day>
                                    <Product_x0020_Family xsi:type="xsd:string">Drink</Product_x0020_Family>
                                    <Product_x0020_Department xsi:type="xsd:string">Beverages</Product_x0020_Department>
                                    <Product_x0020_Category xsi:type="xsd:string">Pure Juice Beverages</Product_x0020_Category>
                                    <Product_x0020_Subcategory xsi:type="xsd:string">Juice</Product_x0020_Subcategory>
                                    <Brand_x0020_Name xsi:type="xsd:string">Excellent</Brand_x0020_Name>
                                    <Product_x0020_Name xsi:type="xsd:string">Excellent Orange Juice</Product_x0020_Name>
                                    <Media_x0020_Type xsi:type="xsd:string">No Media</Media_x0020_Type>
                                    <Promotion_x0020_Name xsi:type="xsd:string">No Promotion</Promotion_x0020_Name>
                                    <State_x0020_Province xsi:type="xsd:string">CA</State_x0020_Province>
                                    <City xsi:type="xsd:string">Concord</City>
                                    <Name xsi:type="xsd:string">Linda Macias</Name>
                                    <Name_x0020__x0028_Key_x0029_ xsi:type="xsd:int">3956</Name_x0020__x0028_Key_x0029_>
                                    <Education_x0020_Level xsi:type="xsd:string">Bachelors Degree</Education_x0020_Level>
                                    <Gender xsi:type="xsd:string">F</Gender>
                                    <Marital_x0020_Status xsi:type="xsd:string">S</Marital_x0020_Status>
                                    <Yearly_x0020_Income xsi:type="xsd:string">$50K - $70K</Yearly_x0020_Income>
                                    <Sales_x0020_Count xsi:type="xsd:int">319</Sales_x0020_Count>
                                </row>
                            </root>
                        </return>
                    </ExecuteResponse>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
        """;
    public static final String DRILL_THROUGH_ZERO_DIMENSIONAL_QUERY_RESPONSE = """
            <?xml version="1.0"?>
            <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                    <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                        <return>
                            <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                    <xsd:element name="root">
                                        <xsd:complexType>
                                            <xsd:sequence>
                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                            </xsd:sequence>
                                        </xsd:complexType>
                                    </xsd:element>
                                    <xsd:simpleType name="uuid">
                                        <xsd:restriction base="xsd:string">
                                            <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                        </xsd:restriction>
                                    </xsd:simpleType>
                                    <xsd:complexType name="row">
                                        <xsd:sequence>
                                            <xsd:element minOccurs="0" name="Store_x0020_Country" sql:field="Store Country" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Store_x0020_State" sql:field="Store State" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Store_x0020_City" sql:field="Store City" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Store_x0020_Name" sql:field="Store Name" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Store_x0020_Sqft" sql:field="Store Sqft" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Store_x0020_Type" sql:field="Store Type" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Year" sql:field="Year" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Quarter" sql:field="Quarter" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Month" sql:field="Month" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Week" sql:field="Week" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Day" sql:field="Day" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Product_x0020_Family" sql:field="Product Family" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Product_x0020_Department" sql:field="Product Department" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Product_x0020_Category" sql:field="Product Category" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Product_x0020_Subcategory" sql:field="Product Subcategory" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Brand_x0020_Name" sql:field="Brand Name" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Product_x0020_Name" sql:field="Product Name" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Media_x0020_Type" sql:field="Media Type" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Promotion_x0020_Name" sql:field="Promotion Name" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="State_x0020_Province" sql:field="State Province" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="City" sql:field="City" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Name" sql:field="Name" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Name_x0020__x0028_Key_x0029_" sql:field="Name (Key)" type="xsd:int"/>
                                            <xsd:element minOccurs="0" name="Education_x0020_Level" sql:field="Education Level" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Gender" sql:field="Gender" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Marital_x0020_Status" sql:field="Marital Status" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Yearly_x0020_Income" sql:field="Yearly Income" type="xsd:string"/>
                                            <xsd:element minOccurs="0" name="Sales_x0020_Count" sql:field="Sales Count" type="xsd:int"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:schema>
                                <row>
                                    <Store_x0020_Country>1</Store_x0020_Country>
                                    <Store_x0020_State>1</Store_x0020_State>
                                    <Store_x0020_City>1</Store_x0020_City>
                                    <Store_x0020_Name>1</Store_x0020_Name>
                                    <Store_x0020_Sqft>1</Store_x0020_Sqft>
                                    <Store_x0020_Type>1</Store_x0020_Type>
                                    <Year>1</Year>
                                    <Quarter>1</Quarter>
                                    <Month>1</Month>
                                    <Week>1</Week>
                                    <Day>1</Day>
                                    <Product_x0020_Family>1</Product_x0020_Family>
                                    <Product_x0020_Department>1</Product_x0020_Department>
                                    <Product_x0020_Category>1</Product_x0020_Category>
                                    <Product_x0020_Subcategory>1</Product_x0020_Subcategory>
                                    <Brand_x0020_Name>1</Brand_x0020_Name>
                                    <Product_x0020_Name>1</Product_x0020_Name>
                                    <Media_x0020_Type>1</Media_x0020_Type>
                                    <Promotion_x0020_Name>1</Promotion_x0020_Name>
                                    <State_x0020_Province>1</State_x0020_Province>
                                    <City>1</City>
                                    <Name>1</Name>
                                    <Name_x0020__x0028_Key_x0029_>1</Name_x0020__x0028_Key_x0029_>
                                    <Education_x0020_Level>1</Education_x0020_Level>
                                    <Gender>1</Gender>
                                    <Marital_x0020_Status>1</Marital_x0020_Status>
                                    <Yearly_x0020_Income>1</Yearly_x0020_Income>
                                    <Sales_x0020_Count>1</Sales_x0020_Count>
                                </row>
                                <row>
                                    <Store_x0020_Country xsi:type="xsd:string">USA</Store_x0020_Country>
                                    <Store_x0020_State xsi:type="xsd:string">CA</Store_x0020_State>
                                    <Store_x0020_City xsi:type="xsd:string">San Francisco</Store_x0020_City>
                                    <Store_x0020_Name xsi:type="xsd:string">Store 14</Store_x0020_Name>
                                    <Store_x0020_Sqft xsi:type="xsd:int">22478</Store_x0020_Sqft>
                                    <Store_x0020_Type xsi:type="xsd:string">Small Grocery</Store_x0020_Type>
                                    <Year xsi:type="xsd:int">1997</Year>
                                    <Quarter xsi:type="xsd:string">Q3</Quarter>
                                    <Month xsi:type="xsd:int">9</Month>
                                    <Week xsi:type="xsd:int">39</Week>
                                    <Day xsi:type="xsd:int">19</Day>
                                    <Product_x0020_Family xsi:type="xsd:string">Drink</Product_x0020_Family>
                                    <Product_x0020_Department xsi:type="xsd:string">Beverages</Product_x0020_Department>
                                    <Product_x0020_Category xsi:type="xsd:string">Pure Juice Beverages</Product_x0020_Category>
                                    <Product_x0020_Subcategory xsi:type="xsd:string">Juice</Product_x0020_Subcategory>
                                    <Brand_x0020_Name xsi:type="xsd:string">Excellent</Brand_x0020_Name>
                                    <Product_x0020_Name xsi:type="xsd:string">Excellent Orange Juice</Product_x0020_Name>
                                    <Media_x0020_Type xsi:type="xsd:string">No Media</Media_x0020_Type>
                                    <Promotion_x0020_Name xsi:type="xsd:string">No Promotion</Promotion_x0020_Name>
                                    <State_x0020_Province xsi:type="xsd:string">CA</State_x0020_Province>
                                    <City xsi:type="xsd:string">Concord</City>
                                    <Name xsi:type="xsd:string">Linda Macias</Name>
                                    <Name_x0020__x0028_Key_x0029_ xsi:type="xsd:int">3956</Name_x0020__x0028_Key_x0029_>
                                    <Education_x0020_Level xsi:type="xsd:string">Bachelors Degree</Education_x0020_Level>
                                    <Gender xsi:type="xsd:string">F</Gender>
                                    <Marital_x0020_Status xsi:type="xsd:string">S</Marital_x0020_Status>
                                    <Yearly_x0020_Income xsi:type="xsd:string">$50K - $70K</Yearly_x0020_Income>
                                    <Sales_x0020_Count xsi:type="xsd:int">319</Sales_x0020_Count>
                                </row>
                            </root>
                        </return>
                    </ExecuteResponse>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
        """;
    public static final String EXECUTE_SLICER_RESPONSE = """
            <?xml version="1.0"?>
            <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                    <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                        <return>
                            <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                    <xsd:complexType name="MemberType">
                                        <xsd:sequence>
                                            <xsd:element name="UName" type="xsd:string"/>
                                            <xsd:element name="Caption" type="xsd:string"/>
                                            <xsd:element name="LName" type="xsd:string"/>
                                            <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                            <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                            <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                            </xsd:sequence>
                                        </xsd:sequence>
                                        <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                    </xsd:complexType>
                                    <xsd:complexType name="PropType">
                                        <xsd:attribute name="name" type="xsd:string"/>
                                    </xsd:complexType>
                                    <xsd:complexType name="TupleType">
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="Member" type="MemberType"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                    <xsd:complexType name="MembersType">
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="Member" type="MemberType"/>
                                        </xsd:sequence>
                                        <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                    </xsd:complexType>
                                    <xsd:complexType name="TuplesType">
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="Tuple" type="TupleType"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                    <xsd:complexType name="CrossProductType">
                                        <xsd:sequence>
                                            <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                <xsd:element name="Members" type="MembersType"/>
                                                <xsd:element name="Tuples" type="TuplesType"/>
                                            </xsd:choice>
                                        </xsd:sequence>
                                        <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                    </xsd:complexType>
                                    <xsd:complexType name="OlapInfo">
                                        <xsd:sequence>
                                            <xsd:element name="CubeInfo">
                                                <xsd:complexType>
                                                    <xsd:sequence>
                                                        <xsd:element maxOccurs="unbounded" name="Cube">
                                                            <xsd:complexType>
                                                                <xsd:sequence>
                                                                    <xsd:element name="CubeName" type="xsd:string"/>
                                                                </xsd:sequence>
                                                            </xsd:complexType>
                                                        </xsd:element>
                                                    </xsd:sequence>
                                                </xsd:complexType>
                                            </xsd:element>
                                            <xsd:element name="AxesInfo">
                                                <xsd:complexType>
                                                    <xsd:sequence>
                                                        <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                            <xsd:complexType>
                                                                <xsd:sequence>
                                                                    <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                        <xsd:complexType>
                                                                            <xsd:sequence>
                                                                                <xsd:sequence maxOccurs="unbounded">
                                                                                    <xsd:element name="UName" type="PropType"/>
                                                                                    <xsd:element name="Caption" type="PropType"/>
                                                                                    <xsd:element name="LName" type="PropType"/>
                                                                                    <xsd:element name="LNum" type="PropType"/>
                                                                                    <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                                </xsd:sequence>
                                                                                <xsd:sequence>
                                                                                    <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                                </xsd:sequence>
                                                                            </xsd:sequence>
                                                                            <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                        </xsd:complexType>
                                                                    </xsd:element>
                                                                </xsd:sequence>
                                                                <xsd:attribute name="name" type="xsd:string"/>
                                                            </xsd:complexType>
                                                        </xsd:element>
                                                    </xsd:sequence>
                                                </xsd:complexType>
                                            </xsd:element>
                                            <xsd:element name="CellInfo">
                                                <xsd:complexType>
                                                    <xsd:sequence>
                                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                            <xsd:choice>
                                                                <xsd:element name="Value" type="PropType"/>
                                                                <xsd:element name="FmtValue" type="PropType"/>
                                                                <xsd:element name="BackColor" type="PropType"/>
                                                                <xsd:element name="ForeColor" type="PropType"/>
                                                                <xsd:element name="FontName" type="PropType"/>
                                                                <xsd:element name="FontSize" type="PropType"/>
                                                                <xsd:element name="FontFlags" type="PropType"/>
                                                                <xsd:element name="FormatString" type="PropType"/>
                                                                <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                                <xsd:element name="SolveOrder" type="PropType"/>
                                                                <xsd:element name="Updateable" type="PropType"/>
                                                                <xsd:element name="Visible" type="PropType"/>
                                                                <xsd:element name="Expression" type="PropType"/>
                                                            </xsd:choice>
                                                        </xsd:sequence>
                                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                        </xsd:sequence>
                                                    </xsd:sequence>
                                                </xsd:complexType>
                                            </xsd:element>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                    <xsd:complexType name="Axes">
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="Axis">
                                                <xsd:complexType>
                                                    <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                        <xsd:element name="Tuples" type="TuplesType"/>
                                                        <xsd:element name="Members" type="MembersType"/>
                                                    </xsd:choice>
                                                    <xsd:attribute name="name" type="xsd:string"/>
                                                </xsd:complexType>
                                            </xsd:element>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                    <xsd:complexType name="CellData">
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                                <xsd:complexType>
                                                    <xsd:sequence maxOccurs="unbounded">
                                                        <xsd:choice>
                                                            <xsd:element name="Value"/>
                                                            <xsd:element name="FmtValue" type="xsd:string"/>
                                                            <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                            <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                            <xsd:element name="FontName" type="xsd:string"/>
                                                            <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                            <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                            <xsd:element name="FormatString" type="xsd:string"/>
                                                            <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                            <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                            <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                            <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                            <xsd:element name="Expression" type="xsd:string"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                                </xsd:complexType>
                                            </xsd:element>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                    <xsd:element name="root">
                                        <xsd:complexType>
                                            <xsd:sequence maxOccurs="unbounded">
                                                <xsd:element name="OlapInfo" type="OlapInfo"/>
                                                <xsd:element name="Axes" type="Axes"/>
                                                <xsd:element name="CellData" type="CellData"/>
                                            </xsd:sequence>
                                        </xsd:complexType>
                                    </xsd:element>
                                </xsd:schema>
                                <OlapInfo>
                                    <CubeInfo>
                                        <Cube>
                                            <CubeName>Sales</CubeName>
                                            <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                            <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                        </Cube>
                                    </CubeInfo>
                                    <AxesInfo>
                                        <AxisInfo name="Axis0">
                                            <HierarchyInfo name="[Customers]">
                                                <UName name="[Customers].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                                <Caption name="[Customers].[MEMBER_CAPTION]" type="xsd:string"/>
                                                <LName name="[Customers].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                                <LNum name="[Customers].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                                <DisplayInfo name="[Customers].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            </HierarchyInfo>
                                        </AxisInfo>
                                        <AxisInfo name="Axis1">
                                            <HierarchyInfo name="[Gender]">
                                                <UName name="[Gender].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                                <Caption name="[Gender].[MEMBER_CAPTION]" type="xsd:string"/>
                                                <LName name="[Gender].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                                <LNum name="[Gender].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                                <DisplayInfo name="[Gender].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            </HierarchyInfo>
                                        </AxisInfo>
                                        <AxisInfo name="SlicerAxis">
                                            <HierarchyInfo name="[Time]">
                                                <UName name="[Time].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                                <Caption name="[Time].[MEMBER_CAPTION]" type="xsd:string"/>
                                                <LName name="[Time].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                                <LNum name="[Time].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                                <DisplayInfo name="[Time].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            </HierarchyInfo>
                                            <HierarchyInfo name="[Marital Status]">
                                                <UName name="[Marital Status].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                                <Caption name="[Marital Status].[MEMBER_CAPTION]" type="xsd:string"/>
                                                <LName name="[Marital Status].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                                <LNum name="[Marital Status].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                                <DisplayInfo name="[Marital Status].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            </HierarchyInfo>
                                            <HierarchyInfo name="[Measures]">
                                                <UName name="[Measures].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                                <Caption name="[Measures].[MEMBER_CAPTION]" type="xsd:string"/>
                                                <LName name="[Measures].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                                <LNum name="[Measures].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                                <DisplayInfo name="[Measures].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            </HierarchyInfo>
                                        </AxisInfo>
                                    </AxesInfo>
                                    <CellInfo>
                                        <Value name="VALUE"/>
                                        <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                    </CellInfo>
                                </OlapInfo>
                                <Axes>
                                    <Axis name="Axis0">
                                        <Tuples>
                                            <Tuple>
                                                <Member Hierarchy="[Customers]">
                                                    <UName>[Customers].[Canada]</UName>
                                                    <Caption>Canada</Caption>
                                                    <LName>[Customers].[Country]</LName>
                                                    <LNum>1</LNum>
                                                    <DisplayInfo>1</DisplayInfo>
                                                </Member>
                                            </Tuple>
                                            <Tuple>
                                                <Member Hierarchy="[Customers]">
                                                    <UName>[Customers].[Mexico]</UName>
                                                    <Caption>Mexico</Caption>
                                                    <LName>[Customers].[Country]</LName>
                                                    <LNum>1</LNum>
                                                    <DisplayInfo>131081</DisplayInfo>
                                                </Member>
                                            </Tuple>
                                            <Tuple>
                                                <Member Hierarchy="[Customers]">
                                                    <UName>[Customers].[USA]</UName>
                                                    <Caption>USA</Caption>
                                                    <LName>[Customers].[Country]</LName>
                                                    <LNum>1</LNum>
                                                    <DisplayInfo>131075</DisplayInfo>
                                                </Member>
                                            </Tuple>
                                        </Tuples>
                                    </Axis>
                                    <Axis name="Axis1">
                                        <Tuples>
                                            <Tuple>
                                                <Member Hierarchy="[Gender]">
                                                    <UName>[Gender].[F]</UName>
                                                    <Caption>F</Caption>
                                                    <LName>[Gender].[Gender]</LName>
                                                    <LNum>1</LNum>
                                                    <DisplayInfo>0</DisplayInfo>
                                                </Member>
                                            </Tuple>
                                            <Tuple>
                                                <Member Hierarchy="[Gender]">
                                                    <UName>[Gender].[M]</UName>
                                                    <Caption>M</Caption>
                                                    <LName>[Gender].[Gender]</LName>
                                                    <LNum>1</LNum>
                                                    <DisplayInfo>131072</DisplayInfo>
                                                </Member>
                                            </Tuple>
                                        </Tuples>
                                    </Axis>
                                    <Axis name="SlicerAxis">
                                        <Tuples>
                                            <Tuple>
                                                <Member Hierarchy="[Time]">
                                                    <UName>[Time].[1997].[Q2]</UName>
                                                    <Caption>Q2</Caption>
                                                    <LName>[Time].[Quarter]</LName>
                                                    <LNum>1</LNum>
                                                    <DisplayInfo>3</DisplayInfo>
                                                </Member>
                                                <Member Hierarchy="[Marital Status]">
                                                    <UName>[Marital Status].[All Marital Status]</UName>
                                                    <Caption>All Marital Status</Caption>
                                                    <LName>[Marital Status].[(All)]</LName>
                                                    <LNum>0</LNum>
                                                    <DisplayInfo>111</DisplayInfo>
                                                </Member>
                                                <Member Hierarchy="[Measures]">
                                                    <UName>[Measures].[Store Sales]</UName>
                                                    <Caption>Store Sales</Caption>
                                                    <LName>[Measures].[MeasuresLevel]</LName>
                                                    <LNum>0</LNum>
                                                    <DisplayInfo>0</DisplayInfo>
                                                </Member>
                                            </Tuple>
                                        </Tuples>
                                    </Axis>
                                </Axes>
                                <CellData>
                                    <Cell CellOrdinal="0">
                                        <FmtValue/>
                                    </Cell>
                                    <Cell CellOrdinal="1">
                                        <FmtValue/>
                                    </Cell>
                                    <Cell CellOrdinal="2">
                                        <Value xsi:type="xsd:double">65857.14</Value>
                                        <FmtValue>65,857.14</FmtValue>
                                    </Cell>
                                    <Cell CellOrdinal="3">
                                        <FmtValue/>
                                    </Cell>
                                    <Cell CellOrdinal="4">
                                        <FmtValue/>
                                    </Cell>
                                    <Cell CellOrdinal="5">
                                        <Value xsi:type="xsd:double">66809.13</Value>
                                        <FmtValue>66,809.13</FmtValue>
                                    </Cell>
                                </CellData>
                            </root>
                        </return>
                    </ExecuteResponse>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
        """;

    public static final String EXECUTE_SLICER_JSON_RESPONSE = """
        "ExecuteResponse": {
          "return": {
            "root": {
              "OlapInfo": {
                "CubeInfo": {
                  "Cube": {
                    "CubeName": "Sales",
                    "LastDataUpdate": {"xxxx-xx-xxTxx:xx:xx"
                    },
                    "LastSchemaUpdate": {"xxxx-xx-xxTxx:xx:xx"
                    }
                  }
                },
                "AxesInfo": [
                  {
                    "HierarchyInfo": [
                      {
                        "UName": {
                        },
                        "Caption": {
                        },
                        "LName": {
                        },
                        "LNum": {
                        },
                        "DisplayInfo": {
                        }
                      }
                    ]
                  },
                  {
                    "HierarchyInfo": [
                      {
                        "UName": {
                        },
                        "Caption": {
                        },
                        "LName": {
                        },
                        "LNum": {
                        },
                        "DisplayInfo": {
                        }
                      }
                    ]
                  },
                  {
                    "HierarchyInfo": [
                      {
                        "UName": {
                        },
                        "Caption": {
                        },
                        "LName": {
                        },
                        "LNum": {
                        },
                        "DisplayInfo": {
                        }
                      },
                      {
                        "UName": {
                        },
                        "Caption": {
                        },
                        "LName": {
                        },
                        "LNum": {
                        },
                        "DisplayInfo": {
                        }
                      },
                      {
                        "UName": {
                        },
                        "Caption": {
                        },
                        "LName": {
                        },
                        "LNum": {
                        },
                        "DisplayInfo": {
                        }
                      }
                    ]
                  }
                ],
                "CellInfo": {
                  "Value": {
                  },
                  "FmtValue": {
                  }
                }
              },
              "Axes": [
                {
                  "Tuples": [
                    [
                      {
                        "UName": "[Customers].[Canada]",
                        "Caption": "Canada",
                        "LName": "[Customers].[Country]",
                        "LNum": 1,
                        "DisplayInfo": 1
                      }
                    ],
                    [
                      {
                        "UName": "[Customers].[Mexico]",
                        "Caption": "Mexico",
                        "LName": "[Customers].[Country]",
                        "LNum": 1,
                        "DisplayInfo": 131081
                      }
                    ],
                    [
                      {
                        "UName": "[Customers].[USA]",
                        "Caption": "USA",
                        "LName": "[Customers].[Country]",
                        "LNum": 1,
                        "DisplayInfo": 131075
                      }
                    ]
                  ]
                },
                {
                  "Tuples": [
                    [
                      {
                        "UName": "[Gender].[F]",
                        "Caption": "F",
                        "LName": "[Gender].[Gender]",
                        "LNum": 1,
                        "DisplayInfo": 0
                      }
                    ],
                    [
                      {
                        "UName": "[Gender].[M]",
                        "Caption": "M",
                        "LName": "[Gender].[Gender]",
                        "LNum": 1,
                        "DisplayInfo": 131072
                      }
                    ]
                  ]
                },
                {
                  "Tuples": [
                    [
                      {
                        "UName": "[Time].[1997].[Q2]",
                        "Caption": "Q2",
                        "LName": "[Time].[Quarter]",
                        "LNum": 1,
                        "DisplayInfo": 3
                      },
                      {
                        "UName": "[Marital Status].[All Marital Status]",
                        "Caption": "All Marital Status",
                        "LName": "[Marital Status].[(All)]",
                        "LNum": 0,
                        "DisplayInfo": 111
                      },
                      {
                        "UName": "[Measures].[Store Sales]",
                        "Caption": "Store Sales",
                        "LName": "[Measures].[MeasuresLevel]",
                        "LNum": 0,
                        "DisplayInfo": 0
                      }
                    ]
                  ]
                }
              ],
              "CellData": [
                {
                  "FmtValue": ""
                },
                {
                  "FmtValue": ""
                },
                {
                  "Value": 65857.14,
                  "FmtValue": "65,857.14"
                },
                {
                  "FmtValue": ""
                },
                {
                  "FmtValue": ""
                },
                {
                  "Value": 66809.13,
                  "FmtValue": "66,809.13"
                }
              ]
            }
          }
        }
        """;
    public static final String EXECUTE_WITHOUT_CELL_PROPERTIES_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Customers]">
                                            <UName name="[Customers].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Customers].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Customers].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Customers].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Customers].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="Axis1">
                                        <HierarchyInfo name="[Gender]">
                                            <UName name="[Gender].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Gender].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Gender].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Gender].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Gender].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis">
                                        <HierarchyInfo name="[Time]">
                                            <UName name="[Time].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Time].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Time].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Time].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Time].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                        <HierarchyInfo name="[Marital Status]">
                                            <UName name="[Marital Status].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Marital Status].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Marital Status].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Marital Status].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Marital Status].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                        <HierarchyInfo name="[Measures]">
                                            <UName name="[Measures].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Measures].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Measures].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Measures].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Measures].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>1</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Mexico]</UName>
                                                <Caption>Mexico</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131081</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                                <Axis name="Axis1">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Gender]">
                                                <UName>[Gender].[F]</UName>
                                                <Caption>F</Caption>
                                                <LName>[Gender].[Gender]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Gender]">
                                                <UName>[Gender].[M]</UName>
                                                <Caption>M</Caption>
                                                <LName>[Gender].[Gender]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131072</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                                <Axis name="SlicerAxis">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Time]">
                                                <UName>[Time].[1997].[Q2]</UName>
                                                <Caption>Q2</Caption>
                                                <LName>[Time].[Quarter]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>3</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Marital Status]">
                                                <UName>[Marital Status].[All Marital Status]</UName>
                                                <Caption>All Marital Status</Caption>
                                                <LName>[Marital Status].[(All)]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>111</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Measures]">
                                                <UName>[Measures].[Store Sales]</UName>
                                                <Caption>Store Sales</Caption>
                                                <LName>[Measures].[MeasuresLevel]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="1">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="2">
                                    <Value xsi:type="xsd:double">65857.14</Value>
                                    <FmtValue>65,857.14</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="3">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="4">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="5">
                                    <Value xsi:type="xsd:double">66809.13</Value>
                                    <FmtValue>66,809.13</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String EXECUTE_WITH_CELL_PROPERTIES_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Customers]">
                                            <UName name="[Customers].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Customers].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Customers].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Customers].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Customers].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="Axis1">
                                        <HierarchyInfo name="[Gender]">
                                            <UName name="[Gender].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Gender].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Gender].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Gender].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Gender].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis">
                                        <HierarchyInfo name="[Time]">
                                            <UName name="[Time].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Time].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Time].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Time].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Time].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                        <HierarchyInfo name="[Marital Status]">
                                            <UName name="[Marital Status].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Marital Status].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Marital Status].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Marital Status].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Marital Status].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                        <HierarchyInfo name="[Measures]">
                                            <UName name="[Measures].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Measures].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Measures].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Measures].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Measures].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                </AxesInfo>
                                <CellInfo>
                                    <FormatString name="FORMAT_STRING" type="xsd:string"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>1</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Mexico]</UName>
                                                <Caption>Mexico</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131081</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                                <Axis name="Axis1">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Gender]">
                                                <UName>[Gender].[F]</UName>
                                                <Caption>F</Caption>
                                                <LName>[Gender].[Gender]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Gender]">
                                                <UName>[Gender].[M]</UName>
                                                <Caption>M</Caption>
                                                <LName>[Gender].[Gender]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131072</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                                <Axis name="SlicerAxis">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Time]">
                                                <UName>[Time].[1997].[Q2]</UName>
                                                <Caption>Q2</Caption>
                                                <LName>[Time].[Quarter]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>3</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Marital Status]">
                                                <UName>[Marital Status].[All Marital Status]</UName>
                                                <Caption>All Marital Status</Caption>
                                                <LName>[Marital Status].[(All)]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>111</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Measures]">
                                                <UName>[Measures].[Store Sales]</UName>
                                                <Caption>Store Sales</Caption>
                                                <LName>[Measures].[MeasuresLevel]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <FormatString>#,###.00</FormatString>
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="1">
                                    <FormatString>#,###.00</FormatString>
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="2">
                                    <FormatString>#,###.00</FormatString>
                                    <FmtValue>65,857.14</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="3">
                                    <FormatString>#,###.00</FormatString>
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="4">
                                    <FormatString>#,###.00</FormatString>
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="5">
                                    <FormatString>#,###.00</FormatString>
                                    <FmtValue>66,809.13</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String EXECUTE_WITH_MEMBER_KEY_DIMENSION_PROPERTY_FOR_MEMBER_WITHOUT_KEY_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Customers]">
                                            <UName name="[Customers].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Customers].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Customers].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Customers].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Customers].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="Axis1">
                                        <HierarchyInfo name="[Gender]">
                                            <UName name="[Gender].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Gender].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Gender].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Gender].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Gender].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            <MEMBER_KEY name="[Gender].[MEMBER_KEY]" type="xsd:string"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis">
                                        <HierarchyInfo name="[Time]">
                                            <UName name="[Time].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Time].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Time].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Time].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Time].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                        <HierarchyInfo name="[Marital Status]">
                                            <UName name="[Marital Status].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Marital Status].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Marital Status].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Marital Status].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Marital Status].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                        <HierarchyInfo name="[Measures]">
                                            <UName name="[Measures].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Measures].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Measures].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Measures].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Measures].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>1</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Mexico]</UName>
                                                <Caption>Mexico</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131081</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                                <Axis name="Axis1">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Gender]">
                                                <UName>[Gender].[F]</UName>
                                                <Caption>F</Caption>
                                                <LName>[Gender].[Gender]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                                <MEMBER_KEY>F</MEMBER_KEY>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Gender]">
                                                <UName>[Gender].[M]</UName>
                                                <Caption>M</Caption>
                                                <LName>[Gender].[Gender]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131072</DisplayInfo>
                                                <MEMBER_KEY>M</MEMBER_KEY>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                                <Axis name="SlicerAxis">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Time]">
                                                <UName>[Time].[1997].[Q2]</UName>
                                                <Caption>Q2</Caption>
                                                <LName>[Time].[Quarter]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>3</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Marital Status]">
                                                <UName>[Marital Status].[All Marital Status]</UName>
                                                <Caption>All Marital Status</Caption>
                                                <LName>[Marital Status].[(All)]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>111</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Measures]">
                                                <UName>[Measures].[Store Sales]</UName>
                                                <Caption>Store Sales</Caption>
                                                <LName>[Measures].[MeasuresLevel]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="1">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="2">
                                    <Value xsi:type="xsd:double">65857.14</Value>
                                    <FmtValue>65,857.14</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="3">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="4">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="5">
                                    <Value xsi:type="xsd:double">66809.13</Value>
                                    <FmtValue>66,809.13</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String EXECUTE_ALIAS_WITH_SHARED_DIMENSION_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Measures]">
                                            <UName name="[Measures].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Measures].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Measures].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Measures].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Measures].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="Axis1">
                                        <HierarchyInfo name="[Customers-Alias]">
                                            <UName name="[Customers-Alias].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Customers-Alias].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Customers-Alias].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Customers-Alias].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Customers-Alias].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis"/>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Measures]">
                                                <UName>[Measures].[Unit Sales]</UName>
                                                <Caption>Unit Sales</Caption>
                                                <LName>[Measures].[MeasuresLevel]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                                <Axis name="Axis1">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Customers-Alias]">
                                                <UName>[Customers-Alias].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers-Alias].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Customers-Alias]">
                                                <UName>[Customers-Alias].[Mexico]</UName>
                                                <Caption>Mexico</Caption>
                                                <LName>[Customers-Alias].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131072</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Customers-Alias]">
                                                <UName>[Customers-Alias].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers-Alias].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131072</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <Value xsi:type="xsd:double">46157</Value>
                                    <FmtValue>46,157</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="1">
                                    <Value xsi:type="xsd:double">203914</Value>
                                    <FmtValue>203,914</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="2">
                                    <Value xsi:type="xsd:double">259916</Value>
                                    <FmtValue>259,916</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
                """;

    public static final String EXECUTE_WITH_MEMBER_KEY_DIMENSION_PROPERTY_FOR_MEMBER_WITH_KEY_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Customers]">
                                            <UName name="[Customers].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Customers].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Customers].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Customers].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Customers].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            <MEMBER_KEY name="[Customers].[MEMBER_KEY]" type="xsd:string"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis"/>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA].[CA].[Altadena].[Alice Cantrell]</UName>
                                                <Caption>Alice Cantrell</Caption>
                                                <LName>[Customers].[Name]</LName>
                                                <LNum>4</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                                <MEMBER_KEY>4422</MEMBER_KEY>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <Value xsi:type="xsd:double">55</Value>
                                    <FmtValue>55</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String EXECUTE_WITH_MEMBER_KEY_DIMENSION_PROPERTY_FOR_ALL_MEMBER_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Customers]">
                                            <UName name="[Customers].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Customers].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Customers].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Customers].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Customers].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            <MEMBER_KEY name="[Customers].[MEMBER_KEY]" type="xsd:string"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis"/>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[All Customers]</UName>
                                                <Caption>All Customers</Caption>
                                                <LName>[Customers].[(All)]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>3</DisplayInfo>
                                                <MEMBER_KEY>0</MEMBER_KEY>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <Value xsi:type="xsd:double">266773</Value>
                                    <FmtValue>266,773</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String EXECUTE_WITH_DIMENSION_PROPERTY_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Customers]">
                                            <UName name="[Customers].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Customers].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Customers].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Customers].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Customers].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            <MEMBER_KEY name="[Customers].[MEMBER_KEY]" type="xsd:string"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis"/>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA].[CA].[Altadena].[Alice Cantrell]</UName>
                                                <Caption>Alice Cantrell</Caption>
                                                <LName>[Customers].[Name]</LName>
                                                <LNum>4</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                                <MEMBER_KEY>4422</MEMBER_KEY>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <Value xsi:type="xsd:double">55</Value>
                                    <FmtValue>55</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String EXECUTE_WITH_DIMENSION_PROPERTIES_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Customers]">
                                            <UName name="[Customers].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Customers].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Customers].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Customers].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Customers].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="Axis1">
                                        <HierarchyInfo name="[Gender]">
                                            <UName name="[Gender].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Gender].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Gender].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Gender].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Gender].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            <PARENT_LEVEL name="[Gender].[PARENT_LEVEL]" type="xsd:unsignedInt"/>
                                            <PARENT_UNIQUE_NAME name="[Gender].[PARENT_UNIQUE_NAME]" type="xsd:string"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis">
                                        <HierarchyInfo name="[Time]">
                                            <UName name="[Time].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Time].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Time].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Time].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Time].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                        <HierarchyInfo name="[Marital Status]">
                                            <UName name="[Marital Status].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Marital Status].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Marital Status].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Marital Status].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Marital Status].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                        <HierarchyInfo name="[Measures]">
                                            <UName name="[Measures].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Measures].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Measures].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Measures].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Measures].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>1</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Mexico]</UName>
                                                <Caption>Mexico</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131081</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                                <Axis name="Axis1">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Gender]">
                                                <UName>[Gender].[F]</UName>
                                                <Caption>F</Caption>
                                                <LName>[Gender].[Gender]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                                <PARENT_LEVEL>0</PARENT_LEVEL>
                                                <PARENT_UNIQUE_NAME>[Gender].[All Gender]</PARENT_UNIQUE_NAME>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Gender]">
                                                <UName>[Gender].[M]</UName>
                                                <Caption>M</Caption>
                                                <LName>[Gender].[Gender]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131072</DisplayInfo>
                                                <PARENT_LEVEL>0</PARENT_LEVEL>
                                                <PARENT_UNIQUE_NAME>[Gender].[All Gender]</PARENT_UNIQUE_NAME>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                                <Axis name="SlicerAxis">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Time]">
                                                <UName>[Time].[1997].[Q2]</UName>
                                                <Caption>Q2</Caption>
                                                <LName>[Time].[Quarter]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>3</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Marital Status]">
                                                <UName>[Marital Status].[All Marital Status]</UName>
                                                <Caption>All Marital Status</Caption>
                                                <LName>[Marital Status].[(All)]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>111</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Measures]">
                                                <UName>[Measures].[Store Sales]</UName>
                                                <Caption>Store Sales</Caption>
                                                <LName>[Measures].[MeasuresLevel]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="1">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="2">
                                    <Value xsi:type="xsd:double">65857.14</Value>
                                    <FmtValue>65,857.14</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="3">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="4">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="5">
                                    <Value xsi:type="xsd:double">66809.13</Value>
                                    <FmtValue>66,809.13</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String EXECUTE_CROSS_JOIN_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Product]">
                                            <UName name="[Product].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Product].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Product].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Product].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Product].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                        <HierarchyInfo name="[Customers]">
                                            <UName name="[Customers].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Customers].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Customers].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Customers].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Customers].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis"/>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Drink]</UName>
                                                <Caption>Drink</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>3</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>1</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Drink]</UName>
                                                <Caption>Drink</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Mexico]</UName>
                                                <Caption>Mexico</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131081</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Drink]</UName>
                                                <Caption>Drink</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Food]</UName>
                                                <Caption>Food</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131087</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131073</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Food]</UName>
                                                <Caption>Food</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131087</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Mexico]</UName>
                                                <Caption>Mexico</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131081</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Food]</UName>
                                                <Caption>Food</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131087</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Non-Consumable]</UName>
                                                <Caption>Non-Consumable</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131077</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131073</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Non-Consumable]</UName>
                                                <Caption>Non-Consumable</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131077</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Mexico]</UName>
                                                <Caption>Mexico</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131081</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Non-Consumable]</UName>
                                                <Caption>Non-Consumable</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131077</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="1">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="2">
                                    <Value xsi:type="xsd:double">24597</Value>
                                    <FmtValue>24,597</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="3">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="4">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="5">
                                    <Value xsi:type="xsd:double">191940</Value>
                                    <FmtValue>191,940</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="6">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="7">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="8">
                                    <Value xsi:type="xsd:double">50236</Value>
                                    <FmtValue>50,236</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
                """;

    public static final String EXECUTE_CROSS_JOIN_ROLE_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Product]">
                                            <UName name="[Product].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Product].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Product].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Product].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Product].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                        <HierarchyInfo name="[Customers]">
                                            <UName name="[Customers].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Customers].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Customers].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Customers].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Customers].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis"/>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Drink]</UName>
                                                <Caption>Drink</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>3</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>1</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Drink]</UName>
                                                <Caption>Drink</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Food]</UName>
                                                <Caption>Food</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131087</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131073</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Food]</UName>
                                                <Caption>Food</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131087</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Non-Consumable]</UName>
                                                <Caption>Non-Consumable</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131077</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[Canada]</UName>
                                                <Caption>Canada</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131073</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Product]">
                                                <UName>[Product].[Non-Consumable]</UName>
                                                <Caption>Non-Consumable</Caption>
                                                <LName>[Product].[Product Family]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131077</DisplayInfo>
                                            </Member>
                                            <Member Hierarchy="[Customers]">
                                                <UName>[Customers].[USA]</UName>
                                                <Caption>USA</Caption>
                                                <LName>[Customers].[Country]</LName>
                                                <LNum>1</LNum>
                                                <DisplayInfo>131075</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="1">
                                    <Value xsi:type="xsd:double">24597</Value>
                                    <FmtValue>24,597</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="2">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="3">
                                    <Value xsi:type="xsd:double">191940</Value>
                                    <FmtValue>191,940</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="4">
                                    <FmtValue/>
                                </Cell>
                                <Cell CellOrdinal="5">
                                    <Value xsi:type="xsd:double">50236</Value>
                                    <FmtValue>50,236</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
                """;

    public static final String EXECUTE_BUG_MONDRIAN_762_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>HR</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Time]">
                                            <UName name="[Time].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Time].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Time].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Time].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Time].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                            <PARENT_UNIQUE_NAME name="[Time].[PARENT_UNIQUE_NAME]" type="xsd:string"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis">
                                        <HierarchyInfo name="[Measures]">
                                            <UName name="[Measures].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Measures].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Measures].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Measures].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Measures].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Time]">
                                                <UName>[Time].[1997]</UName>
                                                <Caption>1997</Caption>
                                                <LName>[Time].[Year]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>4</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                        <Tuple>
                                            <Member Hierarchy="[Time]">
                                                <UName>[Time].[1998]</UName>
                                                <Caption>1998</Caption>
                                                <LName>[Time].[Year]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>4</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                                <Axis name="SlicerAxis">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Measures]">
                                                <UName>[Measures].[Count]</UName>
                                                <Caption>Count</Caption>
                                                <LName>[Measures].[MeasuresLevel]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <Value xsi:type="xsd:double">7392</Value>
                                    <FmtValue>7,392</FmtValue>
                                </Cell>
                                <Cell CellOrdinal="1">
                                    <Value xsi:type="xsd:double">13860</Value>
                                    <FmtValue>13,860</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String EXECUTE_BUG_MONDRIAN_1316_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Measures]">
                                            <UName name="[Measures].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Measures].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Measures].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Measures].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Measures].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis"/>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Measures]">
                                                <UName>[Measures].[Unit Sales]</UName>
                                                <Caption>Unit Sales</Caption>
                                                <LName>[Measures].[MeasuresLevel]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <Value xsi:type="xsd:double">266773</Value>
                                    <FmtValue>266,773</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String EXECUTE_WITH_LOCALE_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0">
                                        <HierarchyInfo name="[Measures]">
                                            <UName name="[Measures].[MEMBER_UNIQUE_NAME]" type="xsd:string"/>
                                            <Caption name="[Measures].[MEMBER_CAPTION]" type="xsd:string"/>
                                            <LName name="[Measures].[LEVEL_UNIQUE_NAME]" type="xsd:string"/>
                                            <LNum name="[Measures].[LEVEL_NUMBER]" type="xsd:unsignedInt"/>
                                            <DisplayInfo name="[Measures].[DISPLAY_INFO]" type="xsd:unsignedInt"/>
                                        </HierarchyInfo>
                                    </AxisInfo>
                                    <AxisInfo name="SlicerAxis"/>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples>
                                        <Tuple>
                                            <Member Hierarchy="[Measures]">
                                                <UName>[Measures].[Sales Formatted]</UName>
                                                <Caption>Sales Formatted</Caption>
                                                <LName>[Measures].[MeasuresLevel]</LName>
                                                <LNum>0</LNum>
                                                <DisplayInfo>0</DisplayInfo>
                                            </Member>
                                        </Tuple>
                                    </Tuples>
                                </Axis>
                            </Axes>
                            <CellData>
                                <Cell CellOrdinal="0">
                                    <Value xsi:type="xsd:double">266773</Value>
                                    <FmtValue>266.773,00 €</FmtValue>
                                </Cell>
                            </CellData>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String EMPTY_SET_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ExecuteResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns="urn:schemas-microsoft-com:xml-analysis:mddataset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:complexType name="MemberType">
                                    <xsd:sequence>
                                        <xsd:element name="UName" type="xsd:string"/>
                                        <xsd:element name="Caption" type="xsd:string"/>
                                        <xsd:element name="LName" type="xsd:string"/>
                                        <xsd:element name="LNum" type="xsd:unsignedInt"/>
                                        <xsd:element name="DisplayInfo" type="xsd:unsignedInt"/>
                                        <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                            <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                        </xsd:sequence>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="PropType">
                                    <xsd:attribute name="name" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TupleType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="MembersType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Member" type="MemberType"/>
                                    </xsd:sequence>
                                    <xsd:attribute name="Hierarchy" type="xsd:string"/>
                                </xsd:complexType>
                                <xsd:complexType name="TuplesType">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Tuple" type="TupleType"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CrossProductType">
                                    <xsd:sequence>
                                        <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                            <xsd:element name="Members" type="MembersType"/>
                                            <xsd:element name="Tuples" type="TuplesType"/>
                                        </xsd:choice>
                                    </xsd:sequence>
                                    <xsd:attribute name="Size" type="xsd:unsignedInt"/>
                                </xsd:complexType>
                                <xsd:complexType name="OlapInfo">
                                    <xsd:sequence>
                                        <xsd:element name="CubeInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="Cube">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element name="CubeName" type="xsd:string"/>
                                                            </xsd:sequence>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="AxesInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element maxOccurs="unbounded" name="AxisInfo">
                                                        <xsd:complexType>
                                                            <xsd:sequence>
                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="HierarchyInfo">
                                                                    <xsd:complexType>
                                                                        <xsd:sequence>
                                                                            <xsd:sequence maxOccurs="unbounded">
                                                                                <xsd:element name="UName" type="PropType"/>
                                                                                <xsd:element name="Caption" type="PropType"/>
                                                                                <xsd:element name="LName" type="PropType"/>
                                                                                <xsd:element name="LNum" type="PropType"/>
                                                                                <xsd:element maxOccurs="unbounded" minOccurs="0" name="DisplayInfo" type="PropType"/>
                                                                            </xsd:sequence>
                                                                            <xsd:sequence>
                                                                                <xsd:any maxOccurs="unbounded" minOccurs="0" processContents="lax"/>
                                                                            </xsd:sequence>
                                                                        </xsd:sequence>
                                                                        <xsd:attribute name="name" type="xsd:string" use="required"/>
                                                                    </xsd:complexType>
                                                                </xsd:element>
                                                            </xsd:sequence>
                                                            <xsd:attribute name="name" type="xsd:string"/>
                                                        </xsd:complexType>
                                                    </xsd:element>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="CellInfo">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:choice>
                                                            <xsd:element name="Value" type="PropType"/>
                                                            <xsd:element name="FmtValue" type="PropType"/>
                                                            <xsd:element name="BackColor" type="PropType"/>
                                                            <xsd:element name="ForeColor" type="PropType"/>
                                                            <xsd:element name="FontName" type="PropType"/>
                                                            <xsd:element name="FontSize" type="PropType"/>
                                                            <xsd:element name="FontFlags" type="PropType"/>
                                                            <xsd:element name="FormatString" type="PropType"/>
                                                            <xsd:element name="NonEmptyBehavior" type="PropType"/>
                                                            <xsd:element name="SolveOrder" type="PropType"/>
                                                            <xsd:element name="Updateable" type="PropType"/>
                                                            <xsd:element name="Visible" type="PropType"/>
                                                            <xsd:element name="Expression" type="PropType"/>
                                                        </xsd:choice>
                                                    </xsd:sequence>
                                                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                                                        <xsd:any maxOccurs="unbounded" processContents="lax"/>
                                                    </xsd:sequence>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="Axes">
                                    <xsd:sequence maxOccurs="unbounded">
                                        <xsd:element name="Axis">
                                            <xsd:complexType>
                                                <xsd:choice maxOccurs="unbounded" minOccurs="0">
                                                    <xsd:element name="CrossProduct" type="CrossProductType"/>
                                                    <xsd:element name="Tuples" type="TuplesType"/>
                                                    <xsd:element name="Members" type="MembersType"/>
                                                </xsd:choice>
                                                <xsd:attribute name="name" type="xsd:string"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:complexType name="CellData">
                                    <xsd:sequence>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Cell">
                                            <xsd:complexType>
                                                <xsd:sequence maxOccurs="unbounded">
                                                    <xsd:choice>
                                                        <xsd:element name="Value"/>
                                                        <xsd:element name="FmtValue" type="xsd:string"/>
                                                        <xsd:element name="BackColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="ForeColor" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FontName" type="xsd:string"/>
                                                        <xsd:element name="FontSize" type="xsd:unsignedShort"/>
                                                        <xsd:element name="FontFlags" type="xsd:unsignedInt"/>
                                                        <xsd:element name="FormatString" type="xsd:string"/>
                                                        <xsd:element name="NonEmptyBehavior" type="xsd:unsignedShort"/>
                                                        <xsd:element name="SolveOrder" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Updateable" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Visible" type="xsd:unsignedInt"/>
                                                        <xsd:element name="Expression" type="xsd:string"/>
                                                    </xsd:choice>
                                                </xsd:sequence>
                                                <xsd:attribute name="CellOrdinal" type="xsd:unsignedInt" use="required"/>
                                            </xsd:complexType>
                                        </xsd:element>
                                    </xsd:sequence>
                                </xsd:complexType>
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence maxOccurs="unbounded">
                                            <xsd:element name="OlapInfo" type="OlapInfo"/>
                                            <xsd:element name="Axes" type="Axes"/>
                                            <xsd:element name="CellData" type="CellData"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:schema>
                            <OlapInfo>
                                <CubeInfo>
                                    <Cube>
                                        <CubeName>Sales</CubeName>
                                        <LastDataUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastDataUpdate>
                                        <LastSchemaUpdate xmlns="http://schemas.microsoft.com/analysisservices/2003/engine">xxxx-xx-xxTxx:xx:xx</LastSchemaUpdate>
                                    </Cube>
                                </CubeInfo>
                                <AxesInfo>
                                    <AxisInfo name="Axis0"/>
                                    <AxisInfo name="SlicerAxis"/>
                                </AxesInfo>
                                <CellInfo>
                                    <Value name="VALUE"/>
                                    <FmtValue name="FORMATTED_VALUE" type="xsd:string"/>
                                </CellInfo>
                            </OlapInfo>
                            <Axes>
                                <Axis name="Axis0">
                                    <Tuples/>
                                </Axis>
                            </Axes>
                            <CellData/>
                        </root>
                    </return>
                </ExecuteResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

}
