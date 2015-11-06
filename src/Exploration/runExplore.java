package Exploration;

import java.awt.Point;

import optimalPath.OptimalPath;

public class runExplore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Point goal=new Point(12,19); //x:1-13,y:1-18
		Point start=new Point(2,2);
		int x=17;
		int y=22;
		
		boolean accessible2[][]=new boolean[y][x];
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){	
				accessible2[j][i]=true;
				
			}
		}
		
		for(int i=0;i<y;i++){
			accessible2[i][0]=false;
			accessible2[i][x-1]=false;
		}
		for(int i=0;i<x;i++){
			accessible2[0][i]=false;
			accessible2[y-1][i]=false;
		}
		for(int j=1;j<6;j++){
			accessible2[6][j]=false;
			accessible2[7][j]=false;
			accessible2[7][2+j]=false;
			accessible2[8][3+j]=false;
			accessible2[8][3+j]=false;
			accessible2[12][2+j]=false;
			accessible2[14+j][15]=false;
	
		}
		
		accessible2[4][1]=false;
		accessible2[4][2]=false;
		accessible2[4][3]=false;
	
	
		accessible2[7][14]=false;
		accessible2[1][10]=false;
		accessible2[2][10]=false;
		accessible2[1][11]=false;
		accessible2[14][4]=false;
		accessible2[9][0]=false;
		accessible2[6][0]=false;
		accessible2[6][1]=false;
		accessible2[13][12]=false;
		accessible2[13][13]=false;

	accessible2[7][0]=false;
	accessible2[11][9]=false;
	accessible2[11][10]=false;
	accessible2[14][13]=false;
	accessible2[14][14]=false;
	accessible2[8][12]=false;    // case 1
	 		
	for(int j=0;j<3;j++){
		accessible2[j][7]=false;
		accessible2[3][j]=false;
		accessible2[j][13]=false;
		accessible2[6][9+j]=false;
		accessible2[7+j][13]=false;
		accessible2[9+j][16]=false;
		accessible2[11+j][8]=false;
	}
	 			
	
		ExploreToGoal e=new ExploreToGoal(start,goal,accessible2);
	}

}
