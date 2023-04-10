/*
* Copyright (c) 2023 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageLoad", propOrder = {

})
public class ImageLoad {

  @XmlElement(name = "ImagePath", namespace = "http://schemas.microsoft.com/analysisservices/2010/engine/200/200")
  protected String imagePath;
  @XmlElement(name = "ImageUrl", namespace = "http://schemas.microsoft.com/analysisservices/2010/engine/200/200")
  protected String imageUrl;
  @XmlElement(name = "ImageUniqueID", namespace = "http://schemas.microsoft.com/analysisservices/2010/engine/200/200")
  protected String imageUniqueID;
  @XmlElement(name = "ImageVersion", namespace = "http://schemas.microsoft.com/analysisservices/2010/engine/200/200")
  protected String imageVersion;
  @XmlElement(name = "ReadWriteMode", namespace = "http://schemas.microsoft.com/analysisservices/2008/engine/100", required = true)
  protected String readWriteMode;
  @XmlElement(name = "DbStorageLocation", namespace = "http://schemas.microsoft.com/analysisservices/2008/engine/100/100")
  protected String dbStorageLocation;
  @XmlElement(name = "DatabaseName", required = true)
  protected String databaseName;
  @XmlElement(name = "DatabaseID", required = true)
  protected String databaseID;
  @XmlElement(name = "Data")
  protected ImageLoad.Data data;

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String value) {
    this.imagePath = value;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String value) {
    this.imageUrl = value;
  }

  public String getImageUniqueID() {
    return imageUniqueID;
  }

  public void setImageUniqueID(String value) {
    this.imageUniqueID = value;
  }

  public String getImageVersion() {
    return imageVersion;
  }

  public void setImageVersion(String value) {
    this.imageVersion = value;
  }

  public String getReadWriteMode() {
    return readWriteMode;
  }

  public void setReadWriteMode(String value) {
    this.readWriteMode = value;
  }

  public String getDbStorageLocation() {
    return dbStorageLocation;
  }

  public void setDbStorageLocation(String value) {
    this.dbStorageLocation = value;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String value) {
    this.databaseName = value;
  }

  public String getDatabaseID() {
    return databaseID;
  }

  public void setDatabaseID(String value) {
    this.databaseID = value;
  }

  public ImageLoad.Data getData() {
    return data;
  }

  public void setData(ImageLoad.Data value) {
    this.data = value;
  }

  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = { "dataBlock" })
  public static class Data {

    @XmlElement(name = "DataBlock")
    protected List<String> dataBlock;

    public List<String> getDataBlock() {
      return this.dataBlock;
    }

      public void setDataBlock(List<String> dataBlock) {
          this.dataBlock = dataBlock;
      }
  }

}
