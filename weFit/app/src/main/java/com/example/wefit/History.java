package com.example.wefit;

public class History {
    private String distance;
    private String time;
    private String speed;
    private String cal;

public History(){

}
public History(String distance,String time,String speed,String cal){
    this.distance=distance;
    this.time = time;
    this.speed = speed;
    this.cal=cal;

}
public String getDistance_His(){
    return distance;
}
public String getTime_His(){
    return time;
}
    public String getSpeed_His(){
        return speed;
    }
    public String getCal(){
        return cal;
    }
}