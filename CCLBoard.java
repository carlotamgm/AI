package aima.core.environment.TP6;

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
public class CCLBoard {

	public static Action MoverCabra = new DynamicAction("MCabra");

	public static Action MoverCol = new DynamicAction("MCol");

	public static Action MoverLobo = new DynamicAction("MLobo");
	
	public static Action MoverBarcaVacía = new DynamicAction("MBarca");
	
	private int[] state;

	//
	// PUBLIC METHODS
	//

	public CCLBoard() {
		
		state = new int[] { 0, 0, 0, 0 };
	}

	public CCLBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public CCLBoard(CCLBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}
	
	public void moverBarcaVacía() {
		if(getValueAt(3) == 0) {
			setValue(3,1);
			
		} else if(getValueAt(3) == 1) {
			setValue(3,0);
		}
	}
	
	public void moverCabra() {
		if(getValueAt(3) == 0) {
			setValue(1, 1);
			setValue(3,1);
	
		} else if(getValueAt(3) == 1){
			setValue(1,0);
			setValue(3,0);
		}
		
	}

	public void moverCol() {
		if(getValueAt(3) == 0) {
			setValue(2, 1);
			setValue(3,1);
		} 
	}

	public void moverLobo() {
		if(getValueAt(3) == 0) {
			setValue(0, 1);
			setValue(3,1);

		} 

	}

	public boolean canMoveGap(Action where) {

		boolean retVal = true;

		if (where.equals(MoverCabra))
			retVal = true;
		else if (where.equals(MoverCol))
			if(getValueAt(3) == 0) {
				retVal = (getValueAt(1) != getValueAt(0));
			} else if(getValueAt(3) == 1) {
				retVal = getValueAt(1) != getValueAt(0);
			}	
			
		else if (where.equals(MoverLobo))
			if(getValueAt(3) == 0) {
				retVal = (getValueAt(1) != getValueAt(2));
			} else if(getValueAt(3) == 1) {
				retVal = getValueAt(1) != getValueAt(2);
			}
			
		else if (where.equals(MoverBarcaVacía))
			retVal = ( getValueAt(0) != getValueAt(1) ) && (getValueAt(2) != getValueAt(1));
			
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
		CCLBoard other = (CCLBoard) obj;
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
		
		String retVal ="\n\n 	RIBERA-IZQ  \n";
	
		if (state[0] == 0) {
			retVal += " Lobo";
		} else {
			retVal += "    ";
		}
			
		if (state[1] == 0) {
			retVal += " Cabra" ;
		} else {
			retVal += "    ";
		}
		
		if (state[2] == 0) {
			retVal += " Col";
		} else {
			retVal += "    ";
		}
	
		retVal += "\n	--RIO--\n";
		
		if (state[0] == 1) {
			retVal += " Lobo";
		} else {
			retVal += "    ";
		}
			
		if (state[1] == 1) {
			retVal += " Cabra" ;
		} else {
			retVal += "    ";
		}
			
		if (state[2] == 1) {
			retVal += " Col";
		} else {
			retVal += "    ";
		}
		
		retVal += "\n	RIBERA-DCH\n";
		
		return retVal;
	}

	//
	// PRIVATE METHODS
	//

	/**
	 * Note: The graphic representation maps x values on row numbers (x-axis in
	 * vertical direction).
	 */

	private int getValueAt(int x) {
		// refactor this use either case or a div/mod soln
		return state[x];
	}

	private void setValue(int x, int val) {
		state[x] = val;

	}
}