
public class PercolateUF implements IPercolate {
	private IUnionFind myFinder;
	private boolean[][] myGrid;
	private final int VTOP;
	private final int VBOTTOM;
	private int myOpenCount;
	
	public PercolateUF(IUnionFind finder, int size) {
		myGrid = new boolean[size][size]; 

        myOpenCount = 0; 

        finder.initialize(size*size+2); 
        myFinder = finder; 
        
        VTOP = size * size;
        VBOTTOM = size * size + 1;
	}
	
	@Override
	public boolean isOpen(int row, int col) {
		 if (!inBounds(row, col)) {
	            throw new IndexOutOfBoundsException(
	                    String.format("(%d,%d) not in bounds", row, col));
	        }

	        
	        return myGrid[row][col];
	}
	
	
	@Override
	public boolean isFull(int row, int col) {
		if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }

        
        return myFinder.connected((row*myGrid.length+ col), VTOP);
	}
	
	
	@Override
	public boolean percolates() {
		// TODO Auto-generated method stub
		if (myFinder.connected(VTOP, VBOTTOM)) {
            return true;
        } else {
            return false;
        }
	}
	
	@Override
	public int numberOfOpenSites() {
		// TODO Auto-generated method stub
		return myOpenCount;
	}

	@Override
	public void open(int row, int col) {
		// TODO Auto-generated method stub
		 if (!inBounds(row, col)) {
	            throw new IndexOutOfBoundsException(
	                    String.format("(%d,%d) not in bounds", row, col));
	        }

	        if (myGrid[row][col] == true)
	            return;

	        myOpenCount += 1; 
	        myGrid[row][col] = true; 
	        int[] rowDelta = {-1, 0, 0, 1}; 
	        int[] colDelta = {0, -1, 1, 0}; 
	        int vRow = row;
	        int vCol = col;

	        if(vRow == 0){ 
	            myFinder.union((vRow*myGrid.length +  vCol), VTOP); 
	        }
	        if(vRow == myGrid.length-1){ 
	            myFinder.union((vRow*myGrid.length + vCol), VBOTTOM); 
	        }

	        for (int i = 0; i < rowDelta.length; i++) {
	            row = vRow + rowDelta[i]; 
	            col = vCol + colDelta[i]; 
	            if (inBounds(row, col) && isOpen(row, col)) { 
	                myFinder.union((vRow*myGrid.length +vCol), (row*myGrid.length+ col));  
	            }
	        }
	}

	
	protected boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}

}
