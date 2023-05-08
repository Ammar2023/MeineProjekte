
import java.io.BufferedWriter; 
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class KampfStatTabelle {

	private GladStat glads[];
	private int num_glads;
	private int capacity;
	
	
	public KampfStatTabelle() {
		this.capacity = 20;
		this.num_glads = 0;
		this.glads =new GladStat[this.capacity];
	}
	
	//Konstruktor, der ein Array von GladStat und dessen Größe verwendet
	public KampfStatTabelle(GladStat[] myGlads, int size) {
		this.capacity = 20;
		while(size>capacity)
			capacity*=2;// verdoppeln 
		this.num_glads = size;					//speichern array size
		this.glads =new GladStat[capacity];		// array Instanziieren 
		
		//initialize the array
		for(int i=0; i<size; i++)
			this.glads[i] = new GladStat(myGlads[i]);
		
		sort(); 	// sort the array
	}
	
	
	public GladStat[] getGlads() {
		return glads;
	}
	
	//sort the array
	public void sort() {
		GladStat temp;			
		for(int i=0; i<num_glads; i++)
			for(int j=i+1; j<num_glads; j++)
				if(this.glads[i].compareTo(this.glads[j]) < 0)	//Austausch von objects
					{
						temp = new GladStat(this.glads[i]);
						this.glads[i] = new GladStat(this.glads[j]);
						this.glads[j] = new GladStat(temp);
					}
	}
	
	//  füge neues objekt in GladStat array ein
	public void add(GladStat gl) {
		if(num_glads + 1 > capacity)		//check capacity
		{
			capacity*=2;					//double the capacity
			GladStat newGlads[] = new GladStat[capacity];	//deklariere  größeres Array mit der neuen Kapazität	
		
			// kopiere die Objekten aus einem kleineren Array in das größere Array
			for(int i=0;i<num_glads;i++)
				newGlads[i] = glads[i];
			glads = newGlads;
		}
		if(find(gl.getName()) == null)		
		{
			glads[num_glads] = new GladStat(gl);	//add the new object 
			num_glads++;
			sort(); 	//resort the array 
		}
		else
			System.err.println("Error <doppelte Datensätze>:  gladitor mit dem selben Name existiert schon.. gib anderen name\n");
	}
	
	// lösche ein vorhandenes Objekt und sortiere dann das Array
	public void delete(String name) {
		for(int i=0; i<num_glads; i++)
			if(glads[i].getName().equals(name))
				 {
					//verschiebe die objects 
					for(int j=i; j< num_glads ; j++)
						glads[j] = glads[j+1];
					
					num_glads--;	//Verringere die Anzahl der GladStat um 1
					glads[num_glads] = null;	
				 }
	}
	
	/**
	 * Suche und gib  das Objekt im GladStat-Array zurück 
	 * @param name 
	 * @return  gib NULL zurück, wenn das Objekt nicht gefunden wird
	 */
	public GladStat find(String name) {
		for(int i=0; i<num_glads; i++)
			if(glads[i].getName().equals(name))
				return glads[i];
		
		return null;
	}
	
	 
	public void reset() {
		for(int i=0; i< num_glads; i++)
			glads[i] = new GladStat(glads[i].getName());
	}
	
	
	//Kampf zwischen 2 GladStat
	public String battle(String g1, String g2) {
		int result = (int) Math.floor(Math.random()*4 + 1);		//{1,2} bedeutet g1 gewinnt und g2 verliert, 1 oder 3 bedeutet der Verlierer lebt noch
		
		//check if gladiators exist
		if(find(g1) == null )
		{
			return("Error: Gladiatior <"+ g1 +"> nicht gefunden..\n");		
			
		} else if(find(g2) == null)
		{
			return("Error: Gladiatior <" + g2 +"> nicht gefunden..\n");
		}
				
		GladStat winner ;
		GladStat looser ;
		// entscheiden wer hat gewonnen und wer hat verloeren 
		if(result <= 2) 
		{
			winner = find(g1);
			looser = find(g2);
		}	
		else
		{
			winner = find(g2);
			looser = find(g1);
		}
		int looser_status = result%2;
		// überprüfe ob beide Gladiatoren nicht gestorben sind
		if(winner.isAlive() && looser.isAlive()) 
		{
			winner.setNum_fights(winner.getNum_fights()+1);
			winner.setWins(winner.getWins()+1);
			winner.setWin_rate((float)winner.getWins()/winner.getLoss());
			looser.setNum_fights(looser.getNum_fights()+1);
			if(looser_status == 0)
				looser.setAlive(false);
			looser.setLoss(looser.getLoss()+1);
			looser.setWin_rate((float)looser.getWins()/looser.getLoss());
			
			sort();
			return("*********************************\nKampf:\t"+g1 +"__VS__"+ g2  + "\n\n\tWinner ==> " + winner.getName()+ "\n\tLooser ==> " +looser.getName()+((looser_status==0)?"  [Dead]":"  [Alive]")+ " \n*********************************\n");

		}
		else
			return("Error: Beide Gladiatoren müssen am Leben sein, um kämpfen zu können..\n");

	}
	
	//Speichern  die Daten von KampfStatTabelle in einem bestimmten Pfad / einer bestimmten Datei
	
	public void saveToFile(String path){
	
		BufferedWriter bw = null;
		File file = new File(path);
		try
		{
			bw = new BufferedWriter(new FileWriter (file,false));
			bw.write(toString());
			bw.close();
			System.out.println("*Daten wurden gespeichert in : \""+path+"\"");
		}
		catch (IOException e)
		{
			System.err.println("Error: Daten konnten nicht gespeichert werden");
		}
	}
	
	//Eine Methode in der ein Pfad zu einer Datei übergeben wird und eine KampfStatTabelle mit
	//den Daten aus der Datei geladen wird.
	
	public void loadFile(String path){
		
		this.glads = new GladStat[capacity];	//aktuelle Tabelle löschen
		this.num_glads = 0;		//Anzahl der Gladiatoren auf Null zurücksetzen
		BufferedReader br = null;
		File file = new File(path);
		try
		{
			br = new BufferedReader(new FileReader(file));
			
			br.readLine();		
			br.readLine();		
			String line =null;
			while ((line = br.readLine()) != null)
			{
				String[] data = line.replace(" ", "").replace("\t", "").split("[|]");	
				add(new GladStat(data[0], Integer.parseInt(data[2]), Integer.parseInt(data[3]),Boolean.parseBoolean((data[5].equals("alive"))?"true":"false")));
			}
			br.close();
			System.out.println("*Daten wurden geladen von: \""+path+"\"");
		}
		catch (IOException e)
		{
			System.err.println("Error: Daten konnten nicht geladen werden");
		}
	}
	
	
	//print all objects 
	public String toString() {
		String s="Gladiator\t| Kämpfe\t  | Siege\t  | Niederlagen\t  | Siegquote\t   | Status\r\n" + 
				"-------------------------------------------------------------------------------------------------\n";
		for(int i=0;i<num_glads;i++)
			s+=glads[i].toString() + "\n";
		return s;
	}

	public int getNum_glads() {
		// TODO Auto-generated method stub
		return num_glads;
	}

	public GladStat[] getAliveGlads() {
		GladStat[] temp = new GladStat[num_glads];
		int i=0;
		for(GladStat g:glads)
		{
			if(g!= null && g.isAlive())
			{temp[i] = g;i++;}
		}
		GladStat[] alive = new GladStat[i];
		for(int j=0;j<i;j++)
			alive[j] = temp[j];
		
		return alive;
	} 
	
}
