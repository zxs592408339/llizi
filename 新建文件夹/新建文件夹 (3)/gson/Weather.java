package com.ittx.android1601.model;

/**
 * {
 "city": "北京",
 "cityid": "101010100",
 "temp": "21",
 "WD": "东南风",
 "WS": "2  级",
 "SD": "78%",
 "WSE": "2",
 "time": "21:10",
 "isRadar": "1",
 "Radar": "JC_RADAR_AZ9010_JB"
 }

 * Created by Administrator on 2016/7/26.
 */
public class Weather {
    private String city;
    private String cityid;
    private String temp;
    private String time;
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
