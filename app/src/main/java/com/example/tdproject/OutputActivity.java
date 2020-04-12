package com.example.tdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OutputActivity extends AppCompatActivity {

    //Add text view and solution here following the format\
    TextView first,second,third,fourth;
    double solution1,solution2,solution3,solution4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Bundle bundle = getIntent().getExtras();
        //format: solutionX=bundle.getFloat("solutionX");
        solution1 = bundle.getDouble("solution1");
        solution2=bundle.getDouble("solution2");
        solution3=bundle.getDouble("solution3");
        solution4=bundle.getDouble("solution4");

        init();

    }

    private void init() {
        //Initialize your view here
        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        third=findViewById(R.id.third);
        fourth=findViewById(R.id.fourth);

        setText();
    }

    private void setText() {
        //Set text here
        first.setText("Inductance per phase per km = "+solution1+" H/km");
        second.setText("Capacitance per phase per km="+solution2+" F/km");
        third.setText("Inductive Reactance of the line= "+solution3+" Ohm");
        fourth.setText("Capacitive Reactance of the line= "+solution4+" Ohm");

    }
}
