package net.tfobz.relationship;
import java.util.ArrayList;
/**
 * Realisiert ein Objekt vom Typ Person
 * @author Michael Morandell
 *
 */
public class Person {
	//Membervariablen
	public enum Gender { FEMALE, MALE };
	protected Gender gender = null;
	protected String name = null;
	protected Person mother = null;
	protected Person father = null;
	protected ArrayList<Person> children = new ArrayList();
	
	/**
	 * Person-Konstruktor
	 * @param name
	 * @param gender
	 */
	public Person(String name, Gender gender) {
		//Wenn Name null ist
		if (name == null) {
			throw new IllegalArgumentException("Name ist NULL");
		}
		else {
			//Wenn Name empty ist
			if (name.length() == 0) {
				throw new IllegalArgumentException("Name muss mindestens 1 Zeichen beinhalten");
			}
			else {
				//Wenn gender null ist
				if (gender == null) {
					throw new IllegalArgumentException("Gender ist null");
				}
			}
		}
		//setzen der eigenschaften
		this.name = name;
		this.gender = gender;
	}
	
	/**
	 * getGender
	 * @return gender, vom Typ Gender
	 */
	public Gender getGender() {
		return gender;
	}
	
	/**
	 * setGender - setzt Gender
	 * @param gender
	 */
	public void setGender(Gender gender) {
		//wenn übergebendes Gender null ist
		if (gender == null) {
			throw new IllegalArgumentException("Gender ist null");
		}
		this.gender = gender;
	}
	
	/**
	 * getName
	 * @return name, als String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * setName - setzt den Namen
	 * @param name
	 */
	public void setName(String name) {
		//Wenn name null ist
		if (name == null) {
			throw new IllegalArgumentException("Name ist NULL");
		}
		else {
			//Wenn name empty ist
			if (name.length() == 0) {
				throw new IllegalArgumentException("Name muss mindestens 1 Zeichen beinhalten");
			}
		}
		this.name = name;
	}
	
	/**
	 * getMother
	 * @return mother, vom Typ Person
	 */
	public Person getMother() {
		return mother;
	}
	
	/**
	 * setMother
	 * @param mother
	 */
	public void setMother(Person mother) {
		//Wenn mother null ist
		if (mother == null) {
			this.mother.children.remove(this);
			this.mother = null;
		}
		else {
			//Wenn mother gleich der Person ist
			if (mother.equals(this)) {
				throw new IllegalArgumentException("Person kann nicht Mutter von sich selbst sein!");
			}
			//Wenn mother Nachkomme von sich selber ist
			if (this.getDescendants().contains(mother)) {
				throw new IllegalArgumentException("Mutter kann nicht Nachkomme der Person sein!");
			}
			if (!mother.children.contains(this) || !this.mother.children.contains(this)) {
				//Wenn mutter weiblich ist
				if (mother.getGender().equals(Gender.FEMALE)) {
					if (this.mother != null) {
						this.mother.children.remove(this);
					}
					this.mother = mother;
					this.mother.children.add(this);
				}
				else {
					throw new IllegalArgumentException("Mutter hat falsches Geschlecht!");
				}
			}
		}
	}
	
	/**
	 * getFather
	 * @return father, vom Typ Person
	 */
	public Person getFather() {
		return father;
	}
	
	/**
	 * setFather
	 * @param father, vom Typ Person
	 */
	public void setFather(Person father) {
		//wenn father null ist
		if (father == null) {
			this.father.children.remove(this);
			this.father = null;
		}
		else {
			//Wenn Person Vater von sich selbst ist
			if (father.equals(this)) {
				throw new IllegalArgumentException("Person kann nicht Vater von sich selbst sein!");
			}
			//Wenn Person Nachkomme von sich selbst ist
			if (this.getDescendants().contains(father)) {
				throw new IllegalArgumentException("Vater kann nicht Nachkomme der Person sein!");
			}
			if (!father.children.contains(this) || !this.father.children.contains(this)) {
				//Wenn Vater männlich ist
				if (father.getGender().equals(Gender.MALE)) {
					//Wenn father nicht gesetzt ist
					if (this.father != null) {
						this.father.children.remove(this);
					}
					this.father = father;
					this.father.children.add(this);
				}
				else {
					throw new IllegalArgumentException("Vater hat falsches Geschlecht!");
				}
			}
		}
	}
	
	/**
	 * getChildren
	 * @return children, vom Typ ArrayList
	 */
	public ArrayList<Person> getChildren() {
		return children;
	}
	
	/**
	 * Equals - Untersucht 2 Personen auf Gleichheit
	 * @param Object o, das zu vergelichende Object
	 * @return true, wenn Personen gleich sind
	 */
	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		//Wenn Object null ist
		if (o == null) {
			throw new IllegalArgumentException("Vergleich mit NULL nicht möglich!");
		}
		//Wenn Object eine Person ist
		if (o instanceof Person) {
			Person other = (Person)o;
			//Wenn Eigenschaften identisch sind
			if (this.getName().equals(other.getName()) && this.getGender().equals(other.gender)) {
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * getDaughters
	 * @return alle Toechter einer Person, als ArrayList
	 */
	public ArrayList<Person> getDaughters() {
		ArrayList<Person> ret = new ArrayList<Person>();
		//Fuer alle children...
		for(int i = 0; i < this.getChildren().size(); i++) {
			//...die weiblich sind
			if (this.getChildren().get(i).getGender() == Gender.FEMALE) {
				ret.add(this.getChildren().get(i));
			}
		}
		return ret;
	}
	
	/**
	 * getSons
	 * @return als Soehne einer Person, als ArrayList
	 */
	public ArrayList<Person> getSons() {
		ArrayList<Person> ret = new ArrayList<Person>();
		//Fuer alle children
		for(int i = 0; i < this.getChildren().size(); i++) {
			//...die maennlich sind
			if (this.getChildren().get(i).getGender() == Gender.MALE) {
				ret.add(this.getChildren().get(i));
			}
		}
		return ret;
	}
	
	/**
	 * getSisters
	 * @return alle Schwestern einer Person, als ArrayList
	 */
	public ArrayList<Person> getSisters() {
		ArrayList<Person> ret = new ArrayList<Person>();
		//Fuer alle Toechter
		for(int i = 0; i < this.mother.getDaughters().size(); i++) {
			//Wenn Mutter und Vater gesetzt sind
			if (this.father != null && this.mother != null && this.mother.getSons().get(i).getFather() != null) {
				if (this.mother.getDaughters().get(i).getFather().equals(this.father) && 
						!this.mother.getDaughters().get(i).equals(this)) {
					ret.add(this.mother.getDaughters().get(i));
				}
			}
		}
		return ret;
	}
	
	/**
	 * getBrothers
	 * @return alle Brueder einer Person, als ArrayList
	 */
	public ArrayList<Person> getBrothers() {
		ArrayList<Person> ret = new ArrayList<Person>();
		//Fuer alle Soehne
		for(int i = 0; i < this.mother.getSons().size(); i++) {
			//Wenn Mutter und Vater gesetzt sind
			if (this.father != null && this.mother != null && this.mother.getSons().get(i).getFather() != null) {
				if (this.mother.getSons().get(i).getFather().equals(this.father) &&
						!this.mother.getSons().get(i).equals(this)) {
					ret.add(this.mother.getSons().get(i));
				}
			}
		}
		return ret;
	}
	
	/**
	 * getDescendants
	 * Solange es noch children gibt, wird diese Methode rekursiv wiederholt
	 * @return alle Nachkommen einer Person, als ArrayList
	 */
	public ArrayList<Person> getDescendants() {
		ArrayList<Person> ret = new ArrayList<Person>();
		//Fuer alle children einer Person
		for(int i = 0; i < this.children.size(); i++) {
			//Solange sie noch nicht geaddet wurden
			if (!ret.contains(this.children.get(i))) {
				//get das Kindeskind
				ret.add(this.children.get(i));
				//add alle gefunden children
				ret.addAll(this.children.get(i).getDescendants());
			}
		}
		return ret;
	}
}
