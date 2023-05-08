

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub -- try catch
		GladStat glads[] = new GladStat[6]; 
		
		glads[0] = new GladStat("Marcos", 5, 2,true);
		glads[1] = new GladStat("Martin", 5, 3,false);
		glads[2] = new GladStat("Olaf", 8, 1,false);
		glads[3] = new GladStat("Gack", 8, 1,true);
		glads[4] = new GladStat("Drusus", 7, 2,true);
		glads[5] = new GladStat("Spartakus", 1, 3,false);
		
		KampfStatTabelle table = new KampfStatTabelle(glads,6);
		System.out.println(table);
	
	
		
		
	GUI game = new GUI(table);
	game.setDefaultCloseOperation(GUI.EXIT_ON_CLOSE);
		

		
		
		
		
		

	}

}
