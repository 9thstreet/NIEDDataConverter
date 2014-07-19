package ui;

import io.DataReader;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class MainPanel extends JPanel implements ActionListener {
	File file;
	File outputFile;
	JFileChooser filechooser;
	JButton[] buttons;
	BufferedWriter bw;
	ArrayList<String> str;
	ArrayList<String> st2;
	ArrayList<String> namelist;
	ArrayList<Integer> data;
	ArrayList<Double> disdegs;
	ArrayList<ArrayList<Integer>> eqdata;
	BufferedReader br;
	GraphPanel gp;
	DataReader reader;
	JLabel label, pos, date, label2, label3;
	String readedLine, datastr, datast2;
	boolean isEQDataInputted;
	String[][] pointData;
	String[] datalist;
	double phie, lambdae, phis, lambdas, disdeg;
	int beginIndex, lastIndex, posnum, maxvalue, minvalue;
	JTextField phieField, lambdaeField;
	ArrayList<Integer> maxvalues;
	ArrayList<Integer> minvalues;
	final int dx = 100;
	
	public MainPanel() {
		beginIndex = 0;
		datastr = "";
		datast2 = "";
		reader = new DataReader();
		pointData = reader.readTabbedTextData("resource/pointData.txt");
		outputFile = new File("new file");
		data = new ArrayList<Integer>();
		str = new ArrayList<String>();
		st2 = new ArrayList<String>();
		disdegs = new ArrayList<Double>();
		namelist = new ArrayList<String>();
		maxvalues = new ArrayList<Integer>();
		minvalues = new ArrayList<Integer>();
		eqdata = new ArrayList<ArrayList<Integer>>();
		phieField = new JTextField("震央の緯度");
		lambdaeField = new JTextField("震央の経度");
		filechooser = new JFileChooser();
		setPreferredSize(new Dimension(800, 600));
		buttons = new JButton[10];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();

		}
		label = new JLabel("Select File to Open");
		pos = new JLabel();
		date = new JLabel();
		label2 = new JLabel("震央位置");
		buttons[0].setText("Open File");
		buttons[1].setText("Save");
		buttons[2].setText("Open 続き");
		buttons[2].setVisible(false);
		buttons[4].setVisible(false);
		buttons[3].setText("登録");
		buttons[4].setText("読み込み終了");
		buttons[5].setText("画像保存");
		add(label);
		add(pos);
		add(date);
		add(buttons[0]);
		add(buttons[1]);
		add(buttons[2]);
		add(buttons[4]);
		add(label2);
		add(phieField);
		add(lambdaeField);
		add(buttons[3]);
		add(buttons[5]);
		gp = new GraphPanel(this);
		add(gp);
		buttons[0].addActionListener(this);
		buttons[1].addActionListener(this);
		buttons[2].addActionListener(this);
		buttons[3].addActionListener(this);
		buttons[4].addActionListener(this);
		buttons[5].addActionListener(this);
	}

	public void initialize() {
		st2.clear();
		data.clear();
		str.clear();
		datastr = "";
		datast2 = "";
		beginIndex = 0;
	}

	public ArrayList<Integer> getData() {
		return data;
	}

	public void processDataFile(File file){
		label.setText("データ読み込み数 " + eqdata.size());
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		try {
			readedLine = br.readLine();
			str.add(readedLine);
			while (readedLine != null) {
				readedLine = br.readLine();
				str.add(readedLine);
			}
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		for (int i = 0; i < str.size() - 1; i++) {

			if (str.get(i).codePointAt(4) != 32) {
				for (int j = 0; j < str.get(i).length(); j++) {
					if (str.get(i).codePointAt(j) == 32 || j == str.get(i).length() - 1) {
						if (beginIndex != 0) {
							lastIndex = (j == str.get(i).length() - 1) ? j + 1 : j;
							if (lastIndex == beginIndex)
								beginIndex -= 1;
							datast2 = str.get(i).substring(beginIndex, lastIndex);
							beginIndex = 0;
							st2.add(datast2);
							datast2 = "";
						}
						else if (str.get(i).length() - 1 == j) {
							lastIndex = j + 1;
							beginIndex = j;
							if (lastIndex == beginIndex)
								beginIndex -= 1;
							datastr = str.get(i).substring(beginIndex, lastIndex);
							beginIndex = 0;
							st2.add(datast2);
							datast2 = "";
						}
					}
					else {
						if (beginIndex == 0) {
							beginIndex = j;
						}
					}
				}
			}
			else {
				for (int j = 0; j < str.get(i).length(); j++) {
					if (str.get(i).codePointAt(j) == 32 || j == str.get(i).length() - 1) {
						if (beginIndex != 0) {
							lastIndex = (j == str.get(i).length() - 1) ? j + 1 : j;

							if (lastIndex == beginIndex)
								beginIndex -= 1;
							//System.out.println(beginIndex);
							datastr = str.get(i).substring(beginIndex, lastIndex);
							beginIndex = 0;
							data.add(Integer.parseInt(datastr));
							//System.out.println(datastr);
							datastr = "";
						}
						else if (str.get(i).length() - 1 == j) {
							lastIndex = j + 1;
							beginIndex = j;
							if (lastIndex == beginIndex)
								beginIndex -= 1;
							//System.out.println(beginIndex);
							datastr = str.get(i).substring(beginIndex, lastIndex);
							beginIndex = 0;
							data.add(Integer.parseInt(datastr));
							//System.out.println(datastr);
							datastr = "";
						}
					}
					else {
						if (beginIndex == 0) {
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
//		gp.notifyDataChange();
		for (int i = 0; i < pointData.length; i++) {
			if (pointData[i][1].equals(st2.get(1))) {
				pos.setText("観測点:" + pointData[i][0]);
				posnum = i;
				break;
			}
		}
		date.setText("日時" + st2.get(4) + "年の" + st2.get(5) + "日目" + st2.get(6) + "時" + st2.get(7) + "分(UTC)");
		if (isEQDataInputted) {
			phis = Double.parseDouble(pointData[posnum][2]);
			lambdas = Double.parseDouble(pointData[posnum][3]);
			phis = phis * Math.PI / 180;
			lambdas = lambdas * Math.PI / 180;
			disdeg = Math.acos(Math.cos(phie) * Math.cos(phis) * Math.cos(lambdae - lambdas) + Math.sin(phie)
					* Math.sin(phis))
					/ Math.PI * 180;
		}
		maxvalue = 0;
		minvalue = 0;
		for (int k : data) {
			if (k > maxvalue)
				maxvalue = k;
			if (k < minvalue)
				minvalue = k;
		}
	}
	public void OpenFile(File file) {
		filechooser = new JFileChooser();
		int selected = filechooser.showOpenDialog(this);
		if (selected == JFileChooser.APPROVE_OPTION) {
			file = filechooser.getSelectedFile();
			if (!file.getName().equals("filelist.txt")) {
				processDataFile(file);
			}
			else{
				datalist = reader.readTextData(file);
				label2.setText("登録しました");
				phie = Double.parseDouble(datalist[0]);
				lambdae = Double.parseDouble(datalist[1]);
				phie = phie * Math.PI / 180;
				lambdae = lambdae * Math.PI / 180;
				isEQDataInputted = true;
				phieField.setVisible(false);
				lambdaeField.setVisible(false);
				buttons[3].setText("登録取り消し");
				for(int i = 2;i<datalist.length;i++){
					if(!datalist[i].equals("EOP")){
						str.clear();
						st2.clear();
						datast2 = "";
						datastr = "";
						beginIndex = 0;
						processDataFile(new File(file.getParent()+"\\"+datalist[i]));
					}
					else{
						disdegs.add(disdeg);
						namelist.add(st2.get(1));
						maxvalues.add(maxvalue);
						minvalues.add(minvalue);
						eqdata.add(new ArrayList<Integer>(data));
						buttons[2].setVisible(false);
						buttons[0].setVisible(true);
						initialize();
					}
				}
			}
		}
		gp.repaint();
	}

	public int getMaxvalue() {
		return maxvalue;
	}

	public int getMinvalue() {
		return minvalue;
	}

	public ArrayList<Integer> getMaxvalues() {
		return maxvalues;
	}

	public ArrayList<Integer> getMinvalues() {
		return minvalues;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if (e.getSource() == buttons[0]) {
			initialize();
			OpenFile(file);
			if (!data.isEmpty()) {
				buttons[0].setVisible(false);
				buttons[4].setVisible(true);
				buttons[2].setVisible(true);
			}
		}
		if (e.getSource() == buttons[1]) {
			filechooser = new JFileChooser();
			int selected = filechooser.showSaveDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION) {
				if (eqdata.isEmpty()) {
					try {
						bw = new BufferedWriter(new FileWriter(filechooser.getSelectedFile() + ".txt"));
						for (int i = 0; i < data.size(); i++) {
							bw.write(i + 1 + "\t" + data.get(i));
							bw.newLine();
						}
						if (isEQDataInputted) {
							bw.write("距離 " + disdeg);
						}
						bw.close();
					} catch (IOException e1) {
						// TODO 自動生成された catch ブロック
						e1.printStackTrace();
					}
				}
				else {
					str.clear();
					try {
						bw = new BufferedWriter(new FileWriter(filechooser.getSelectedFile() + ".txt"));
						for (int i = 0; i < disdegs.size(); i++) {
							if (i == 0) {
								str.add("震央距離\t" + disdegs.get(i).toString());
							}
							else {
								str.set(0, str.get(0) + "\t" + disdegs.get(i));
							}
						}
						for (int i = 0; i < namelist.size(); i++) {
							if (i == 0) {
								str.add("観測点\t" + namelist.get(i));
							}
							else {
								str.set(1, str.get(1) + "\t" + namelist.get(i));
							}
						}

						System.out.println(eqdata.get(0).size());
						for (int i = 0; i < eqdata.size(); i++) {
							for (int j = 0; j < eqdata.get(i).size(); j++) {
								if (i == 0) {
									str.add(j + "\t" + eqdata.get(i).get(j).toString());
								}
								else {
									str.set(j + 2, str.get(j + 2) + "\t" + eqdata.get(i).get(j));
								}
							}
						}
						for (String s : str) {
							bw.write(s);
							bw.newLine();
							//		    				System.out.println(s);
						}
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		if (e.getSource() == buttons[2]) {
			str.clear();
			st2.clear();
			datast2 = "";
			datastr = "";
			beginIndex = 0;
			OpenFile(file);
		}
		if (e.getSource() == buttons[3]) {
			if (!isEQDataInputted) {
				label2.setText("登録しました");
				phie = Double.parseDouble(phieField.getText());
				lambdae = Double.parseDouble(lambdaeField.getText());
				phie = phie * Math.PI / 180;
				lambdae = lambdae * Math.PI / 180;
				isEQDataInputted = true;
				phieField.setVisible(false);
				lambdaeField.setVisible(false);
				buttons[3].setText("登録取り消し");
			}
			else {
				isEQDataInputted = false;
				phieField.setVisible(true);
				lambdaeField.setVisible(true);
				buttons[3].setText("登録");
			}

		}
		if (e.getSource() == buttons[4]) {
			if (data.isEmpty()) {
				label.setText("データが読み込まれていません");
			} else {
				//				System.out.println(data.size());
				disdegs.add(disdeg);
				namelist.add(st2.get(1));
				maxvalues.add(maxvalue);
				minvalues.add(minvalue);
				eqdata.add(new ArrayList<Integer>(data));
				buttons[2].setVisible(false);
				buttons[0].setVisible(true);
				initialize();
			}
		}
		if(e.getSource() == buttons[5]){
			filechooser = new JFileChooser();
			filechooser.addChoosableFileFilter(new PNGFilter());
			int selected = filechooser.showSaveDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION) {
			    try {
			    	
					OutputStream out=new FileOutputStream(filechooser.getSelectedFile());
					ImageIO.write(gp.makeImage(), "PNG", out);
				} catch (IOException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}
			}	
		}
	}
	
	public ArrayList<ArrayList<Integer>> getEqdata() {
		return eqdata;
	}

	public ArrayList<Double> getDisdegs() {
		return disdegs;
	}

	public double getDisdeg() {
		return disdeg;
	}

	public ArrayList<String> getNamelist() {
		return namelist;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("NIEDDataConverter");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		        PrintStream nullStream = new PrintStream(new OutputStream() {
		            public void write(int b) throws IOException {
		            }

		            public void write(byte b[]) throws IOException {
		            }

		            public void write(byte b[], int off, int len) throws IOException {
		            }
		        });
		        System.setErr(nullStream);
		        System.setOut(nullStream);
		        System.exit(0);
		    }
		});
		MainPanel mp = new MainPanel();
		frame.add(mp);
		frame.pack();
		frame.setVisible(true);
	}

	class PNGFilter extends FileFilter{

		@Override
		public boolean accept(File f) {
			if (f.isDirectory()){
			      return true;
			    }

			    String ext = getExt(f);
			    if (ext != null){
			      if (ext.equals("png")){
			        return true;
			      }else{
			        return false;
			      }
			    }

			    return false;
		}
		public String getExt(File file){
			String fileName = file.getName();
		    int point = fileName.lastIndexOf(".");
		    if (point != -1) {
		        return fileName.substring(point + 1);
		    }
		    return fileName;
		}
		@Override
		public String getDescription() {
			// TODO 自動生成されたメソッド・スタブ
			return "PNG 画像ファイル";
		}
		
	}
}

