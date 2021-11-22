package net.tfobz.relationship;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.util.ArrayList;
import java.io.*;

/**
 * PersonListTest
 * Testklasse für PersonList
 * JUnit 5
 * @author Michael Morandell
 *
 */
public class PersonListTest {
	//Personlist für die tests
	PersonList test = null;
	//TestStrings mit Fehlerfaellen
	String fileString0 = "";
	String fileString1 = "\n";
	String fileString2 = "blabla";
	String fileString3 = "blabla,blabla";
	String fileString4 = "blabla;blabla";
	String fileString5 = "blabla;blabla;blabla";
	String fileString6 = "blabla;blabla;blabla;blabla;blabla";
	String fileString7 = ";;;";
	String fileString8 = "null;null;null;null";
	String fileString9 = ";;null;null";
	String fileString10 = "Sepp;;null;null";
	String fileString11 = "Sepp;blabla;null;null";
	String fileString12 = "Sepp;MALE;null;null\nblabla";
	String fileString13 = "Sepp;MALE;null;null\nblabla,blabla";
	String fileString14 = "Sepp;MALE;null;null\nblabla;blabla";
	String fileString15 = "Sepp;MALE;null;null\nblabla;blabla;blabla";
	String fileString16 = "Sepp;MALE;null;null\nblabla;blabla;blabla;blabla;blabla";
	String fileString17 = "Sepp;MALE;null;null\n;;;";
	String fileString18 = "Sepp;MALE;null;null\nnull;null;null;null";
	String fileString19 = "Sepp;MALE;null;null\n;;null;null";
	String fileString20 = "Sepp;MALE;null;null\nElsa;;null;null";
	String fileString21 = "Sepp;MALE;null;null\nElsa;blabla;null;null";
	String fileString22 = "Sepp;MALE;;";
	String fileString23 = "Sepp;MALE;null;null\nSepp;MALE;null;null";
	String fileString24 = "Sepp;MALE;Resi;null";
	String fileString25 = "Sepp;MALE;null;Hugo";
	
	/**
	 * Testet den filestring0
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr0() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString0)));
		});
	}
	
	/**
	 * Testet den filestring1
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr1() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString1)));
		});
	}
	
	/**
	 * Testet den filestring2
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr2() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString2)));
		});
	}
	
	/**
	 * Testet den filestring3
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr3() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString3)));
		});
	}
	
	/**
	 * Testet den filestring4
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr4() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString4)));
		});
	}
	
	/**
	 * Testet den filestring5
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr5() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString5)));
		});
	}
	
	/**
	 * Testet den filestring6
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr6() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString6)));
		});
	}
	
	/**
	 * Testet den filestring7
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr7() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString7)));
		});
	}
	
	/**
	 * Testet den filestring8
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr8() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString8)));
		});
	}
	
	/**
	 * Testet den filestring9
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr9() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString9)));
		});
	}
	
	/**
	 * Testet den filestring10
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr10() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString10)));
		});
	}
	
	/**
	 * Testet den filestring11
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr11() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString11)));
		});
	}
	
	/**
	 * Testet den filestring12
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr12() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString12)));
		});
	}
	
	/**
	 * Testet den filestring13
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr13() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString13)));
		});
	}
	
	/**
	 * Testet den filestring14
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr14() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString14)));
		});
	}
	
	/**
	 * Testet den filestring15
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr15() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString15)));
		});
	}
	
	/**
	 * Testet den filestring16
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr16() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString16)));
		});
	}
	
	/**
	 * Testet den filestring17
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr17() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString17)));
		});
	}
	
	/**
	 * Testet den filestring18
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr18() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString18)));
		});
	}
	
	/**
	 * Testet den filestring19
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr19() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString19)));
		});
	}
	
	/**
	 * Testet den filestring20
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr20() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString20)));
		});
	}
	
	/**
	 * Testet den filestring21
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr21() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString21)));
		});
	}
	
	/**
	 * Testet den filestring22
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr22() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString22)));
		});
	}
	
	/**
	 * Testet den filestring23
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr23() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString23)));
		});
	}
	
	/**
	 * Testet den filestring24
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr24() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString24)));
		});
	}
	
	/**
	 * Testet den filestring25
	 * Erwartet IllegalArgumentException
	 */
	@Test
	public void filestr25() {
		assertThrows(IllegalArgumentException.class, () -> {
			test = new PersonList(new BufferedReader(new StringReader(fileString25)));
		});
	}
	
	/**
	 * Testet ob der fileString richtig eingelesen werden kann
	 */
	@Test 
	public void readPersonsCorrect() {
		String fileString =
				"Sepp;MALE;null;null\nRosi;FEMALE;null;null\n" +
				"Rudi;MALE;Rosi;Sepp\nElsa;FEMALE;Rosi;Sepp\nEdit;FEMALE;Rosi;Sepp\n" + 
				"Hugo;MALE;Elsa;Rudi\nHerta;FEMALE;Elsa;Rudi";
		try {
			test = new PersonList(new BufferedReader(new StringReader(fileString)));
			//7 Personen in der ArrayList werden erwartet
			assertEquals(7, test.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testet das Einlesen einer Date
	 */
	@Test
	public void readPersonsFromFile() {
		try {
			test = new PersonList("C:\\Users\\Michael Morandell\\Documents\\"
					+ "Schule\\TP\\Work\\U03testgetriebenesEntwickeln\\src\\net\\tfobz\\relationship\\relationship.txt");
			//erwartet 4 Personen im ArrayList
			assertEquals(4, test.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

