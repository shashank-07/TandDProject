package com.example.tdproject.outputs;

import android.util.Log;

import java.util.Stack;
import java.util.Vector;

public class Inductance {
    //spacing between phase subconductors
    float ab,bc,ca;
    float strandDiameter;
    float numberOfStrands;
    float numberOfLayers;
    float diameter;
    float phaseSpacing;
    float netRadius;
    double inductance;
    double sgmd;
    float subconductors;
    float frequency;
    float lengthOfLine;
    Vector<Float> spacingSub;


    public Inductance(float ab,float bc,float ca,float diameter,float numberOfStrands, float subConductors,    Vector <Float>  spacingSub,float frequency, float lengthOfLine){
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

        netRadius=0.3894f*diameter;
        Log.d("WUT"," "+netRadius);



        //SGMD calculation
        float prod=1;
        for(int rand=0;rand<spacingSub.size();rand++){
            prod*=spacingSub.get(rand);
        }

        sgmd=Math.pow((Math.pow(netRadius,subconductors)*prod*prod),1/(subconductors*subconductors));

       inductance=(0.0000002*Math.log(phaseSpacing/sgmd));
        return inductance*1000;


    }
    public double getInductiveReactance(double Inductance){
        Log.d("INDUCT"," "+Inductance);
        return 2*3.14*frequency*Inductance*lengthOfLine;

    }
}
