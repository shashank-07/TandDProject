package com.example.tdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.tdproject.outputs.Inductance;

public class MainActivity extends AppCompatActivity {
    Button chooseModel,enter;
    ToggleButton symmetryInput;
    LinearLayout asymSpacing;
    float spacingPhaseAB,spacingPhaseBC,spacingPhaseCA;
    EditText spacingPhaseABInput,spacingPhaseBCInput,spacingPhaseCAInput,spacingPhaseInput,subconductorsInput,spacingSubInput,strandsInput,diameterInput,lengthInput,resistanceInput,powerFrequencyInput,nominalSysVoltageInput,recievinEndLoadInput,powerFactorInput;


    //All input values
    String symmetry,model;

    float spacingPhase,subconductors,spacingSub,strands,diameter,length,resistance,powerFrequency,nominalSysVoltage,recievinEndLoad,powerFactor;


    //Initialize solution class
    Inductance inductance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       init();
    }


   private void generateOutput() {
       //Ignore
       Intent intent = new Intent(MainActivity.this, OutputActivity.class);

       // Call constructor
       //For inductance
       if (symmetryInput.getText().toString().matches("Asymmetrical")) {
           //initializing a class and passing the required value as a parameter
           inductance = new Inductance(spacingPhaseAB, spacingPhaseBC, spacingPhaseCA, diameter, strands);

       } else {
           inductance = new Inductance(spacingPhase, diameter, strands);
       }


       //Copy paste this replace name with solutionN where N is solution number, second thing is the function which returns the solution
       intent.putExtra("solution1", inductance.getResult());

       //End of solutions
       startActivity(intent);
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
                    spacingSub=Float.parseFloat(spacingSubInput.getText().toString());
                    strands=Float.parseFloat(strandsInput.getText().toString());
                    diameter=Float.parseFloat(diameterInput.getText().toString());
                    length=Float.parseFloat(lengthInput.getText().toString());
                    resistance=Float.parseFloat(resistanceInput.getText().toString());
                    powerFrequency=Float.parseFloat(powerFrequencyInput.getText().toString());
                    nominalSysVoltage=Float.parseFloat(nominalSysVoltageInput.getText().toString());
                    recievinEndLoad=Float.parseFloat(recievinEndLoadInput.getText().toString());
                    powerFactor=Float.parseFloat(powerFactorInput.getText().toString());

                    generateOutput();

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
