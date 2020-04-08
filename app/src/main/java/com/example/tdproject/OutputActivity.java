package com.example.tdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OutputActivity extends AppCompatActivity {

    //Add text view and solution here following the format\
    TextView first;
    float solution1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Bundle bundle = getIntent().getExtras();
        //format: solutionX=bundle.getFloat("solutionX");
        solution1 = bundle.getFloat("solution1");

        init();

    }

    private void init() {
        //Initialize your view here
        first=findViewById(R.id.first);



        setText();
    }

    private void setText() {
        //Set text her
        first.setText("Inductance per phase per km = "+solution1+" H/km");
    }
}
