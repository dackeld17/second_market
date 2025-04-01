package com.dcu.demo.weather;

public class GeoLocation {
    public String city;
    public String regionName;
    public String country;
    public double lat;
    public double lon;

    // getter/setter 생략해도 됨 (RestTemplate는 public 필드도 매핑함)
}
