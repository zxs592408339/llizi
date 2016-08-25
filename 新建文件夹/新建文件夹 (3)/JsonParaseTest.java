package com.ittx.android1601;

import android.content.res.AssetManager;
import android.test.AndroidTestCase;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.model.Around;
import com.ittx.android1601.model.Info;
import com.ittx.android1601.model.Merchant;
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
 * 新闻接口数据
 * http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
 *
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

    public void testAroundGson() throws IOException {
        String jsonStr = readStringFromAsset("around.txt");
        Gson gson = new Gson();
        Around aroun = gson.fromJson(jsonStr, Around.class);
        Info info = aroun.getInfo();
        List<Merchant> merchants =info.getMerchantKey();

        Logs.e("name      coupon         loaction       distance");
        for(Merchant merchant : merchants){
            Logs.v(merchant.getName()+" "+merchant.getCoupon()+"  "+merchant.getLocation()+ "  "+merchant.getDistance());
        }

    }

    public void testFastJsonAround() throws IOException {
        Logs.e("testFastJsonAround >>>>>>");
        String jsonStr = readStringFromAsset("around.txt");
        Around around = JSON.parseObject(jsonStr,Around.class);
        Info info = around.getInfo();
        List<Merchant> merchants =info.getMerchantKey();

        Logs.e("name      coupon         loaction       distance");
        for(Merchant merchant : merchants){
            Logs.e(merchant.getName()+" "+merchant.getCoupon()+"  "+merchant.getLocation()+ "  "+merchant.getDistance());
        }
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

    /**
     *  * {
     "students": [
         {
             "name": "张三",
             "id": 100,
             "age": 23,
             "sex": true
             },
         {
             "name": "李四",
             "id": 110,
             "sex": false
         }
         ]
     }
     */
    public void testParseStudent() throws IOException, JSONException {
        String studentJson = readStringFromAsset("students");
        JSONObject jsonObject = new JSONObject(studentJson);

        List<StudentBean> studentLists = new ArrayList<>();
        Logs.e("姓名  年龄  邮箱");
        JSONArray jsonArray = jsonObject.getJSONArray("student");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject itemObject = jsonArray.getJSONObject(i);
            String name = itemObject.getString("name");
            int age = -1;
            if(itemObject.has("age")) {
                age = itemObject.getInt("age");
            }
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
        Logs.e(fileName+ " :" + jsonStr);
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

    /**
     * :[{"name":"张三","id":100,"age":23,"sex":true},{"name":"李四","id":110,"age":13,"sex":false}]
     *
     * {
        "students": [
             {
                 "name": "张三",
                 "id": 100,
                 "age": 23,
                 "sex": true
             },
             {
                 "name": "李四",
                 "id": 110,
                 "sex": false
             }
        ]
     }
     *
     */
    public void testToJsonObject(){
        List<Student> lists = new ArrayList<>();
        Student student = new Student();
        student.setId(100);
        student.setName("张三");
        student.setSex(true);
        student.setAge(23);
        lists.add(student);

        student = new Student();
        student.setId(110);
        student.setName("李四");
        student.setSex(false);
        student.setAge(13);
        lists.add(student);

        StudentLists studentLists = new StudentLists();
        studentLists.setStudents((ArrayList<Student>) lists);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(studentLists);
        Logs.e("jsonStr :"+jsonStr);  // {students:[{"id":100,"name":"张三","sex":true,"age":23}]}
    }

}