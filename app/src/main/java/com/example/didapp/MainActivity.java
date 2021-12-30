package com.example.didapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends AppCompatActivity {

    //----------navigation var---------------------------------------
    Button btn1, btn2, btn3, btn4;
    //-----------------------Documents to execute--------------------
    Document dustdoc = null; Document vaccinedoc = null; Document coviddoc = null;
    //----------------------for dust---------------------------------
    String dustpm10 = ""; String dustpm25 = ""; String value = ""; String khai = "";
    String dustkey = "mHJloRzsgZPtkAlarSul%2BF2pmkou3rHz59owpoE3JP6VoS0%2FRfWpjrTdYF%2BvsegpKBsOP4f989pTj%2BXdWrRVuQ%3D%3D";
    //----------------------for weather-------------------------------
    String temp_v; String feeltemp_v; int hum_v; String weather_v; String cloud_v;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "a161b4b2515e6e68c53a2742576b8167";
    DecimalFormat df = new DecimalFormat("#.##");
    //----------------------for vaccine-------------------------------
    String firstCnt = ""; String secondCnt = ""; String firstCnt1 = ""; String secondCnt1 = "";
    //----------------------for covid----------------------------------
    String update = ""; String clear = ""; String death = "";
    String defCnt = ""; String incDec = ""; String location = "";
    String seoul1 = ""; String seoul2 = ""; String seoul3 = "";
    String incheon1 = ""; String incheon2 = ""; String incheon3 = "";
    String busan1 = ""; String busan2 = ""; String busan3 = "";
    String daegu1 = ""; String daegu2 = ""; String daegu3 = "";
    String gwangju1 = ""; String gwangju2 = ""; String gwangju3 = "";
    String daejeon1 = ""; String daejeon2 = ""; String daejeon3 = "";
    String ulsan1 = ""; String ulsan2 = ""; String ulsan3 = "";
    String gyeonggi1 = ""; String gyeonggi2 = ""; String gyeonggi3 = "";
    String gangwon1 = ""; String gangwon2 = ""; String gangwon3 = "";
    String chungbuk1 = ""; String chungbuk2 = ""; String chungbuk3 = "";
    String chungnam1 = ""; String chungnam2 = ""; String chungnam3 = "";
    String jeonbuk1 = ""; String jeonbuk2 = ""; String jeonbuk3 = "";
    String jeonnam1 = ""; String jeonnam2 = ""; String jeonnam3 = "";
    String gyeongbuk1 = ""; String gyeongbuk2 = ""; String gyeongbuk3 = "";
    String gyeongnam1 = ""; String gyeongnam2 = ""; String gyeongnam3 = "";
    String jeju1 = ""; String jeju2 = ""; String jeju3 = "";
    String geom1 = ""; String geom2 = ""; String geom3 = "";
    String sejong1 = ""; String sejong2 = ""; String sejong3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------------Location Selecting to parce weathermap api-------------------------------
        HashMap<String, String> cities = new HashMap<String, String>();
        cities.put("서울", "seoul"); cities.put("인천", "incheon"); cities.put("부산", "busan");
        cities.put("울산", "ulsan"); cities.put("대구", "daegu"); cities.put("대전", "daejeon");
        cities.put("광주", "gwangju"); cities.put("제주", "jeju city"); cities.put("세종", "gongju");
        cities.put("경기", "suwon-si"); cities.put("강원", "gangneung"); cities.put("전남", "mokpo");
        cities.put("전북", "jeonju"); cities.put("경남", "changwon"); cities.put("경북", "andong");
        cities.put("충남", "yesan"); cities.put("충북", "cheongju-si");
        //-----------------Location Selecting to parce dust api-------------------------------------
        HashMap<String, String> stations = new HashMap<String, String>();
        stations.put("서울", "송파구"); stations.put("인천", "길상"); stations.put("부산", "개금동");
        stations.put("울산", "대송동"); stations.put("대구", "수창동"); stations.put("대전", "구성동");
        stations.put("광주", "오선동"); stations.put("제주", "대정읍"); stations.put("세종", "신흥동");
        stations.put("경기", "신풍동"); stations.put("강원", "옥천동"); stations.put("전남", "부흥동");
        stations.put("전북", "노송동"); stations.put("경남", "내서읍"); stations.put("경북", "명륜동");
        stations.put("충남", "예산군"); stations.put("충북", "산남동");

        GetVaccineTask vaccineTask = new GetVaccineTask(this);
        vaccineTask.execute("https://nip.kdca.go.kr/irgd/cov19stats.do?list=all");
        GetCovidTask covidTask = new GetCovidTask(this);
        covidTask.execute("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?" +
                "serviceKey=pVeUbALT4rmjedteccBxRrwvEnb1IGjL5DtH1imu2E7ciZ1JtQcMX1a13JYaI5CRhu9BH7M1%2BlNcJGe01ahV%2FA%3D%3D");

        btn1 = (Button)findViewById(R.id.btn_home);
        btn2 = (Button)findViewById(R.id.btn_weatherdust);
        btn3 = (Button)findViewById(R.id.btn_covid);
        btn4 = (Button)findViewById(R.id.btn_a);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainFragment mainFragment = new MainFragment();
        transaction.replace(R.id.frame_container, mainFragment);
        transaction.commit();

        //---------------------------------------------------------------------------


        Spinner spinner = (Spinner) findViewById(R.id.spinner_location);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.location_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWeatherDetails(cities.get(parent.getItemAtPosition(position).toString()));
                getDustDetails(stations.get(parent.getItemAtPosition(position).toString()));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                location = parent.getItemAtPosition(position).toString();

                builder.setTitle("검색중").setMessage("");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                new Handler().postDelayed(new Runnable() {  // 1.8초뒤에 AlertDialog 실행
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        MainFragment mainFragment1 = new MainFragment();
                        Bundle frag1bundle = new Bundle();
                        frag1bundle.putString("FRAG1_PM10", dustpm10);
                        frag1bundle.putString("FRAG1_PM25", dustpm25);
                        frag1bundle.putString("FRAG1_KHAI", khai);
                        frag1bundle.putString("FRAG1_feelTemp", feeltemp_v);
                        frag1bundle.putString("FRAG1_loc", location);
                        frag1bundle.putString("seoul", seoul2);
                        frag1bundle.putString("incheon", incheon2);
                        frag1bundle.putString("busan", busan2);
                        frag1bundle.putString("daejeon", daejeon2);
                        frag1bundle.putString("daegu", daegu2);
                        frag1bundle.putString("gwangju", gwangju2);
                        frag1bundle.putString("ulsan", ulsan2);
                        frag1bundle.putString("sejong", sejong2);
                        frag1bundle.putString("jeju", jeju2);
                        frag1bundle.putString("gangwon", gangwon2);
                        frag1bundle.putString("gyeongbuk", gyeongbuk2);
                        frag1bundle.putString("gyeongnam", gyeongnam2);
                        frag1bundle.putString("gyeonggi", gyeonggi2);
                        frag1bundle.putString("jeonbuk", jeonbuk2);
                        frag1bundle.putString("jeonnam", jeonnam2);
                        frag1bundle.putString("chungbuk", chungbuk2);
                        frag1bundle.putString("chungnam", chungnam2);
                        mainFragment1.setArguments(frag1bundle);
                        transaction.replace(R.id.frame_container, mainFragment1);
                        transaction.commit();
                        alertDialog.cancel();
                    }
                },1800);// 1.8초 설정
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //------------------------------------------------------------------------


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                MainFragment mainFragment1 = new MainFragment();
                Bundle frag1bundle = new Bundle();
                frag1bundle.putString("FRAG1_PM10", dustpm10);
                frag1bundle.putString("FRAG1_PM25", dustpm25);
                frag1bundle.putString("FRAG1_KHAI", khai);
                frag1bundle.putString("FRAG1_feelTemp", feeltemp_v);
                frag1bundle.putString("FRAG1_loc", location);
                frag1bundle.putString("seoul", seoul2);
                frag1bundle.putString("incheon", incheon2);
                frag1bundle.putString("busan", busan2);
                frag1bundle.putString("daejeon", daejeon2);
                frag1bundle.putString("daegu", daegu2);
                frag1bundle.putString("gwangju", gwangju2);
                frag1bundle.putString("ulsan", ulsan2);
                frag1bundle.putString("sejong", sejong2);
                frag1bundle.putString("jeju", jeju2);
                frag1bundle.putString("gangwon", gangwon2);
                frag1bundle.putString("gyeongbuk", gyeongbuk2);
                frag1bundle.putString("gyeongnam", gyeongnam2);
                frag1bundle.putString("gyeonggi", gyeonggi2);
                frag1bundle.putString("jeonbuk", jeonbuk2);
                frag1bundle.putString("jeonnam", jeonnam2);
                frag1bundle.putString("chungbuk", chungbuk2);
                frag1bundle.putString("chungnam", chungnam2);
                mainFragment1.setArguments(frag1bundle);
                transaction.replace(R.id.frame_container, mainFragment1);
                transaction.commit();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                WeatherDust weatherDust = new WeatherDust();
                Bundle frag2bundle = new Bundle();
                frag2bundle.putString("FRAG2_PM10", dustpm10);
                frag2bundle.putString("FRAG2_PM25", dustpm25);
                frag2bundle.putString("FRAG2_VAL", value);
                frag2bundle.putString("FRAG2_KHAI", khai);
                frag2bundle.putString("FRAG2_temp", temp_v);
                frag2bundle.putString("FRAG2_feelTemp", feeltemp_v);
                frag2bundle.putInt("FRAG2_hum", hum_v);
                frag2bundle.putString("FRAG2_des", weather_v);
                frag2bundle.putString("FRAG2_cloud", cloud_v);
                weatherDust.setArguments(frag2bundle);
                transaction.replace(R.id.frame_container, weatherDust);
                transaction.commit();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Covid covid = new Covid();
                Bundle frag3bundle = new Bundle();
                frag3bundle.putString("FRAG3_FCNT", firstCnt);
                frag3bundle.putString("FRAG3_SCNT", secondCnt);
                frag3bundle.putString("FRAG3_FCNT1", firstCnt1);
                frag3bundle.putString("FRAG3_SCNT1", secondCnt1);
                frag3bundle.putString("FRAG3_UPDATE", update);
                frag3bundle.putString("FRAG3_CLEAR", clear);
                frag3bundle.putString("FRAG3_DEATH", death);
                frag3bundle.putString("FRAG3_DEFCNT", defCnt);
                frag3bundle.putString("FRAG3_INCDEC", incDec);
                frag3bundle.putString("FRAG3_SEOUL1", seoul1);
                frag3bundle.putString("FRAG3_SEOUL2", seoul2);
                frag3bundle.putString("FRAG3_SEOUL3", seoul3);
                frag3bundle.putString("FRAG3_busan1", busan1);
                frag3bundle.putString("FRAG3_busan2", busan2);
                frag3bundle.putString("FRAG3_busan3", busan3);
                frag3bundle.putString("FRAG3_daegu1", daegu1);
                frag3bundle.putString("FRAG3_daegu2", daegu2);
                frag3bundle.putString("FRAG3_daegu3", daegu3);
                frag3bundle.putString("FRAG3_daejeon1", daejeon1);
                frag3bundle.putString("FRAG3_daejeon2", daejeon2);
                frag3bundle.putString("FRAG3_daejeon3", daejeon3);
                frag3bundle.putString("FRAG3_gwangju1", gwangju1);
                frag3bundle.putString("FRAG3_gwangju2", gwangju2);
                frag3bundle.putString("FRAG3_gwangju3", gwangju3);
                frag3bundle.putString("FRAG3_ulsan1", ulsan1);
                frag3bundle.putString("FRAG3_ulsan2", ulsan2);
                frag3bundle.putString("FRAG3_ulsan3", ulsan3);
                frag3bundle.putString("FRAG3_incheon1", incheon1);
                frag3bundle.putString("FRAG3_incheon2", incheon2);
                frag3bundle.putString("FRAG3_incheon3", incheon3);
                frag3bundle.putString("FRAG3_sejong1", sejong1);
                frag3bundle.putString("FRAG3_sejong2", sejong2);
                frag3bundle.putString("FRAG3_sejong3", sejong3);
                frag3bundle.putString("FRAG3_gyeonggi1", gyeonggi1);
                frag3bundle.putString("FRAG3_gyeonggi2", gyeonggi2);
                frag3bundle.putString("FRAG3_gyeonggi3", gyeonggi3);
                frag3bundle.putString("FRAG3_gangwon1", gangwon1);
                frag3bundle.putString("FRAG3_gangwon2", gangwon2);
                frag3bundle.putString("FRAG3_gangwon3", gangwon3);
                frag3bundle.putString("FRAG3_chungbuk1", chungbuk1);
                frag3bundle.putString("FRAG3_chungbuk2", chungbuk2);
                frag3bundle.putString("FRAG3_chungbuk3", chungbuk3);
                frag3bundle.putString("FRAG3_chungnam1", chungnam1);
                frag3bundle.putString("FRAG3_chungnam2", chungnam2);
                frag3bundle.putString("FRAG3_chungnam3", chungnam3);
                frag3bundle.putString("FRAG3_jeonbuk1", jeonbuk1);
                frag3bundle.putString("FRAG3_jeonbuk2", jeonbuk2);
                frag3bundle.putString("FRAG3_jeonbuk3", jeonbuk3);
                frag3bundle.putString("FRAG3_jeonnam1", jeonnam1);
                frag3bundle.putString("FRAG3_jeonnam2", jeonnam2);
                frag3bundle.putString("FRAG3_jeonnam3", jeonnam3);
                frag3bundle.putString("FRAG3_gyeongbuk1", gyeongbuk1);
                frag3bundle.putString("FRAG3_gyeongbuk2", gyeongbuk2);
                frag3bundle.putString("FRAG3_gyeongbuk3", gyeongbuk3);
                frag3bundle.putString("FRAG3_gyeongnam1", gyeongnam1);
                frag3bundle.putString("FRAG3_gyeongnam2", gyeongnam2);
                frag3bundle.putString("FRAG3_gyeongnam3", gyeongnam3);
                frag3bundle.putString("FRAG3_jeju1", jeju1);
                frag3bundle.putString("FRAG3_jeju2", jeju2);
                frag3bundle.putString("FRAG3_jeju3", jeju3);
                frag3bundle.putString("FRAG3_geom1", geom1);
                frag3bundle.putString("FRAG3_geom2", geom2);
                frag3bundle.putString("FRAG3_geom3", geom3);
                covid.setArguments(frag3bundle);
                transaction.replace(R.id.frame_container, covid);
                transaction.commit();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BoardActivity.class));
            }
        });
    }

    private class GetDustTask extends AsyncTask<String, Void, Document> {
        private Activity dustcontext;

        public GetDustTask(Activity dustcontext) { this.dustcontext = dustcontext; }
        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                dustdoc = db.parse(new InputSource(url.openStream()));
                dustdoc.getDocumentElement().normalize();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_LONG).show();
            }
            return dustdoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            NodeList nodeList = dustdoc.getElementsByTagName("items");
            Node node = nodeList.item(0);
            Element fstElmt = (Element)node;

            NodeList nodeList1 = fstElmt.getElementsByTagName("item");
            Node node1 = nodeList1.item(0);
            Element fstElmt1 = (Element)node1;

            NodeList valueList = fstElmt1.getElementsByTagName("khaiGrade");
            Element valueElement = (Element)valueList.item(0);
            valueList = valueElement.getChildNodes();
            value = ((Node) valueList.item(0)).getNodeValue();

            NodeList pm10List = fstElmt1.getElementsByTagName("pm10Value");
            Element pm10Element = (Element)pm10List.item(0);
            pm10List = pm10Element.getChildNodes();
            dustpm10 = ((Node) pm10List.item(0)).getNodeValue();

            NodeList pm25List = fstElmt1.getElementsByTagName("pm25Value");
            Element pm25Element = (Element)pm25List.item(0);
            pm25List = pm25Element.getChildNodes();
            dustpm25 = ((Node) pm25List.item(0)).getNodeValue();

            NodeList khaiList = fstElmt1.getElementsByTagName("khaiValue");
            Element khaiElement = (Element)khaiList.item(0);
            khaiList = khaiElement.getChildNodes();
            khai = ((Node) khaiList.item(0)).getNodeValue();
        }
    }

    private class GetVaccineTask extends AsyncTask<String, Void, Document> {
        private Activity vaccinecontext;

        public GetVaccineTask(Activity vaccinecontext) { this.vaccinecontext = vaccinecontext; }

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try{
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                vaccinedoc = db.parse(new InputSource(url.openStream()));
                vaccinedoc.getDocumentElement().normalize();
            }  catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return vaccinedoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            NodeList nodeList = vaccinedoc.getElementsByTagName("item");

            Node node = nodeList.item(2);
            Element fstElmnt = (Element)node;

            NodeList firstCntList = fstElmnt.getElementsByTagName("firstCnt");
            Element firstCntElement = (Element) firstCntList.item(0);
            firstCntList = firstCntElement.getChildNodes();
            firstCnt += ((Node) firstCntList.item(0)).getNodeValue();

            NodeList secondCntList = fstElmnt.getElementsByTagName("secondCnt");
            Element secondCntElement = (Element) secondCntList.item(0);
            secondCntList = secondCntElement.getChildNodes();
            secondCnt += ((Node) secondCntList.item(0)).getNodeValue();

            Node node1 = nodeList.item(0);
            Element fstElmnt1 = (Element)node1;

            NodeList firstCntList1 = fstElmnt1.getElementsByTagName("firstCnt");
            Element firstCnt1Element = (Element) firstCntList1.item(0);
            firstCntList1 = firstCnt1Element.getChildNodes();
            firstCnt1 += "▲" + ((Node) firstCntList1.item(0)).getNodeValue();

            NodeList secondCntList1 = fstElmnt1.getElementsByTagName("secondCnt");
            Element secondCnt1Element = (Element) secondCntList1.item(0);
            secondCntList1 = secondCnt1Element.getChildNodes();
            secondCnt1 += "▲" +((Node) secondCntList1.item(0)).getNodeValue();

        }

    }
    public String getWeatherDescription(int code){
        switch (code/100) {
            case 1:
            case 2:
                return "천둥이 칩니다.";
            case 3:
                return "비가 조금씩오네요.";
            case 4:
                return "비가 많이오네요.";
            case 5:
                return "비가 엄청와요.";
            case 6:
                return "눈이 와요";
            case 7:
                return "태풍에 주의해주세요";
            case 8:
                return "맑은 하늘입니다";
            case 9:
                return "바람이 조금많이부네요";
        }
        return null;
    }


    public void getDustDetails(String station) {
        GetDustTask dusttask = new GetDustTask(this);
        dusttask.execute("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=" + dustkey +
                "&returnType=xml&numOfRows=1&pageNo=1&stationName=" + station + "&dataTerm=DAILY&ver=1.0");
    }


    public void getWeatherDetails(String city) {
        String tempUrl = "";
        tempUrl = url + "?q=" + city + "&appid=" + appid;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    int weatherId = jsonObjectWeather.getInt("id");
                    String description = getWeatherDescription(weatherId);
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp= jsonObjectMain.getDouble("temp") - 273.15;
                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                    int humidity = jsonObjectMain.getInt("humidity");
                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");
                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    String cityName = jsonResponse.getString("name");
                    temp_v=df.format(temp);
                    feeltemp_v=df.format(feelsLike);
                    hum_v=humidity;
                    weather_v=description;
                    cloud_v=clouds;
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                 Toast.makeText(getApplicationContext(), error.toString().trim(),Toast.LENGTH_SHORT).show();
            }
        });
         RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
         requestQueue.add(stringRequest);
    }

    private class GetCovidTask extends AsyncTask<String, Void, Document> {
        private Activity covidcontext;

        public GetCovidTask(Activity covidcontext) { this.covidcontext = covidcontext; }

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                coviddoc = db.parse(new InputSource(url.openStream()));
                coviddoc.getDocumentElement().normalize();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return coviddoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            NodeList nodeList = coviddoc.getElementsByTagName("item");
            Node node = nodeList.item(18);
            Element cntElmnt = (Element) node;

            NodeList updateList = cntElmnt.getElementsByTagName("stdDay");
            Element updateElement = (Element) updateList.item(0);
            updateList = updateElement.getChildNodes();
            update += "" + ((Node) updateList.item(0)).getNodeValue();

            NodeList defCntList = cntElmnt.getElementsByTagName("defCnt");
            Element defCntElement = (Element) defCntList.item(0);
            defCntList = defCntElement.getChildNodes();
            defCnt += "" + ((Node) defCntList.item(0)).getNodeValue();

            NodeList clearList = cntElmnt.getElementsByTagName("isolClearCnt");
            Element clearElement = (Element) clearList.item(0);
            clearList = clearElement.getChildNodes();
            clear += "" + ((Node) clearList.item(0)).getNodeValue();

            NodeList deathList = cntElmnt.getElementsByTagName("deathCnt");
            Element deathElement = (Element) deathList.item(0);
            deathList = deathElement.getChildNodes();
            death += "" + ((Node) deathList.item(0)).getNodeValue();

            NodeList incDecList = cntElmnt.getElementsByTagName("incDec");
            Element incDecElement = (Element) incDecList.item(0);
            incDecList = incDecElement.getChildNodes();
            incDec += "▲" + ((Node) incDecList.item(0)).getNodeValue();

            Node node1 = nodeList.item(17);
            Element seoulElmnt = (Element)node1;

            NodeList seoul1List = seoulElmnt.getElementsByTagName("defCnt");
            Element seoul1Element = (Element) seoul1List.item(0);
            seoul1List = seoul1Element.getChildNodes();
            seoul1 += "" + ((Node) seoul1List.item(0)).getNodeValue();

            NodeList seoul2List = seoulElmnt.getElementsByTagName("incDec");
            Element seoul2Element = (Element) seoul2List.item(0);
            seoul2List = seoul2Element.getChildNodes();
            seoul2 += "" + ((Node) seoul2List.item(0)).getNodeValue();

            NodeList seoul3List = seoulElmnt.getElementsByTagName("deathCnt");
            Element seoul3Element = (Element) seoul3List.item(0);
            seoul3List = seoul3Element.getChildNodes();
            seoul3 += "" + ((Node) seoul3List.item(0)).getNodeValue();

            Node node2 = nodeList.item(16);
            Element busanElmnt = (Element)node2;

            NodeList busan1List = busanElmnt.getElementsByTagName("defCnt");
            Element busan1Element = (Element) busan1List.item(0);
            busan1List = busan1Element.getChildNodes();
            busan1 += "" + ((Node) busan1List.item(0)).getNodeValue();

            NodeList busan2List = busanElmnt.getElementsByTagName("incDec");
            Element busan2Element = (Element) busan2List.item(0);
            busan2List = busan2Element.getChildNodes();
            busan2 += "" + ((Node) busan2List.item(0)).getNodeValue();

            NodeList busan3List = busanElmnt.getElementsByTagName("deathCnt");
            Element busan3Element = (Element) busan3List.item(0);
            busan3List = busan3Element.getChildNodes();
            busan3 += "" + ((Node) busan3List.item(0)).getNodeValue();

            Node node3 = nodeList.item(15);
            Element daeguElmnt = (Element)node3;

            NodeList daegu1List = daeguElmnt.getElementsByTagName("defCnt");
            Element daegu1Element = (Element) daegu1List.item(0);
            daegu1List = daegu1Element.getChildNodes();
            daegu1 += "" + ((Node) daegu1List.item(0)).getNodeValue();

            NodeList daegu2List = daeguElmnt.getElementsByTagName("incDec");
            Element daegu2Element = (Element) daegu2List.item(0);
            daegu2List = daegu2Element.getChildNodes();
            daegu2 += "" + ((Node) daegu2List.item(0)).getNodeValue();

            NodeList daegu3List = daeguElmnt.getElementsByTagName("deathCnt");
            Element daegu3Element = (Element) daegu3List.item(0);
            daegu3List = daegu3Element.getChildNodes();
            daegu3 += "" + ((Node) daegu3List.item(0)).getNodeValue();

            Node node20 = nodeList.item(14);
            Element incheonElmnt = (Element)node20;

            NodeList incheon1List = incheonElmnt.getElementsByTagName("defCnt");
            Element incheon1Element = (Element) incheon1List.item(0);
            incheon1List = incheon1Element.getChildNodes();
            incheon1 += "" + ((Node) incheon1List.item(0)).getNodeValue();

            NodeList incheon2List = incheonElmnt.getElementsByTagName("incDec");
            Element incheon2Element = (Element) incheon2List.item(0);
            incheon2List = incheon2Element.getChildNodes();
            incheon2 += "" + ((Node) incheon2List.item(0)).getNodeValue();

            NodeList incheon3List = incheonElmnt.getElementsByTagName("deathCnt");
            Element incheon3Element = (Element) incheon3List.item(0);
            incheon3List = incheon3Element.getChildNodes();
            incheon3 += "" + ((Node) incheon3List.item(0)).getNodeValue();

            Node node4 = nodeList.item(13);
            Element gwangjuElmnt = (Element)node4;

            NodeList gwangju1List = gwangjuElmnt.getElementsByTagName("defCnt");
            Element gwangju1Element = (Element) gwangju1List.item(0);
            gwangju1List = gwangju1Element.getChildNodes();
            gwangju1 += "" + ((Node) gwangju1List.item(0)).getNodeValue();

            NodeList gwangju2List = gwangjuElmnt.getElementsByTagName("incDec");
            Element gwangju2Element = (Element) gwangju2List.item(0);
            gwangju2List = gwangju2Element.getChildNodes();
            gwangju2 += "" + ((Node) gwangju2List.item(0)).getNodeValue();

            NodeList gwangju3List = gwangjuElmnt.getElementsByTagName("deathCnt");
            Element gwangju3Element = (Element) gwangju3List.item(0);
            gwangju3List = gwangju3Element.getChildNodes();
            gwangju3 += "" + ((Node) gwangju3List.item(0)).getNodeValue();

            Node node5 = nodeList.item(12);
            Element daejeonElmnt = (Element)node5;

            NodeList daejeon1List = daejeonElmnt.getElementsByTagName("defCnt");
            Element daejeon1Element = (Element) daejeon1List.item(0);
            daejeon1List = daejeon1Element.getChildNodes();
            daejeon1 += "" + ((Node) daejeon1List.item(0)).getNodeValue();

            NodeList daejeon2List = daejeonElmnt.getElementsByTagName("incDec");
            Element daejeon2Element = (Element) daejeon2List.item(0);
            daejeon2List = daejeon2Element.getChildNodes();
            daejeon2 += "" + ((Node) daejeon2List.item(0)).getNodeValue();

            NodeList daejeon3List = daejeonElmnt.getElementsByTagName("deathCnt");
            Element daejeon3Element = (Element) daejeon3List.item(0);
            daejeon3List = daejeon3Element.getChildNodes();
            daejeon3 += "" + ((Node) daejeon3List.item(0)).getNodeValue();

            Node node6 = nodeList.item(11);
            Element ulsanElmnt = (Element)node6;

            NodeList ulsan1List = ulsanElmnt.getElementsByTagName("defCnt");
            Element ulsan1Element = (Element) ulsan1List.item(0);
            ulsan1List = ulsan1Element.getChildNodes();
            ulsan1 += "" + ((Node) ulsan1List.item(0)).getNodeValue();

            NodeList ulsan2List = ulsanElmnt.getElementsByTagName("incDec");
            Element ulsan2Element = (Element) ulsan2List.item(0);
            ulsan2List = ulsan2Element.getChildNodes();
            ulsan2 += "" + ((Node) ulsan2List.item(0)).getNodeValue();

            NodeList ulsan3List = ulsanElmnt.getElementsByTagName("deathCnt");
            Element ulsan3Element = (Element) ulsan3List.item(0);
            ulsan3List = ulsan3Element.getChildNodes();
            ulsan3 += "" + ((Node) ulsan3List.item(0)).getNodeValue();

            Node node7 = nodeList.item(10);
            Element sejongElmnt = (Element)node7;

            NodeList sejong1List = sejongElmnt.getElementsByTagName("defCnt");
            Element sejong1Element = (Element) sejong1List.item(0);
            sejong1List = sejong1Element.getChildNodes();
            sejong1 += "" + ((Node) sejong1List.item(0)).getNodeValue();

            NodeList sejong2List = sejongElmnt.getElementsByTagName("incDec");
            Element sejong2Element = (Element) sejong2List.item(0);
            sejong2List = sejong2Element.getChildNodes();
            sejong2 += "" + ((Node) sejong2List.item(0)).getNodeValue();

            NodeList sejong3List = sejongElmnt.getElementsByTagName("deathCnt");
            Element sejong3Element = (Element) sejong3List.item(0);
            sejong3List = sejong3Element.getChildNodes();
            sejong3 += "" + ((Node) sejong3List.item(0)).getNodeValue();

            Node node8 = nodeList.item(9);
            Element gyeonggiElmnt = (Element)node8;

            NodeList gyeonggi1List = gyeonggiElmnt.getElementsByTagName("defCnt");
            Element gyeonggi1Element = (Element) gyeonggi1List.item(0);
            gyeonggi1List = gyeonggi1Element.getChildNodes();
            gyeonggi1 += "" + ((Node) gyeonggi1List.item(0)).getNodeValue();

            NodeList gyeonggi2List = gyeonggiElmnt.getElementsByTagName("incDec");
            Element gyeonggi2Element = (Element) gyeonggi2List.item(0);
            gyeonggi2List = gyeonggi2Element.getChildNodes();
            gyeonggi2 += "" + ((Node) gyeonggi2List.item(0)).getNodeValue();

            NodeList gyeonggi3List = gyeonggiElmnt.getElementsByTagName("deathCnt");
            Element gyeonggi3Element = (Element) gyeonggi3List.item(0);
            gyeonggi3List = gyeonggi3Element.getChildNodes();
            gyeonggi3 += "" + ((Node) gyeonggi3List.item(0)).getNodeValue();

            Node node9 = nodeList.item(8);
            Element gangwonElmnt = (Element)node9;

            NodeList gangwon1List = gangwonElmnt.getElementsByTagName("defCnt");
            Element gangwon1Element = (Element) gangwon1List.item(0);
            gangwon1List = gangwon1Element.getChildNodes();
            gangwon1 += "" + ((Node) gangwon1List.item(0)).getNodeValue();

            NodeList gangwon2List = gangwonElmnt.getElementsByTagName("incDec");
            Element gangwon2Element = (Element) gangwon2List.item(0);
            gangwon2List = gangwon2Element.getChildNodes();
            gangwon2 += "" + ((Node) gangwon2List.item(0)).getNodeValue();

            NodeList gangwon3List = gangwonElmnt.getElementsByTagName("deathCnt");
            Element gangwon3Element = (Element) gangwon3List.item(0);
            gangwon3List = gangwon3Element.getChildNodes();
            gangwon3 += "" + ((Node) gangwon3List.item(0)).getNodeValue();

            Node node10 = nodeList.item(7);
            Element chungbukElmnt = (Element)node10;

            NodeList chungbuk1List = chungbukElmnt.getElementsByTagName("defCnt");
            Element chungbuk1Element = (Element) chungbuk1List.item(0);
            chungbuk1List = chungbuk1Element.getChildNodes();
            chungbuk1 += "" + ((Node) chungbuk1List.item(0)).getNodeValue();

            NodeList chungbuk2List = chungbukElmnt.getElementsByTagName("incDec");
            Element chungbuk2Element = (Element) chungbuk2List.item(0);
            chungbuk2List = chungbuk2Element.getChildNodes();
            chungbuk2 += "" + ((Node) chungbuk2List.item(0)).getNodeValue();

            NodeList chungbuk3List = chungbukElmnt.getElementsByTagName("deathCnt");
            Element chungbuk3Element = (Element) chungbuk3List.item(0);
            chungbuk3List = chungbuk3Element.getChildNodes();
            chungbuk3 += "" + ((Node) chungbuk3List.item(0)).getNodeValue();

            Node node11 = nodeList.item(6);
            Element chungnamElmnt = (Element)node11;

            NodeList chungnam1List = chungnamElmnt.getElementsByTagName("defCnt");
            Element chungnam1Element = (Element) chungnam1List.item(0);
            chungnam1List = chungnam1Element.getChildNodes();
            chungnam1 += "" + ((Node) chungnam1List.item(0)).getNodeValue();

            NodeList chungnam2List = chungnamElmnt.getElementsByTagName("incDec");
            Element chungnam2Element = (Element) chungnam2List.item(0);
            chungnam2List = chungnam2Element.getChildNodes();
            chungnam2 += "" + ((Node) chungnam2List.item(0)).getNodeValue();

            NodeList chungnam3List = chungnamElmnt.getElementsByTagName("deathCnt");
            Element chungnam3Element = (Element) chungnam3List.item(0);
            chungnam3List = chungnam3Element.getChildNodes();
            chungnam3 += "" + ((Node) chungnam3List.item(0)).getNodeValue();

            Node node12 = nodeList.item(5);
            Element jeonbukElmnt = (Element)node12;

            NodeList jeonbuk1List = jeonbukElmnt.getElementsByTagName("defCnt");
            Element jeonbuk1Element = (Element) jeonbuk1List.item(0);
            jeonbuk1List = jeonbuk1Element.getChildNodes();
            jeonbuk1 += "" + ((Node) jeonbuk1List.item(0)).getNodeValue();

            NodeList jeonbuk2List = jeonbukElmnt.getElementsByTagName("incDec");
            Element jeonbuk2Element = (Element) jeonbuk2List.item(0);
            jeonbuk2List = jeonbuk2Element.getChildNodes();
            jeonbuk2 += "" + ((Node) jeonbuk2List.item(0)).getNodeValue();

            NodeList jeonbuk3List = jeonbukElmnt.getElementsByTagName("deathCnt");
            Element jeonbuk3Element = (Element) jeonbuk3List.item(0);
            jeonbuk3List = jeonbuk3Element.getChildNodes();
            jeonbuk3 += "" + ((Node) jeonbuk3List.item(0)).getNodeValue();

            Node node13 = nodeList.item(4);
            Element jeonnamElmnt = (Element)node13;

            NodeList jeonnam1List = jeonnamElmnt.getElementsByTagName("defCnt");
            Element jeonnam1Element = (Element) jeonnam1List.item(0);
            jeonnam1List = jeonnam1Element.getChildNodes();
            jeonnam1 += "" + ((Node) jeonnam1List.item(0)).getNodeValue();

            NodeList jeonnam2List = jeonnamElmnt.getElementsByTagName("incDec");
            Element jeonnam2Element = (Element) jeonnam2List.item(0);
            jeonnam2List = jeonnam2Element.getChildNodes();
            jeonnam2 += "" + ((Node) jeonnam2List.item(0)).getNodeValue();

            NodeList jeonnam3List = jeonnamElmnt.getElementsByTagName("deathCnt");
            Element jeonnam3Element = (Element) jeonnam3List.item(0);
            jeonnam3List = jeonnam3Element.getChildNodes();
            jeonnam3 += "" + ((Node) jeonnam3List.item(0)).getNodeValue();

            Node node14 = nodeList.item(3);
            Element gyeongbukElmnt = (Element)node14;

            NodeList gyeongbuk1List = gyeongbukElmnt.getElementsByTagName("defCnt");
            Element gyeongbuk1Element = (Element) gyeongbuk1List.item(0);
            gyeongbuk1List = gyeongbuk1Element.getChildNodes();
            gyeongbuk1 += "" + ((Node) gyeongbuk1List.item(0)).getNodeValue();

            NodeList gyeongbuk2List = gyeongbukElmnt.getElementsByTagName("incDec");
            Element gyeongbuk2Element = (Element) gyeongbuk2List.item(0);
            gyeongbuk2List = gyeongbuk2Element.getChildNodes();
            gyeongbuk2 += "" + ((Node) gyeongbuk2List.item(0)).getNodeValue();

            NodeList gyeongbuk3List = gyeongbukElmnt.getElementsByTagName("deathCnt");
            Element gyeongbuk3Element = (Element) gyeongbuk3List.item(0);
            gyeongbuk3List = gyeongbuk3Element.getChildNodes();
            gyeongbuk3 += "" + ((Node) gyeongbuk3List.item(0)).getNodeValue();

            Node node15 = nodeList.item(2);
            Element gyeongnamElmnt = (Element)node15;

            NodeList gyeongnam1List = gyeongnamElmnt.getElementsByTagName("defCnt");
            Element gyeongnam1Element = (Element) gyeongnam1List.item(0);
            gyeongnam1List = gyeongnam1Element.getChildNodes();
            gyeongnam1 += "" + ((Node) gyeongnam1List.item(0)).getNodeValue();

            NodeList gyeongnam2List = gyeongnamElmnt.getElementsByTagName("incDec");
            Element gyeongnam2Element = (Element) gyeongnam2List.item(0);
            gyeongnam2List = gyeongnam2Element.getChildNodes();
            gyeongnam2 += "" + ((Node) gyeongnam2List.item(0)).getNodeValue();

            NodeList gyeongnam3List = gyeongnamElmnt.getElementsByTagName("deathCnt");
            Element gyeongnam3Element = (Element) gyeongnam3List.item(0);
            gyeongnam3List = gyeongnam3Element.getChildNodes();
            gyeongnam3 += "" + ((Node) gyeongnam3List.item(0)).getNodeValue();

            Node node16 = nodeList.item(1);
            Element jejuElmnt = (Element)node16;

            NodeList jeju1List = jejuElmnt.getElementsByTagName("defCnt");
            Element jeju1Element = (Element) jeju1List.item(0);
            jeju1List = jeju1Element.getChildNodes();
            jeju1 += "" + ((Node) jeju1List.item(0)).getNodeValue();

            NodeList jeju2List = jejuElmnt.getElementsByTagName("incDec");
            Element jeju2Element = (Element) jeju2List.item(0);
            jeju2List = jeju2Element.getChildNodes();
            jeju2 += "" + ((Node) jeju2List.item(0)).getNodeValue();

            NodeList jeju3List = jejuElmnt.getElementsByTagName("deathCnt");
            Element jeju3Element = (Element) jeju3List.item(0);
            jeju3List = jeju3Element.getChildNodes();
            jeju3 += "" + ((Node) jeju3List.item(0)).getNodeValue();

            Node node17 = nodeList.item(0);
            Element geomElmnt = (Element)node17;

            NodeList geom1List = geomElmnt.getElementsByTagName("defCnt");
            Element geom1Element = (Element) geom1List.item(0);
            geom1List = geom1Element.getChildNodes();
            geom1 += "" + ((Node) geom1List.item(0)).getNodeValue();

            NodeList geom2List = geomElmnt.getElementsByTagName("incDec");
            Element geom2Element = (Element) geom2List.item(0);
            geom2List = geom2Element.getChildNodes();
            geom2 += "" + ((Node) geom2List.item(0)).getNodeValue();

            NodeList geom3List = geomElmnt.getElementsByTagName("deathCnt");
            Element geom3Element = (Element) geom3List.item(0);
            geom3List = geom3Element.getChildNodes();
            geom3 += "" + ((Node) geom3List.item(0)).getNodeValue();

        }
    }
}