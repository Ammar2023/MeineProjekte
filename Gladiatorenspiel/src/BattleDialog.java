
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BattleDialog extends JDialog implements ActionListener  {

	JLabel jl1 ;//private 
	JLabel jl2;
	JLabel jf1 ;
	JLabel jf2 ;
	JComboBox<String> g1;
	JComboBox<String> g2;
	JButton btnY ;
	JButton btnN ;
	JDialog diag ;
    Container cd ;
    String[] input;
	public BattleDialog(GladStat[]gladNames ) {
		input = new String[3];
		
		String names[] =new String[gladNames.length];
		int i=0;
		for(GladStat g:gladNames) {
			names[i] = g.getName();i++;
		}
		 jl1 = new JLabel("Wähle zwei verschiedene Kämpfer aus!");
		 jl2 = new JLabel("");
		 jf1 = new JLabel("Erster Kämpfer");
		 jf2 = new JLabel("Zweiter Kämpfer ");
		 g1= new JComboBox<String>(names);
		 g2= new JComboBox<String>(names);
		 btnY = new JButton("Beginne den Kampf");
		 btnN = new JButton("Cancel");
		
		
		//create a JDialog and add JOptionPane to it 
		 
         diag = new JDialog(this,"Kampf",Dialog.ModalityType.APPLICATION_MODAL);
         cd = diag.getContentPane();
        cd.setLayout(new GridLayout(4, 2, 20, 20));
        
        jl1.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        jl2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        jf1.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        jf2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        
        btnY.addActionListener(this);
        btnN.addActionListener(this);
        
        //add comboboxes to JOptionPane			    		
        cd.add(jl1);
        cd.add(jl2);
        cd.add(jf1);
        cd.add(g1);
        cd.add(jf2);
        cd.add(g2);
        cd.add(btnY);
        cd.add(btnN);
        diag.setLocation(500, 300);
        diag.setSize(500,250);
        diag.setVisible(true);
        diag.setResizable(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnY) {
			input[0] = "OK";
			input[1] = (String)g1.getSelectedItem();
			input[2] = (String)g2.getSelectedItem();
			
			// überprüfe ob der Benutzer in beiden como boxes den selben Kämpfer ausgewählt hat
			if(!input[1].equals(input[2]))
				this.dispose();
			else
				JOptionPane.showMessageDialog(rootPane , "Bitte wählen Sie verschiedene Kämpfer", "Achtung", JOptionPane.WARNING_MESSAGE);
		}
		if(e.getSource() == btnN) {
			input[0] = "CANCEL";
			this.dispose();
		}
	}
}


