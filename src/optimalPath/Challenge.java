/*
 * @author: lingyun
 * @date:5/9/2015
*/
package optimalPath;

import java.awt.Point;
import java.util.ArrayList;

public class Challenge {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Point goal=new Point(19,12); //x:1-13,y:1-18
		Point start=new Point(2,2);
		int x=17;
		int y=22;
		//boolean accessible[][]=new boolean[x-2][y-2];
		boolean accessible[][]=new boolean[y][x];
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){	
				accessible[j][i]=true;
			}
		}

		accessible[4][1]=false;
		accessible[4][2]=false;
		accessible[4][3]=false;
	
	
		for(int j=0;j<3;j++){

		accessible[j][3]=false;
		accessible[j][9]=false;
		accessible[7][j+4]=false;
		accessible[7][j+7]=false;
		accessible[12+j][5]=false;
		accessible[12+j][9]=false;
		accessible[5+j][14+j]=false;

	}
	accessible[7][0]=false;
	accessible[11][9]=false;
	accessible[11][10]=false;
	accessible[14][13]=false;
	accessible[14][14]=false;
	
	 			
		OptimalPath t=new OptimalPath(accessible, start,goal);
	}

}
