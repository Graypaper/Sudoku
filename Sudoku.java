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
	static int [][] testBoard2 =    {{1,8,0,4,0,0,0,9,0},
									{0,0,9,0,7,1,0,0,8},
									{0,4,0,5,0,0,1,0,6},
									{0,0,3,0,5,0,0,7,0},
									{9,0,6,0,8,0,5,0,2},
									{0,1,0,0,4,9,3,0,0},
									{8,0,4,0,0,5,0,3,0},
									{7,0,0,8,3,0,6,0,0},
									{0,3,0,0,0,4,0,8,5},};
									//del 3 1 3
									//del 7 2 5
									//del 8 4 8
	private int maxSize = 9;
	private int[][] board = new int[maxSize][maxSize];
	public int solveNum = 0;
	private int[][][] choice = new int[maxSize][maxSize][maxSize];
	public Sudoku(){
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				board[i][j] = 0;
			}
		}
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				for(int k=0;k<maxSize;k++)choice[i][j][k]=0;
			}
		}
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
						//System.out.println("value: " + value);
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
						//System.out.println("break");
						tryTimes=0;
						for(int k=0;k<maxSize;k++){
							board[i][k] = 0;
						}
						j=-1;
						/*
						if(i<0){
								i=0;
								System.out.println("i = -");
						}
						*/
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
		printBoard(board);
	}
	
	public void solve(int[][] b){
		boolean fillAns = false;
		boolean otherAns = false;
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				if(b[i][j] == 0){
					boolean findAns = false;
					for(int k=1;k<maxSize+1;k++){
						if(check(b,i,j,k)){
							//if(choice[i][j][k-1]==0){
								System.out.println("find  i " + i + " j "+ j + " ans is " + k);
								int[][] copy = copyBoard(b);
								copy[i][j] = k;
								//if(i==6 && j==5)printBoard(copy);
								//choice[i][j][k-1]=1;
								//System.out.println("solveNum check1 " + solveNum);
								//solveNum+=solve(copy);
								solve(copy);
								//System.out.println("solveNum check2 " + solveNum);
								fillAns = true;
								findAns = true;
							//}
							//else{
							//	otherAns = true;
							//}
							findAns = true;
							
						}
						
						else if(k==maxSize && !findAns){
							System.out.println("i " + i + " j " + j +" find no ans return");
							//return ;
						}
						
					}
					return ;
				}
			}
		}
		//if(fillAns){
			//if(!otherAns)
			solveNum++;
			System.out.println("return solveNum " + solveNum);
			//printBoard(testBoard);
			//return solveNum;
		//}
		//else return 0;
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
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				System.out.print(b[i][j] + " ");
				if((j+1)%3==0)
					System.out.print("| ");
			}
			System.out.print("\n");
			if((i+1)%3==0)
				System.out.println("-----------------------");
			
		}
	}
	
	public boolean check(int[][] b,int r,int c,int value){
		int blockNum = (c/3)+(r/3)*3;
		int bRow,bCol;
		for(int i=0;i<maxSize;i++){
			if(b[r][i]==value && i!=c){
				//if(r==6 && c==6)System.out.println("row wrong");
				return false;
			}
			if(b[i][c]==value && i!=r){
				//if(r==6 && c==6)System.out.println("col wrong");
				return false;
			}
			bRow = (i/3)+(blockNum/3)*3;
			bCol = (i%3)+(blockNum%3)*3;
			if(b[bRow][bCol]==value && !(bRow==r && bCol==c)){
				//if(bRow==6 && bCol==6)System.out.println("block wrong");
				return false;
			}
		}
		return true;
	}
	
	public static void main(String args[]){
		Sudoku sudoku = new Sudoku();
		//sudoku.printBoard();
		//sudoku.genAns();
		//sudoku.printBoard();
		sudoku.printBoard(testBoard2);
		sudoku.solve(testBoard2);
		System.out.println(sudoku.solveNum);
	}
}