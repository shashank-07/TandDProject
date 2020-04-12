package com.example.tdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tdproject.outputs.Capacitance;
import com.example.tdproject.outputs.Inductance;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    Button chooseModel,enter,enterSubSpacing;
    ToggleButton symmetryInput;
    LinearLayout asymSpacing,subSpacingLayout;
    float spacingPhaseAB,spacingPhaseBC,spacingPhaseCA;
    int limit=-1;
    EditText spacingPhaseABInput,spacingPhaseBCInput,spacingPhaseCAInput,spacingPhaseInput,subconductorsInput,spacingSubInput,strandsInput,diameterInput,lengthInput,resistanceInput,powerFrequencyInput,nominalSysVoltageInput,recievinEndLoadInput,powerFactorInput;
    double solution1,solution2,solution3,solution4;

    //All input values
    String symmetry,model;

    float spacingPhase,subconductors,strands,diameter,length,resistance,powerFrequency,nominalSysVoltage,recievinEndLoad,powerFactor;
    Vector<Float> spacingSub=new Stack<>();

    //Initialize solution class
    Inductance inductance;
    Capacitance capacitance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       init();
    }

    private void goToOutput(){
        Intent intent = new Intent(MainActivity.this, OutputActivity.class);

        intent.putExtra("solution1", solution1);
        intent.putExtra("solution2",solution2);
        intent.putExtra("solution3",solution3);
        intent.putExtra("solution4",solution4);

        //End of solutions
        startActivity(intent);
    }

   private void generateOutput() {
       //Ignore

       // Call constructor
       //For inductance

       if (symmetryInput.getText().toString().matches("Asymmetrical")) {
           //initializing a class and passing the required value as a parameter
           inductance = new Inductance(spacingPhaseAB, spacingPhaseBC, spacingPhaseCA, diameter, strands,subconductors,spacingSub,powerFrequency,length);
           capacitance= new Capacitance(spacingPhaseAB, spacingPhaseBC, spacingPhaseCA, diameter, strands,subconductors,spacingSub,powerFrequency,length);

       } else {

           inductance = new Inductance(spacingPhase,spacingPhase,spacingPhase, diameter, strands,subconductors,spacingSub,powerFrequency,length);
           capacitance= new Capacitance(spacingPhase, spacingPhase, spacingPhase, diameter, strands,subconductors,spacingSub,powerFrequency,length);
       }
       solution1=inductance.getResult();
      solution2=capacitance.getResult();
       solution3=inductance.getInductiveReactance(solution1);
      solution4=capacitance.getCapacitiveReactance(solution2);
       goToOutput();


       //Copy paste this replace name with solutionN where N is solution number, second thing is the function which returns the solution

   }








    private void init() {

        asymSpacing=findViewById(R.id.asymetricalSpacing);
        symmetryInput=findViewById(R.id.symmetry);
        spacingPhaseABInput=findViewById(R.id.ab);
        spacingPhaseBCInput=findViewById(R.id.bc);
        spacingPhaseCAInput=findViewById(R.id.ca);
        spacingPhaseInput=findViewById(R.id.spacing);
        subconductorsInput=findViewById(R.id.subconductors);
        spacingSubInput=findViewById(R.id.subconductors_spacing);
        strandsInput=findViewById(R.id.subconductor_strands);
        diameterInput=findViewById(R.id.strandDiameter);
        lengthInput=findViewById(R.id.length);
        resistanceInput=findViewById(R.id.resistance);
        powerFrequencyInput=findViewById(R.id.powerfrequency);
        nominalSysVoltageInput=findViewById(R.id.systemVoltage);
        recievinEndLoadInput=findViewById(R.id.endload);
        powerFactorInput=findViewById(R.id.powerfactor);
        chooseModel=findViewById(R.id.model);
        enter=findViewById(R.id.enter);
        enterSubSpacing=findViewById(R.id.enterSubSpacing);

        subconductorsInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s!=null){
                        if(!spacingSub.isEmpty()){
                            spacingSub.clear();
                        }
                        if(!s.toString().isEmpty()){
                            try {
                                int temp=Integer.parseInt(s.toString());
                                limit=(temp*(temp-1))/2;
                                enterSubSpacing.setText("Submissions left: "+limit);
                            } catch (Exception e){
                                enterSubSpacing.setText("Enter a valid number");

                            }

                        }else{
                            enterSubSpacing.setText("Enter No of subconductors");

                        }

                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        enterSubSpacing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(limit!=0){
                    if(!spacingSubInput.getText().toString().isEmpty()){
                        try{
                            float temp=Float.parseFloat(spacingSubInput.getText().toString());
                            spacingSub.add(temp);
                            limit--;
                            Toast.makeText(getApplicationContext(),temp+" Added",Toast.LENGTH_SHORT).show();
                            enterSubSpacing.setText("Submissions left: "+limit);

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Enter a valid input",Toast.LENGTH_SHORT).show();
                        }
                    }
                }



            }
        });
        asymSpacing.setVisibility(View.GONE);
        spacingPhaseInput.setVisibility(View.GONE);
        if(symmetryInput.getText().toString().matches("Asymmetrical")){
            asymSpacing.setVisibility(View.VISIBLE);
            spacingPhaseInput.setVisibility(View.GONE);

        }else{
            asymSpacing.setVisibility(View.GONE);
            spacingPhaseInput.setVisibility(View.VISIBLE);

        }
        symmetryInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(symmetryInput.getText().toString().matches("Asymmetrical")){
                    asymSpacing.setVisibility(View.VISIBLE);
                    spacingPhaseInput.setVisibility(View.GONE);

                }else{
                    asymSpacing.setVisibility(View.GONE);
                    spacingPhaseInput.setVisibility(View.VISIBLE);

                }
            }
        });





        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    symmetry=symmetryInput.getText().toString();
                    if(symmetryInput.getText().toString().matches("Asymmetrical")){
                        spacingPhaseAB= Float.parseFloat(spacingPhaseABInput.getText().toString());
                        spacingPhaseBC= Float.parseFloat(spacingPhaseBCInput.getText().toString());
                        spacingPhaseCA= Float.parseFloat(spacingPhaseCAInput.getText().toString());


                    }else{
                        spacingPhase= Float.parseFloat(spacingPhaseInput.getText().toString());
                    }
                    subconductors=Float.parseFloat(subconductorsInput.getText().toString());
                    strands=Float.parseFloat(strandsInput.getText().toString());
                    diameter=Float.parseFloat(diameterInput.getText().toString());
                    length=Float.parseFloat(lengthInput.getText().toString());
                    resistance=Float.parseFloat(resistanceInput.getText().toString());
                    powerFrequency=Float.parseFloat(powerFrequencyInput.getText().toString());
                    nominalSysVoltage=Float.parseFloat(nominalSysVoltageInput.getText().toString());
                    recievinEndLoad=Float.parseFloat(recievinEndLoadInput.getText().toString());
                    powerFactor=Float.parseFloat(powerFactorInput.getText().toString());
                    if(spacingSub.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Please fill all fields",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        generateOutput();

                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(),"Please fill all fields",Toast.LENGTH_SHORT).show();
                    return;
                }
            }


        });




        chooseModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(MainActivity.this, chooseModel);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.models, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        chooseModel.setText(item.getTitle());
                        model=item.getTitle().toString();
                        return true;
                    }

                });
                popup.show();//showing popup menu

            }
        });


    }
}
