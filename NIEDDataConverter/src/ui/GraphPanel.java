package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	MainPanel panel;
	int width = 600;int height = 400;
	ArrayList<WaveData> data;
	BufferedImage bufferImage;

	Color[] colors = {Color.red,Color.blue,Color.green,Color.orange};
	public GraphPanel(MainPanel panel){
		setPreferredSize(new Dimension(width,height));
		this.panel = panel;
		data = panel.getWaveData();
	}
	public BufferedImage makeImage(){

		int width = 2339;
		int height = 1654;

		bufferImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
		Graphics2D g2=bufferImage.createGraphics();
		draw(g2,width,height);
		return bufferImage;
	}

	private void draw(Graphics2D g2,int width,int height){
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, width, height);
		for(int i= 1;i<101;i++){
			g2.setColor((i%10 == 0)?Color.cyan :Color.green);
			g2.drawLine(0,i*(height-20)/100,width,i*(height-20)/100);
		}
		for(int i= 1;i<101;i++){
			g2.setColor((i%10 == 0)?Color.cyan :Color.green);
			g2.drawLine((width-50)*i/100, 0, (width-50)*i/100, height);
		}
		
		g2.setColor(Color.blue);
		g2.drawLine(0,height-20,width,height-20);
		g2.drawLine(width-50, 0, width-50, height);
		g2.setColor(Color.black);
		if(!data.isEmpty()){
			g2.drawString(data.get(0).getDate(),0,height-10);
			g2.drawString(Integer.toString(data.get(0).getDataSize()/20/2),width/2-45,height-10);
			g2.drawString(Integer.toString(data.get(0).getDataSize()/20),width-80,height-10);
			if(data.get(0).getHappenedTime() != 0){
				g2.setColor(Color.red);
				g2.drawString("発生時刻 "+data.get(0).getHappenedTime()+"(sec)",20*data.get(0).getHappenedTime()*width/data.get(0).getDataSize(),height);
				g2.drawLine(20*data.get(0).getHappenedTime()*width/data.get(0).getDataSize(),0,20*data.get(0).getHappenedTime()*width/data.get(0).getDataSize(),height);
			}
		}
		g2.setColor(Color.black);
		g2.drawString("20",width-50,10);
		g2.drawString("10",width-50,height/2+5);
		g2.drawString("距離(deg)",width-50,height-10);
		g2.drawString("0",width-50,height-20);
		g2.drawString("t(sec)",width-80,height);
		for(int i=0;i<data.size();i++){
			data.get(i).draw(g2,width-50,height-20);
		}
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		draw(g2,width,height);
	}

}
