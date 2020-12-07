package com.example.wefit;

public class History {
    private String distance;
    private String time;
    private String speed;
    private String cal;
    private String points, type;

public History(){

}
public History(String type, String distance,String time,String speed,String cal, String points){
    this.type = type;
    this.distance=distance;
    this.time = time;
    this.speed = speed;
    this.cal=cal;
    this.points = points;

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
    public String getPoints(){
        return points;
    }
    public String getType(){
        return type;
    }
}