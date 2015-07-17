import java.util.*;

public class Sudoku{
	private int maxSize = 9;
	private int[][] board = new int[maxSize][maxSize];
	
	public Sudoku(){
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				board[i][j] = 0;
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
					if(check(i,j,value)){
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
						System.out.println("break");
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
	}
	
	public int sovle(){
		int ans = 0;
		return ans;
	}
	
	public void printBoard(){
		for(int i=0;i<maxSize;i++){
			for(int j=0;j<maxSize;j++){
				System.out.print(board[i][j] + " ");
				if((j+1)%3==0)
					System.out.print("| ");
			}
			System.out.print("\n");
			if((i+1)%3==0)
				System.out.println("-----------------------");
			
		}
	}
	
	public boolean check(int r,int c,int value){
		int blockNum = (c/3)+(r/3)*3;
		int bRow,bCol;
		for(int i=0;i<maxSize;i++){
			if(board[r][i]==value && i!=c)
				return false;
			if(board[i][c]==value && i!=r)
				return false;
			bRow = (i/3)+(blockNum/3)*3;
			bCol = (i%3)+(blockNum%3)*3;
			if(board[bRow][bCol]==value && !(bRow==r && bCol==c))
				return false;
		}
		return true;
	}
	
	public static void main(String args[]){
		Sudoku sudoku = new Sudoku();
		//sudoku.printBoard();
		sudoku.genAns();
		sudoku.printBoard();
	}
}