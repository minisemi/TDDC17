package tddc17;

public class PathNode {
	
	private int xPos, yPos;
	private PathNode father;
	
	private int gCost, hCost, fCost;
	
	public PathNode(int xPos, int yPos, PathNode father){
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.father = father;
		
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
	public void setHCost(int h){
		hCost = h;
	}
	
	public void setGCost(int g){
		gCost = g;
	}
	
	public int getFCost(){
		return gCost + hCost;
	}

}
