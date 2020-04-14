package com.example.tdproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class OutputActivity extends AppCompatActivity {

    //Add text view and solution here following the format\
    TextView first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,tenth,eleventh,thirteenth,fourteenth,team;
    double solution1,solution2,solution3,solution4,solution5;
    String solution6,solution7,solution8,solution9,solution10,solution11;
    double radius,rX,rY,sX,sY;
    GraphView graph,sendingCircle;
    LineGraphSeries<DataPoint> series,series2,series3,series4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Bundle bundle = getIntent().getExtras();
        solution1 = bundle.getDouble("solution1");
        solution2=bundle.getDouble("solution2");
        solution3=bundle.getDouble("solution3");
        solution4=bundle.getDouble("solution4");
        solution5=bundle.getDouble("solution5");
        solution6=bundle.getString("solution6");
        solution7=bundle.getString("solution7");
        solution8=bundle.getString("solution8");
        solution9=bundle.getString("solution9");
        solution10=bundle.getString("solution10");
        solution11=bundle.getString("solution11");
        radius=bundle.getDouble("radius");
        rX=bundle.getDouble("rX");
        rY=bundle.getDouble("rY");
        sX=bundle.getDouble("sX");
        sY=bundle.getDouble("sY");


        init();

    }

    private void init() {
        //Initialize your view here
        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        third=findViewById(R.id.third);
        fourth=findViewById(R.id.fourth);
        fifth=findViewById(R.id.fifth);
        sixth=findViewById(R.id.sixth);
        seventh=findViewById(R.id.seventh);
        eighth=findViewById(R.id.eigth);
        ninth=findViewById(R.id.ninth);
        tenth=findViewById(R.id.tenth);
        eleventh=findViewById(R.id.eleventh);
        thirteenth=findViewById(R.id.thirteenth);
        fourteenth=findViewById(R.id.fourteenth);
        team=findViewById(R.id.team);

        setText();
    }



    private void setText() {
        team.setText("Team Members:\n"+"Kalai Kaamesh : 107118040\n"+"Sarvesh  : 107118088\n"+"Shashank Kakarlapudi : 107118092\n");
        first.setText(        Html.fromHtml("<b>"+"Inductance per phase per km = "+"</b>"+solution1+" H/km"));
        second.setText(        Html.fromHtml("<b>"+"Capacitance per phase per km="+"</b>"+solution2+" F/km"));
        third.setText(Html.fromHtml("<b>"+"Inductive Reactance of the line= "+"</b>"+solution3+" Ohm/phase"));
        fourth.setText(        Html.fromHtml("<b>"+"Capacitive Reactance of the line= "+"</b>"+solution4+" Ohm/phase"));
        fifth.setText(        Html.fromHtml("<b>"+"Charging current drawn from sending substation= "+"</b>"+solution5+" A")
        );
        sixth.setText(solution6);
        seventh.setText(        Html.fromHtml("<b>"+"Sending Voltage="+"</b>"+solution7+"kV (Phase Voltage)")
        );
        eighth.setText(Html.fromHtml("<b>"+"Sending Current="+"</b>"+solution8+"A (Phase Current)"));
        ninth.setText(        Html.fromHtml("<b>"+"Voltage Regulation=("+"</b>"+solution9+"<b>"+")%"+"</b>"));
        tenth.setText(        Html.fromHtml("<b>"+"Power Loss="+"</b>"+solution10+"MW")
        );

        eleventh.setText(        Html.fromHtml("<b>"+"Transmission Effiency="+"</b>"+solution11+"%")
        );
        if(rX>0){
            rX=-rX;
        }
        if(rY>0){
            rY=-rY;
        }
        if(sX<0){
            sX=-rX;
        }
        if(sY<0){
            sY=-sY;
        }
        thirteenth.setText("Recieving end circle diagram:\n"+"Radius="+radius+"\nMidPoint X="+rX+"\nMidPoint Y="+rY);
        fourteenth.setText("Sending end circle diagram:\n"+"Radius="+radius+"\nMidPoint X="+sX+"\nMidPoint Y="+sY);
        setGraphRecieving();
        setGraphSending();

        //Recieving end circle diagram





//Sending circle

    }

    private void setGraphSending() {
        graph = (GraphView) findViewById(R.id.graph);
        series=new LineGraphSeries<>();
        series2=new LineGraphSeries<>();
        double h=rX;
        double r=radius;
        double k=rY;
        double x=h-r;
        Log.d("Recieving"," radius="+radius+" h="+h+" k="+k);
        while(x<=h+r){

            double temp=Math.sqrt((r*r)-Math.pow((x-h),2));
            double y=temp+k;
            double y2=k-temp;
            series.appendData(new DataPoint(x,y),false,12000);
            series2.appendData(new DataPoint(x,y2),false,12000);
            x+=radius/500;

        }

        graph.getViewport().setMinX((Math.abs(h)+Math.abs(radius))*-3);
        graph.getViewport().setMaxX((Math.abs(h)+Math.abs(radius))*3);
        graph.getViewport().setMinY((Math.abs(h)+Math.abs(radius))*-3);
        graph.getViewport().setMaxY((Math.abs(h)+Math.abs(radius))*3);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

//        graph.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
//        graph.getViewport().setScrollable(true);  // activate horizontal scrolling
//        graph.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
//        graph.getViewport().setScrollableY(true);
//        graph.getViewport().get
        graph.setTitle("Recieving end circle diagram");
        graph.setTitleTextSize(52f);
        graph.computeScroll();
        graph.addSeries(series);
        graph.addSeries(series2);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Pr (MW)");
        gridLabel.setVerticalAxisTitle("Qr (MVAR)");
    }

    private void setGraphRecieving() {
        graph = (GraphView) findViewById(R.id.sendingCircle);
        series=new LineGraphSeries<>();
        series2=new LineGraphSeries<>();
        double h=sX;
        double r=radius;
        double k=sY;
        double x=h-r;
        Log.d("Recieving"," radius="+radius+" h="+-h+" k="+-k);
        while(x<=h+r){

            double temp=Math.sqrt((r*r)-Math.pow((x-h),2));
            double y=temp+k;
            double y2=k-temp;
            series.appendData(new DataPoint(x,y),false,12000);
            series2.appendData(new DataPoint(x,y2),false,12000);
            x+=radius/500;

        }

        graph.getViewport().setMinX((Math.abs(h)+Math.abs(radius))*-3);
        graph.getViewport().setMaxX((Math.abs(h)+Math.abs(radius))*3);
        graph.getViewport().setMinY((Math.abs(h)+Math.abs(radius))*-3);
        graph.getViewport().setMaxY((Math.abs(h)+Math.abs(radius))*3);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

//        graph.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
//        graph.getViewport().setScrollable(true);  // activate horizontal scrolling
//        graph.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
//        graph.getViewport().setScrollableY(true);
//        graph.getViewport().get
        graph.setTitle("Sending end circle diagram");
        graph.setTitleTextSize(52f);
        graph.computeScroll();
        graph.addSeries(series);
        graph.addSeries(series2);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Ps (MW)");
        gridLabel.setVerticalAxisTitle("Qs (MVAR)");

    }


}

