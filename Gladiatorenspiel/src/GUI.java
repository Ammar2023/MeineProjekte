
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

  

public class GUI extends JFrame implements ActionListener{
	
	KampfStatTabelle statTable;
    JPanel pn_down, pn_top, pn_btns ;
    JTable table;
    JScrollPane sp_table,sp_textarea;
    JButton btnNew, btnReset, btnLoad, btnSave, btnAdd, btnDelete, btnBattle, btnExit; 
    JTextArea textArea;
    
    public GUI(KampfStatTabelle st)
    {
    	super("Gladiators Game");
    	statTable = new KampfStatTabelle(st.getGlads(), st.getNum_glads());
    	Container c = getContentPane();
    	c.setLayout(new GridLayout(2, 1, 10, 10));
    	
    	//  top panel
    	pn_top = new JPanel(new GridLayout(1, 1));
    	
    	//Deklarieren die Tabelle und deaktivieren die Zellenbearbeitung, indem die editCellAt-Methode überschreiben wird
    	table = new JTable() {
    		public boolean editCellAt(int row, int column, java.util.EventObject e) {
            return false;
    		}
         };
    	table.setModel(createTableModel());
//        table.setFillsViewportHeight(true);
	    table.setBounds(20,20,700,250);//       
	    table.setRowHeight(25);
    	sp_table = new JScrollPane(table);	
    	pn_top.add(sp_table);				

    	//تجهيز down panel
    	pn_down = new JPanel(new GridLayout(1, 2, 10, 10));
    	textArea = new JTextArea("<<Das Ergebnis des Kampfes>>\n");
    	textArea.setEditable(false);
    	textArea.setFont(new Font("Serif", 1, 22));
    	textArea.setLineWrap(true);
    	textArea.setWrapStyleWord(true);
    	textArea.setAlignmentX(CENTER_ALIGNMENT);
    	sp_textarea = new JScrollPane(textArea);
    	
    	// buttons panel
    	pn_btns = new JPanel(new GridLayout(4,2,10,10));
    	btnNew = new JButton("New");					//task 1	
    	btnReset = new JButton("Reset");				//task 2
    	btnLoad = new JButton("Load");					//task 3
    	btnSave= new JButton("Save");					//task 4
    	btnAdd= new JButton("Add Gladiator");			//task 5
    	btnDelete = new JButton("Delete Gladiator");	//task 6
    	btnBattle = new JButton("Fight");				//task 7
    	btnExit = new JButton("Exit");
    	
    	btnNew.addActionListener(this);
    	btnReset.addActionListener(this);
    	btnLoad.addActionListener(this);
    	btnSave.addActionListener(this);
    	btnAdd.addActionListener(this);
    	btnDelete.addActionListener(this);
    	btnBattle.addActionListener(this);
    	btnExit.addActionListener(this);
    	//Buttons in panal einfügen
    	pn_btns.add(btnBattle);
    	pn_btns.add(btnNew);
    	pn_btns.add(btnSave);
    	pn_btns.add(btnAdd);
    	pn_btns.add(btnLoad);
    	pn_btns.add(btnDelete);
    	pn_btns.add(btnReset);
    	pn_btns.add(btnExit);
    		
    	pn_down.add(pn_btns);
    	pn_down.add(sp_textarea);
    	
    	c.add(pn_top);
    	c.add(pn_down);
    	setSize(800,700);    
	    setVisible(true); 
    	
    }   
    
    public DefaultTableModel createTableModel() {
    	GladStat[] glads = statTable.getGlads();
	    String data[][] = new String[statTable.getNum_glads()][6];
	    for(int i =0;i<statTable.getNum_glads() ; i++) 
	    {
	    	data[i][0] = glads[i].getName();
	    	data[i][1] = String.valueOf(glads[i].getNum_fights());
	    	data[i][2] = String.valueOf(glads[i].getWins());
	    	data[i][3] = String.valueOf(glads[i].getLoss()); 
	    	data[i][4] = String.valueOf(glads[i].getWin_rate());
			data[i][5] = (glads[i].isAlive()?"alive":"dead");
	   
    	}
	    
	    String column[]={"Gladiator","Fights","Wins","Losses","Win rate","Status"};         
	    return new DefaultTableModel(data,column);  
    }

    public void actionPerformed(ActionEvent e) {
    	//new table
    	if(e.getSource() == btnNew) 
    	{
    		neueTabelleErstellen();
    	}
    	//reset the table
    	else if(e.getSource() == btnReset) 
    	{
    		resertTabel();
        	
    	}
    	//load table from file
    	else if(e.getSource() == btnLoad) 
    	{
			 loadTableFromFile();
    	}
    	//Save table's data to file
    	else if(e.getSource() == btnSave) 
    	{
			 saveTableToFile();
    	}
    	//Add Gladiator
    	else if(e.getSource() == btnAdd) 
    	{
    		addGladiator();
    	}
    	//Delete Gladiator
    	else if(e.getSource() == btnDelete) 
    	{
    		deleteGladiator();
    		
    	}
    	//make Kampf
    	else if(e.getSource() == btnBattle) 
    	{    
    		startDerKamp();
            	
    		
    	}
    	//Exit the game
    	else if(e.getSource() == btnExit) 
    	{
    		kampBeenden();

    	}
    	
    }

	private void resertTabel() {
		statTable.reset();
		statTable.sort();
		table.setModel(createTableModel());
		textArea.setText("<<Das Ergebnis des Kampfes>>\n");
	}

	private void neueTabelleErstellen() {
		statTable = new KampfStatTabelle();		//löschen KampfStatTabelle
		table.setModel(createTableModel());			//reinitialize the table
		textArea.setText("<<Das Ergebnis des Kampfes>>\n");
	}

	private void kampBeenden() {
		int ans = JOptionPane.showConfirmDialog(this,"Are you sure?"); //confirmation Nachricht
		if(ans == JOptionPane.YES_OPTION) 
			dispose();
	}

	private void startDerKamp() {
		BattleDialog bd = new BattleDialog(statTable.getAliveGlads());
		bd.setResizable(false);
         
		if(bd.input[0] == "OK") {
			textArea.setForeground(Color.BLUE);
			textArea.setText(statTable.battle(bd.input[1],bd.input[2]));
			table.setModel(createTableModel()); 
		}else
			{textArea.setForeground(Color.RED);textArea.setText("Kampf wurde abgebrochen");}
	}

	private void deleteGladiator() {
		if(statTable.getNum_glads() > 0) {
		Object names[] =new String[statTable.getNum_glads()];
		for(int i=0;i<statTable.getNum_glads();i++) {
			names[i] = statTable.getGlads()[i].getName();
		}
		String selectedValue = (String)JOptionPane.showInputDialog(this,
		"Choose one", "delete gladiator",
		JOptionPane.QUESTION_MESSAGE, null,
		names, names[0]);
		if(selectedValue != null) {
			int ans = JOptionPane.showConfirmDialog(this,"Sind Sie sicher, dass Sie "+selectedValue+" löschen möchten ?"); //confirmation massage
			if(ans == JOptionPane.YES_OPTION) 
			{   
				statTable.delete(selectedValue);
				table.setModel(createTableModel()); 
			}
		}
		}else
		JOptionPane.showMessageDialog(rootPane , "Tabelle ist leer", "Warnung", JOptionPane.WARNING_MESSAGE);
	}

	private void addGladiator() {
		String name = "";
		name = JOptionPane.showInputDialog(this, "Enter Gladiator Name to Add");
		//check if user chose cancel
		if(name != null)
		{
			//check for invalid input
			if(name.strip().equals(""))
				JOptionPane.showMessageDialog(this,"Name ist erforderlich.","Achtung",JOptionPane.WARNING_MESSAGE);   
			else if(statTable.find(name) == null)
			{
				GladStat gl = new GladStat(name); //initialize new gladiator
				statTable.add(gl);
				table.setModel(createTableModel());
			}
			else
				JOptionPane.showMessageDialog(this,"Gladiator existiert schon .","Achtung",JOptionPane.WARNING_MESSAGE);
		}
		else
			System.err.println("Das Hinzufügen eines neuen Gladiators wurde abgebrochen.");	//wenn der Benutzer auf cancel klickt, wird dies auf Konsole gedruckt
	}

	private void saveTableToFile() {
		JFileChooser fc = new JFileChooser();    
		    int i = fc.showOpenDialog(this);    
		    //if the user click open load data otherwise do nothing
		    if(i == JFileChooser.APPROVE_OPTION)
		    {    
		        File file = fc.getSelectedFile();    
		        String filepath = file.getPath();             
				statTable.saveToFile(filepath);
			   JOptionPane.showMessageDialog(this,"Erfolgreich gespeichert.");   //zeige to user diese Nachricht
		    }
	}

	private void loadTableFromFile() {
		JFileChooser fc = new JFileChooser();   
		    int i = fc.showOpenDialog(this);    
		    //
		    if(i == JFileChooser.APPROVE_OPTION)
		    {    
		        File file = fc.getSelectedFile();    
		        String filepath = file.getPath();             
				statTable.loadFile(filepath);
				table.setModel(createTableModel());		//create table with its new data
				JOptionPane.showMessageDialog(this,"Erfolgreich geladen.");   //zeige to user diese Nachricht
		    }
	}
    
}
