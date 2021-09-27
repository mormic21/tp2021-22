package net.tfobz.relationship;
import org.junit.jupiter.api.*;
import net.tfobz.relationship.Person.Gender;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;


public class PersonTest {
	private static Person p;
	private static Person p1;
	
	@Test
	public void creation() {
		p = new Person("Sepp", Gender.MALE);
		assertEquals("Sepp", p.getName());
		assertEquals(Gender.MALE, p.getGender());
	}
	
	@Test
	public void nameGenderEmpty() {
		p = new Person("Sepp", Gender.MALE);
		assertThrows(IllegalArgumentException.class, () -> {
			p.setName(null);
			p.setGender(null);
			p.setName("");
		}); 	
	}
	
	@Test
	public void nameEmpty() throws Exception {
		p = new Person("Sepp", Gender.MALE);
		assertThrows(IllegalArgumentException.class, () -> {
			p.setName(null);
			p.setName("");
		});
	}
	
	@Test
	public void genderEmpty() throws Exception {
		p = new Person("Sepp", Gender.MALE);
		assertThrows(IllegalArgumentException.class, () -> {
			p.setGender(null);
		});
		
	}
	
	@Test
	public void creationNameGenderEmpty() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			p1 = new Person(null, null);
		});
		
	}
	
	@Test
	public void creationNameEmpty() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			p1 = new Person("", null);
		});
		
	}
	
	@Test
	public void creationGenderEmpty() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			p1 = new Person("Sepp", null);
		});
		
	}
	
	@Test
	public void equals() {
		p = new Person("Sepp", Gender.MALE);
		p1 = new Person("Sepp", Gender.MALE);
		assertTrue(p.equals(p1));
	}
	
	@Test
	public void equalsNull() {
		p = new Person("Sepp", Gender.MALE);
		p1 = null;
		assertThrows(IllegalArgumentException.class, () -> {
			assertTrue(p.equals(p1));
		});
	}
	
	@Test
	public void parent() {
		p = new Person("Sepp", Gender.MALE);
		Person m = new Person("m", Gender.FEMALE);
		p.setMother(m);
		assertEquals("m", p.getMother().getName());
		assertEquals(Gender.FEMALE, p.getMother().getGender());
		p.setMother(null);
		assertEquals(null, p.getMother());
		//vater
		Person v = new Person("v", Gender.MALE);
		p.setFather(v);
		assertEquals("v", p.getFather().getName());
		assertEquals(Gender.MALE, p.getFather().getGender());
		p.setFather(null);
		assertEquals(null, p.getFather());
	}
}
