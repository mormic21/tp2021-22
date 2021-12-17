package net.tfobz.rssreader;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.*;

/**
 * RssReader.java
 * realisiert einen rss-reader, welcher den Rss-Feed einliest und analysiert
 * Die Daten können von einer lokalen Datei oder ueber eine URL eingelesen werden
 * @author Michael Morandell
 *
 */
public class RssReader {
	//URL
	private final static String URL_STRING = "http://www.provinz.bz.it/wetter/rss.asp";
	//Pfad zur Datei
	private final static String XML_FILE = "src/net/tfobz/rssreader/party.xml";
	//auf true setzten um eine lokale Datei zu analysieren. Ansonsten wird das rss aus dem url gelesen
	private static boolean file = false;
	//Membervariablen
	private static boolean inItem = false;
	private static List<Item> items = new ArrayList<>();
	
	/**
	 * Main
	 * @param args
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 */
	public static void main(String[] args) 
			throws FileNotFoundException, XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream in = null;
		//Wenn Url
		if (!file) {
			try {
				//Url wird eingelesen
				URL url = new URL(URL_STRING);
				in = url.openStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//Wenn aus lokaler Datei
		else {
			in = new FileInputStream(XML_FILE);
		}
		//Neuer parser
		XMLStreamReader parser = factory.createXMLStreamReader(in);
		//Neuer Channel
		Channel c = new Channel();
		//Neues Item
		Item item = new Item();
		String characters = "";
		while (parser.hasNext()) {
			int elementType = parser.next();
			switch (elementType) {
			//Ende des RSS-Feeds
				case XMLStreamConstants.END_DOCUMENT : {
					parser.close();
					break;
				}
				case XMLStreamConstants.START_ELEMENT: {
					characters = "";
					//Item-Start-Element
					if (parser.getLocalName().equals("item")) {
						item = new Item();
						inItem = true;
					}
					break;
				}
				case XMLStreamConstants.END_ELEMENT: {
					//Titel-Element
					if (parser.getLocalName().equals("title")) {
						if (inItem) {
							item.setTitle(characters);
						} else {
							c.setTitle(characters);
						}
					}
					//Link-Element
					if (parser.getLocalName().equals("link")) {
						if (inItem) {
							item.setLink(characters);
						}
						else {
							c.setLink(characters);
						}
					}
					//Desciption-Element
					if (parser.getLocalName().equals("description")) {
						if (inItem) {
							item.setDescription(characters);
						}
						else {
							c.setDescription(characters);
						}
					}
					//Language-Element
					if (parser.getLocalName().equals("language")) {
						c.setLanguage(characters);
					}
					//Copyright-Element
					if (parser.getLocalName().equals("copyright")) {
						c.setCopyright(characters);
					}
					//Autor-Element
					if (parser.getLocalName().equals("author")) {
						item.setAuthor(characters);
					}
					//pubDate-Element
					if (parser.getLocalName().equals("pubDate")) {
						item.setPubDate(characters);
					}
					//url-Element
					if (parser.getLocalName().equals("url")) {
						c.setUrl(characters);
					}
					//Item-End-Element
					if (parser.getLocalName().equals("item")) {
						//item wird geadded
						items.add(item);
						item = null;
						inItem = false;
					}
					characters = "";
					break;
				}
				//Characters
				case XMLStreamConstants.CHARACTERS: {
					if (!parser.isWhiteSpace() && parser.getText() != null && parser.getText().length() > 0)
						characters+=parser.getText();
					break;
				}
			}
		}
		//Alle Items werden dem Channel hinzugefügt
		c.setItems(items);
		//Ausgabe des Channels an die Konsole
		System.out.println("Channel: \n");
		System.out.println("Titel: "+c.getTitle());
		System.out.println("Description: "+c.getDescription());
		System.out.println("Url: "+c.getUrl());
		System.out.println("Link: "+c.getLink());
		System.out.println("Sprache: "+c.getLanguage());
		System.out.println("Copyright: "+c.getCopyright());
		//Ausgabe der Items an die Konsole
		for (int i = 0; i < items.size(); i++) {
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("Item: \n");
			System.out.println("Title: "+items.get(i).getTitle());
			System.out.println("Beschreibung: "+items.get(i).getDescription());
			System.out.println("Link: "+items.get(i).getLink());
			System.out.println("Author: "+items.get(i).getAuthor());
			System.out.println("PubDate: "+items.get(i).getPubDate());
		}
	}
}
