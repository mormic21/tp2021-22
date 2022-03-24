package net.tfobz.tunnel.client;

/**
 * An ihm kann ein F�hrer angefordert aber auch ein solcher zur�ckgegeben 
 * werden. Dieser muss eine Referenz auf ClientForm haben, damit die 
 * Statusmeldungen dort angezeigt werden k�nnen
 */
public class GuidesMonitor {
	
	/**
	 * Maximalanzahl der am Eingang vorhanden F�hrer
	 */
	protected final int MAX_GUIDES = 4;
	
	/**
	 * Anzahl der momentan verf�gbaren F�hrer
	 */
	protected int availableGuides = MAX_GUIDES;
	
	/**
	 * Referenz auf das ClientForm um Statustexte auszugeben
	 */
	protected ClientForm clientForm = null;
	
	/**
	 * Konstruktor, dem eine Referenz auf das ClientForm �bergeben wird
	 * @param clientForm
	 */
	public GuidesMonitor(ClientForm clientForm) {
		this.clientForm = clientForm;
	}
	
	/**
	 * Ein F�hrer wird angefordert, gleichzeitig werden die Statusmeldungen im
	 * ClientForm ausgegeben und die Benutzerschnittstelle angepasst
	 */
	public synchronized void request() {
		//Statusmeldungen im ClientForm
		clientForm.status_txtarea.append("Guide requested...\n");
		//wartet solange bis min. 1 guide vorhanden ist
		while (availableGuides - 1 < 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (availableGuides - 1 >= 0) {
			availableGuides--;
			//Statusmeldungen im ClientForm
			clientForm.status_txtarea.append("Guide reserved. "+getGuidesString(getAvailableGuides())+" now available\n");
			updateClientform();
		}
		
	}
	
	/**
	 * F�hrer wird bei Beendigung einer F�hrung zur�ck gegeben. Statusmeldungen 
	 * werden ausgegeben und die Benutzerschnittstelle angepasst
	 */
	public synchronized void release() {
		if (availableGuides + 1 <= 4) {
			availableGuides++;
			//Statusmeldungen im ClientForm
			clientForm.status_txtarea.append("Guide released. "+getGuidesString(getAvailableGuides())+" now available\n");
			updateClientform();
			notifyAll();
		}
	}

	/**
	 * Die Anzahl der momentan verf�gbaren F�hrer wird zur�ck geliefert
	 * @return Anzahl der momentan verf�gbaren F�hrer
	 */
	public synchronized int getAvailableGuides() {
		return availableGuides;
	}
	
	/**
	 * updates the clientform
	 * updates the avaiable guides label
	 */
	private void updateClientform() {
		String labeltext = clientForm.guides_label.getText();
		String []texte = labeltext.split(" ");
		String newtext = texte[0] + " " + this.getAvailableGuides();
		clientForm.guides_label.setText(newtext);
	}
	
	/**
	 * getGuidesString
	 * gibt Einzahl oder Mehrzahl zurueck
	 * @param numberOfGuides
	 * @return "guide" or "guides"
	 */
	private String getGuidesString(int numberOfGuides) {
		numberOfGuides = Math.abs(numberOfGuides);
		String ret = String.valueOf(numberOfGuides) + " guide";
		if (numberOfGuides != 1) {
			ret = ret + "s";
		}
		return ret;
	}
}