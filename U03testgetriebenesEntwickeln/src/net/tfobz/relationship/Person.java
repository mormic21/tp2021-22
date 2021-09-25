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
	
	public Person(String name, Gender gender) {
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
	public void setName(String name) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("Name ist null oder nicht vorhanden");
		}
		this.name = name;
	}
	
	public Person getMother() {
		return mother;
	}
	public void setMother(Person mother) {
		this.mother = mother;
	}
	
	public Person getFather() {
		return father;
	}
	public void setFather(Person father) {
		this.father = father;
	}
	
	public ArrayList<Person> getChildren() {
		return children;
	}
}
