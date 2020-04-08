package com.example.tdproject.outputs;

import android.util.Log;

public class Inductance {
    //spacing between phase subconductors
    float ab,bc,ca;
    float strandDiameter;
    float numberOfStrands;
    float numberOfLayers;
    float diameter;
    float phaseSpacing;
    float netRadius;
    float inductance;

    public Inductance(float spacing,float diameter,float numberOfStrands) {
        this.ab=this.bc=this.ca=spacing;
        this.strandDiameter=diameter;
        this.numberOfStrands=numberOfStrands;
    }
    public Inductance(float ab,float bc,float ca,float diameter,float numberOfStrands){
        this.ab=ab;
        this.bc=bc;
        this.ca=ca;
        this.strandDiameter=diameter;
        this.numberOfStrands=numberOfStrands;
    }

    public float getResult(){
        //Calculations
        double x;
        x= (3+Math.sqrt(9-4*3*(1-numberOfStrands)))/(2*3);
        if(x<0){
            x= (3-Math.sqrt(9-(4*3*(1-numberOfStrands))))/6;

        }
        numberOfLayers=(float) x;
        diameter=((2*numberOfLayers)-1)*strandDiameter;
        phaseSpacing=(float)(Math.cbrt(ab*bc*ca));
        netRadius=0.3894f*diameter;
        inductance=(float)(0.0000002*Math.log(phaseSpacing/netRadius));


        return inductance;


    }
}
