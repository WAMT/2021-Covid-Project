package com.example.didapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.view.menu.ExpandedMenuView;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;




public class MainFragment extends Fragment {

    private View view;

    //----------------------dust---------------------------------------
    private String dustpm10; private String dustpm25; private String khaivalue;
    private float pm10; private float pm25; private float khai;
    //----------------------covid---------------------------------------
    private String covid; private float cpop; private String mylocation;
    //----------------------feel temp-----------------------------------
    private String ftemp; private float ftp;
    //----------------------radar chart----------------------------------
    private float a; private float b; private float c; private float d; private float e;
    private RadarChart chart;
    public static final float MAX = 80, MIN = 1f;
    public static final int NB_QUALITIES = 5;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);
        chart = view.findViewById(R.id.radar_chart);

        if (getArguments() != null) {

            dustpm10 = getArguments().getString("FRAG1_PM10");
            dustpm25 = getArguments().getString("FRAG1_PM25");
            khaivalue = getArguments().getString("FRAG1_KHAI");
            ftemp = getArguments().getString("FRAG1_feelTemp");
            mylocation = getArguments().getString("FRAG1_loc");
            if (mylocation.equals("서울")) {
                covid = getArguments().getString("seoul");
                cpop = Float.parseFloat(covid) / 96;
            } else if (mylocation.equals("인천")) {
                covid = getArguments().getString("incheon");
                cpop = Float.parseFloat(covid) / 30;
            } else if (mylocation.equals("부산")) {
                covid = getArguments().getString("busan");
                cpop = Float.parseFloat(covid) / 34;
            } else if (mylocation.equals("대전")) {
                covid = getArguments().getString("daejeon");
                cpop = Float.parseFloat(covid) / 11;
            } else if (mylocation.equals("대구")) {
                covid = getArguments().getString("daegu");
                cpop = Float.parseFloat(covid) / 24;
            } else if (mylocation.equals("광주")) {
                covid = getArguments().getString("gwangju");
                cpop = Float.parseFloat(covid) / 4;
            } else if (mylocation.equals("울산")) {
                covid = getArguments().getString("ulsan");
                cpop = Float.parseFloat(covid) / 11;
            } else if (mylocation.equals("세종")) {
                covid = getArguments().getString("sejong");
                cpop = Float.parseFloat(covid) / 4;
            } else if (mylocation.equals("제주")) {
                covid = getArguments().getString("jeju");
                cpop = Float.parseFloat(covid) / 7;
            } else if (mylocation.equals("강원")) {
                covid = getArguments().getString("gangwon");
                cpop = Float.parseFloat(covid) / 15;
            } else if (mylocation.equals("경기")) {
                covid = getArguments().getString("gyeonggi");
                cpop = Float.parseFloat(covid) / 136;
            } else if (mylocation.equals("경북")) {
                covid = getArguments().getString("gyeongbuk");
                cpop = Float.parseFloat(covid) / 26;
            } else if (mylocation.equals("경남")) {
                covid = getArguments().getString("gyeongnam");
                cpop = Float.parseFloat(covid) / 33;
            } else if (mylocation.equals("전북")) {
                covid = getArguments().getString("jeonbuk");
                cpop = Float.parseFloat(covid) / 18;
            } else if (mylocation.equals("전남")) {
                covid = getArguments().getString("jeonnam");
                cpop = Float.parseFloat(covid) / 18;
            } else if (mylocation.equals("충북")) {
                covid = getArguments().getString("chungbuk");
                cpop = Float.parseFloat(covid) / 16;
            } else if (mylocation.equals("충남")) {
                covid = getArguments().getString("chungnam");
                cpop = Float.parseFloat(covid) / 21;
            }

            if (cpop < 5) {
                if (cpop < 3) {
                    if (cpop < 1) { e = 40; }
                    else {e = 60;}
                } else {e = 80;}
            } else {e = 100;}


            pm10 = Float.parseFloat(dustpm10);
            if (pm10 <151) {
                if (pm10 <81) {
                    if (pm10 <31) { a = 40;}
                    else {a = 60;}
                }
                else {a = 80;}
            } else {a = 100;}

            pm25 = Float.parseFloat(dustpm25);
            if (pm25 <76) {
                if (pm25 <36) {
                    if (pm25 <16) { b = 40;}
                    else {b = 60;}
                }
                else {b = 80;}
            } else {b = 100;}

            khai = Float.parseFloat(khaivalue);
            if (khai <251) {
                if (khai <101) {
                    if (khai <51) { c = 40;}
                    else {c = 60;}
                }
                else {c = 80;}
            } else {c = 100;}


            ftp = Float.parseFloat(ftemp);
            if (ftp < 30 && ftp > -15.4) {
                if ( ftp< 28 && ftp > -10.5) {
                    if (ftp < 25 && ftp > -3.2) { d = 40; }
                    else {d = 60; }
                }
                else {d = 80;}
            } else {d = 100;}


            chart.setBackgroundColor(Color.rgb(60,65,82));
            chart.getDescription().setEnabled(false);
            chart.setWebLineWidth(3f);
            chart.setWebColor(Color.WHITE);
            chart.setWebLineWidth(3f);
            chart.setWebColorInner(Color.WHITE);
            chart.setWebAlpha(100);

            setData();

            chart.animateXY(1400,1400, Easing.EaseInOutQuad, Easing.EaseInOutQuad);
            XAxis xAxis = chart.getXAxis();
            xAxis.setTextSize(14f);
            xAxis.setYOffset(0);
            xAxis.setXOffset(0);
            String[] labels = {"미세먼지", "초미세먼지", "통합대기환경지수", "코로나 상황", "체감온도(" + ftp + "℃)"};
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

            xAxis.setTextColor(Color.WHITE);

            YAxis yAxis = chart.getYAxis();
            yAxis.setLabelCount(NB_QUALITIES, false);
            yAxis.setTextSize(9f);
            yAxis.setAxisMinimum(MIN);
            yAxis.setAxisMaximum(MAX);
            yAxis.setDrawLabels(false);

            Legend l = chart.getLegend();
            l.setTextSize(15f);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(5f);
            l.setTextColor(Color.WHITE);

        }




        return view;
    }

    private void setData() {
        ArrayList<RadarEntry> visitors = new ArrayList<>();
        visitors.add(new RadarEntry(a));
        visitors.add(new RadarEntry(b));
        visitors.add(new RadarEntry(c));
        visitors.add(new RadarEntry(e));
        visitors.add(new RadarEntry(d));

        ArrayList<RadarEntry> danger = new ArrayList<>();
        danger.add(new RadarEntry(100));
        danger.add(new RadarEntry(100));
        danger.add(new RadarEntry(100));
        danger.add(new RadarEntry(100));
        danger.add(new RadarEntry(100));

        ArrayList<RadarEntry> bad = new ArrayList<>();
        bad.add(new RadarEntry(80));
        bad.add(new RadarEntry(80));
        bad.add(new RadarEntry(80));
        bad.add(new RadarEntry(80));
        bad.add(new RadarEntry(80));

        ArrayList<RadarEntry> soso = new ArrayList<>();
        soso.add(new RadarEntry(60));
        soso.add(new RadarEntry(60));
        soso.add(new RadarEntry(60));
        soso.add(new RadarEntry(60));
        soso.add(new RadarEntry(60));

        ArrayList<RadarEntry> good = new ArrayList<>();
        good.add(new RadarEntry(40));
        good.add(new RadarEntry(40));
        good.add(new RadarEntry(40));
        good.add(new RadarEntry(40));
        good.add(new RadarEntry(40));

        RadarDataSet set1 = new RadarDataSet(visitors, "현재상태");
        RadarDataSet set2 = new RadarDataSet(good, "양호");
        RadarDataSet set3 = new RadarDataSet(soso, "보통");
        RadarDataSet set4 = new RadarDataSet(bad, "주의");
        RadarDataSet set5 = new RadarDataSet(danger, "나쁨");

        set1.setColor(Color.MAGENTA);
        set1.setFillColor(Color.MAGENTA);
        set1.setDrawFilled(true);
        set1.setFillAlpha(100);
        set1.setLineWidth(2f);
        set1.setDrawHighlightIndicators(false);
        set1.setDrawHighlightCircleEnabled(true);

        set2.setColor(Color.BLUE);
        set2.setLineWidth(1f);
        set2.setDrawHighlightIndicators(false);
        set2.setDrawHighlightCircleEnabled(true);

        set3.setColor(Color.GREEN);
        set3.setLineWidth(1f);
        set3.setDrawHighlightIndicators(false);
        set3.setDrawHighlightCircleEnabled(true);

        set4.setColor(Color.YELLOW);
        set4.setLineWidth(1f);
        set4.setDrawHighlightIndicators(false);
        set4.setDrawHighlightCircleEnabled(true);

        set5.setColor(Color.RED);
        set5.setLineWidth(1f);
        set5.setDrawHighlightIndicators(false);
        set5.setDrawHighlightCircleEnabled(true);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set2);
        sets.add(set3);
        sets.add(set4);
        sets.add(set5);
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();
    }


}