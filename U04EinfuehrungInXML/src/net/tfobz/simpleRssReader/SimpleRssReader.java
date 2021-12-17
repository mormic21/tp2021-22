package net.tfobz.simpleRssReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.*;

/**
 * SimpleRssReader
 * @author Michael Morandell
 *
 */
public class SimpleRssReader extends JFrame {
	public static Font defaultFont_Bold = new Font("Sans Serif", Font.BOLD, 17);
	public static Font defaultFont_Normal = new Font("Sans Serif", 0, 17);
	public static JEditorPane editor = null;
	private JScrollPane scroll = null;
	private JButton add = null;
	private JButton scheduler = null;
	private JButton update = null;
	public static List<URL> urls = new ArrayList<>();
	private List<ParserRunnable> runnables = new ArrayList<>();
	private List<Future<?>> future = new ArrayList<>();
	private ScheduledExecutorService schedExecutor;
	
	public SimpleRssReader() {
		// Einstellen der Werte für das Fenster
		int height = 600;
		int width = 1000;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2, 
				(Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2,
						width, height);
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
		editor.setBounds(0, 0, scroll.getWidth()+200, scroll.getHeight());
		editor.setFont(new Font("Sans Serif", 0, 15));
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getContentPane().add(scroll);
		
		//Add URL
		add = new JButton();
		add.setBounds(this.getWidth()/2-50, this.getHeight()-82, 100, 40);
		add.setText("Add URL...");
		add.addActionListener(listener);
		this.getContentPane().add(add);
		
		//Disactivate
		scheduler = new JButton();
		scheduler.setBounds(this.getWidth()/2 +60, this.getHeight()-82, 180, 40);
		scheduler.setText("Activate Scheduler");
		scheduler.addActionListener(listener);
		this.getContentPane().add(scheduler);
		
		//update
		update = new JButton();
		update.setBounds(this.getWidth()/2 + 250, this.getHeight()-82, 100, 40);
		update.setText("Update");
		update.addActionListener(listener);
		this.getContentPane().add(update);
	}
	
	/**
	 * @return the urls
	 */
	public static List<URL> getUrls() {
		return urls;
	}

	/**
	 * @param urls the urls to set
	 */
	public static void setUrls(List<URL> urls) {
		SimpleRssReader.urls = urls;
	}
	
	/**
	 * ButtonListener
	 * @author Michael Morandell
	 *
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * actionPerformed
		 * @param e, ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(add)) {
				AddUrlGUI urlgui = new AddUrlGUI(SimpleRssReader.this);
				urlgui.setVisible(true);
				urlgui.dispose();
			}
			if (e.getSource().equals(scheduler)) {
				if (urls.size() > 0) {
					if (scheduler.getText().equals("Activate Scheduler")) {
						scheduler.setText("Disactivate Scheduler");
						editor.setText(editor.getText() + 
						"\nMessage: Scheduler is now running (schedule period = 10sec.)");
						schedExecutor  =  Executors.newSingleThreadScheduledExecutor();
						runnables = new ArrayList<>();
						future = new ArrayList<>();
						for (int i = 0; i < urls.size(); i++) {
							runnables.add(i, new ParserRunnable(urls.get(i)));
							future.add(i, schedExecutor.scheduleAtFixedRate(runnables.get(i), 1, 10, TimeUnit.SECONDS));
						}
//						boolean allDone = true;
//						do {
//							allDone = true;
//							for (int i = 0; i < future.size(); i++) {
//								if (!future.get(i).isDone()) {
//									allDone = false;
//								}
//							}
//							System.out.println("Updating...");
//						} while (!allDone);
//						for (int i = 0; i < runnables.size(); i++) {
//							editor.setText(editor.getText() + "\n" + 
//									"Channel: " + runnables.get(i).getC().getTitle() + " " +
//									"Newest item: " + runnables.get(i).getItem().getTitle() + " " +
//									"Date: "+ runnables.get(i).getItem().getPubDate());
//						}
					}
					else {
						scheduler.setText("Activate Scheduler");
						schedExecutor.shutdown();
						editor.setText(editor.getText() + 
								"\nMessage: Scheduler disactivated");
					}
				}
				else {
					JOptionPane.showMessageDialog(SimpleRssReader.this, 
							"Bitte fügen sie eine URL zu einem RSS-Feed ein", 
							"Fehler: Keine URLs vorhanden", JOptionPane.ERROR_MESSAGE);
				}
			}
			if (e.getSource().equals(update)) {
				if (urls.size() > 0) {
					runnables = new ArrayList<>();
					future = new ArrayList<>();
					System.out.println("here");
					ExecutorService executor =
							Executors.newCachedThreadPool();
					for (int i = 0; i < urls.size(); i++) {
						System.out.println("ButtonListener: "+i);
						runnables.add(i, new ParserRunnable(urls.get(i)));
						future.add(i, executor.submit(runnables.get(i)));
//						//Future<String> future = executor.submit(callable);
//						while (!future.isDone()) {
//							System.out.println("Do other things while waiting");
//						}
					}
//					boolean allDone = true;
//					do {
//						allDone = true;
//						for (int i = 0; i < future.size(); i++) {
//							if (!future.get(i).isDone()) {
//								allDone = false;
//							}
//						}
//						System.out.println("Updating...");
//					} while (!allDone);
//					for (int i = 0; i < runnables.size(); i++) {
//						editor.setText(editor.getText() + "\n" + 
//								"Channel: " + runnables.get(i).getC().getTitle() + " " +
//								"Newest item: " + runnables.get(i).getItem().getTitle() + " " +
//								"Date: "+ runnables.get(i).getItem().getPubDate());
//					}
				}
				else {
					JOptionPane.showMessageDialog(SimpleRssReader.this, 
							"Bitte fügen sie eine URL zu einem RSS-Feed ein", 
							"Fehler: Keine URLs vorhanden", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
