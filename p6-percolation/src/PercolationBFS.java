import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast{

	public PercolationBFS(int n) {
		super(n);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dfs(int row, int col) {
		int size = myGrid.length;
	        int[] rowDelta = {-1,0,0,1};
	        int[] colDelta = {0,-1,1,0};
	    
	        
	    if (!inBounds(row, col)) return;

		Queue<Integer> qu = new LinkedList<>();
		myGrid[row][col] = FULL;
		
		qu.add(row * size + col);
		while(qu.size() != 0){
			int value = qu.remove();
			for (int k=0; k< rowDelta.length; k++) {
				row = value/size +rowDelta[k];
				col = value%size + colDelta[k];
				if (inBounds(row,col) && isOpen(row,col) &&!(isFull(row,col))) {
					myGrid[row][col]= FULL;
					qu.add(row * size + col);
					
				}
			}
			
			
		}
		
		
		
		
		
	}

}
