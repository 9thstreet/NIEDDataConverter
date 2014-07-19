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
	ArrayList<Integer> data;
	ArrayList<ArrayList<Integer>> eqdata;
	ArrayList<Double> disdegs;
	ArrayList<String> names;
	ArrayList<Integer> maxvalues;
	ArrayList<Integer> minvalues;
	BufferedImage bufferImage;

	Color[] colors = {Color.red,Color.blue,Color.green,Color.orange};
	public GraphPanel(MainPanel panel){
		setPreferredSize(new Dimension(width,height));
		this.panel = panel;
		getData();
	
	}
	public void getData(){
		this.data = panel.getData();
		this.eqdata = panel.getEqdata();
		this.names = panel.getNamelist();
		this.disdegs = panel.getDisdegs();
		maxvalues = panel.getMaxvalues();
		minvalues = panel.getMinvalues();
		
		
	}
	public BufferedImage makeImage(){

		int width = 1920;
		int height = 1080;
		
		bufferImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
		Graphics2D g2=bufferImage.createGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, width, height);
		g2.setColor(Color.green);
		g2.drawLine(0,height-1,width,height-1);
		g2.setColor(Color.red);
		int max = (panel.getMaxvalue()-panel.getMinvalue())*10;
		for(int i = 0;i<data.size()-1;i++){
			g2.drawLine(i*width/data.size(),-(int)(height*(panel.getDisdeg()/20))+height-data.get(i)*height/max,(i+1)*width/data.size(),-(int)(height*(panel.getDisdeg()/20))+height-data.get(i+1)*height/max);
		}
		for(int i = 0;i<eqdata.size();i++){
			g2.setColor(colors[i%colors.length]);
			g2.drawString(names.get(i)+" "+((double)Math.round(disdegs.get(i)*100))/100,0,height-(int)(height*(disdegs.get(i)/20)));

			g2.setColor(Color.black);
			int maxv = (maxvalues.get(i)-minvalues.get(i))*10;
			for(int k = 0;k<eqdata.get(i).size()-1;k++){
				g2.drawLine(k*width/eqdata.get(i).size(),-(int)(height*(disdegs.get(i)/20))+height-eqdata.get(i).get(k)*height/maxv,(k+1)*width/eqdata.get(i).size(),-(int)(height*(disdegs.get(i)/20))+height-eqdata.get(i).get(k+1)*height/maxv);
			}

			
		}
		return bufferImage;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, width, height);
		g2.setColor(Color.green);
		g2.drawLine(0,height-1,width,height-1);
		g2.setColor(Color.red);
		int max = (panel.getMaxvalue()-panel.getMinvalue())*10;
		for(int i = 0;i<data.size()-1;i++){
			g2.drawLine(i*width/data.size(),-(int)(height*(panel.getDisdeg()/20))+height-data.get(i)*height/max,(i+1)*width/data.size(),-(int)(height*(panel.getDisdeg()/20))+height-data.get(i+1)*height/max);
		}
		for(int i = 0;i<eqdata.size();i++){
			g2.setColor(colors[i%colors.length]);
			g2.drawString(names.get(i)+" "+((double)Math.round(disdegs.get(i)*100))/100,0,height-(int)(height*(disdegs.get(i)/20)));

			g2.setColor(Color.black);
			int maxv = (maxvalues.get(i)-minvalues.get(i))*10;
			for(int k = 0;k<eqdata.get(i).size()-1;k++){
				g2.drawLine(k*width/eqdata.get(i).size(),-(int)(height*(disdegs.get(i)/20))+height-eqdata.get(i).get(k)*height/maxv,(k+1)*width/eqdata.get(i).size(),-(int)(height*(disdegs.get(i)/20))+height-eqdata.get(i).get(k+1)*height/maxv);
			}
			
		}
	}

}
