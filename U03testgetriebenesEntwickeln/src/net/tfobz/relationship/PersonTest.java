package net.tfobz.relationship;
import org.junit.jupiter.api.*;
import net.tfobz.relationship.Person.Gender;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;


public class PersonTest {
	private Person p = null;
	
	@Test
	public void creation() throws Exception {
		p = new Person("Sepp", Gender.MALE);
		assertEquals("Sepp", p.getName());
		assertEquals(Gender.MALE, p.getGender());
	}
	
	@Test
	public void nameGenderEmpty() throws Exception {
		p.setName(null);
		p.setGender(null);
		p.setName("");
	}
	
	@Test
	public void nameEmpty() throws Exception {
		p.setName("");
		p.setName(null);
	}
	
	@Test
	public void genderEmpty() throws Exception {
		p.setGender(null);
	}
}
