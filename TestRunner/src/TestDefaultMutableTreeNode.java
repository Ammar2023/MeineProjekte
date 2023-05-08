

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.junit.*;
import org.junit.Before;

public class TestDefaultMutableTreeNode  {
	String strRoot = "0. Root";
	DefaultMutableTreeNode tnRoot = new DefaultMutableTreeNode(strRoot);
	// Parent
	String strParent1 = "1. Parent";
	DefaultMutableTreeNode tnParent1 = new DefaultMutableTreeNode(strParent1);
	DefaultMutableTreeNode tnParent2 = new DefaultMutableTreeNode("2. Parent");
	DefaultMutableTreeNode tnLeaf= new DefaultMutableTreeNode("3. Leaf");
	//Parent zu Parent 1 hinzufügen
	DefaultMutableTreeNode tnParent1_1= new DefaultMutableTreeNode("1.1 Parent");
	//1.2 Leaf to 1. Parent
	DefaultMutableTreeNode tnLeaf1_2= new DefaultMutableTreeNode("1.2 Leaf");
	//
	DefaultMutableTreeNode tnLeaf1_1_1= new DefaultMutableTreeNode("1.1.1 Leaf");
	DefaultMutableTreeNode tnLeaf1_1_2= new DefaultMutableTreeNode("1.1.2 Leaf");
	DefaultMutableTreeNode tnLeaf2_1= new DefaultMutableTreeNode("2.1 Leaf");
	
	

	
	@Test
	public void testRootAndChildren() {
		String strRoot = "0. Root";
		DefaultMutableTreeNode tnRoot = new DefaultMutableTreeNode(strRoot);
		// kinder
		String strChild1 = "Child1";
		DefaultMutableTreeNode tnChild1 = new DefaultMutableTreeNode(strChild1);
		tnRoot.add(tnChild1);
		String strChild2 = "Child2";
		DefaultMutableTreeNode tnChild2 = new DefaultMutableTreeNode(strChild2);
		tnRoot.add(tnChild2);
		
		assertEquals(tnRoot, tnChild1.getRoot());
		assertEquals(tnRoot, tnChild2.getRoot());
		assertEquals(2, tnRoot.getChildCount());
		assertTrue(tnRoot.getChildAt(0) == tnChild1);
		assertTrue(tnRoot.getChildAt(1) == tnChild2);
		
		tnRoot.setUserObject("changed Root");// userobject ändern 
	}
	
	@Before
	public void setUp() {
		
		tnRoot.add(tnParent1);
	
		
		tnRoot.add(tnParent2);
		
		tnRoot.add(tnLeaf);
		
		tnParent1.add(tnParent1_1);
		
		tnParent1.add(tnLeaf1_2);
		
		//leafs to parent1_1
		tnParent1_1.add(tnLeaf1_1_1);
		tnParent1_1.add(tnLeaf1_1_2);
		//2.1 leaf in 2.parent
		tnParent2.add(tnLeaf2_1);
		
		
		
	}
	
	@Test
	public void testD() {
		// Tests
		// d
		assertEquals(tnParent1_1, tnLeaf1_1_1.getParent());
		assertEquals(tnRoot, tnLeaf1_1_1.getRoot());
	}

	/**
	 * Schreiben Sie einen Testfall, der von den Knoten „1.1.1 Leaf“ und „2. Parent“
	 * mittels getUserObject() die Strings wieder zurückbekommt
	 */
	@Test
	public void testGetString() throws Exception {
		// userObject ist der Name diese Knoten
		assertEquals("1.1.1 Leaf", tnLeaf1_1_1.getUserObject());
		assertEquals("2. Parent", tnParent2.getUserObject());

		//System.out.println(tnParent2.getUserObject());

	}
	/**
	 * gehört nicht zur Aufgabe 
	 * hier wird das userobject bekommen, aber erstmal muss man vergleichen ob das null ist und ob er vom typ objekt ist
	 * @throws Exception
	 */
	private void getUserObjectVorlesungsCode() throws Exception {
		Object obj=tnLeaf1_1_1.getUserObject();
		Object obj2=tnParent2.getUserObject();
		if(obj==null) {
			throw new Exception("No user object");
		}else if(obj instanceof String) {
			String strLeaf=(String) obj;
			assertEquals("1.1.1 Leaf", strLeaf);
		}else {
			
			throw new Exception("Unexpected Type!" + obj.getClass().toString() + "!");
		}
		// do not repeat ur self !!
		if(obj2==null) {
			throw new Exception("No user object");
		}else if(obj instanceof String) {
			String strParent=(String) obj2;
			assertEquals("2. Parent", strParent);
		}else {
			
			throw new Exception("Unexpected Type!" + obj2.getClass().toString() + "!");
		}
	}
	
	
	

	/**
	 * Schreiben Sie einen Testfall, der die Knoten „1.1.1 Leaf“ und „2. Parent“ aus
	 * dem Baum entfernt1 .
	 */
	@Test
	public void testRemove() {
		tnParent1_1.remove(tnLeaf1_1_1);
		tnParent2.removeFromParent();
		
		assertNull(tnParent2.getParent());
		assertNull(tnLeaf1_1_1.getParent());
	}
	/**
	 * mit der bekommt man das root object jede knoten
	 * @param tn
	 * @return
	 */
	public DefaultMutableTreeNode getRoot(DefaultMutableTreeNode tn) {
		while(tn.getParent()!=null) {// solange der Parent von knoten  nicht null ist, dann hat er ein Parent
			tn=(DefaultMutableTreeNode) tn.getParent();
		}
		return tn;
	}
	
	@Test
	public void testGetRoot() {
		assertEquals(tnRoot,this.getRoot(tnLeaf));
		assertEquals(tnRoot, this.getRoot(tnLeaf1_1_1));
	}

	private List<DefaultMutableTreeNode> getLeavesRekursiv(DefaultMutableTreeNode node){
		List<DefaultMutableTreeNode> retList= new ArrayList<>();
		if(node.isLeaf()) {
			retList.add(node);
		}
		
		for (int i = 0; i < node.getChildCount(); i++) {
			// in nodeChild lege ich alle UnterKnoten von node
			/*
			 * Die Annotation SuppressWarning wird verwendet, um Compiler-Warnungen für das
			 * kommentierte Element zu unterdrücken. Insbesondere ermöglicht die
			 * uncheckedKategorie die Unterdrückung von Compilerwarnungen, die als Ergebnis
			 * von ungeprüften Typumwandlungen generiert werden
			 */
			@SuppressWarnings("unchecked")
			DefaultMutableTreeNode nodeChild = (DefaultMutableTreeNode) node.getChildAt(i);
			retList.addAll(getLeavesRekursiv(nodeChild));// die knotenchildern rekursiv in der liste zufügen
		}
		return retList;
	}
	/**
	 * hier hole ich alle Blätter eines Knoten mit Enumeration
	 * @param node
	 * @return
	 */
	private List<DefaultMutableTreeNode> getLeavesIterativ(DefaultMutableTreeNode node) {
		List<DefaultMutableTreeNode> reListe = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		 Enumeration<TreeNode> enumer = (Enumeration<TreeNode>) node.depthFirstEnumeration();
		while (enumer.hasMoreElements() == true) {

			DefaultMutableTreeNode blatt = (DefaultMutableTreeNode) enumer.nextElement();
			if (blatt.isLeaf() == true) {
				reListe.add(blatt);
			}

		}
		return reListe;
	}
	
	
	@Test
	public void testGetLeaves() {
		List<DefaultMutableTreeNode> leaves = getLeavesRekursiv(tnRoot);

		assertEquals(5, leaves.size());
		assertEquals(leaves.get(0).getUserObject(), "1.1.1 Leaf");
		assertEquals(leaves.get(1).getUserObject(), "1.1.2 Leaf");
		
	}
	/**
	 * gibt die Blätter eines Konoten zurück
	 * @param node
	 */
	private void printLeaves(DefaultMutableTreeNode node) {
		List<DefaultMutableTreeNode> list=getLeavesIterativ(node);
		for(DefaultMutableTreeNode blatt : list ) {
			System.out.println(blatt);
		}
	}
	/**
	 * alle kinder eines Knoten bekommen
	 * @param tnStart : der Knote von dem ich seine Kinder bekommen möchte
	 */
	public List<DefaultMutableTreeNode> getAlleKinder(DefaultMutableTreeNode tnStart) {
		List<DefaultMutableTreeNode> reliste = new ArrayList<>();
		// reliste.add(tnStart); start knote wird so angezeigt
		for (int i = 0; i < tnStart.getChildCount(); i++) {

			DefaultMutableTreeNode nodeChild = (DefaultMutableTreeNode) tnStart.getChildAt(i);
			reliste.add(nodeChild);// start knote wird da nicht angezeigt

			List<DefaultMutableTreeNode> lstChild = getAlleKinder(nodeChild);//alle obj rekusriv hinzufügen
			reliste.addAll(lstChild);

		}

		return reliste;
	
	}
	/**
	 * um das ausgeben zu können muss ich @before auskommntieren und setUp() methode in Main methode aufrufen
	 * @param node : man kann auch eine eingabe paramerter z.b tnStart und somit bekommt man alle unterknoten ein beliebigen Knoten,
	 * aber dafür muss mann die DefaultMutableTreeNode objekte in main methode definieren
	 */
	public void printAlleKinder (DefaultMutableTreeNode node) {
		List<DefaultMutableTreeNode> reliste= getAlleKinder(node);
		
		for(DefaultMutableTreeNode child: reliste) {
			System.out.println(child);
		}

	}
	@Test
	public void testgetKinder() {
		List<DefaultMutableTreeNode> list = getAlleKinder(tnParent1);
		// parent1 unterknoten
		/*1.1 Parent
		1.1.1 Leaf
		1.1.2 Leaf
		1.2 Leaf*/
		assertEquals(4,list.size());
		assertEquals("1.1 Parent",list.get(0).getUserObject());
		assertEquals("1.1.1 Leaf",list.get(1).getUserObject());
	
		
		
	}
	/**
	 * anzahl von Knoten rekusiv bekommen
	 * @param tnStart
	 * @return
	 */
	public int countTreeNodes(TreeNode tnStart) {
		if (tnStart == null) {
			return 0;
		}

		int iCount = 1; // 0+1 (==this node)
		for (int i = 0; i < tnStart.getChildCount(); i++) {
			TreeNode tnChild = tnStart.getChildAt(i);
			iCount += countTreeNodes(tnChild);
		}
		return iCount;
	}
	

	/**
	 * Gets all tree nodes of the tree (including the start tree node) meeting a
	 * condition
	 * hier kann man die Knoten mit einer Bedingung bekommen
	 * z.b nur die Knoten, die Blätter sind oder Parent sind
	 *
	 * @param cond IN: The condition to test as interface
	 *
	 * @return the treenodes meeting the condition
	 */
	public List<DefaultMutableTreeNode> getAllNodesWithCondition(Predicate<DefaultMutableTreeNode> cond) {
		List <DefaultMutableTreeNode> retList= new ArrayList<>();
		for(DefaultMutableTreeNode blatt : getAlleKinder(tnRoot)) {// am besten eine andere getAllKinder methode defineren, die kein Parm übergeben belommt
			if(cond.test(blatt)==true) {
				retList.add(blatt);
			}
			
		}
		// ToDo: Probieren Sie das mal selbst noch:
		//throw new UnsupportedOperationException();
		return retList;
	}
	@Test
	public void testgetAllNodesWithCondition() {
		
		List <DefaultMutableTreeNode>  list=getAllNodesWithCondition(new Predicate<DefaultMutableTreeNode>() {

			@Override
			public boolean test(DefaultMutableTreeNode t) {
				
				return t.isLeaf();
			}
		
			
		});
		
		
		assertEquals(5, list.size());
		assertEquals(list.get(0).getUserObject(), "1.1.1 Leaf");
		assertEquals(list.get(1).getUserObject(), "1.1.2 Leaf");
	}
	
	public static void main(String[] args) {
		TestDefaultMutableTreeNode test=new TestDefaultMutableTreeNode();
	
		System.out.println("......................");
		String strRoot = "0. Root";
		
		DefaultMutableTreeNode tnRoot = new DefaultMutableTreeNode(strRoot);
		String strParent1 = "1. Parent";
		DefaultMutableTreeNode tnParent1 = new DefaultMutableTreeNode(strParent1);
		DefaultMutableTreeNode tnParent2 = new DefaultMutableTreeNode("2. Parent");
		DefaultMutableTreeNode tnLeaf = new DefaultMutableTreeNode("3. Leaf");
		DefaultMutableTreeNode tnParent1_1 = new DefaultMutableTreeNode("1.1 Parent");
		DefaultMutableTreeNode tnLeaf1_2 = new DefaultMutableTreeNode("1.2 Leaf");
		DefaultMutableTreeNode tnLeaf1_1_1 = new DefaultMutableTreeNode("1.1.1 Leaf");
		DefaultMutableTreeNode tnLeaf1_1_2 = new DefaultMutableTreeNode("1.1.2 Leaf");
		DefaultMutableTreeNode tnLeaf2_1 = new DefaultMutableTreeNode("2.1 Leaf");
		
		tnRoot.add(tnParent1);
		tnRoot.add(tnParent2);
		tnRoot.add(tnLeaf);
		tnParent1.add(tnParent1_1);
		tnParent1.add(tnLeaf1_2);
		tnParent1_1.add(tnLeaf1_1_1);
		tnParent1_1.add(tnLeaf1_1_2);
		tnParent2.add(tnLeaf2_1);
		
		test.printAlleKinder(tnRoot);// alle kinder von root ausgeben
		System.out.println( "......................\n"
				+"alle blätter von root holen\n");
		test.printLeaves(tnRoot);// alle blätter von root holen
		System.out.println("......................");
		;
		System.out.println("Anzahl alle Knoten von Root: "+test.countTreeNodes(tnRoot));
		
		System.out.println("......................");
		
		
		
	
	}
	
	
	

}
