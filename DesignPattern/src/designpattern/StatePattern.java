package designpattern;
//상태패턴
abstract class State {
	StatePattern caller = null;
	public State(StatePattern caller) {
		this.caller = caller;
	}
	abstract public void nextLevel();
}

class DefaultState extends State{ //point 0~10
	public DefaultState(StatePattern caller) {
		super(caller);
	}
	@Override
	public void nextLevel() {
		caller.setState(new Level1State(caller));
	} 
	public String toString() {
		return "DEFAULT";
	}
}
class Level1State extends State{ //point 11~40
	public Level1State(StatePattern caller) {
		super(caller);
	}
	@Override
	public void nextLevel() {
		caller.setState(new Level2State(caller));
	} 
	public String toString() {
		return "LEVEL1";
	}
}
class Level2State extends State{//point 31~99
	public Level2State(StatePattern caller) {
		super(caller);
	}
	@Override
	public void nextLevel() {
		caller.setState(new Level3State(caller));
	} 
	public String toString() {
		return "LEVEL2";
	}
}
class Level3State extends State{//point 100~
	public Level3State(StatePattern caller) {
		super(caller);
	}
	@Override
	public void nextLevel() {
		caller.setState(new Level3State(caller));
	} 
	public String toString() {
		return "LEVEL3";
	}
}


public class StatePattern {
	State state = null;
	public StatePattern() {
		state = new DefaultState(this);
	}
	
	public void run(){
		state.nextLevel();
	}
	public void setState(State state){
		this.state = state;
	}
	
	public void printState(){
		System.out.println(state);
	}
	
	public static void main(String[] args) {
		StatePattern s = new StatePattern();
		s.printState();
		s.run();
		s.printState();
		s.run();
		s.printState();
		s.run();
		s.printState();
		s.run();
		s.printState();
	}
}
