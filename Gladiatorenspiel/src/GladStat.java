/**
 * die Klasse GladStat repräsentiert die Statistikendaten zu einem Gladiator
 * @author Ammar
 * */

public class GladStat {
	
	/**
	 * name : name des Gladiatores 
	 * wins : Anzahl die Gewinne 
	 * loss : Anzahl Niederlagen
	 * alive : Status ob er lebt oder tot
	 * win_rate: Siegquote
	 */
	private String name;
	private int num_fights;
	private int	wins;
	private int loss;
	private boolean alive;
	private float win_rate;
	
	 
	public GladStat(String name, int wins, int loss, boolean alive) {
		this.name = name;
		this.num_fights = wins + loss;
		this.wins = wins;
		this.loss = loss;
		this.win_rate = (float)wins / loss;
		this.alive = alive;
	}
	
	 
		public GladStat(String name) {
			this.name = name;
			this.num_fights = 0;
			this.wins = 0;
			this.loss = 0;
			this.win_rate = 0;
			this.alive = true;
		}
	
	/**
	 *  Konstruktor zum Klonen der Daten von einem vorhandenen Objekt
	 * @param gl IN: übergebene Objekt 
	 */
	public GladStat(GladStat gl) {
		this.name = gl.name;
		this.num_fights = gl.num_fights; 
		this.wins = gl.wins;
		this.loss = gl.loss;
		this.win_rate = gl.win_rate ;
		this.alive = gl.alive;
	}
	
	/**
	 * gibt die Anzahl der Kämpfe zurück
	 * @return die Azahl der Kämpfe 
	 */
	public int getNum_fights() {
		return num_fights;
	}
    /**
     * setzt die num_fights 
     * @param num_fights IN: übergebene Anzahl der Kämpfe
     */
	public void setNum_fights(int num_fights) {
		this.num_fights = num_fights;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLoss() {
		return loss;
	}

	public void setLoss(int loss) {
		this.loss = loss;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void setWin_rate(float win_rate) {
		this.win_rate = win_rate;
	}

	public String getName() {
		return name;
	}

	
	/**
	 * die Statistikdaten von zwei Gladiatoren vergleichen
	 * @param gl
	 * @return : das Vergleichergebnis 
	 */
	public int compareTo(GladStat gl) {
		int result = 0;		//Vergleichsergebnis speichern 
		if(wins > gl.wins)
			result = 1;
		else if (wins < gl.wins)
			result = -1;
		else if(win_rate > gl.win_rate)
			result = 1;
		else if (win_rate < gl.win_rate)
			result = -1;
		else if (alive && !gl.alive)
			result = 1;
		else if (!alive && gl.alive)
			result = -1;
		
		else result = name.compareToIgnoreCase(gl.name) * -1;
		// die Methode compareToIgnoreCase vergleicht zwei Zeichenketten und ignoriert Unterschiede zwichen Klein und Großbuchstaben
		
		return result;
	}
	
	public String toString() {
		String life = (this.alive? "alive":"dead");// Conditional Operator 
		return String.format(" %-10s\t|  %d\t\t  |  %d\t\t  |  %d\t\t  |  %.2f\t   |  %s", name, num_fights,
				wins, loss, win_rate, life);
		//https://www.javatpoint.com/java-string-format
	}

	public float getWin_rate() {
		return win_rate;
	}

	
}
