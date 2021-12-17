package net.tfobz.simpleRssReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.tfobz.rssreader.Channel;
import net.tfobz.rssreader.Item;

public class ParserRunnable implements Runnable {

	private URL link = null;
	private boolean inItem = false;
	private boolean end = false;
	private Channel c = null;
	private Item item = null;
	
	/**
	 * ParserCallable
	 * @param url
	 */
	public ParserRunnable(URL url) {
		this.link = url;
	}

	/**
	 * @return the c
	 */
	public Channel getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(Channel c) {
		this.c = c;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

//	@Override
//	public String call() throws Exception {
//		
//		return "ok";
//	}

	@Override
	public void run() {
		System.out.println("Parser: here");
		if (link != null) {
			if (this.link.equals(SimpleRssReader.getUrls().get(0))) {
				SimpleRssReader.editor.setText(SimpleRssReader.editor.getText() + "\n" + "Message: Updating channels...");
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
								// item wird geadded
								// item = null;
								end = true;
								System.out.println("Parser: end");
								//inItem = false;
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
				end = false;
				inItem = false;
				SimpleRssReader.editor.setText(SimpleRssReader.editor.getText() + "\n" + 
						"Channel: " + c.getTitle() + " " +
						"Newest item: " + item.getTitle() + " " +
						"Date: "+ item.getPubDate());
			} catch (XMLStreamException e) {}
		}
		else {
			System.out.println("Keine url = null");
		}
	}
}
