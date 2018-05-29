package com.sanshang.li.mybaseframwork;

import java.util.Arrays;

/**
 * Created by li on 2018/5/24.
 * WeChat 18571658038
 * author LiWei
 */

public class PassDeviceInfo {

    private String type;
    private double[] coordinates;
    private double[] RMS;
    private String Quality;

    public PassDeviceInfo() {
    }

    public PassDeviceInfo(String type, double[] coordinates, double[] RMS, String quality) {
        this.type = type;
        this.coordinates = coordinates;
        this.RMS = RMS;
        Quality = quality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public double[] getRMS() {
        return RMS;
    }

    public void setRMS(double[] RMS) {
        this.RMS = RMS;
    }

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String quality) {
        Quality = quality;
    }

    @Override
    public String toString() {
        return "PassDeviceInfo{" +
                "type='" + type + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                ", RMS=" + Arrays.toString(RMS) +
                ", Quality='" + Quality + '\'' +
                '}';
    }
}
