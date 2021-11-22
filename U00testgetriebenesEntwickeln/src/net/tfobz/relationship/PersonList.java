package net.tfobz.relationship;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import net.tfobz.relationship.Person.Gender;

/**
 * PersonList, abgeleitet von ArrayList<Person>
 * Realisiert eine Liste von Personen + 
 * korrektes Einlesen von Strings und Dateien
 * @author Michael Morandell
 *
 */
public class PersonList extends ArrayList<Person> {
	/**
	 * Default-Constructor
	 */
	public PersonList() {
	}
	
	/**
	 * PersonList-Constructor
	 * @param reader, Buffered Reader
	 * @throws IOException
	 */
	public PersonList(BufferedReader reader) throws IOException {
		//readPersons
		readPersons(reader);
	}
	
	/**
	 * PersonList-Constructor
	 * @param filename, pfad/dateiname der datei
	 * @throws FileNotFoundException, wenn datei nicht gefunden bzw. nicht geoeffnet werden kann
	 * @throws IOException
	 */
	public PersonList(String filename) throws FileNotFoundException, IOException {
		FileInputStream in = new FileInputStream(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		//readPersons
		readPersons(reader);
	}
	
	/**
	 * readPersons
	 * Liest eine Datei oder einen String über den BufferedReader und analysiert die Daten
	 * Bei erkennen von ungueltigen Angaben, wird ein entsprechender Fehler geworfen
	 * @param reader, BufferedReader
	 * @throws IOException
	 */
	private void readPersons(BufferedReader reader) throws IOException {
		String zeile = "";
		ArrayList<String> persons = new ArrayList<String>();
		//Alle Zeilen werden in eine String-ArrayList zwischengespeichert
		while(true) {
			//Solange noch zeilen vorhanden sind
			if ((zeile = reader.readLine()) != null) {
				//add zeile zur ArrayList
				persons.add(zeile);
			}
			else {
				//abbruch am Ende
				break;
			}	
		}
		//Wenn keine Personen eingelesen werden konnten
		if (persons.size() == 0) {
			throw new IllegalArgumentException("Aus der Datei konnte keine Person gelesen werden");
		}
		//Für j in persons
		for (int j = 0; j < persons.size(); j++) {
			//holt String aus der ArrayList
			String line = persons.get(j).trim();
			//Wenn String nicht empty ist
			if(!line.isEmpty()) {
				//String wird auf die verschiedenen Eigenschaften aufgeteilt und in einem StringArray zwischengespeichert
				String[] components = line.split(";");
				//Wenn genau 4 Eigenschaften der Person gesetzt sind
				if(components.length == 4) {
					//Wenn eine Eigenschaft empty ist
					if (components[0].isEmpty() || components[1].isEmpty() || components[2].isEmpty() || components[3].isEmpty()) {
						throw new IllegalArgumentException("Eine Eigenschaft von: "+line+" ist nicht gesetzt!");
					}
					//Wenn name null ist
					if (components[0].equals("null")) {
						throw new IllegalArgumentException("Name von:"+line+" ist null!");
					}
					//Wenn Gender gesetzt ist
					if (components[1].equals(Gender.MALE.toString()) || components[1].equals(Gender.FEMALE.toString())) {
						//Person wird aus dem String erstellt
						Person p = new Person(components[0], Gender.valueOf(components[1]));
						//Wenn Mutter nicht null ist
						if (!components[2].equals("null")) {
							Person mother = null;
							//Es wird nach der Mutter im Datenbestand gesucht
							for (int i = 0; i < this.size(); i++) {
								if (this.get(i).getName().equals(components[2])) {
									mother = this.get(i);
									break;
								}
							}
							//Wenn Mutter im Datenbestand nicht exsistert
							if (mother == null) {
								throw new IllegalArgumentException("Mutter von: "+line+" konnte nicht korrekt gesetzt werden!");
							}
							else {
								//Mutter wird gesetzt
								p.setMother(mother);
							}
						}
						//Wenn Vater gesetzt ist
						if (!components[3].equals("null")) {
							Person father = null;
							//Es wird nach dem Vater im Datenbestand gesucht
							for (int i = 0; i < this.size(); i++) {
								if (this.get(i).getName().equals(components[3])) {
									father = this.get(i);
									break;
								}
							}
							//Wenn Vater im Datenbestand nicht exsistert
							if (father == null) {
								throw new IllegalArgumentException("Vater von: "+line+" konnte nicht korrekt gesetzt werden!");
							}
							else {
								//Vater wird gesetzt
								p.setFather(father);
							}
						}
						//Wenn Person noch nicht im Datenbestand ist
						if (!this.contains(p)) {
							//Person wird hinzugefuegt
							this.add(p);
						}
						else {
							throw new IllegalArgumentException("Person: "+line+" wurde bereits importiert!");
						}
					}
					else {
						throw new IllegalArgumentException("Person: "+line+" hat ein ungültiges Geschlecht!");
					}
				}
				else {
					throw new IllegalArgumentException("Bei Person: "+line+" fehlen Eigenschaften!");
				}
			}
			else {
				throw new IllegalArgumentException("Zeile ist Empty!");
			}
		}
	}
}

