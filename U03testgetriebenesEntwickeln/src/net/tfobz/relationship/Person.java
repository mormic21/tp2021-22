package net.tfobz.relationship;
import java.util.ArrayList;

public class Person
{
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
		if (name == null) {
			throw new IllegalArgumentException("Name ist NULL");
		}
		else {
			if (name.length() == 0) {
				throw new IllegalArgumentException("Name muss mindestens 1 Zeichen beinhalten");
			}
			else {
				if (gender == null) {
					throw new IllegalArgumentException("Gender ist null");
				}
			}
		}
		this.name = name;
		this.gender = gender;
	}

	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		if (gender == null) {
			throw new IllegalArgumentException("Gender ist null");
		}
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * setName
	 * @param name
	 */
	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name ist NULL");
		}
		else {
			if (name.length() == 0) {
				throw new IllegalArgumentException("Name muss mindestens 1 Zeichen beinhalten");
			}
		}
		this.name = name;
	}
	
	public Person getMother() {
		return mother;
	}
	
	public void setMother(Person mother) {
		if (mother == null) {
			this.mother = null;
		}
		else {
			if (mother.getGender().equals(Gender.FEMALE)) {
				this.mother = mother;
			}
			else {
				throw new IllegalArgumentException("Mutter hat falsches Geschlecht!");
			}
		}
	}
	
	public Person getFather() {
		return father;
	}
	
	public void setFather(Person father) {
		if (father == null) {
			this.father = null;
		}
		else {
			if (father.getGender().equals(Gender.MALE)) {
				this.father = father;
			}
			else {
				throw new IllegalArgumentException("Father hat falsches Geschlecht!");
			}
		}
	}
	
	public ArrayList<Person> getChildren() {
		return children;
	}
	
	/**
	 * Equals
	 */
	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		if (o == null) {
			throw new IllegalArgumentException("Vergleich mit NULL nicht möglich!");
		}
		if (o instanceof Person) {
			Person other = (Person)o;
			if (this.getName().equals(other.getName()) && this.getGender().equals(other.gender)) {
				ret = true;
			}
		}
		return ret;
	}
}
