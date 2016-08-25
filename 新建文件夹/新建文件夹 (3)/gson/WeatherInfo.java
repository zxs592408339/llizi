package com.ittx.android1601.model;

/**
 * {
 "weatherinfo": {
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
 }
 */
public class WeatherInfo {
    public Weather getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(Weather weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    private Weather weatherinfo;
}
