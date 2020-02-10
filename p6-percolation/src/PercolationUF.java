public class PercolationUF implements IPercolate {
	private IUnionFind myFinder;
	private boolean[][] myGrid;
	private final int VTOP;
	private final int VBOTTOM;
	private int myOpenCount;
	
	public PercolationUF(IUnionFind finder, int size) {
		myGrid = new boolean[size][size]; //initializing size of myGrid two-dimensional boolean array

        myOpenCount = 0; //setting myOpenCount to initial value

        finder.initialize(size*size+2); //initializing IUnionFind object with size
        myFinder = finder; //storing finder object in myFinder

        //setting top and bottom row values
        VTOP = size * size;
        VBOTTOM = size * size + 1;
	}
	
	@Override
	public boolean isOpen(int row, int col) {
		 if (!inBounds(row, col)) {
	            throw new IndexOutOfBoundsException(
	                    String.format("(%d,%d) not in bounds", row, col));
	        }

	        //returning appropriate myGrid value
	        return myGrid[row][col];
	}
	
	
	@Override
	public boolean isFull(int row, int col) {
		if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }

        //checking if the cell is connected to VTOP
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

	        myOpenCount += 1; //iterating myOpenCount by 1 to track opened cells
	        myGrid[row][col] = true; //opening cell


	        int[] rowDelta = {-1, 0, 0, 1}; //declaring rowDelta for easy traversal in loop
	        int[] colDelta = {0, -1, 1, 0}; //declaring colDelta for easy traversal in loop
	        int vRow = row;
	        int vCol = col;

	        if(vRow == 0){ //checking if in first row
	            myFinder.union((vRow*myGrid.length +  vCol), VTOP); //joining set to VTOP
	        }
	        if(vRow == myGrid.length-1){ //checking if in last row
	            myFinder.union((vRow*myGrid.length + vCol), VBOTTOM); //joining set to VBOTTOM
	        }

	        for (int i = 0; i < rowDelta.length; i++) {
	            row = vRow + rowDelta[i]; //applying row shift but using new variable to prevent spillover error
	            col = vCol + colDelta[i]; //applying col shift but using new variable to prevent spillover error
	            if (inBounds(row, col) && isOpen(row, col)) { //checking if cell is in bounds, is open, and not full
	                myFinder.union((vRow*myGrid.length +vCol), (row*myGrid.length+ col)); //joining sets through union
	            }
	        }
	}

	
	protected boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}

}

