package com.insidecoding.opium.entity;

/**
 * TODO: Make this a shared library
 * 
 * @author ludovicianul
 *
 */
public class Device {

  private String ip;
  private String uid;
  private String name;
  private String brand;
  private String model;
  private String sdkVersion;
  private String androidVersion;
  private String manufacturer;
  private String type;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getSdkVersion() {
    return sdkVersion;
  }

  public void setSdkVersion(String sdkVersion) {
    this.sdkVersion = sdkVersion;
  }

  public String getAndroidVersion() {
    return androidVersion;
  }

  public void setAndroidVersion(String androidVersion) {
    this.androidVersion = androidVersion;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Device [ip=" + ip + ", uid=" + uid + ", name=" + name + ", brand=" + brand + ", model="
        + model + ", sdkVersion=" + sdkVersion + ", androidVersion=" + androidVersion
        + ", manufacturer=" + manufacturer + ", type=" + type + "]";
  }

}
