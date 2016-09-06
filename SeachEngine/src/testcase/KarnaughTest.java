package testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import karnaugh.KarnaughMap;

public class KarnaughTest {

	KarnaughMap km1, km2;
	
	@Before
	public void setUp() throws Exception {
		String values[] = new String[]{"tool","visual","java","linux"};
		
		km1 = new KarnaughMap("tool'.visual.java'+tool.linux+visual.java'.linux'",values);
		km2 = new KarnaughMap("visual.java'+tool.linux",values);
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
		
		assertTrue(km1.equals(km2));
	}

}
