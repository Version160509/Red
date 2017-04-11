import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class StudentMSV3 extends WindowAdapter implements ActionListener {
	MenuBar mb;
	Menu menu;
	MenuItem[] options;
	Frame f;
	Dialog inputDg,deleteDg,updateDg;
	Label dLab1, dLab2, dLab3, dLab4, dLab5;
	TextField dTf1, dTf2, dTf3, dTf4, dTf5;
	Button dB1, dB2, dB3, dB4;
	Panel[] dP1, dp2, dp3;
	Choice display;
	Map students;

	public StudentMSV3() {
		this.students = new Hashtable();
	}

	public void gui() throws FileNotFoundException, ClassNotFoundException, IOException {
		int i;
		f = new Frame("学生管理系统V3");
		f.setBounds(100, 100, 600, 400);
		f.addWindowListener(this);
		mb = new MenuBar();
		menu = new Menu("OPTIONS");
		this.options = new MenuItem[5];
		for (i = 0; i < this.options.length; i++) {
			this.options[i] = new MenuItem();
			this.options[i].addActionListener(this);
			this.menu.add(this.options[i]);
		}
		this.options[0].setLabel("增加学生");
		this.options[1].setLabel("删除学生");
		this.options[2].setLabel("更改学生信息");
		this.options[3].setLabel("刷新学生信息");
		this.options[4].setLabel("保存学生信息");
		this.mb.add(menu);
		f.setMenuBar(mb);
		f.setLayout(null);
		this.display = new Choice();
		this.display.setBounds(150, 80, 300, 80);
		f.add(this.display);
		// Setup the dialog to gather info of a new student
		this.inputDg = new Dialog(f, "增加学生", false);
		this.inputDg.setLayout(new GridLayout(4, 1));
		this.inputDg.setBounds(150, 150, 400, 350);
		this.inputDg.addWindowListener(this);
		this.deleteDg = new Dialog(f, "删除学生", false);
		this.deleteDg.setLayout(new GridLayout(2, 1));
		this.deleteDg.setBounds(150, 150, 400, 150);
		this.deleteDg.addWindowListener(this);
		this.updateDg = new Dialog(f, "更改学生信息", false);
		this.updateDg.setLayout(new GridLayout(2, 1));
		this.updateDg.setBounds(150, 150, 400, 150);
		this.updateDg.addWindowListener(this);
		this.dLab1 = new Label("ID:");
		this.dTf1 = new TextField(15);
		this.dLab2 = new Label("姓名:");
		this.dTf2 = new TextField(15);
		this.dLab3 = new Label("年龄:");
		this.dTf3 = new TextField(15);
		this.dLab4 = new Label("请输入删除ID:");
		this.dTf4 = new TextField(15);
		this.dLab5 = new Label("请输入更改ID:");
		this.dTf5 = new TextField(15);
		this.dB1 = new Button("确定");
		this.dB2 = new Button("清空");
		this.dB3 = new Button("确定");
		this.dB4 = new Button("确定");
		this.dB1.addActionListener(this);
		this.dB2.addActionListener(this);
		this.dB3.addActionListener(this);
		this.dB4.addActionListener(this);
		this.dP1 = new Panel[4];
		this.dp2 = new Panel[2];
		this.dp3 = new Panel[2];
		for (i = 0; i < this.dP1.length; i++) {
			this.dP1[i] = new Panel();
			this.inputDg.add(this.dP1[i]);
			if (i == 0) {
				this.dP1[i].add(dLab1);
				this.dP1[i].add(dTf1);
			} else if (i == 1) {
				this.dP1[i].add(dLab2);
				this.dP1[i].add(dTf2);
			} else if (i == 2) {
				this.dP1[i].add(dLab3);
				this.dP1[i].add(dTf3);
			} else {
				this.dP1[i].add(dB1);
				this.dP1[i].add(dB2);
			}
		}
		for(i = 0;i<this.dp2.length;i++){
			this.dp2[i] = new Panel();
			this.deleteDg.add(this.dp2[i]);
			if(i == 0){
				this.dp2[i].add(dLab4);
				this.dp2[i].add(dTf4);
			} else {
				this.dp2[i].add(dB3);
			}
		}
		for(i = 0;i<this.dp3.length;i++){
			this.dp3[i] = new Panel();
			this.updateDg.add(this.dp3[i]);
			if(i == 0){
				this.dp3[i].add(dLab5);
				this.dp3[i].add(dTf5);
			} else {
				this.dp3[i].add(dB4);
			}
		}
		f.setVisible(true);
		readStudents();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.options[0]) {
			this.inputDg.setVisible(true);
		} else if(e.getSource() == this.options[1]){
			this.deleteDg.setVisible(true);
		}else if(e.getSource() == this.options[2]){
			this.updateDg.setVisible(true);
		} else if (e.getSource() == this.dB1) {
			try{
				int temp = Integer.parseInt(dTf3.getText());
				this.addAStudent(dTf1.getText(), dTf2.getText(), Integer.parseInt(dTf3.getText()));
				this.inputDg.setVisible(false);
			}
			catch(Exception a){
				this.dTf3.setText("请输入数字！");
			}
		} else if (e.getSource() == this.dB2) {
			this.dTf1.setText("");
			this.dTf2.setText("");
			this.dTf3.setText("");
		} else if(e.getSource() == this.dB3){
			if(this.deleteAStudent(dTf4.getText())){
				this.dTf4.setText("");
				this.deleteDg.dispose();
			}
		}else if(e.getSource() == this.dB4){
			if(this.students.containsKey(dTf5.getText())){
				updateAStudent(dTf5.getText());
				this.updateDg.dispose();
				this.inputDg.setVisible(true);
			}
			else
				this.dTf5.setText("该id不存在！");
		} else if (e.getSource() == this.options[3]) {
			this.display.removeAll();
			Set ids = this.students.keySet();
			Iterator ptr = ids.iterator();
			while (ptr.hasNext()) {
				String acId = (String) ptr.next();
				STUDENT acStu = (STUDENT) this.students.get(acId);
				String info = acId + "   " + acStu.getName() + "   " + acStu.getAge();
				this.display.addItem(info);
			}
		}
		else if(e.getSource() == this.options[4]){
			try {
				saveStudents();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		super.windowClosing(e);
		if(e.getSource() == this.f){
			System.exit(1);
		}
		else if(e.getSource() == this.inputDg){
			this.inputDg.dispose();
		}
		else if(e.getSource() == this.deleteDg){
			this.deleteDg.dispose();
		}
		else if(e.getSource() == this.updateDg){
			this.updateDg.dispose();
		}
	}

	public void addAStudent(String id, String name, int age) {
		STUDENT stu = new STUDENT(id, name, age);
		this.students.put(id, stu);
	}
	
	public boolean deleteAStudent(String id){
    	if(this.students.containsKey(id))
    	{
    		this.students.remove(id);
    		return true;
    	}
        else{
        	this.dTf4.setText("删除失败！该id不存在!");
        	return false;
        }
	}
	
	public void updateAStudent(String id){
		this.students.remove(id);
	}
	
	public void saveStudents() throws IOException,FileNotFoundException{
		String path ="c:\\students.data";
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.students);
        oos.flush();
        fos.close();
        oos.close();
	}
	
	public void readStudents() throws IOException,FileNotFoundException,ClassNotFoundException{
		String path ="c:\\students.data";
        FileInputStream fos = new FileInputStream(path);
        ObjectInputStream oos = new ObjectInputStream(fos);
        Hashtable loadStudents =(Hashtable)oos.readObject();
        Iterator ptr= loadStudents.keySet().iterator();
        while(ptr.hasNext())
        {
        	String key = (String)ptr.next();
        	Object value = loadStudents.get(key);
        	this.students.put(key, value);
        }
        fos.close();
        oos.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		new StudentMSV3().gui();
	}
}
