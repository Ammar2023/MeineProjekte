import javax.naming.spi.DirStateFactory.Result;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.*;
import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import turban.utils.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestRunnerGui extends JFrame {
	private Class<?>[] testklassen = { testg.class, testEnumAufgabe.class, TestDefaultMutableTreeNode.class };

	private DefaultMutableTreeNode _tnRoot;
	private DefaultTreeModel _treeModell;
	private JTree _jtree;

	// button
	private JButton _startBtn, _stopBtn, _xmlLadenBtn, _DBladenbtn;

	//
	JScrollPane _jscrol;
	// JLabel
	JLabel _running;
	// List<Testklassen> klassen; in dieser Liste werden die Testmethoden mit dem
	// Testergebnis angelegt
	HashMap<String, DefaultMutableTreeNode> nodeUndBeschreibung = new HashMap<>();
	// boolean
	private static volatile boolean stopTest = false;
	private boolean _testDurchlaufen = false;
	// textArea
	private JTextArea _textArea = new JTextArea(5, 20);

	// das brauche ich für speichernInDB methode
	private int _anzahlTests = 0;
	private int _anzahlPassendTests = 0;
	private int _anzahlFailedTests = 0;
	private Timestamp _testlaufbeginn;
	private Timestamp _testlaufende;

	/**
	 * 
	 */

	public TestRunnerGui() {

		super("Testrunner-GUI");

		_tnRoot = new DefaultMutableTreeNode("Root");
		_treeModell = new DefaultTreeModel(_tnRoot);
		_jtree = new JTree(_treeModell);
		_jtree.setRootVisible(true);

		// einfache selektion vom Baumknoten
		_jtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		_jscrol = new JScrollPane(_jtree);
		_jscrol.setPreferredSize(new Dimension(450, 250));
		this.add(_jscrol);
		this.add(_jscrol, BorderLayout.CENTER);

		// JButtonPanel
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(2, 2));
		this.add(btnPanel, BorderLayout.SOUTH);
		_startBtn = new JButton("Start Tests");
		btnPanel.add(_startBtn);
		_stopBtn = new JButton("Stop Tests");
		btnPanel.add(_stopBtn);

		_running = new JLabel();
		btnPanel.add(_running);

		_xmlLadenBtn = new JButton("In Xml ausgeben");
		btnPanel.add(_xmlLadenBtn);

		_DBladenbtn = new JButton("Speichen in DB");
		_DBladenbtn.setEnabled(false);
		btnPanel.add(_DBladenbtn);

		this.add(_textArea, BorderLayout.EAST);

		treeSelectionListener();

		Thread testThreads = new Thread(new Runnable() {

			@Override
			public void run() {
				if (stopTest == false) {
					try {

						runAllTests();
					} catch (Exception e) {

						e.printStackTrace();
					}

				}

			}
		});

		startBtnListener(testThreads);

		stopBtnListener(testThreads);

		xmlBtnListener();

		datenBankBtnListener();

		// Baum mit Testklassen erstellen
		testBaumErstellen();
		_treeModell.reload();

		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void testBaumErstellen() {
		for (Class testklasse : testklassen) {

			DefaultMutableTreeNode klassenNode = new DefaultMutableTreeNode(
					"Testklasse: " + testklasse.getSimpleName());
			List<String> nameTests = ReflectionUtils.getMethodNamesWithAnnotation(testklasse, Test.class);// test
																											// Mthoden
																											// als list
																											// bekommen
			List<String> nameIgnorierterTests = ReflectionUtils.getMethodNamesWithAnnotation(testklasse, Ignore.class);// ignore
																														// Mthoden
																														// als
																														// list
																														// bekommen

			testMethodenErstellen(nameTests, klassenNode, "@Test");

			testMethodenErstellen(nameIgnorierterTests, klassenNode, "@Ignore");

			_tnRoot.add(klassenNode);
		}
	}

	private void testMethodenErstellen(List<String> lstTests, DefaultMutableTreeNode elternNode, String anmerkung) {

		for (String testName : lstTests) {
			DefaultMutableTreeNode testNode = new DefaultMutableTreeNode(testName + " " + anmerkung);// erzeuge Knoten
																										// für jede
																										// Testmethode
																										// mit einer
																										// Anmerkung
			_anzahlTests++;
			elternNode.add(testNode);// die Testmethoden zu ihren Elternknoten(Klasse) hinzufügen
			nodeUndBeschreibung.put(testName, testNode);
		}

	}

	public void runAllTests() {
		JUnitCore junitCore = new JUnitCore();
		for (Class<?> testklasse : testklassen) {
			if (stopTest == true) {
				break;
			}
			List<String> nameTests = ReflectionUtils.getMethodNamesWithAnnotation(testklasse, Test.class);
			for (String testName : nameTests) {
				Request req = Request.method(testklasse, testName);
				org.junit.runner.Result res = junitCore.run(req);
				DefaultMutableTreeNode aktuellerTestNode = nodeUndBeschreibung.get(testName);
				if (res.wasSuccessful() == true) {

					aktuellerTestNode.setUserObject(testName + " <- PASSED, ✓");
					_anzahlPassendTests++;
				} else {
					aktuellerTestNode.setUserObject(testName + " <- FAILED, X");
					_anzahlFailedTests++;
				}
			}
		}
		_treeModell.reload();
	}

	public void setStartTimestampNow() {
		System.out.println("Zeitmessung der Testdauer gestartet");
		_testlaufbeginn = new Timestamp(System.currentTimeMillis());
	}

	public void setEndTimestampNow() {
		System.out.println("Zeitmessung der Testdauer beendet");
		_testlaufende = new Timestamp(System.currentTimeMillis());
		System.out.println("Testende: " + _testlaufende);
	}

	public void speicherInDB() {
		if (_testDurchlaufen == true) {
			try {
				Connection myCon = DriverManager.getConnection("jdbc:mariadb:// localhost:3306/Jdbc", "user2", "pmt");

				Statement stm = myCon.createStatement();
				try {
					int count = stm.executeUpdate

					("INSERT INTO TestRunner (start,ende,AnzahlTests, AnzahlPassendTests, AnzahlFailedTests) "
							+ "VALUES ('" + _testlaufbeginn + "', '" + _testlaufende + "', " + _anzahlTests + "','"
							+ (_anzahlPassendTests) + "','" + _anzahlFailedTests + "');");

					ErrorHandler.Assert(count == 1, true, TestRunnerGui.class, "Falsch Hinzufügen");

				} finally {
					stm.close();
				}

			} catch (Throwable th) {
				ErrorHandler.logException(th, TestRunnerGui.class,
						"Fehler beim Abspeichern der Statistik in die DB aufgetreten.");
			}

		}

	}

	/**
	 * eine Testrunner Tabelle in DB erstellen
	 * 
	 * @throws SQLException
	 */

	private void erstellTabele() throws SQLException {
		// this.verbindeMitDB();
		Connection myCon = DriverManager.getConnection("jdbc:mariadb:// localhost:3306/Jdbc", "user2", "pmt");
		Statement myStm = myCon.createStatement();
		try {
			// tabelle erstellen
			String createTable = "create table if not exists TestRunner" + "(start Timestamp NOT NULL primary KEY ,\r\n"
					+ "(ende Timestamp NOT NULL primary KEY ,\r\n" + "(AnzahlTests INT NOT NULL primary KEY ,\r\n"
					+ " AnzahlPassendTests INT not NULL,\r\n" + "	AnzahlFailedTests INT NOT NULL)";
			myStm.execute(createTable);

		} catch (Throwable th) {
			ErrorHandler.logException(th, false, TestRunnerGui.class, "Fehler beim erstellen von Tabelle ");
		}
	}

	/**
	 * diese Methode gibt die Testergebnisse zurück
	 * 
	 * @return
	 */
	public String getStatistik() {
		String str = "Testsanzahl: " + _anzahlTests + "\n" + " Anzahl erfolgreiche Tests: "
				+ String.valueOf(_anzahlPassendTests) + "\n" + " Anzahl fehlgeschlage Tests: "
				+ String.valueOf(_anzahlFailedTests);
		return str;
	}

	public void saveAsXml(File xmlFile) throws ParserConfigurationException, TransformerException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
		Document document = documentBuilder.newDocument();

		// Root hinzufügen
		Element root = document.createElement("Testergebnisse");
		document.appendChild(root);

		Element testklasses = document.createElement("Testklassen");
		root.appendChild(testklasses);

		for (Class tk : testklassen) {
			// Testklassen hinzufügen
			Element testklasse = document.createElement("Testklasse");

			Attr classname = document.createAttribute("name");
			classname.setValue(tk.getName());
			testklasse.setAttributeNode(classname);

			Attr testresult = document.createAttribute("result");
			testresult.setValue(tk.getSimpleName());
			testklasse.setAttributeNode(testresult);

			for (Method tm : tk.getMethods()) {
				// Testmethoden hinzufügen
				Element testmethode = document.createElement("Testmethode");

				Attr methodname = document.createAttribute("name");
				methodname.setValue(tm.getName());
				testmethode.setAttributeNode(methodname);

				Attr testresultMethod = document.createAttribute("result");
				testresultMethod.setValue(tm.getName());
				testmethode.setAttributeNode(testresultMethod);

				testmethode.setTextContent(tm.getName());

				testklasse.appendChild(testmethode);
			}

			testklasses.appendChild(testklasse);
		}

		// Generate DomSource
		DOMSource domSource = new DOMSource(document);

		// Save XML in File
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();

		FileWriter writer = new FileWriter(xmlFile);
		StreamResult result = new StreamResult(writer);

		transformer.transform(domSource, result);
	}

	private void datenBankBtnListener() {
		_DBladenbtn.addActionListener((e) -> {
			try {
				// erstellTabele(); wurde schon erstellen daher auskommentieren damit kein
				// Fehler geworden wird
				speicherInDB();
			} catch (Exception e1) {

				ErrorHandler.logException(e1, false, TestRunnerGui.class, " Fehler in _DBladenbtn");
			}
		});
	}

	private void xmlBtnListener() {
		_xmlLadenBtn.addActionListener((e) -> {
			try {

				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(null);

//				SimpleFrameworkDeAndSerializer.serializeToFile(gui,fw.toString());
				File xmlFile = chooser.getSelectedFile();
				System.out.println("Speicherort: " + xmlFile);
				if (xmlFile.exists()) {
					int result = JOptionPane.showConfirmDialog(this, "Die Datei existiert bereits.");
					return;

				}
				// XMl abspeichern
				try {
					saveAsXml(xmlFile);

				} catch (Throwable throwable) {

					ErrorHandler.logException(throwable, TestRunnerGui.class,
							"Fehler beim Speichern des XMLs aufgetreten");
				}

			} catch (Exception e2) {
				ErrorHandler.logException(e2, false, TestRunnerGui.class, " speichern Exception");
			}

		});
	}

	private void stopBtnListener(Thread testThreads) {
		_stopBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					stopTest = true;

					testThreads.interrupt();
					_running.setText(" Test beendet ");
					_running.validate();
				} catch (Throwable ex) {
					ErrorHandler.logException(ex, false, TestRunnerGui.class, " Fehler beim stoben der Tests");
				}
			}
		});
	}

	private void startBtnListener(Thread testThreads) {
		_startBtn.addActionListener((e) -> {
			try {
				testThreads.start();
				// runAllTests();
				_running.setText(" Test läuft");
				_running.validate();

			} catch (Throwable ex) {
				ErrorHandler.logException(ex, false, TestRunnerGui.class, " Fehler beim stoben der Tests");
			}

		});
	}

	private void treeSelectionListener() {
		_jtree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode tnSelected = (DefaultMutableTreeNode) _jtree.getLastSelectedPathComponent();
				if (tnSelected == null) {
					_DBladenbtn.setEnabled(false);
					return;
				} else {
					_DBladenbtn.setEnabled(true);
					// Main main= new Main();
					_textArea.setText(getStatistik());

				}

			}

		});
	}

	public static void main(String[] args) {
		new TestRunnerGui();
	}
}
