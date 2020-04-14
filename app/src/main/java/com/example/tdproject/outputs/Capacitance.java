package com.example.tdproject.outputs;

import android.util.Log;

import java.util.Stack;
import java.util.Vector;

public class Capacitance {
    //spacing between phase subconductors
    float ab,bc,ca;
    float strandDiameter;
    float numberOfStrands;
    float numberOfLayers;
    float diameter;
    float phaseSpacing;
    float netRadius;
    double capacitance;
    double sgmd;
    float subconductors;
   Vector<Float> spacingSub;
    float frequency;
    float lengthOfLine;


    public Capacitance(float ab,float bc,float ca,float diameter,float numberOfStrands, float subConductors,    Vector <Float>  spacingSub,float frequency, float lengthOfLine){
        this.ab=ab;
        this.bc=bc;
        this.ca=ca;
        this.strandDiameter=diameter;
        this.numberOfStrands=numberOfStrands;
        this.subconductors=subConductors;
        this.spacingSub=spacingSub;
        this.frequency=frequency;
        this.lengthOfLine=lengthOfLine;



    }

    public double getResult(){

        //Calculations

        //x is number of layers
        double x;
        x= (3+Math.sqrt(9-4*3*(1-numberOfStrands)))/(2*3);
        if(x<0){
            x= (3-Math.sqrt(9-(4*3*(1-numberOfStrands))))/6;

        }
        numberOfLayers=(float) x;
        diameter=((2*numberOfLayers)-1)*strandDiameter;
        phaseSpacing=(float)(Math.cbrt(ab*bc*ca));

        netRadius=diameter/2;



        //SGMD calculation
        float prod=1;
        for(int rand=0;rand<spacingSub.size();rand++){
            prod*=spacingSub.get(rand);
        }

        //SGMD calculation
        sgmd=Math.pow((Math.pow(netRadius,subconductors)*prod*prod),1/(subconductors*subconductors));
        capacitance=((8.854e-12*2*3.14)/Math.log(phaseSpacing/sgmd));
        Log.d("How","phase spacing:"+phaseSpacing+" sgmd="+sgmd);
        return capacitance*1000;


    }
    public double getCapacitiveReactance(double capacitance){
    return 1/(2*3.14*frequency*capacitance*lengthOfLine);

    }
    public double getChargingCurrent(double capacitiveReactance,float voltage){
        return (voltage*1000)/capacitiveReactance;
    }


}

