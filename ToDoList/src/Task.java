/**
 * Die Klasse Task repräsentiert eine Aufgabe mit einer eindeutigen ID, einer
 * Beschreibung und einem Status, ob sie abgeschlossen wurde. Es wird benötigt,
 * um die Daten, die aus der Datenbank abgerufen werden, in ein
 * objektorientiertes Format zu bringen
 * 
 * @author Ammar
 *
 */

public class Task {
	private  int id;
	private String description;
	private boolean isComplete;


	public Task(int id, String description, boolean isComplete) {
		this.id = id;
		this.description = description;
		this.isComplete = isComplete;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
	public void setDescription(String description) {
		this.description=description;
	}
	@Override
	public String toString() {
		String status= isComplete ? "[x]" : "[ ]";
		return String.format("%s%s",status,description);
			
	}
}
