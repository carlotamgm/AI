package aima.core.environment.Canibales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * @author R. Lunde
 */
public class CanibalesBoard {

	public static Action Mover1C = new DynamicAction("M1C");

	public static Action Mover2C = new DynamicAction("M2C");

	public static Action Mover1M = new DynamicAction("M1M");

	public static Action Mover2M = new DynamicAction("M2M");
	
	public static Action Mover1M1C = new DynamicAction("M1M1C");

	private int[] state;

	//
	// PUBLIC METHODS
	//

	public CanibalesBoard() {
		state = new int[] { 3, 3, 0, 0, 0 };
	}

	public CanibalesBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public CanibalesBoard(CanibalesBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}
/*
	public int getValueAt(XYLocation loc) {
		return getValueAt(loc.getXCoOrdinate());
	}
*/
	public void mover1C() {
		if(getValueAt(2) == 0) {
			setValue(2, 1);
			setValue(1, getValueAt(1)-1);
			setValue(4, getValueAt(4)+1);
		} else {
			setValue(2, 0);
			setValue(1, getValueAt(1)+1);
			setValue(4, getValueAt(4)-1);
		}
		
	}

	public void mover2C() {
		if(getValueAt(2) == 0) {
			setValue(2, 1);
			setValue(1, getValueAt(1)-2);
			setValue(4, getValueAt(4)+2);
		} else {
			setValue(2, 0);
			setValue(1, getValueAt(1)+2);
			setValue(4, getValueAt(4)-2);
		}
	}

	public void mover1M() {
		if(getValueAt(2) == 0) {
			setValue(2, 1);
			setValue(0, getValueAt(0)-1);
			setValue(3, getValueAt(3)+1);
		} else {
			setValue(2, 0);
			setValue(0, getValueAt(0)+1);
			setValue(3, getValueAt(3)-1);
		}

	}

	public void mover2M() {
		if(getValueAt(2) == 0) {
			setValue(2, 1);
			setValue(0, getValueAt(0)-2);
			setValue(3, getValueAt(3)+2);
		} else {
			setValue(2, 0);
			setValue(0, getValueAt(0)+2);
			setValue(3, getValueAt(3)-2);
		}
	}
	
	public void mover1M1C() {
		if(getValueAt(2) == 0) {
			setValue(2, 1);
			setValue(1, getValueAt(1)-1);
			setValue(4, getValueAt(4)+1);
			setValue(0, getValueAt(0)-1);
			setValue(3, getValueAt(3)+1);
		} else {
			setValue(2, 0);
			setValue(1, getValueAt(1)+1);
			setValue(4, getValueAt(4)-1);
			setValue(0, getValueAt(0)+1);
			setValue(3, getValueAt(3)-1);
		}
	
	}

	public boolean canMoveGap(Action where) {

		boolean retVal = true;

		if (where.equals(Mover1C))
			retVal = (getValueAt(2) == 0 && getValueAt(1) >=1 && ((getValueAt(3)>=getValueAt(4)+1) || (getValueAt(3) == 0) )
					|| (getValueAt(2) == 1 && getValueAt(4) >=1 && ((getValueAt(0)>=getValueAt(1)+1) || (getValueAt(0) == 0) )) );
		else if (where.equals(Mover1M))
			retVal = (getValueAt(2) == 0 && getValueAt(0) >=1 && ((getValueAt(0)>=getValueAt(1)+1) || (getValueAt(0) == 1) ) && (getValueAt(3)>=getValueAt(4) -1))
			|| (getValueAt(2) == 1 && getValueAt(3) >=1 && ((getValueAt(3)>=getValueAt(4)+1) || (getValueAt(3) == 1) ) && (getValueAt(0)>=getValueAt(1) -1));
		else if (where.equals(Mover2C))
			retVal = (getValueAt(2) == 0 && getValueAt(1) >=2 && ((getValueAt(3)>=getValueAt(4)+2) || (getValueAt(3) == 0) ))
			|| getValueAt(2) == 1 && getValueAt(4) >=2 && ((getValueAt(0)>=getValueAt(1)+2) || (getValueAt(0) == 0) );
		else if (where.equals(Mover2M))
			retVal = (getValueAt(2) == 0 && getValueAt(0) >=2 && ((getValueAt(0)>=getValueAt(1)+2) || (getValueAt(0) == 2) ) && (getValueAt(3)>=getValueAt(4) -2))
			|| getValueAt(2) == 1 && getValueAt(3) >=2 && ((getValueAt(3)>=getValueAt(4)+2) || (getValueAt(3) == 2) ) && (getValueAt(0)>=getValueAt(1) -2 );
		else if (where.equals(Mover1M1C))
			retVal = (getValueAt(2) == 0 && getValueAt(0) >=1 && getValueAt(1) >=1 && getValueAt(3)>=getValueAt(4))
			|| (getValueAt(2) == 1 && getValueAt(3) >=1 && getValueAt(4) >=1 && getValueAt(0)>=getValueAt(1));
		return retVal;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CanibalesBoard other = (CanibalesBoard) obj;
		return Arrays.equals(state, other.state);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(state);
		return result;
	}

	@Override
	public String toString() {
		
		String retVal ="		    RIBERA-IZQ  ";
	
		for(int i=0; i<getValueAt(0); i++) {
			retVal += "M";
		}
		for(int i=0; i<=3-getValueAt(0); i++) {
			retVal += "  ";
		}

		for(int i=0; i<getValueAt(1); i++) {
			retVal += "C";
		}
		for(int i=0; i<= 3-getValueAt(1); i++) {
			retVal += "  ";
		}
		
	
		retVal += "--RIO--";
		
		if (getValueAt(2) == 1) {
			retVal += "	BOTE ";
		} else if (getValueAt(2) == 0) {
			retVal += "	     ";
		}
		
		for(int i=0; i<getValueAt(3); i++) {
			retVal += "M ";
		}
		for(int i=0; i<= 3-getValueAt(3); i++) {
			retVal += "  ";
		}
		for(int i=0; i<getValueAt(4); i++) {
			retVal += "C ";
		}
		for(int i=0; i<= 3-getValueAt(4); i++) {
			retVal += "  ";
		}
		
		retVal += "RIBERA-DCH\n";
		
		return retVal;
	}

	//
	// PRIVATE METHODS
	//

	/**
	 * Note: The graphic representation maps x values on row numbers (x-axis in
	 * vertical direction).
	 */

	private int getXCoord(int absPos) {
		return absPos / 3;
	}

	/**
	 * Note: The graphic representation maps y values on column numbers (y-axis
	 * in horizontal direction).
	 */
	/*
	private int getYCoord(int absPos) {
		return absPos % 3;
	}

	private int getAbsPosition(int x, int y) {
		return x * 3 + y;
	}
*/
	private int getValueAt(int x) {
		// refactor this use either case or a div/mod soln
		return state[x];
	}
/*
	private int getGapPosition() {
		return getPositionOf(0);
	}
*/
	private int getPositionOf(int val) {
		int retVal = -1;
		for (int i = 0; i < 5; i++) {
			if (state[i] == val) {
				retVal = i;
			}
		}
		return retVal;
	}

	private void setValue(int x, int val) {
		state[x] = val;

	}
}