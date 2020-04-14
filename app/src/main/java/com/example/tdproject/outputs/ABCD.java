package com.example.tdproject.outputs;

import android.util.Log;

import com.example.tdproject.Complex;

/*  FOR ABCD parameters Input length of line in km, 1. if short: A=1,C=0 ,D =1 ,B=z.  Z=complex value (r*l)+jXl,Nominal Pi: B=z,A=D=1+(YZ/2)  ,C=Y*(1+(YZ/4)).
    Long, gamma=root(YZ) Zc=root(Z/Y) A=cosh(gamma*l) B=Zc*sinh(gamma*l), C=1/Zc(sinh(gamma*l) D=cosh(gamma*l)
*
* Y=j/Xc
*
* */


public class ABCD {
    Complex A,B,C,D;
    Complex Y,Z,Zc,gamma;
    float resistance;
    float length;
    double Xl,Xc;
    String type;

    public ABCD(float resistance, float length, double xl, double xc, String type) {
        this.resistance = resistance;
        this.length = length;
        Xl = xl;
        Xc = xc;
        this.type = type;
    }
    public String getParameters(){
        Z=new Complex((resistance),(Xl/length));
        Y=new Complex(0,1/(Xc*length));
        Complex temp=Y.times(Z);

        if(type.matches("Short")){
            this.A=new Complex(1,0);
            this.B=Z.times(new Complex(length,0));
            this.C=new Complex(0,0);
            this.D=new Complex(1,0);
            return "ABCD Parameters:\nA=1\nB="+B+"\nC=0\nD=1";

        }
        else if(type.matches("Nominal pi")){


            //A and D
            Complex temp2=temp.times(new Complex(length*length,0)).div(new Complex(2,0));
            Complex A=temp2.plus(new Complex(1,0));

            //C ,C=Y*(1+(YZ/4))
            Complex temp3=temp.times(new Complex(length*length,0)).div(new Complex(4,0));
            Complex temp4=temp3.plus(new Complex(1,0));
            Complex C=Y.times(new Complex(length,0)).times(temp4);

            this.A=A;
            this.B=Z.times(new Complex(length,0));
            this.C=C;
            this.D=A;
            return "ABCD Parameters:\nA="+A+"\nB="+B+"\nC="+C+"\nD="+A;

        }
        else{
           /* gamma=root(YZ) Zc=root(Z/Y) A=cosh(gamma*l) B=Zc*sinh(gamma*l), C=1/Zc(sinh(gamma*l) D=cosh(gamma*l)
                    */
            gamma=temp.sqrt();
            Log.d("Please"," gamma="+gamma.toString()+" Z="+Z+" Y="+Y+" YZ="+temp);
           Complex temp5=Z.div(Y);
           Zc=temp5.sqrt();
           Complex temp6=gamma.times(new Complex(length,0));
            Log.d("Please",temp6.toString());
           Complex A=temp6.cosh();
           Complex B=Zc.times(temp6.sinh());
           Complex C=temp6.sinh().div(Zc);
            this.A=A;
            this.B=B;
            this.C=C;
            this.D=A;
            return "ABCD Parameters:\nA="+A+"\nB="+B+"\nC="+C+"\nD="+A;

        }
    }
    /* 7.  Sending in Voltage in kV , Vs=A*NominalSysVoltage + B*Ir, Ir=(endLoad(mW))*1000/(root(3)*Vr*Power factor        */
/*8.Is=C*Vr+D*Ir

 9. Voltge regulation = 100*(Vs-Vr/Vs)*/
   public double recievingCurrent(float endload,float powerFactor,float sysVoltage){
       double I=(endload*1000)/(3*sysVoltage*powerFactor);
       Log.d("Ok",I+"");
       return I;

   }
   public Complex getSendingV(float sysVoltage,double Ir){
       Complex temp1=A.times(new Complex(sysVoltage*1000,0));
       Complex temp2=B.times(new Complex(Ir,0));
       return temp1.plus(temp2);

   }
    public Complex getSendingI(float sysVoltage,double Ir){
        Complex temp1=C.times(new Complex(sysVoltage*1000,0));
        Complex temp2=D.times(new Complex(Ir,0));
        return temp1.plus(temp2);

    }
    public double voltageReg(Complex Vs,Complex Vr){
        double temp=(((Vs.mod()/1000)-Vr.mod())/(Vr.mod()))*100;
        return temp;

    }
    public double getPowerLoss(double Is,float resistance,float length,Complex Vs){
        double pow;
        Complex Ch1=Vs.times((Y.times(new Complex(length,0)))).div(new Complex(2,0));

        if(type=="Nominal pi"){
                 pow=3*Math.pow((Is-Ch1.mod()),2)*resistance*length/1000000;
            }
            else{
                pow=3*Is*Is*resistance*length/1000000;

            }
            return pow;
    }
    public double getTransmissionEfficiency(double powerLoss,float outputPower){
       return (outputPower/(outputPower+powerLoss))*100;
    }
    public double getRadius(Complex Vs,float Vr){
        return (((Vs.mod()/1000)*Vr)/B.mod());
    }
    public double getRecievingX(float Vr){
       double temp=(A.mod()*Vr*Vr/B.mod());
       double beta=Math.atan(B.imag()/B.real());
        double alpha=Math.atan(A.imag()/A.real());
        return temp*Math.cos(beta-alpha);

    }
    public double getRecievingY(float Vr){
        double temp=(A.mod()*Vr*Vr/B.mod());
        double beta=Math.atan(B.imag()/B.real());
        double alpha=Math.atan(A.imag()/A.real());
        return temp*Math.sin(beta-alpha);

    }
    public double getSendingX(Complex Vs){
       double Vr=Vs.mod()/1000;
        double temp=(A.mod()*Vr*Vr/B.mod());
        double beta=Math.atan(B.imag()/B.real());
        double alpha=Math.atan(A.imag()/A.real());
        return temp*Math.cos(beta-alpha);

    }
    public double getSendingY(Complex Vs){
        double Vr=Vs.mod()/1000;

        double temp=(A.mod()*Vr*Vr/B.mod());
        double beta=Math.atan(B.imag()/B.real());
        double alpha=Math.atan(A.imag()/A.real());
        return temp*Math.sin(beta-alpha);

    }
    public double getChargingCurrent(String model,Complex Vs,float rV){
       Complex Vr=new Complex(rV,0);
        if(type.matches("Short")){
            return 0;

        }
        else if(type.matches("Nominal pi")){
            Complex Ch1=Vs.times((Y.times(new Complex(length,0)))).div(new Complex(2,0));
            Complex Ch2=Vr.times((Y.times(new Complex(length,0)))).div(new Complex(2,0));

            return Ch1.mod()+Ch2.mod();
        }
        else{

            double Ic=Vs.div(
                    (Z.times(new Complex(length,0)))
                            .plus(new Complex(2,0).div((Y.times(new Complex(length,0)))))).mod();
            return Ic;


        }
    }

}
