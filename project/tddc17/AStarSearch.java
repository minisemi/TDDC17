package tddc17;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


public class AStarSearch {
	
	private int[][] map;
	private int agentX, agentY, goalX, goalY, cheapX, cheapY;
	
	private int s1, s2 ,s3 ,s4;
	
	private PathNode currentNode, tempNode;
	
	private List<PathNode> unvistedNodes = new LinkedList<PathNode>();
	
	
	
	public AStarSearch(int[][] map, int agentX, int agentY, int goalX, int goalY){
		this.map = map;
		this.agentX = agentX;
		this.agentY = agentY;
		this.goalX = goalX;
		this.goalY = goalY;	
	}
	
	public void doStuff(){
		
		currentNode = new PathNode(agentX, agentY, null);
		
		
		while(true){
			
			
			cheapX = currentNode.getXPos();
			cheapY = currentNode.getYPos();
			
			
			s1 = map[cheapX][cheapY - 1];
			s2 = map[cheapX + 1][cheapY];
			s3 = map[cheapX][cheapY + 1];
			s4 = map[cheapX - 1][cheapY];
			
			if(s1 != MyAgentState.WALL){
				tempNode = new PathNode(cheapX, cheapY -1, currentNode);
			}
			
		}
		
		
		
	}
	
	
	
	
	public Stack<Integer> getXPath(){
		
		return new Stack<Integer>();
	}

}
