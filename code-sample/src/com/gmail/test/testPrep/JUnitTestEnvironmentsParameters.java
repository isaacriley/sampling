/**
 * 
 */
package com.gmail.test.testPrep;

import java.util.Arrays;
import java.util.List;

import com.gmail.constants.GridConstants;

/**
 * This class' sole intention is facilitate the parametrization of Grid nodes to JUnit's Parameterized class
 * @author IRiley
 *
 */
public final class JUnitTestEnvironmentsParameters {
	//suppress default constructor
	private JUnitTestEnvironmentsParameters(){}
	
	/**
	 * The nodes for the grid are the parameters for each JUnit test
	 * @return Collection of nodes that are to be parameterized in the
	 * JUnit test case
	 */
	public static List<Object[]> initNodesForGrid(){
		Object [][]nodes={
				
			
				//{GridConstants.SAFARI_NODE},
				{GridConstants.CHROME_NODE_LOCAL},
				{GridConstants.FIREFOX_NODE_LOCAL},
				
		};
		return Arrays.asList(nodes);
	}

}
