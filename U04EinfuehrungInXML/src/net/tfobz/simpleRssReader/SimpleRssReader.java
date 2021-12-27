package net.tfobz.simpleRssReader;
import java.util.List;
import java.util.concurrent.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;

/**
 * SimpleRssReader
 * realisisert eine GUI zum Auslesen eines RSS-Feeds
 * Es können mehrere RSS-Feeds zugleich abgefragt werden
 * Möglich ist auch das Abfragen alle x Sekunden
 * Der SimpleRssReader erstellt ein Tray-Icon und Informiert über eine Message über neue Items
 * erbt von JFrame
 * @author Michael Morandell
 *
 */
@SuppressWarnings("serial")
public class SimpleRssReader extends JFrame {
	//Font
	public static Font defaultFont_Bold = new Font("Sans Serif", Font.BOLD, 17);
	//Editorpane
	public static JEditorPane editor = null;
	//Scrollpane
	private JScrollPane scroll = null;
	//Buttons
	private JButton add = null;
	private JButton scheduler = null;
	private JButton update = null;
	//listen
	public static List<URL> urls = new ArrayList<>();
	private List<ParserRunnable> runnables = new ArrayList<>();
	private ScheduledExecutorService schedExecutor;
	//Trayicon
	public static final TrayIcon trayIcon =
             new TrayIcon(createImage("rssfeed15px.png", "SimpleRssReader"));
    private final SystemTray tray = SystemTray.getSystemTray();
	
    /**
     * SimpleRssReader-Konstruktor
     */
	public SimpleRssReader() {
		// Einstellen der Werte für das Fenster
		int height = 600;
		int width = 1080;
		//Exit on close
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2, 
				(Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2,
						width, height);
		//title
		this.setTitle("Simple RSSReader");
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		
		//ActionListener
		ButtonListener listener = new ButtonListener();
		
		//editorpane
		editor = new JEditorPane();
		editor.setEditable(false);
		//scrollpane
		scroll = new JScrollPane(editor);
		scroll.setBounds(0, 0, this.getWidth()-10, this.getHeight() - 90);
		//content-type html
		editor.setContentType("text/html");
		editor.setText("<html><body><p style=\"font-size:300%;\"></p></body></html>");
		editor.setBounds(0, 0, scroll.getWidth()+200, scroll.getHeight());
		//show scrollbars
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getContentPane().add(scroll);
		
		//Add URL - Button
		add = new JButton();
		add.setBounds(this.getWidth()-480, this.getHeight()-82, 120, 40);
		add.setText("Add URL...");
		add.setFont(defaultFont_Bold);
		add.addActionListener(listener);
		this.getContentPane().add(add);
		
		//Activate Scheduler - Button
		scheduler = new JButton();
		scheduler.setBounds(this.getWidth()-350, this.getHeight()-82, 220, 40);
		scheduler.setText("Activate Scheduler");
		scheduler.setFont(defaultFont_Bold);
		scheduler.addActionListener(listener);
		this.getContentPane().add(scheduler);
		
		//update - Button
		update = new JButton();
		update.setBounds(this.getWidth()-120, this.getHeight()-82, 100, 40);
		update.setFont(defaultFont_Bold);
		update.setText("Update");
		update.addActionListener(listener);
		this.getContentPane().add(update);
		
		//default-Button
		this.getRootPane().setDefaultButton(add);
		
		//Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
        }
        //not autosize icon
        trayIcon.setImageAutoSize(false);
        //icon wird geadded
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
        //tooltip-text fuer icon
        trayIcon.setToolTip("SimpleRssReader");
	}
	
	/**
	 * getUrls
	 * @return the urls
	 */
	public static List<URL> getUrls() {
		return urls;
	}

	/**
	 * setUrls
	 * @param urls the urls to set
	 */
	public static void setUrls(List<URL> urls) {
		SimpleRssReader.urls = urls;
	}
	
	/**
	 * creareImage
	 * @param path, path to Image, String
	 * @param description, String
	 * @return Image
	 */
    protected static Image createImage(String path, String description) {
        URL imageURL = SimpleRssReader.class.getResource(path);
        //image not found
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
	
	/**
	 * ButtonListener
	 * reagiert auf das Druecken der Buttons
	 * implementiert Actionlistener
	 * @author Michael Morandell
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * actionPerformed
		 * @param e, ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//Wenn Add-Url gedrueckt wurde
			if (e.getSource().equals(add)) {
				//AddUrl-Gui wird instanziiert und geoeffnet
				AddUrlGUI urlgui = new AddUrlGUI(SimpleRssReader.this);
				urlgui.setVisible(true);
				urlgui.dispose();
			}
			//Wenn Scheduler-Button gedrueckt wurde
			if (e.getSource().equals(scheduler)) {
				//Es sind URLs in der Liste vorhanden
				if (urls.size() > 0) {
					//Wenn scheduler noch nicht aktiviert wurde
					if (scheduler.getText().equals("Activate Scheduler")) {
						//Neuer Dialog zum Eingeben der Schedule-Period wird instanziiert und sichtbar gemacht
						SetSchedulePeriodGUI dialog = new SetSchedulePeriodGUI(SimpleRssReader.this);
						dialog.setVisible(true);
						//Wenn im Dialog "ok" gedrueckt wurde
						if (dialog.isDialogApproved()) {
							//Message-Text wird an Jeditorpane ausgegeben
							String begin = SimpleRssReader.editor.getText().substring(0, SimpleRssReader.editor.getText().length()-19);
							String end = SimpleRssReader.editor.getText().substring(SimpleRssReader.editor.getText().length()-19);
							editor.setText(begin + "<b>Message:</b> Scheduler is now running (schedule period = "+dialog.getSchedulePeriod()+"sec.) <br>" + end);
							//neuer scheduled Executor
							schedExecutor  =  Executors.newSingleThreadScheduledExecutor();
							runnables = new ArrayList<>();
							//benoetigte Runnables werden instanziiert und als scheduledTask zum Executor hinzugefuegt
							for (int i = 0; i < urls.size(); i++) {
								runnables.add(i, new ParserRunnable(urls.get(i)));
								schedExecutor.scheduleAtFixedRate(runnables.get(i), 1, dialog.getSchedulePeriod(), TimeUnit.SECONDS);
							}
							//Aendern des Button-Textes
							scheduler.setText("Disactivate Scheduler");
						}
						//Freigeben des Grafikspeichers
						dialog.dispose();
						
					}
					//Wenn Scheduler bereits aktiv ist
					else {
						//Aendern des Button-Textes
						scheduler.setText("Activate Scheduler");
						//scheduledExecutor wird gestoppt
						schedExecutor.shutdown();
						//Meldung an JEditorPane
						String begin = SimpleRssReader.editor.getText().substring(0, SimpleRssReader.editor.getText().length()-19);
						String end = SimpleRssReader.editor.getText().substring(SimpleRssReader.editor.getText().length()-19);
						editor.setText(begin + "<b>Message:</b> Scheduler disactivated <br>" + end);
					}
				}
				//Wenn keine URLs vorhanden sind -> Fehlermeldung
				else {
					JOptionPane.showMessageDialog(SimpleRssReader.this, 
							"Bitte fügen sie eine URL zu einem RSS-Feed ein", 
							"Fehler: Keine URLs vorhanden", JOptionPane.ERROR_MESSAGE);
				}
			}
			//Wenn Update-Button gedrueckt wurde
			if (e.getSource().equals(update)) {
				//Wenn URLs vorhanden sind
				if (urls.size() > 0) {
					runnables = new ArrayList<>();
					//Neuer Thread Pool im Executor-Service
					ExecutorService executor =
							Executors.newCachedThreadPool();
					//Runnables werden instanziiert und zum Executor hinzugefuegt
					for (int i = 0; i < urls.size(); i++) {
						runnables.add(i, new ParserRunnable(urls.get(i)));
						executor.submit(runnables.get(i));
					}
				}
				//MessageDialog, wenn keine URLs vorhanden sind
				else {
					JOptionPane.showMessageDialog(SimpleRssReader.this, 
							"Bitte fügen sie eine URL zu einem RSS-Feed ein", 
							"Fehler: Keine URLs vorhanden", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}