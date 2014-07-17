package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	MainPanel panel;
	int maxvalue;
	int minvalue;
	int width = 600;int height = 400;
	ArrayList<Integer> data;
	public GraphPanel(MainPanel panel){
		setPreferredSize(new Dimension(width,height));
		this.panel = panel;
		this.data = panel.getData();
		for(int k :data){
			if(k>maxvalue)maxvalue = k;
			if(k<minvalue)minvalue = k;
		}

	
	}
	public void notifyDataChange(){
		this.data = panel.getData();
		maxvalue = 0;minvalue = 0;
		for(int k :data){
			if(k>maxvalue)maxvalue = k;
			if(k<minvalue)minvalue = k;
		}
		repaint();

	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawLine(0,height/2,width,height/2);
		for(int i = 0;i<data.size()-1;i++){
			g.drawLine(i*width/data.size(),height/2-data.get(i)*height/(maxvalue+-1*minvalue),(i+1)*width/data.size(),height/2-data.get(i+1)*height/(maxvalue+-1*minvalue));
		}
	}

}
