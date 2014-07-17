package ui;

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

public class MainPanel extends JPanel implements ActionListener{
	File file;
	File outputFile;
	JFileChooser filechooser;
	JButton[] buttons;
	BufferedWriter bw;
	ArrayList<String> str;
	ArrayList<Integer> data;
	BufferedReader br;
	GraphPanel gp;
	JLabel label;
	String readedLine,datastr;
	int beginIndex,lastIndex;
	
	
	public MainPanel(){
		beginIndex = 0;
		datastr = "";
		outputFile = new File("new file");
		data = new ArrayList<Integer>();
		str = new ArrayList<String>();
		filechooser = new JFileChooser();
		setPreferredSize(new Dimension(800,600));
		buttons = new JButton[10];
		for(int i=0;i<buttons.length;i++){
			buttons[i] = new JButton();
			
		}
		label = new JLabel("Select File to Open");
		buttons[0].setText("Open File");
		buttons[1].setText("Save");
		buttons[2].setText("Open 続き");
		add(label);add(buttons[0]);add(buttons[1]);add(buttons[2]);
		gp = new GraphPanel(this);
		add(gp);
		buttons[0].addActionListener(this);
		buttons[1].addActionListener(this);
		buttons[2].addActionListener(this);
	}
	
	public void initialize(){
		data.clear();
		str.clear();
		datastr = "";
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
	    		  continue;
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
			datastr = "";
			beginIndex = 0;
			OpenFile(file);
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
