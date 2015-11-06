
/*
 * @author: lingyun
 * @date:5/9/2015
 */

package optimalPath;
import java.awt.Point;
import java.util.ArrayList;


public class OptimalPath {


	private   int nX=22;
	private 	 int nY=17;
	private Point start;//center point of robot
	private Point goal;//center point of goal
	private  int distance[][];
	private boolean tree[][];
	private ArrayList<Point> shortestPath;
	private boolean accessible[][];
	private int distancePerStep=1;
	private int stepCost=1;
	private  ArrayList <Point> queue;
	private int estimateCost[][];
	private int possibleTotalCost[][];
	private boolean realMap[][];

	public OptimalPath(boolean accessiblity2[][],Point start,Point goal)
	{	//start.setLocation(start.y,start.x);
		//goal.setLocation(goal.y,goal.x);
	
		calculateDistance(accessiblity2, start, goal);
		//printMapData();		
		hasPathToGoal();
		 printOptimalPath();
		
	}
	private void exploreToGoal(ArrayList<Point> unexploredPts) {
		// TODO Auto-generated method stub
		System.out.println( "exploring");
		Point startPointToExplore=getTheStartPointToExplore(unexploredPts);
		ArrayList<Point> pathToStartOfExplore=getOptimalPath(start,startPointToExplore);
		exploreTheRest(startPointToExplore,goal);
		
	}
	private void exploreTheRest(Point startPointToExplore,Point goal) {
		// TODO Auto-generated method stub		
		Point here=new Point(startPointToExplore);
		while(here!=goal||hasPathToGoal()){
			//System.out.println( "here:"+here);
			updateTheAccessibilityMap(here);
			here=nextExploringPt(here);
		}
	}
	private Point nextExploringPt(Point here) {
		// TODO Auto-generated method stub
		ArrayList <Point>surroundingPts=getSurroundingPt(here,distancePerStep,false);
		return  nearestPointToGoal(surroundingPts);
		
	}
	private void updateTheAccessibilityMap(Point here) {
		// TODO Auto-generated method stub
		
		
	}
	private void printEstimateCost() {
		// TODO Auto-generated method stub
		if (estimateCost==null)return;
		for(int i=0;i<nY;i++){
			System.out.println();
			for(int j=0;j<nX;j++){
				int dd=estimateCost[j][i];
				System.out.printf( "%-5d ", dd);
			}
		}
		
	}
	public void calculateEstimateCost(){
		int estCost=1000;
		estimateCost=new int[nX][nY];
		Point temp=new Point(goal);
		for(int i=0;i<nY;i++){
			for(int j=0;j<nX;j++){
				estCost=Math.abs(goal.x-j)+Math.abs(goal.y-i);
				estimateCost[j][i]=estCost;
			}
		}
	}
	public void calculateTotalCost(){
		int estCost=1000;
		possibleTotalCost=new int[nX][nY];
		Point temp=new Point(goal);
		for(int i=0;i<nY;i++){
			for(int j=0;j<nX;j++){
				possibleTotalCost[j][i]=distance[j][i]+estimateCost[j][i];
			}
		}
	}
	public Point getTheStartPointToExplore(ArrayList<Point> unexploredPts){
		ArrayList posibleStartPts= getUnexploredPtWithPathToIt(unexploredPts);
		Point nearestPointToGoal=nearestPointToGoal(posibleStartPts);
		return nearestPointToGoal;	
	}
	public ArrayList getUnexploredPtWithPathToIt(ArrayList<Point> unexploredPts){
		ArrayList<Point> surroundingPts=new ArrayList();
		ArrayList<Point> unexploredPtWithPathToIt=new ArrayList();
		ArrayList indexToRemove=new ArrayList();
		for(int i=0;i<unexploredPts.size();i++){
			Point unexplored=new Point(unexploredPts.get(i));
			surroundingPts=getSurroundingPt(unexplored,distancePerStep,false);
			for(Point p:surroundingPts){
				if(distance [p.x][p.y]<1000){
					unexploredPtWithPathToIt.add(unexplored);
					break;
				}		
			}
		}
		return unexploredPtWithPathToIt;	
	}

	public Point nearestPointToGoal(ArrayList<Point> pts){
		int minDistToGoal=1000;
		Point nearestPointToGoal=new Point();
		for (Point p:pts){
			if (possibleTotalCost[p.x][p.y]<minDistToGoal)
				{
				minDistToGoal=possibleTotalCost[p.x][p.y];
				nearestPointToGoal.setLocation(p);
				}
		}
		return nearestPointToGoal;
		
	}
	private void printPossibleTotalCost() {
		// TODO Auto-generated method stub
		if (possibleTotalCost==null)return;
		for(int i=0;i<nY;i++){
			System.out.println();
			for(int j=0;j<nX;j++){
				int dd=possibleTotalCost[j][i];
				System.out.printf( "%-5d ", dd);
			}
		}
		
	}
	
	
	public boolean hasPathToGoal(){
		shortestPath=getOptimalPath(start,goal);
		if (shortestPath.size()<=1)
			return false;
		else return true;
	}
	public ArrayList getShortestPath(){
		return shortestPath;
	}
	public int[][] getMapData(){
		return distance;
	}
	

	private  int[][] calculateDistance(boolean[][] accessiblity2, Point start, Point goal) {
		initialiseData(start, goal);	
		this.accessible=accessiblity2;
		checkSetting(start, goal);
		printData();	
		int step=1;
		Point cur=new Point();
		ArrayList<Point> list=new ArrayList<Point>();

		while(!(queue.size()==0)){
			cur.setLocation(getTheNextPt());
			list=getSurroundingPt(cur,distancePerStep,true);
			queue.addAll(list);			
			queue.remove(cur);
			assignCostForSurroundingPt(cur,list);
		}
		return distance;
	}
	private void printData() {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("Robot size:         3 X 3");
		System.out.println("center of start:    "+start.x+","+start.y);
		System.out.println("center of goal:     "+goal.x+","+goal.y);
		System.out.println("start/goal:         3 X 3");
		System.out.println();
	}
	private void checkSetting(Point start, Point goal) {
		boolean settingCorrect=true;
		for(int i=-1;i<2;i++){
			for(int j=-1;j<2;j++){
				if(accessible[start.x+j][start.y+i]&&			
						accessible[goal.x+j][goal.y+i]&&
						settingCorrect)
					settingCorrect=true;
				else
				{settingCorrect=false;
				break;}	

		}}

		if (!settingCorrect){
			System.out.println("setting not correct");
			for(int i=-1;i<2;i++){	
				for(int j=-1;j<2;j++){
					accessible[start.x+i][start.y+j]=true;
					accessible[goal.x+i][goal.y+j]=true;
				}
			}
		}
		for(int i=0;i<nX;i++){
			accessible[i][0]=false;
			accessible[i][nY-1]=false;
		}
		for(int i=0;i<nY;i++){
			accessible[0][i]=false;
			accessible[nX-1][i]=false;
		}
	}
	private void printMapData() {
		for(int i=0;i<nY;i++){
			System.out.println();
			for(int j=0;j<nX;j++){
				int dd=distance[j][i];
				System.out.printf( "%-5d ", dd);
			}
		}
	}
	private void initialiseData( Point start, Point goal) {
		
		this.start=start;
		this.goal=goal;
		this.distance=new int[nX][nY];
		this.tree=new boolean[nX][nY];
		this.accessible=new boolean[nX][nY];
		for(int i=0;i<nY;i++){
			for(int j=0;j<nX;j++){
				distance[j][i]=1000;
				tree[j][i]=false;
				accessible[j][i]=true;
			}
		}

		distance[start.x][start.y]=0;	
		tree[start.x][start.y]=true;	
		queue=new ArrayList<Point> ();	
		queue.add(start);
	}
	private void setObstacle() {
		
	}
	private Point getTheNextPt() {
		// TODO Auto-generated method stub
		int min=10000;
		Point ptWithLowestCost=new Point();
		for(Point p:queue)
			if (distance[p.x][p.y]<min){
				min=distance[p.x][p.y];
				ptWithLowestCost.setLocation(p);;
			}

		return ptWithLowestCost;
	}
	public ArrayList<Point> getSurroundingPt(Point p,int step,boolean unvisited){
		ArrayList list=new ArrayList();
		int x=p.x;int y=p.y;
		for(int i=y-step;i<=y+step;i++){
			for(int j=x-step;j<=x+step;j++){
				if (i==x&&j==y)continue;
				if (i<0||i>=this.nY||j<0||j>=this.nX)continue;
				if (tree[j][i]==true&&unvisited)continue;				
				if (accessibility(new Point(j,i),new Point(p))==false)continue;
				if (i==y||j==x){
					list.add(new Point(j,i));
				}
			}
		}
		return list;
	}
	private boolean accessibility(Point nextP,Point curPoint) {
		// TODO Auto-generated method stub
		int x=nextP.x;
		int y=nextP.y;
		int direction= getDirection(nextP,curPoint);
		if (accessible[nextP.x][nextP.y]==false)return false;
		if (x-1<0||x+1>=nX||y-1<0||y+1>=nY)return false;
		if (direction==0){		
			return(accessible[x-1][y-1]&&
					accessible[x][y-1]&&
					accessible[x+1][y-1]);	
		}
		else if (direction==2){	
			return(accessible[x-1][y+1]&&
					accessible[x][y+1]&&
					accessible[x+1][y+1]);	
		}
		else if (direction==1){
			return(accessible[x+1][y-1]&&
					accessible[x+1][y]&&
					accessible[x+1][y+1]);	
		}
		else if (direction==-1){		
			return(accessible[x-1][y-1]&&
					accessible[x-1][y]&&
					accessible[x-1][y+1]);	
		}
		return false;
	}
	public void assignCostForSurroundingPt(Point cur,
			ArrayList<Point> surroundingPts){

		for(Point p:surroundingPts){
			if (tree[p.x][p.y]==false)
			{			
				distance[p.x][p.y]=distance[cur.x][cur.y]+stepCost;
				tree[p.x][p.y]=true;
			}
		}

	}
	public ArrayList<Point> getOptimalPath(Point start,Point goal){
		System.out.println("goal:"+goal.toString());
		ArrayList<Point> pathTemp=new ArrayList<Point>();
		ArrayList<Point> surroundingPt=new ArrayList<Point>();
		Point cur=new Point(goal);
		Point temp = null;
		int curDirection=0;
		int preDirection=2;
		int tempDirection=0;
		int cost;
		int curTurnCost;
		int minTurnCost;
		int maxStep=nX*nY+100;
		int count=0;
		pathTemp.add(goal);
		while(!cur.equals(start)&&count<maxStep){
			count++;
			cost=distance[cur.x][cur.y];
			surroundingPt=getSurroundingPt(cur,distancePerStep,false);
			minTurnCost=3;
			tempDirection=preDirection;
			for(Point p:surroundingPt){
				curDirection=getDirection(p,new Point(cur.x,cur.y));
				if (distance[p.x][p.y]!=cost-stepCost)continue;
				curTurnCost = getTurningCost(tempDirection, curDirection);
				if (curTurnCost<minTurnCost)
				{				
					temp=new Point(p.x, p.y);		
					minTurnCost=curTurnCost;
					preDirection=curDirection;
				}
			}
			if (temp==null)continue;
			else
				cur=new Point(temp.x,temp.y);
			pathTemp.add(cur);
		}

		return pathTemp;
	}
	private int getTurningCost(int curDirection, int preDirection) {
		if (curDirection==preDirection)
			return 0;
		else if (Math.abs(curDirection)==Math.abs(preDirection))
			return 2;
		else
			return Math.abs(Math.abs(curDirection)-Math.abs(preDirection));
	}

	public int getDirection(Point next, Point cur) {
		// TODO Auto-generated method stub
		int direction=0;
		if(cur.equals(next))return 3;
		if (!(cur.x==next.x||cur.y==next.y))return 3;//diagonal 

		else if (cur.x==next.x){
			if(cur.y>next.y)direction=0;//up
			else direction=2;//down
		}
		else{
			if(cur.x<next.x)direction=1;//right
			else direction=-1;//left
		}
		return direction;
	}
	public void printOptimalPath(){
		Point temp=new Point();
		System.out.print("nX:"+nX+"  nY:"+nY);
		for(int i=0;i<nY;i++){
			System.out.print("\n");
			for(int j=0;j<nX;j++){
				temp.setLocation(j, i);
				if (temp.equals(start)&&shortestPath.contains(temp))
					System.out.print(" S ");
				else if (temp.equals(goal)&&shortestPath.contains(temp))
					System.out.print(" G ");
				else if(accessible[temp.x][temp.y]==false)
					System.out.print(" X ");
				else if (shortestPath!=null&&shortestPath.contains(temp)){
					System.out.print(" - ");
				}
				else{
					System.out.print(" m ");
				}
			}
		}
	}






}
