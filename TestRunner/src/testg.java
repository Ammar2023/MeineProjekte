import javax.naming.spi.DirStateFactory.Result;
import javax.swing.*;
import javax.swing.tree.*;

import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import turban.utils.IGuifiable;
import turban.utils.*;
import org.junit.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class testg {
	private boolean sleapAktivieren=true;
	@Test
	public void test() {
		if(sleapAktivieren){
			try {
				Thread.sleep(1000);
				assertEquals(10, 9-2);
				
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	@Ignore
	public void testignore() {
		assertEquals(4, 2+2);
	}
	@Test
	public void testSleap() {
		if(sleapAktivieren){
			try {
				Thread.sleep(1000);
				assertEquals(10, 9+2);
				
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
	}

}
