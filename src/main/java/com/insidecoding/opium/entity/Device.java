package com.insidecoding.opium.entity;

/**
 * TODO: Make this a shared library
 *
 * @author ludovicianul
 */
public class Device {

    private String ip;
    private String uid;
    private String name;
    private String brand;
    private String model;
    private String sdkVersion;
    private String osVersion;
    private String manufacturer;
    private String type;
    private DeviceStatus status;

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

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
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

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Device [ip=" + ip + ", uid=" + uid + ", name=" + name + ", brand=" + brand + ", model="
                + model + ", sdkVersion=" + sdkVersion + ", osVersion=" + osVersion
                + ", manufacturer=" + manufacturer + ", type=" + type + "]";
    }

    public static enum DeviceStatus {
        AVAILABLE, LOCKED, BUSY
    }

}
