package net.tfobz.relationship;
import org.junit.jupiter.api.*;
import net.tfobz.relationship.Person.Gender;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.util.ArrayList;

/**
 * PersonTest
 * Testklasse für Person.java
 * Junit 5
 * @author Michael Morandell
 * 
 */
public class PersonTest {
	//Membervariablen
	private static Person p;
	private static Person p1;
	
	/**
	 * Versucht vor jedem Test ein neues Objekt vom Typ Person zu erstellen
	 */
	@BeforeEach
	public void create() {
		p = new Person("Sepp", Gender.MALE);
	}
	
	/**
	 * Testet das korrekte Erstellen eines Objekts vom Typ Person
	 */
	@Test
	public void creation() {
		assertEquals("Sepp", p.getName());
		assertEquals(Gender.MALE, p.getGender());
	}
	
	/**
	 * Versucht den Namen null oder "" zu setzen und versucht Gender auf null zu setzen
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void nameGenderEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			p.setName(null);
			p.setGender(null);
			p.setName("");
		});
	}
	
	/**
	 * Versucht den Namen null oder "" zu setzen
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void nameEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			p.setName(null);
			p.setName("");
		});
	}
	
	/**
	 * Setzt Gender auf null
	 * Erwartet  IllegalArgumentException
	 */
	@Test
	public void genderEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			p.setGender(null);
		});

	}
	
	/**
	 * Testet den Konstruktoraufruf mit Name = null und Gender = null
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void creationNameGenderEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			p1 = new Person(null, null);
		});

	}
	
	/**
	 * Testet den Konstruktoraufruf mit Name = "" und Gender = null
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void creationNameEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			p1 = new Person("", null);
		});

	}
	
	/**
	 * Testet den Konstruktoraufruf mit Name = "Sepp" und Gender = null
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void creationGenderEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			p1 = new Person("Sepp", null);
		});

	}
	
	/**
	 * Testet ob equals-Methode 2 identische Objekte als gleich erkennt
	 */
	@Test
	public void equals() {
		p1 = new Person("Sepp", Gender.MALE);
		assertTrue(p.equals(p1));
	}
	
	/**
	 * Versucht ein Objekt mit null zu vergleichen
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void equalsNull() {
		p1 = null;
		assertThrows(IllegalArgumentException.class, () -> {
			assertTrue(p.equals(p1));
		});
	}
	
	/**
	 * Testet:
	 *  - ob Mutter und Vater richtig gesetzt werden
	 *  - ob bei Übergabe von null das jeweilige Elternteil gelöscht wird
	 */
	@Test
	public void parent() {
		//Mutter
		Person m = new Person("Mother", Gender.FEMALE);
		p.setMother(m);
		assertEquals("Mother", p.getMother().getName());
		p.setMother(null);
		assertEquals(null, p.getMother());
		//Vater
		Person f = new Person("Father", Gender.MALE);
		p.setFather(f);
		assertEquals("Father", p.getFather().getName());
		p.setFather(null);
		assertEquals(null, p.getFather());
	}
	
	/**
	 * Testet ob beim Setzen Mutter und Vater das passende Geschlecht haben
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void parentIncorrectGender() {
		Person father = new Person("Father", Gender.FEMALE);
		Person mother = new Person("Mother", Gender.MALE);
		assertThrows(IllegalArgumentException.class, () -> {
			p.setMother(mother);
			p.setFather(father);
		});
	}
	
	/**
	 * Testet ob beim Setzen von Mutter und Vater auch die Kinder richtig setzt
	 */
	@Test
	public void children() {
		//Mutter
		Person m = new Person("Mutter", Gender.FEMALE);
		p.setMother(m);
		p.setMother(m);
		//Vater
		Person f = new Person("Vater", Gender.MALE);
		p.setFather(f);
		p.setFather(f);
		assertEquals("Mutter", p.getMother().getName());
		assertEquals("Vater", p.getFather().getName());
		assertEquals("Sepp", p.getMother().getChildren().get(0).getName());
		assertEquals("Sepp", p.getFather().getChildren().get(0).getName());
		assertThrows(IndexOutOfBoundsException.class, () -> {
			assertEquals("Sepp", p.getMother().getChildren().get(1).getName());
			assertEquals("Sepp", p.getFather().getChildren().get(1).getName());
		});
		assertEquals(1, p.getMother().getChildren().size());
		assertEquals(1, p.getFather().getChildren().size());
		Person m2 = new Person("Mutter2", Gender.FEMALE);
		p.setMother(m2);
		assertEquals(0, m.getChildren().size());
		assertEquals(1, m2.getChildren().size());
		Person f2 = new Person("Vater2", Gender.MALE);
		p.setFather(f2);
		assertEquals(0, f.getChildren().size());
		assertEquals(1, f2.getChildren().size());
		p.setMother(null);
		assertEquals(0, m2.getChildren().size());
		assertEquals(null, p.getMother());
		p.setFather(null);
		assertEquals(0, f2.getChildren().size());
		assertEquals(null, p.getFather());
	}
	/**
	 * Testet die getDaughters- und die getSons-Methode
	 */
	@Test
	public void daughtersSons() {
		assertEquals(0, p.getDaughters().size());
		assertEquals(0, p.getSons().size());
		Person m = new Person("Mutter", Gender.FEMALE);
		p.setMother(m);
		assertEquals(0, m.getDaughters().size());
		assertEquals(1, m.getSons().size());
		Person f = new Person("Vater", Gender.MALE);
		p.setFather(f);
		assertEquals(0, f.getDaughters().size());
		assertEquals(1, f.getSons().size());
		Person f2 = new Person("Vater2", Gender.MALE);
		m.setFather(f2);
		assertEquals(1, f2.getDaughters().size());
		assertEquals(0, f2.getSons().size());
	}
	
	/**
	 * Testet die getSisters und die getBrothers-Methode
	 */
	@Test
	public void sistersBrothers() {
		Person m = new Person("Mutter", Gender.FEMALE);
		p.setMother(m);
		Person f = new Person("Vater", Gender.MALE);
		p.setFather(f);
		Person anna = new Person("Anna", Gender.FEMALE);
		anna.setMother(m);
		anna.setFather(f);
		Person maria = new Person("Maria", Gender.FEMALE);
		maria.setMother(m);
		maria.setFather(f);
		Person wm = new Person("withoutMother", Gender.FEMALE);
		wm.setFather(f);
		Person herbert = new Person("Herbert", Gender.MALE);
		herbert.setMother(m);
		herbert.setFather(f);
		Person hans = new Person("Hans", Gender.MALE);
		hans.setMother(m);
		hans.setFather(f);
		Person wf = new Person("withoutFather", Gender.MALE);
		wf.setMother(m);
		assertTrue(p.getSisters().get(0).equals(anna));
		assertTrue(p.getSisters().get(1).equals(maria));
		//Erwartet IndexOutofBoundsException
		assertThrows(IndexOutOfBoundsException.class, () -> {
			assertTrue(p.getSisters().get(2).equals(null));
		});
		assertTrue(p.getBrothers().get(0).equals(herbert));
		assertTrue(p.getBrothers().get(1).equals(hans));
		//Erwartet IndexOutofBoungsException
		assertThrows(IndexOutOfBoundsException.class, () -> {
			assertTrue(p.getBrothers().get(2).equals(null));
		});
	}
	
	/**
	 * Testet die getDescendants-Methode
	 */
	@Test
	public void descendants() {
		Person m = new Person("Mutter", Gender.FEMALE);
		p.setMother(m);
		Person f = new Person("Vater", Gender.MALE);
		p.setFather(f);
		Person anna = new Person("Anna", Gender.FEMALE);
		anna.setMother(m);
		anna.setFather(f);
		Person maria = new Person("Maria", Gender.FEMALE);
		maria.setMother(m);
		maria.setFather(f);
		Person wm = new Person("withoutMother", Gender.FEMALE);
		wm.setFather(f);
		Person herbert = new Person("Herbert", Gender.MALE);
		herbert.setMother(m);
		herbert.setFather(f);
		Person hans = new Person("Hans", Gender.MALE);
		hans.setMother(m);
		hans.setFather(f);
		Person wf = new Person("withoutFather", Gender.MALE);
		wf.setMother(m);
		assertTrue(f.getDescendants().get(0).equals(p));
		assertTrue(f.getDescendants().get(1).equals(anna));
		assertTrue(f.getDescendants().get(2).equals(maria));
		assertTrue(f.getDescendants().get(3).equals(wm));
		assertTrue(f.getDescendants().get(4).equals(herbert));
		assertTrue(f.getDescendants().get(5).equals(hans));
	}
	
	/*
	 * Testet eine Person als Elternteil von sich selbst zu setzen
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void parentOfHimself() {
		p1 = new Person("Franz", Gender.MALE);
		assertThrows(IllegalArgumentException.class, () -> p.setMother(p));
		assertThrows(IllegalArgumentException.class, () -> p1.setFather(p1));
	}
	
	/**
	 * Testet eine Person als Nachkommen von sich selbst zu setzen
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void parentIsDescendants() {
		p1 = new Person("Franz", Gender.MALE);
		Person p2 = new Person("Anna", Gender.FEMALE);
		Person p3 = new Person("Herbert", Gender.MALE);
		p.setMother(p2);
		assertThrows(IllegalArgumentException.class, () -> p2.setMother(p));
		p1.setFather(p3);
		assertThrows(IllegalArgumentException.class, () -> p3.setFather(p1));
	}
}
