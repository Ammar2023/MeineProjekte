import org.junit.runner.*;
import org.junit.runner.notification.Failure;

public class Main {
	private final static Class<?> [] testClasses= {testg.class,testEnumAufgabe.class, TestDefaultMutableTreeNode.class};
	private static volatile boolean stopTest=false;
	public static String massage;
	
	public static void main(String[] args) {
		
		
	}
	
	public  void runTests() {
		for(Class<?> clazz: testClasses) {
			if(stopTest == true) {
				break;
			}
			Result result = JUnitCore.runClasses(clazz);

			for (Failure failure : result.getFailures()) {
				System.out.println("Fehler in Testklasse: " + failure.getDescription().getClassName() + ", "
						+ " bei folgender Methode: " + failure.getDescription().getMethodName() + "," 
						+ " mit folgender Fehlermeldung: " + failure.getMessage());
				
				//massage=massage+" test  "+failure.getMessage();
			}

			System.out.println(result.wasSuccessful() ? "all tests succeeded " : "Einige Tests sind fehlgeschlagen");
			
	 }
	}

}
