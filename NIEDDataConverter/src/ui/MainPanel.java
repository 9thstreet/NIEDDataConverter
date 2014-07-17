package ui;

import io.DataReader;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainPanel extends JPanel implements ActionListener{
	File file;
	File outputFile;
	JFileChooser filechooser;
	JButton[] buttons;
	BufferedWriter bw;
	ArrayList<String> str;
	ArrayList<String> st2;
	ArrayList<Integer> data;
	BufferedReader br;
	GraphPanel gp;
	DataReader reader;
	JLabel label,pos,date,distance;
	String readedLine,datastr,datast2;
	String[][] pointData;
	double phie,lambdae,phis,lambdas,disdeg;
	int beginIndex,lastIndex,posnum;
	JTextField phieField,lambdaeField;
	
	public MainPanel(){
		beginIndex = 0;
		datastr = "";
		datast2 = "";
		reader = new DataReader();
		pointData = reader.readTabbedTextData("resource/pointData.txt");
		outputFile = new File("new file");
		data = new ArrayList<Integer>();
		str = new ArrayList<String>();
		st2 = new ArrayList<String>();
		phieField = new JTextField("震源の緯度");
		lambdaeField = new JTextField("震源の経度");
		filechooser = new JFileChooser();
		setPreferredSize(new Dimension(800,600));
		buttons = new JButton[10];
		for(int i=0;i<buttons.length;i++){
			buttons[i] = new JButton();
			
		}
		label = new JLabel("Select File to Open");
		pos = new JLabel();
		date = new JLabel();
		distance = new JLabel();
		buttons[0].setText("Open File");
		buttons[1].setText("Save");
		buttons[2].setText("Open 続き");
		buttons[3].setText("距離計算");
		add(label);add(pos);add(date);add(buttons[0]);add(buttons[1]);add(buttons[2]);add(phieField);add(lambdaeField);add(buttons[3]);add(distance);
		gp = new GraphPanel(this);
		add(gp);
		buttons[0].addActionListener(this);
		buttons[1].addActionListener(this);
		buttons[2].addActionListener(this);
		buttons[3].addActionListener(this);
	}
	
	public void initialize(){
		st2.clear();
		data.clear();
		str.clear();
		datastr = "";
		datast2 = "";
		beginIndex = 0;
	}
	public ArrayList<Integer> getData(){
		return data;
	}
	public void OpenFile(File file){
		filechooser = new JFileChooser();
		int selected = filechooser.showOpenDialog(this);
	    if (selected == JFileChooser.APPROVE_OPTION){
	      file = filechooser.getSelectedFile();
	      label.setText(file.getName());
	      try {
	    	  br = new BufferedReader(new FileReader(file));
	      } catch (FileNotFoundException e1) {
	    	  // TODO 自動生成された catch ブロック
	    	  e1.printStackTrace();
	      }
	      try {
	    	  readedLine = br.readLine();
	    	  str.add(readedLine);
	    	  while(readedLine != null){
		    	  readedLine = br.readLine();
		    	  str.add(readedLine);
		      }
	      } catch (IOException e1) {
	    	  // TODO 自動生成された catch ブロック
	    	  e1.printStackTrace();
	      }
	      for(int i = 0;i<str.size()-1;i++){

	    	  if(str.get(i).codePointAt(4) != 32){
	    		  for(int j=0;j<str.get(i).length();j++){
	    			  if(str.get(i).codePointAt(j) == 32||j == str.get(i).length()-1){
	    				  if(beginIndex != 0){
	    					  lastIndex = (j == str.get(i).length()-1) ? j+1 : j;
	    					  if(lastIndex == beginIndex)beginIndex -= 1;
	    					  datast2 = str.get(i).substring(beginIndex,lastIndex);
	    					  beginIndex = 0;
	    					  st2.add(datast2);
	    					  datast2 = "";
	    				  }
	    				  else if(str.get(i).length()-1 == j){
	    					  lastIndex = j+1;
	    					  beginIndex = j;
	    					  if(lastIndex == beginIndex)beginIndex -= 1;
	    					  datastr = str.get(i).substring(beginIndex,lastIndex);
	    					  beginIndex = 0;
	    					  st2.add(datast2);
	    					  datast2 = "";
	    				  }
	    			  }
	    			  else{
	    				  if(beginIndex == 0){
	    					  beginIndex = j;
	    				  }
	    			  }
	    		  }
	    	  }
	    	  else{
	    		  for(int j = 0;j<str.get(i).length();j++){
	    			  if(str.get(i).codePointAt(j) == 32||j == str.get(i).length()-1){
	    				  if(beginIndex != 0){
	    					  lastIndex = (j == str.get(i).length()-1) ? j+1 : j;
	    					  
	    					  if(lastIndex == beginIndex)beginIndex -= 1;
	    					  //System.out.println(beginIndex);
	    					  datastr = str.get(i).substring(beginIndex,lastIndex);
	    					  beginIndex = 0;
	    					  data.add(Integer.parseInt(datastr));
	    					  //System.out.println(datastr);
	    					  datastr = "";
	    				  }
	    				  else if(str.get(i).length()-1 == j){
	    					  lastIndex = j+1;
	    					  beginIndex = j;
	    					  if(lastIndex == beginIndex)beginIndex -= 1;
	    					  //System.out.println(beginIndex);
	    					  datastr = str.get(i).substring(beginIndex,lastIndex);
	    					  beginIndex = 0;
	    					  data.add(Integer.parseInt(datastr));
	    					  //System.out.println(datastr);
	    					  datastr = "";
	    				  }
	    			  }
	    			  else{
	    				  if(beginIndex == 0){
	    					  beginIndex = j;
	    					  //System.out.println(j);
	    				  }
	    			  }
	    		  }
	    	  }
	      }
//	      for(int i = 0;i<data.size();i++){
//	    	  System.out.println(data.get(i));
//	      }
//	      System.out.println(data.size());
	      gp.notifyDataChange();
	      for(int i = 0;i<pointData.length;i++){
	    	  if(pointData[i][1].equals(st2.get(1))){
	    	      pos.setText("観測点:"+pointData[i][0]);
	    	      posnum = i;
	    	      break;
	    	  }
	      }
	      date.setText("日時"+st2.get(4)+"年の"+st2.get(5)+"日目"+st2.get(6)+"時"+st2.get(7)+"分(UTC)");
	      }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(e.getSource() == buttons[0]){
			initialize();
			OpenFile(file);
		}
		if(e.getSource() == buttons[1]){
			filechooser = new JFileChooser();
			int selected = filechooser.showSaveDialog(this);
		    if (selected == JFileChooser.APPROVE_OPTION){
		    	try {
					bw = new BufferedWriter(new FileWriter( filechooser.getSelectedFile() + ".txt"));
					for(int i = 0;i<data.size();i++){
						try {
							bw.write(i+1+" "+data.get(i));
							bw.newLine();
						} catch (IOException e1) {
							// TODO 自動生成された catch ブロック
							e1.printStackTrace();
						}
					}

					bw.close();
				} catch (IOException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}
				
		    }
		}
		if(e.getSource() == buttons[2]){
			str.clear();
			st2.clear();
			datast2 = "";
			datastr = "";
			beginIndex = 0;
			OpenFile(file);
		}
		if(e.getSource() == buttons[3]){
			if(st2 == null){
				distance.setText("データを読み込んで下さい");
			}
			else{
				phie = phie * Math.PI /180;
				lambdae = lambdae *Math.PI/180;
				phis = Double.parseDouble(pointData[posnum][2]);
				lambdas = Double.parseDouble(pointData[posnum][3]);
				phis = phis * Math.PI/180;
				lambdas =lambdas *Math.PI/180;
				disdeg = Math.acos(Math.cos(phie)*Math.cos(phis)*Math.cos(lambdae-lambdas)+Math.sin(phie)*Math.sin(phis))/Math.PI * 180;
				distance.setText("距離(deg) "+disdeg);
			}
		}
	}
	public static void main(String[] args){
		JFrame frame = new JFrame("NIEDDataConverter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainPanel mp = new MainPanel();
		frame.add(mp);
		frame.pack();
		frame.setVisible(true);
	}

}
