import java.util.*;

import static org.junit.Assert.*;
import static turban.utils.ReflectionUtils.*;
import org.junit.*;

public class testEnumAufgabe {
	
	@Test
	public void testPresident() {
		HSPersonellType hsp = HSPersonellType.President;
		assertTrue(hsp.hasOrgResp());
		assertFalse(hsp.isAdminStaff());
		//assertFalse(hsp.name() == "admin");
		assertTrue(hsp.givesLessons());
	}
	@Test
	public void testPresident2() {
		HSPersonellType hsp = HSPersonellType.President;
		assertTrue(hsp.hasOrgResp());
		assertFalse(hsp.isAdminStaff());
		//assertFalse(hsp.name() == "admin");
		assertTrue(hsp.givesLessons());
	}

}
