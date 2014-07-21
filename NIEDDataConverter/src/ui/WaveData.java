package ui;

import java.awt.Graphics;
import java.util.ArrayList;

public class WaveData {
	ArrayList<Integer> data;
	String position;
	String posID;
	int minvalue,maxvalue,year,date,hour,min,sec,happenedTime;
	public int getHappenedTime() {
		return happenedTime;
	}

	public void setHappenedTime(int happenedTime) {
		this.happenedTime = happenedTime;
	}
	double disdeg;

	public WaveData(){
		data = new ArrayList<Integer>();
		maxvalue = 0;
		minvalue = 0;
		year = 1994;date = 250;hour = 0;min = 0;sec = 0;disdeg = 0;
	}

	public void addData(int i){
		data.add(i);
		if(maxvalue < i)maxvalue = i;
		if(minvalue > i)minvalue = i;
	}

	
	public void setPosition(String s){
		position = s;
	}

	public void setPosID(String s){
		posID = s;
	}
	public void setDisDeg(double d){
		disdeg = d;
	}
	public double getDisDeg(){
		return disdeg;
	}
	public int getDataSize(){
		return data.size();
	}

	public int getData(int index){
		return data.get(index);
	}
	public void setDate(int year,int date,int hour,int min,int sec){
		this.year = year;
		this.date = date;
		this.hour = hour;
		this.min = min;
		this.sec = sec;
	}
	public int getMinValue(){
		return minvalue;
	}
	public int getMaxValue(){
		return maxvalue;
	}
	public String getPosition(){
		return position;
	}
	public String getPosID(){
		return posID;
	}
	public void draw(Graphics g,int width,int height){
		int max = (maxvalue-minvalue)*10;
		g.drawString(position, 0, -(int)(height*(disdeg/20))+height);
		for(int i = 0;i<data.size()-1;i++){
			g.drawLine(i*width/data.size(),-(int)(height*(disdeg/20))+height-data.get(i)*height/max,(i+1)*width/data.size(),-(int)(height*(disdeg/20))+height-data.get(i+1)*height/max);
		}
		
	}
	public String getDate(){
		String s = year+" day "+date+" "+hour+":"+min+":"+sec+"(UTC)";
		return s;
	}
}
