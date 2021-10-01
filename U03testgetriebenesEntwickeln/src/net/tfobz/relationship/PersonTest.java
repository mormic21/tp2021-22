package net.tfobz.relationship;
import org.junit.jupiter.api.*;
import net.tfobz.relationship.Person.Gender;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;


public class PersonTest {
	private static Person p;
	private static Person p1;
	
	@BeforeEach
	public void create() {
		p = new Person("Sepp", Gender.MALE);
	}
	
	@Test
	public void creation() {
		assertEquals("Sepp", p.getName());
		assertEquals(Gender.MALE, p.getGender());
	}
	
	@Test
	public void nameGenderEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			p.setName(null);
			p.setGender(null);
			p.setName("");
		}); 	
	}
	
	@Test
	public void nameEmpty() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			p.setName(null);
			p.setName("");
		});
	}
	
	@Test
	public void genderEmpty() throws Exception {
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
		p1 = new Person("Sepp", Gender.MALE);
		assertTrue(p.equals(p1));
	}
	
	@Test
	public void equalsNull() {
		p1 = null;
		assertThrows(IllegalArgumentException.class, () -> {
			assertTrue(p.equals(p1));
		});
	}
	
	@Test
	public void parent() {
		Person m = new Person("Mother", Gender.FEMALE);
		p.setMother(m);
		assertEquals("Mother", p.getMother().getName());
		p.setMother(null);
		assertEquals(null, p.getMother());
		//vater
		Person f = new Person("Father", Gender.MALE);
		p.setFather(f);
		assertEquals("Father", p.getFather().getName());
		p.setFather(null);
		assertEquals(null, p.getFather());
	}
	
	@Test
	public void parentIncorrectGender() {
		Person father = new Person("Father", Gender.FEMALE);
		Person mother = new Person("Mother", Gender.MALE);
		assertThrows(IllegalArgumentException.class, () -> {
			p.setMother(mother);
			p.setFather(father);
			assertTrue(p.equals(p1));
		});
	}
}
