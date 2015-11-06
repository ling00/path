package Exploration;

import java.awt.Point;
import java.util.ArrayList;

public class ExploreToGoal {
	private   int nX=17;
	private 	 int nY=22;
	private Point start;//center point of robot
	private Point goal;//center point of goal
	private boolean explored[][];
	private boolean nowall[][];
	private boolean visited[][];
	private boolean realMap[][];
	int explorePercent=100;
	private Point cur;
	private ArrayList <Point>queue;

	ExploreToGoal(Point start,Point goal,boolean[][] realMap){
		Point temp;
		this.realMap=realMap;
		initialiseData(start, goal);
		queue.add(cur);
		int count=0;
		Point cur=this.cur;
		while(!cur.equals(goal)){
			count++;
			visited[cur.y][cur.x]=true;
			exploreSurrounding(cur);
			while(queue.contains(cur))
				queue.remove(cur);
			//if (queue.contains(cur))System.out.println("not removed22");		
			temp=nextPointToExplore();
			if(temp==null)
				break;
			else 
				cur.setLocation(temp.x,temp.y);
			//printExplore();
			
		}
		printExplore();
	}

	private Point nextPointToExplore() {
		// TODO Auto-generated method stub
		
		queue.trimToSize();
		if (queue.size()==0)return null;	
		int min=1000;
		int dist=1000;
		Point next = null;
		for(Point p:queue){
			dist=Math.abs(p.x-goal.x)+Math.abs(p.y-goal.y);
			//System.out.println(dist);
			if (dist<min){
				next=new Point(p);
				min=dist;
			}
		}
		return next;		
	}

	private Point nearestUnexplored(Point curr) {
		// TODO Auto-generated method stub
		Point temp=new Point(curr);
		for(int i=0;i<nY;i++){
				if( temp.y-i>0&&explored[temp.y-i][temp.x]==false)
					return new Point(temp.x,temp.y-i); 
				if( temp.y+i<nY&&explored[temp.y+i][temp.x]==false)
					return new Point(temp.x,temp.y+i); 
				if( temp.x-i>0&&explored[temp.y][temp.x-i]==false)
					return new Point(temp.x-i,temp.y); 
				if( temp.x+i<nX&&explored[temp.y][temp.x+i]==false)
					return new Point(temp.x+i,temp.y); 
		}
		return null;
	}

	private void printExplore() {
		// TODO Auto-generated method stub
		for(int i=0;i<nY;i++){
			System.out.println();
			for(int j=0;j<nX;j++){
				if (j==cur.x&&i==cur.y)
					System.out.print(" H ");
				else if(j==start.x&&i==start.y)
					System.out.print(" S ");
				else if(j==goal.x&&i==goal.y)
					System.out.print(" G ");
				else if(explored[i][j]==true&&nowall[i][j]==false)
					System.out.print(" X ");
				else if(queue.contains(new Point(j,i)))
					System.out.print(" q ");
				else if(explored[i][j]==true)
					System.out.print(" - ");
			
				else
					System.out.print(" m ");
			}
		}
		/*for(int i=0;i<nY;i++){
			System.out.println();
			for(int j=0;j<nX;j++){
				if (realMap[i][j]==true)
					System.out.print(" - ");
				else 
					System.out.print(" X ");
			
			}
		}*/


	}

	private void exploreSurrounding(Point cur) {
		// TODO Auto-generated method stub
		//System.out.println("exploring...");
		//System.out.println("cur:"+cur);
		for(int i=-1;i<2;i++){
			if (i==0)continue;			
	
			if (cur.y+i>0&&cur.y+i<nY){
				explored[cur.y+i][cur.x]=true;
				nowall[cur.y+i][cur.x]=realMap[cur.y+i][cur.x];
				if (nowall[cur.y+i][cur.x]==true&&
						visited[cur.y+i][cur.x]==false){
					//System.out.println("add:");
					queue.add(new Point(cur.x,cur.y+i));
					}
				}			
			if (cur.x+i>0&&cur.x+i<nX){
				explored[cur.y][cur.x+i]=true;
				nowall[cur.y][cur.x+i]=realMap[cur.y][cur.x+i];
				if (nowall[cur.y][cur.x+i]==true&&
						visited[cur.y][cur.x+i]==false){
					//System.out.println("add:");
					queue.add(new Point(cur.x+i,cur.y));
					}
				}
		}
		//System.out.println("end of exploring...");
	}

	private boolean finishedExploration() {
		// TODO Auto-generated method stub
		int acceptableUnExplored=3*(100-explorePercent);
		int unExplored=0;

		for(int i=1;i<nX-1;i++){
			for(int j=1;j<nY-1;j++){
				if (explored[j][i]==false)
					unExplored++;
				if (unExplored>acceptableUnExplored)
					return false;
			}
		}
		return true;
	}

	private void initialiseData(Point start, Point goal) {
		this.start=new Point(start);
		this.goal=new Point(goal);
		explored=new boolean [nY][nX];

		nowall=new boolean[nY][nX];
		visited=new boolean [nY][nX];
	
		for(int i=0;i<nX;i++){
			for(int j=0;j<nY;j++){
				explored[0][i]=true;
				explored[nY-1][i]=true;
				explored[j][nX-1]=true;
				explored[j][0]=true;
				visited[j][i]=false;
				nowall[j][i]=false;
				
			}
		}
		explored[goal.y][goal.x]=true;
		explored[start.y][start.x]=true;
		cur=new Point(start);

		queue=new ArrayList<Point>();
		nowall[cur.y][cur.x]=realMap[cur.y][cur.x];
		nowall[start.y][start.x]=realMap[start.y][start.x];
		nowall[goal.y][goal.x]=realMap[goal.y][goal.x];
		
	}


}
