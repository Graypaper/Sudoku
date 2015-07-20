import java.util.*;

public class Sudoku{
	static int [][] testBoard =    {{8,6,5,7,1,3,9,2,4},
									{7,4,2,6,9,5,1,3,8},
									{1,3,9,4,2,8,7,5,6},
									{3,2,1,8,6,4,5,7,9},
									{9,8,4,5,7,1,2,6,3},
									{5,7,6,2,3,0,0,0,0},
									{6,9,8,1,5,7,3,4,2},
									{2,5,3,9,4,6,8,1,7},
									{4,1,7,3,8,2,6,9,5},};
									//del 3 1 3
									//del 7 2 5
									//del 8 4 8
	static int [][] testBoard2 =   {{3,0,0,9,0,7,0,6,0},
									{6,0,0,0,1,0,9,2,0},
									{0,9,1,0,0,5,0,4,0},
									{0,0,7,0,9,1,0,0,4},
									{4,3,0,0,0,2,0,1,6},
									{1,0,0,0,0,8,2,0,0},
									{0,1,0,8,0,0,7,9,0},
									{0,6,3,0,5,0,0,0,2},
									{0,4,0,7,0,3,0,0,1},};
									//del 3 1 3
									//del 7 2 5
									//del 8 4 8
	private int maxSize = 9;
	private int[][] board = new int[maxSize][maxSize];
	public int[][] playerBoard = new int[maxSize][maxSize];
	public int solveNum = 0;
	private int[][][] choice = new int[maxSize][maxSize][maxSize];
	public Sudoku(){
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				board[i][j] = 0;
			}
		}
	}
	
	public void init(){
		genAns();
		digBoard(playerBoard);
		boolean win = false;
		int value,i,j;
		Scanner scan = new Scanner(System.in);
		String line;
		while(!win){
			printBoard(playerBoard);
			System.out.println("input answer(value row col): ");
			value = scan.nextInt();
			i = scan.nextInt();
			j = scan.nextInt();
			if(i<1 || i>9 || j<1 || j>9){
				System.out.println("invalid location");
				continue;
			}
			if(value<1 || value>9){
				System.out.println("invalid value");
				continue;
			}
			if(!check(playerBoard,i-1,j-1,value)){
				System.out.println("wrong answer");
				continue;
			}
			else{
				playerBoard[i-1][j-1]=value;
				win = winCheck(playerBoard,board);
			}
		}
		System.out.println("you win");
	}
	
	public void genAns(){
		Random ran = new Random();
		Set<Integer> set = new HashSet<Integer>();
		int tryLimit = 9;
		int tryTimes = 0;
		int tryLineLimit = 1000000;
		int tryLineTimes = 0;
		int value;
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				boolean done = false;
				while(!done){
					do{
						value = ran.nextInt(9)+1;
					}while(set.contains(value));
					if(check(board,i,j,value)){
						board[i][j] = value;
						tryTimes=0;
						
						set.clear();
						done = true;
					}
					else{
						tryTimes++;
						tryLineTimes++;
						set.add(value);
					}
					if(tryTimes>=tryLimit){
						tryTimes=0;
						for(int k=0;k<maxSize;k++){
							board[i][k] = 0;
						}
						j=-1;
						
						set.clear();
						done = true;
					}
					if(tryLineTimes>=tryLineLimit){
						for(int k=0;k<maxSize;k++){
							board[i][k] = 0;
						}
						i--;
						j=-1;
						if(i<0)i=0;
						tryLineTimes=0;
						set.clear();
						done = true;
					}
				}
			}
		}
		playerBoard = copyBoard(board);
	}
	
	public void solve(int[][] b){
		boolean fillAns = false;
		boolean otherAns = false;
		int[][] copy = new int[maxSize][maxSize];
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				if(b[i][j] == 0){
					boolean findAns = false;
					for(int k=1;k<maxSize+1;k++){
						if(check(b,i,j,k)){
								copy = copyBoard(b);
								copy[i][j] = k;
								solve(copy);
								fillAns = true;
								findAns = true;
							findAns = true;
						}
						
						else if(k==maxSize && !findAns){
						}
						
					}
					return ;
				}
			}
		}
		//printBoard(b);
		solveNum++;
	}
	
	public void digBoard(int[][] b){
		Random ran = new Random();
		int i,j,ri,rj;
		for(int k=0;k<maxSize*maxSize/4;k++){
			do{
				i=ran.nextInt(9);
				j=ran.nextInt(9);
				ri = maxSize-1-i;
				rj = maxSize-1-j;
			}while(b[i][j]==0);
		
			int temp1 = b[i][j];
			int temp2 = b[ri][rj];
			b[i][j] = 0;
			b[ri][rj] = 0;
			solveNum=0;
			solve(b);
			if(solveNum>1){
				b[i][j] = temp1;
				b[ri][rj] = temp2;
				k--;
			}
		}
		printBoard(b);
	}
	
	public int[][] copyBoard(int[][] b){
		int[][] copy= new int[maxSize][maxSize];
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				copy[i][j] = b[i][j];
			}
		}
		return copy;
	}
	
	public void printBoard(int[][] b){
		System.out.println();
		System.out.println(" -----------------------");
		
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				if(j==0)
					System.out.print("| ");
				System.out.print(b[i][j] + " ");
				if((j+1)%3==0)
					System.out.print("| ");
			}
			System.out.print("\n");
			if((i+1)%3==0)
				System.out.println(" -----------------------");
			
		}
		System.out.println();
	}
	
	public boolean check(int[][] b,int r,int c,int value){
		int blockNum = (c/3)+(r/3)*3;
		int bRow,bCol;
		for(int i=0;i<maxSize;i++){
			if(b[r][i]==value && i!=c){
				return false;
			}
			if(b[i][c]==value && i!=r){
				return false;
			}
			bRow = (i/3)+(blockNum/3)*3;
			bCol = (i%3)+(blockNum%3)*3;
			if(b[bRow][bCol]==value && !(bRow==r && bCol==c)){
				return false;
			}
		}
		return true;
	}
	
	public boolean winCheck(int[][] b,int[][] ansB){
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				if(b[i][j]!=ansB[i][j])
					return false;
			}
		}
		return true;
	}
	
	public static void main(String args[]){
		Sudoku sudoku = new Sudoku();
		//sudoku.printBoard();
		sudoku.init();
		//sudoku.printBoard(testBoard2);
		//sudoku.printBoard();
		//sudoku.printBoard(testBoard2);
		//sudoku.solve(testBoard2);
		//System.out.println(sudoku.solveNum);
	}
}