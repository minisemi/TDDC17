package tddc17;

import aima.core.environment.liuvacuum.*;
import sun.security.acl.WorldGroupImpl;
import aima.core.agent.Action;
import aima.core.agent.AgentProgram;
import aima.core.agent.Percept;
import aima.core.agent.impl.*;

import java.nio.file.Path;
import java.util.Random;
import java.util.Stack;

import com.sun.org.apache.bcel.internal.classfile.Unknown;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

class MyAgentState {
	public int[][] world = new int[30][30];
	public int initialized = 0;
	final int UNKNOWN = 0;
	public static final int WALL = 1;
	final int CLEAR = 2;
	final int DIRT = 3;
	final int HOME = 4;
	final int ACTION_NONE = 0;
	final int ACTION_MOVE_FORWARD = 1;
	final int ACTION_TURN_RIGHT = 2;
	final int ACTION_TURN_LEFT = 3;
	final int ACTION_SUCK = 4;

	public int agent_x_position = 1;
	public int agent_y_position = 1;
	public int agent_last_action = ACTION_NONE;

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public int agent_direction = EAST;

	MyAgentState() {
		for (int i = 0; i < world.length; i++)
			for (int j = 0; j < world[i].length; j++)
				world[i][j] = UNKNOWN;
		world[1][1] = HOME;
		agent_last_action = ACTION_NONE;
	}

	// Based on the last action and the received percept updates the x & y agent
	// position
	public void updatePosition(DynamicPercept p) {
		Boolean bump = (Boolean) p.getAttribute("bump");

		if (agent_last_action == ACTION_MOVE_FORWARD && !bump) {
			switch (agent_direction) {
			case MyAgentState.NORTH:
				agent_y_position--;
				break;
			case MyAgentState.EAST:
				agent_x_position++;
				break;
			case MyAgentState.SOUTH:
				agent_y_position++;
				break;
			case MyAgentState.WEST:
				agent_x_position--;
				break;
			}
		}
	}

	public void updateWorld(int x_position, int y_position, int info) {
		world[x_position][y_position] = info;
	}

	public void printWorldDebug() {
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				if (world[j][i] == UNKNOWN)
					System.out.print(" ? ");
				if (world[j][i] == WALL)
					System.out.print(" # ");
				if (world[j][i] == CLEAR)
					System.out.print(" . ");
				if (world[j][i] == DIRT)
					System.out.print(" D ");
				if (world[j][i] == HOME)
					System.out.print(" H ");
			}
			System.out.println("");
		}
	}
}

class MyAgentProgram implements AgentProgram {

	private int initnialRandomActions = 10;
	private Random random_generator = new Random();

	// Here you can define your variables!
	public int iterationCounter = 15 * 15 * 2;
	public MyAgentState state = new MyAgentState();
	public int s1, s2, s3, s4, EAST_WALL = 0, SOUTH_WALL = 0, counter = 0, size = 100, numberOfBumbs = 0;
	public Stack<int[][]> unknown = new Stack<int[][]>();
	public Stack<int[][]> path = new Stack<int[][]>();
	
	public Stack<Integer> xPath = new Stack<Integer>();
	public Stack<Integer> yPath = new Stack<Integer>();
	
	boolean testCheck = true;

	Random random = new Random();

	public int goalX = 50, goalY = 52, pathDirection;

	// moves the Agent to a random start position
	// uses percepts to update the Agent position - only the position, other
	// percepts are ignored
	// returns a random action
	private Action moveToRandomStartPosition(DynamicPercept percept) {
		int action = random_generator.nextInt(6);
		initnialRandomActions--;
		state.updatePosition(percept);
		if (action == 0) {
			state.agent_direction = ((state.agent_direction - 1) % 4);
			if (state.agent_direction < 0)
				state.agent_direction += 4;
			state.agent_last_action = state.ACTION_TURN_LEFT;
			return LIUVacuumEnvironment.ACTION_TURN_LEFT;
		} else if (action == 1) {
			state.agent_direction = ((state.agent_direction + 1) % 4);
			state.agent_last_action = state.ACTION_TURN_RIGHT;
			return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
		}
		state.agent_last_action = state.ACTION_MOVE_FORWARD;
		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
	}

	@Override
	public Action execute(Percept percept) {

		// DO NOT REMOVE this if condition!!!
		if (initnialRandomActions > 0) {
			return moveToRandomStartPosition((DynamicPercept) percept);
		} else if (initnialRandomActions == 0) {
			// process percept for the last step of the initial random actions
			initnialRandomActions--;
			state.updatePosition((DynamicPercept) percept);
			System.out.println("Processing percepts after the last execution of moveToRandomStartPosition()");
			state.agent_last_action = state.ACTION_SUCK;
			return LIUVacuumEnvironment.ACTION_SUCK;
		}

		// This example agent program will update the internal agent state while
		// only moving forward.
		// START HERE - code below should be modified!

		s1 = state.world[state.agent_x_position][state.agent_y_position - 1];
		s2 = state.world[state.agent_x_position + 1][state.agent_y_position];
		s3 = state.world[state.agent_x_position][state.agent_y_position + 1];
		s4 = state.world[state.agent_x_position - 1][state.agent_y_position];
		
		
		
		
		
		if(testCheck){
			xPath.push(1);
			yPath.push(1);
			xPath.push(5);
			yPath.push(1);
			xPath.push(5);
			yPath.push(3);
			xPath.push(1);
			yPath.push(5);
			xPath.push(5);
			yPath.push(5);
			testCheck = false;
			
		}
		
		

		System.out.println(size + "  " + counter);

		iterationCounter--;

		if (EAST_WALL != 0 && size == 100) {
			size = (EAST_WALL - 1) * (EAST_WALL - 1) - 1;
		}
		if (SOUTH_WALL != 0 && size == 100) {
			size = (SOUTH_WALL - 1) * (SOUTH_WALL - 1) - 1;
		}

		if (iterationCounter == 0)
			return NoOpAction.NO_OP;

		DynamicPercept p = (DynamicPercept) percept;
		Boolean bump = (Boolean) p.getAttribute("bump");
		Boolean dirt = (Boolean) p.getAttribute("dirt");
		Boolean home = (Boolean) p.getAttribute("home");
		System.out.println("percept: " + p);

		if (counter >= size && home) {
			return NoOpAction.NO_OP;
		}

		// State update based on the percept value and the last action
		state.updatePosition((DynamicPercept) percept);

		System.out.println("x=" + state.agent_x_position);
		System.out.println("y=" + state.agent_y_position);
		System.out.println("dir=" + state.agent_direction);

		if (bump) {
			numberOfBumbs++;

			switch (state.agent_direction) {
			case MyAgentState.NORTH:
				state.updateWorld(state.agent_x_position, state.agent_y_position - 1, state.WALL);
				break;
			case MyAgentState.EAST:
				state.updateWorld(state.agent_x_position + 1, state.agent_y_position, state.WALL);
				EAST_WALL = state.agent_x_position + 1;
				break;
			case MyAgentState.SOUTH:
				state.updateWorld(state.agent_x_position, state.agent_y_position + 1, state.WALL);
				SOUTH_WALL = state.agent_y_position + 1;
				break;
			case MyAgentState.WEST:
				state.updateWorld(state.agent_x_position - 1, state.agent_y_position, state.WALL);
				break;
			}
		}
		if (dirt)
			state.updateWorld(state.agent_x_position, state.agent_y_position, state.DIRT);
		else {

			if (state.world[state.agent_x_position][state.agent_y_position] != state.HOME
					&& state.world[state.agent_x_position][state.agent_y_position] != state.CLEAR) {
				state.updateWorld(state.agent_x_position, state.agent_y_position, state.CLEAR);
				counter++;

			}
		}

		state.printWorldDebug();

		// Next action selection based on the percept value
		if (dirt) {
			System.out.println("DIRT -> choosing SUCK action!");
			state.agent_last_action = state.ACTION_SUCK;
			return LIUVacuumEnvironment.ACTION_SUCK;
		} else {
			if (bump) {
				state.agent_direction = ((state.agent_direction + 1) % 4);
				state.agent_last_action = state.ACTION_TURN_RIGHT;
				return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
			} else {
				
				if(goalX == state.agent_x_position && goalY == state.agent_y_position) {
					xPath.pop();
					yPath.pop();
				}
				
				goalX = xPath.peek();
				goalY = yPath.peek();

				pathDirection = getDirectionForPath(xPath.peek(), yPath.peek());
				
				if(pathDirection == state.agent_direction){
					state.agent_last_action = state.ACTION_MOVE_FORWARD;
					return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
				} else{

				switch (pathDirection) {
				case MyAgentState.NORTH:
					if(state.agent_direction == MyAgentState.SOUTH || state.agent_direction == MyAgentState.WEST){
						state.agent_direction = ((state.agent_direction + 1) % 4);
						state.agent_last_action = state.ACTION_TURN_RIGHT;
						return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
					} else{
						
						state.agent_direction = ((state.agent_direction - 1) % 4);
						if (state.agent_direction < 0)
							state.agent_direction += 4;
						state.agent_last_action = state.ACTION_TURN_LEFT;
						return LIUVacuumEnvironment.ACTION_TURN_LEFT;
					}
				case MyAgentState.EAST:
					if(state.agent_direction == MyAgentState.NORTH || state.agent_direction == MyAgentState.WEST){
						state.agent_direction = ((state.agent_direction + 1) % 4);
						state.agent_last_action = state.ACTION_TURN_RIGHT;
						return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
					} else{
						
						state.agent_direction = ((state.agent_direction - 1) % 4);
						if (state.agent_direction < 0)
							state.agent_direction += 4;
						state.agent_last_action = state.ACTION_TURN_LEFT;
						return LIUVacuumEnvironment.ACTION_TURN_LEFT;
					}
				case MyAgentState.SOUTH:
					if(state.agent_direction == MyAgentState.NORTH || state.agent_direction == MyAgentState.EAST){
						state.agent_direction = ((state.agent_direction + 1) % 4);
						state.agent_last_action = state.ACTION_TURN_RIGHT;
						return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
					} else{
						
						state.agent_direction = ((state.agent_direction - 1) % 4);
						if (state.agent_direction < 0)
							state.agent_direction += 4;
						state.agent_last_action = state.ACTION_TURN_LEFT;
						return LIUVacuumEnvironment.ACTION_TURN_LEFT;
					}
				case MyAgentState.WEST:
					if(state.agent_direction == MyAgentState.SOUTH || state.agent_direction == MyAgentState.EAST){
						state.agent_direction = ((state.agent_direction + 1) % 4);
						state.agent_last_action = state.ACTION_TURN_RIGHT;
						return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
					} else{
						
						state.agent_direction = ((state.agent_direction - 1) % 4);
						if (state.agent_direction < 0)
							state.agent_direction += 4;
						state.agent_last_action = state.ACTION_TURN_LEFT;
						return LIUVacuumEnvironment.ACTION_TURN_LEFT;
					}
					

				}
				state.agent_last_action = state.ACTION_MOVE_FORWARD;
				return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
				}
/*
				int action = random.nextInt(10);
				if (action < 8 || counter >= size || numberOfBumbs <= 5) {
					state.agent_last_action = state.ACTION_MOVE_FORWARD;
					return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
				} else {
					state.agent_direction = ((state.agent_direction + 1) % 4);
					state.agent_last_action = state.ACTION_TURN_RIGHT;
					return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
				}
	*/		}
		}

	}

	public int getDirectionForPath(int x, int y) {
		int direction = 0;

		if (x < state.agent_x_position)
			return MyAgentState.WEST;
		if (x > state.agent_x_position)
			return MyAgentState.EAST;
		if (y < state.agent_y_position)
			return MyAgentState.NORTH;
		if (y > state.agent_y_position)
			return MyAgentState.SOUTH;

		return direction;
	}

}

public class MyVacuumAgent extends AbstractAgent {
	public MyVacuumAgent() {
		super(new MyAgentProgram());
	}
}
