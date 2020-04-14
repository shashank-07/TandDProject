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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tdproject.outputs.ABCD;
import com.example.tdproject.outputs.Capacitance;
import com.example.tdproject.outputs.Inductance;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Stack;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    Button chooseModel,enter,enterSubSpacing;
    ToggleButton symmetryInput;
    LinearLayout asymSpacing,subSpacingLayout;
    float spacingPhaseAB,spacingPhaseBC,spacingPhaseCA;
    int limit=-1;
    MaterialEditText spacingPhaseABInput,spacingPhaseBCInput,spacingPhaseCAInput,spacingPhaseInput,subconductorsInput,spacingSubInput,strandsInput,diameterInput,lengthInput,resistanceInput,powerFrequencyInput,nominalSysVoltageInput,recievinEndLoadInput,powerFactorInput;
    double solution1,solution2,solution3,solution4,solution5,radius,rX,rY,sX,sY;
    String solution6,solution7,solution8,solution9,solution10,solution11;
    //All input values
    String symmetry,model;

    float spacingPhase,subconductors,strands,diameter,length,resistance,powerFrequency,nominalSysVoltage,recievinEndLoad,powerFactor;
    Vector<Float> spacingSub=new Stack<>();

    //Initialize solution class
    Inductance inductance;
    ABCD abcd;
    Capacitance capacitance;

    Complex a=new Complex(1,2);
    Complex b=new Complex(2,3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Complex"," Add= "+ a.plus(b));

       init();
    }

    private void goToOutput(){
        Intent intent = new Intent(MainActivity.this, OutputActivity.class);

        intent.putExtra("solution1", solution1);
        intent.putExtra("solution2",solution2);
        intent.putExtra("solution3",solution3);
        intent.putExtra("solution4",solution4);
        intent.putExtra("solution5",solution5);
        intent.putExtra("solution6",solution6);
        intent.putExtra("solution7",solution7);
        intent.putExtra("solution8",solution8);
        intent.putExtra("solution9",solution9);
        intent.putExtra("solution10",solution10);
        intent.putExtra("solution11",solution11);
        intent.putExtra("radius",radius);
        intent.putExtra("rX",rX);
        intent.putExtra("rY",rY);
        intent.putExtra("sX",sX);
        intent.putExtra("sY",sY);

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

       abcd=new ABCD(resistance,length,solution3,solution4,model);
       solution6=abcd.getParameters();
       double Ir=abcd.recievingCurrent(recievinEndLoad,powerFactor,nominalSysVoltage);
       Complex Vs=abcd.getSendingV(nominalSysVoltage,Ir);
       solution7=String.valueOf(Vs.mod()/1000);
       Complex sendingCurrent=abcd.getSendingI(nominalSysVoltage,Ir);
       solution8=String.valueOf(sendingCurrent.mod());
       solution9=String.valueOf(abcd.voltageReg(Vs,new Complex(nominalSysVoltage,0)));
       double powerLoss=abcd.getPowerLoss(sendingCurrent.mod(),resistance,length,Vs);
       solution10=String.valueOf(powerLoss);
       solution11=String.valueOf(abcd.getTransmissionEfficiency(powerLoss,recievinEndLoad));
       solution5=abcd.getChargingCurrent(model,Vs,nominalSysVoltage/1000);
       radius=abcd.getRadius(Vs,nominalSysVoltage);
       rX=abcd.getRecievingX(nominalSysVoltage);
       rY=abcd.getRecievingY(nominalSysVoltage);
       sX=abcd.getSendingX(Vs);
       sY=abcd.getSendingY(Vs);
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
                            spacingSub.add(temp/1000f);
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
                    diameter=Float.parseFloat(diameterInput.getText().toString())/1000f;
                    length=Float.parseFloat(lengthInput.getText().toString());
                    resistance=Float.parseFloat(resistanceInput.getText().toString());
                    powerFrequency=Float.parseFloat(powerFrequencyInput.getText().toString());
                    nominalSysVoltage=(float) (Float.parseFloat(nominalSysVoltageInput.getText().toString())/Math.sqrt(3));
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
