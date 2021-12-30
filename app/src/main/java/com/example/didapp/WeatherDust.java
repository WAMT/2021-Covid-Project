package com.example.didapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherDust extends Fragment {

    private View view;
    private TextView dustView;
    private TextView weatherView;
    private TextView dangerinfoView;
    private TextView dustinfoView;
    private String dustpm10;
    private String dustpm25;
    private String value;

    //------------------------------------------------
    private String temp;
    private String feeltemp;
    private int hum;
    private String description;
    private String clouds;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weatherdust, container, false);

        dustView = view.findViewById(R.id.dust);
        weatherView=view.findViewById(R.id.weather);
        dustinfoView = view.findViewById(R.id.dustinfo);
        dangerinfoView=view.findViewById(R.id.dangerinfo);


        if (getArguments() != null) {
            dustpm10 = getArguments().getString("FRAG2_PM10");
            dustpm25 = getArguments().getString("FRAG2_PM25");
            value = getArguments().getString("FRAG2_VAL");
            //-------------------------------------------------------
            temp=getArguments().getString("FRAG2_temp");
            feeltemp=getArguments().getString("FRAG2_feelTemp");
            hum=getArguments().getInt("FRAG2_hum");
            //--------------------------------------------------------
            description=getArguments().getString("FRAG2_des");
            clouds=getArguments().getString("FRAG2_cloud");
            //--------------------------------------------------------
            String dust = "";
            dust += "미세먼지: " + dustpm10 + ", 초미세먼지: " + dustpm25;
            dustView.setText(dust);
            //------------------------------------------------------
            String weather = "";
            weather += "온도: " + temp +"℃"+"  "+"체감온도: " + feeltemp +"℃"+"  "+" 습도: " + hum+"%";
            weatherView.setText(weather);
            //--------------------------------------------------------
            String dangerinfo = "";
            dangerinfo += "날씨: " + description +"    "+ " 구름: " + clouds+"%";
            dangerinfoView.setText(dangerinfo);
            //-------------------------------------------------------
            String dustinfo = "";
            double val = Double.parseDouble(value);
            dustinfo += "통합대기환경지수: " + value + " (";
            String kahivalue = "";
            if (val == 1) {
                kahivalue += "좋음)";
            }
            else if (val == 2) {
                kahivalue += "보통)";
            }
            else if (val == 3) {
                kahivalue += "나쁨)";
            }
            else if (val == 4) {
                kahivalue += "매우나쁨)";
            }
            dustinfo += "" + kahivalue;
            dustinfoView.setText(dustinfo);
        }

        return view;
    }

}
