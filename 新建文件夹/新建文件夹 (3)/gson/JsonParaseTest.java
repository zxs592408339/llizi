package com.ittx.android1601;

import android.content.res.AssetManager;
import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.model.Student;
import com.ittx.android1601.model.StudentBean;
import com.ittx.android1601.model.StudentLists;
import com.ittx.android1601.model.Weather;
import com.ittx.android1601.model.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 单元测试三个问题
 * 1.测试类的位置 (androidTest)
 * 2.测试类继承 AndroidTestCase
 * 3.测试方法 访问修饰符用 public 方法名以 test开头, 如:  public void testReadFile(){}
 */
public class JsonParaseTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testGsonStudent() {
        String jsonStr = "\n" +
                "{\"students\":[{\"name\":\"小明\",\"age\":18,\"id\":112,\"sex\":\"男\"},{\"name\":\"小王\",\"age\":17,\"id\":102,\"sex\":\"女\"}]}";
        Gson gson = new Gson();
        StudentLists studentLists = gson.fromJson(jsonStr, StudentLists.class);

        ArrayList<Student> lists = studentLists.getStudents();
        Logs.e("id 姓名  年龄  性别");
        for (Student student : lists) {
            Logs.e(student.getId() + "  " + student.getName() + "  " + student.getAge() + "  " + student.isSex());
        }

    }


    public void testParaseJsonStr() throws JSONException {
        String jsonStr = "{\"userName\":\"张三\"}";
        JSONObject jsonObject = new JSONObject(jsonStr); //jsonStr字符串转换成jsonobject对象
        String userName = jsonObject.getString("userName");
        Logs.e("userName :" + userName);
    }

    public void testParaseJsonStrTwo() throws JSONException {
        String jsonStr = "{ \"name\": \"Romney\",\"age\": 56}";
        JSONObject jsonObject = new JSONObject(jsonStr);
        int age = jsonObject.getInt("age");
        String name = jsonObject.getString("name");
        Logs.e("name :" + name + " , age :" + age);
    }

    public void testParseThree() throws JSONException {
        String jsonStr = "{ \"city\":{\"name\": \"北京\"},\"weatherinfo\":{\"weather\": \"sunny\"}}";
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject jsonCityObject = jsonObject.getJSONObject("city");
        String name = jsonCityObject.getString("name");
        Logs.e("name :" + name);
    }

    public void testParaseWehter() throws IOException, JSONException {
        String jsonStr = readStringFromAsset("wehter.txt");

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject jsonWeatherinfoObject = jsonObject.getJSONObject("weatherinfo");
        String city = jsonWeatherinfoObject.getString("city");
        String time = jsonWeatherinfoObject.getString("time");
        Logs.e("city :" + city + " , time :" + time);
    }

    public void testWeatherByGson() throws IOException {
        String jsonStr = readStringFromAsset("wehter.txt");
        Gson gson = new Gson();
        WeatherInfo weatherInfo = gson.fromJson(jsonStr, WeatherInfo.class);
        Weather weather = weatherInfo.getWeatherinfo();
        Logs.e("city :" + weather.getCity() + " , time :" + weather.getTime());

    }

    public void testParseStudent() throws IOException, JSONException {
        String studentJson = readStringFromAsset("students");
        JSONObject jsonObject = new JSONObject(studentJson);

        List<StudentBean> studentLists = new ArrayList<>();
        Logs.e("姓名  年龄  邮箱");
        JSONArray jsonArray = jsonObject.getJSONArray("student");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject itemObject = jsonArray.getJSONObject(i);
            String name = itemObject.getString("name");
            int age = itemObject.getInt("age");
            String email = itemObject.getString("email");

            StudentBean studentBean = new StudentBean();
            studentBean.setName(name);
            studentBean.setAge(age);
            studentBean.setEmail(email);

            studentLists.add(studentBean);

            Logs.e(studentBean.getName() + "  " + studentBean.getAge() + "  " + studentBean.getEmail());

        }

    }

    public String readStringFromAsset(String fileName) throws IOException {
        AssetManager assetManager = getContext().getAssets();
        InputStream is = assetManager.open(fileName);
        String jsonStr = readIOFile(is);
        Logs.e("readStringFromAsset :" + jsonStr);
        return jsonStr;
    }

    /**
     * 输入流转换成字符串 方法二 适应于读小文件
     *
     * @param is
     * @return
     * @throws IOException
     */
    public String readIOFile(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        isr.close();
        return sb.toString();

    }
}