package net.tfobz.simpleRssReader;
import java.awt.TrayIcon;
import java.io.*;
import java.net.URL;
import javax.xml.stream.*;

/**
 * ParserRunnable
 * realisiert ein Runnable-Objekt welches einen Rss-Link einliest und diesen analysiert
 * implementiert Runnable
 * @author Michael Morandell
 */
public class ParserRunnable implements Runnable {
	//Membervariablen
	private URL link = null;
	private boolean inItem = false;
	private boolean end = false;
	private Channel c = null;
	private Item item = null;
	
	/**
	 * ParserCallable - Konstruktor
	 * @param url, URL
	 */
	public ParserRunnable(URL url) {
		this.link = url;
	}

	/**
	 * getC
	 * @return the c
	 */
	public Channel getC() {
		return c;
	}

	/**
	 * getItem
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}
	
	/**
	 * run-Methode
	 */
	@Override
	public void run() {
		//Link muss vorhanden sein
		if (link != null) {
			//Wenn es die erste URL in der Liste ist, erfolgt eine Status-Meldung an das Editor-Pane
			if (this.link.equals(SimpleRssReader.getUrls().get(0))) {
				String begin = SimpleRssReader.editor.getText().substring(0, SimpleRssReader.editor.getText().length()-19);
				String end = SimpleRssReader.editor.getText().substring(SimpleRssReader.editor.getText().length()-19);
				SimpleRssReader.editor.setText(begin + "<b>Message: </b>Updating channels... <br>" + end);
			}
			try {
				XMLInputFactory factory = XMLInputFactory.newInstance();
				InputStream in = null;
				try {
					// Url wird eingelesen
					in = link.openStream();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Neuer parser
				XMLStreamReader parser;
				parser = factory.createXMLStreamReader(in);
				// Neuer Channel
				c = new Channel();
				// Neues Item
				item = new Item();
				String characters = "";
				while (parser.hasNext()) {
					int elementType = parser.next();
					//Wenn Channel und das erste Item eingelesen wurde, wird der parser geschlossen 
					if (end) {
						elementType = XMLStreamConstants.END_DOCUMENT;
					}
					switch (elementType) {
						// Ende des RSS-Feeds
						case XMLStreamConstants.END_DOCUMENT: {
							parser.close();
							break;
						}
						case XMLStreamConstants.START_ELEMENT: {
							characters = "";
							// Item-Start-Element
							if (parser.getLocalName().equals("item")) {
								item = new Item();
								inItem = true;
							}
							break;
						}
						case XMLStreamConstants.END_ELEMENT: {
							// Titel-Element
							if (parser.getLocalName().equals("title")) {
								if (inItem) {
									item.setTitle(characters);
								} else {
									c.setTitle(characters);
								}
							}
							// Link-Element
							if (parser.getLocalName().equals("link")) {
								if (inItem) {
									item.setLink(characters);
								} else {
									c.setLink(characters);
								}
							}
							// Desciption-Element
							if (parser.getLocalName().equals("description")) {
								if (inItem) {
									item.setDescription(characters);
								} else {
									c.setDescription(characters);
								}
							}
							// Language-Element
							if (parser.getLocalName().equals("language")) {
								c.setLanguage(characters);
							}
							// Copyright-Element
							if (parser.getLocalName().equals("copyright")) {
								c.setCopyright(characters);
							}
							// Autor-Element
							if (parser.getLocalName().equals("author")) {
								item.setAuthor(characters);
							}
							// pubDate-Element
							if (parser.getLocalName().equals("pubDate")) {
								item.setPubDate(characters);
							}
							// url-Element
							if (parser.getLocalName().equals("url")) {
								c.setUrl(characters);
							}
							// Item-End-Element
							if (parser.getLocalName().equals("item")) {
								end = true;
							}
							characters = "";
							break;
						}
						// Characters
						case XMLStreamConstants.CHARACTERS: {
							if (!parser.isWhiteSpace() && parser.getText() != null && parser.getText().length() > 0)
								characters += parser.getText();
							break;
						}
					}
				}
				//Membervariablen werden auf den Anfangszustand zurueckgesetzt
				end = false;
				inItem = false;
				//Ausgabe der Daten an das JEditorPane
				String begin = SimpleRssReader.editor.getText().substring(0, SimpleRssReader.editor.getText().length()-19);
				String end = SimpleRssReader.editor.getText().substring(SimpleRssReader.editor.getText().length()-19);
				SimpleRssReader.editor.setText(begin+ "" +
						"<b>Channel: </b>" + c.getTitle() + " " +
						"<b>Newest item: </b>" + item.getTitle() + " " +
						"<b>Date: </b>"+ item.getPubDate() + "<br>"+end);
				//Wenn link das letzte Element in der Liste ist, wird eine Info-Message durch das Tray-Icon gezeigt
				if (this.link.equals(SimpleRssReader.getUrls().get(SimpleRssReader.getUrls().size()-1))) {
					SimpleRssReader.trayIcon.displayMessage("New Itmes", "There are new RSS Items", TrayIcon.MessageType.INFO);
				}
				//Fehler mit dem Parser werden aufgefangen
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
		//wenn URL null ist
		else {
			System.out.println("keine url (null)");
		}
	}
}