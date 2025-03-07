/** Generated by itemis CREATE code generator. */
package smarthub.java;

import com.yakindu.core.IEventDriven;
import com.yakindu.core.IStatemachine;
import com.yakindu.core.ITimed;
import com.yakindu.core.ITimerService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Microwave_Unit implements ITimed, IEventDriven {
	public static class Device {
		private Microwave_Unit parent;
		
		public Device(Microwave_Unit parent) {
			this.parent = parent;
		}
		private boolean on;
		
		
		public void raiseOn() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					on = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean off;
		
		
		public void raiseOff() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					off = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean start;
		
		
		public void raiseStart() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					start = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean pause;
		
		
		public void raisePause() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					pause = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean addTimer;
		
		
		public void raiseAddTimer() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					addTimer = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean resetTimer;
		
		
		public void raiseResetTimer() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					resetTimer = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean isOn;
		
		public synchronized boolean getIsOn() {
			synchronized(parent) {
				return isOn;
			}
		}
		
		public void setIsOn(boolean value) {
			synchronized(parent) {
				this.isOn = value;
			}
		}
		
		private long timer;
		
		public synchronized long getTimer() {
			synchronized(parent) {
				return timer;
			}
		}
		
		public void setTimer(long value) {
			synchronized(parent) {
				this.timer = value;
			}
		}
		
	}
	
	public static class Door {
		private Microwave_Unit parent;
		
		public Door(Microwave_Unit parent) {
			this.parent = parent;
		}
		private boolean open;
		
		
		public void raiseOpen() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					open = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean close;
		
		
		public void raiseClose() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					close = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean closed;
		
		public synchronized boolean getClosed() {
			synchronized(parent) {
				return closed;
			}
		}
		
		public void setClosed(boolean value) {
			synchronized(parent) {
				this.closed = value;
			}
		}
		
	}
	
	protected Device device;
	
	protected Door door;
	
	public enum State {
		MICROWAVE_UNIT__OFF_,
		MICROWAVE_UNIT__ON_,
		MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_IDLE_STANDBY,
		MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER,
		MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_PAUSE,
		MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_START,
		MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_ADDTIMER,
		MICROWAVE_UNIT__ON__DOORSTATUS_DOORCLOSED,
		MICROWAVE_UNIT__ON__DOORSTATUS_DOOROPENED,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[2];
	
	private ITimerService timerService;
	
	private final boolean[] timeEvents = new boolean[3];
	
	private BlockingQueue<Runnable> inEventQueue = new LinkedBlockingQueue<Runnable>();
	private boolean completed;
	
	protected boolean getCompleted() {
		synchronized(Microwave_Unit.this) {
			return completed;
		}
	}
	
	protected void setCompleted(boolean value) {
		synchronized(Microwave_Unit.this) {
			this.completed = value;
		}
	}
	private boolean doCompletion;
	
	protected boolean getDoCompletion() {
		synchronized(Microwave_Unit.this) {
			return doCompletion;
		}
	}
	
	protected void setDoCompletion(boolean value) {
		synchronized(Microwave_Unit.this) {
			this.doCompletion = value;
		}
	}
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		synchronized(Microwave_Unit.this) {
			return isExecuting;
		}
	}
	
	protected void setIsExecuting(boolean value) {
		synchronized(Microwave_Unit.this) {
			this.isExecuting = value;
		}
	}
	private long stateConfVectorPosition;
	
	protected long getStateConfVectorPosition() {
		synchronized(Microwave_Unit.this) {
			return stateConfVectorPosition;
		}
	}
	
	protected void setStateConfVectorPosition(long value) {
		synchronized(Microwave_Unit.this) {
			this.stateConfVectorPosition = value;
		}
	}
	public Microwave_Unit() {
		device = new Device(this);
		door = new Door(this);
		for (int i = 0; i < 2; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		
		setMessage("");
		
		setFood_inside_sensed(false);
		
		setIn_use(false);
		
		device.setIsOn(false);
		
		device.setTimer(0l);
		
		door.setClosed(true);
		
		isExecuting = false;
	}
	
	public synchronized void enter() {
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
		}
		
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		enterSequence_Microwave_Unit_default();
		doCompletion = false;
		
		do { 
			if (getCompleted()) {
				doCompletion = true;
			}
			completed = false;
			
			microStep();
			
			doCompletion = false;
		} while (getCompleted());
		
		isExecuting = false;
	}
	
	public synchronized void exit() {
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		exitSequence_Microwave_Unit();
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public synchronized boolean isActive() {
		return stateVector[0] != State.$NULLSTATE$||stateVector[1] != State.$NULLSTATE$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public synchronized boolean isFinal() {
		return false;
	}
	private void clearInEvents() {
		device.on = false;
		device.off = false;
		device.start = false;
		device.pause = false;
		device.addTimer = false;
		device.resetTimer = false;
		door.open = false;
		door.close = false;
		timeEvents[0] = false;
		timeEvents[1] = false;
		timeEvents[2] = false;
	}
	
	private void microStep() {
		long transitioned = -1l;
		
		stateConfVectorPosition = 0l;
		
		switch (stateVector[0]) {
		case MICROWAVE_UNIT__OFF_:
			transitioned = microwave_Unit__off__react(transitioned);
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_IDLE_STANDBY:
			transitioned = microwave_Unit__on__MicrowaveUnitProcedure_idle_standby_react(transitioned);
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_PAUSE:
			transitioned = microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause_react(transitioned);
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_START:
			transitioned = microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start_react(transitioned);
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_ADDTIMER:
			transitioned = microwave_Unit__on__MicrowaveUnitProcedure_addTimer_react(transitioned);
			break;
		default:
			break;
		}
		
		if (getStateConfVectorPosition()<1l) {
			switch (stateVector[1]) {
			case MICROWAVE_UNIT__ON__DOORSTATUS_DOORCLOSED:
				microwave_Unit__on__doorStatus_DoorClosed_react(transitioned);
				break;
			case MICROWAVE_UNIT__ON__DOORSTATUS_DOOROPENED:
				microwave_Unit__on__doorStatus_DoorOpened_react(transitioned);
				break;
			default:
				break;
			}
		}
	}
	
	private void runCycle() {
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
		}
		
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		nextEvent();
		do { 
			doCompletion = false;
			
			do { 
				if (getCompleted()) {
					doCompletion = true;
				}
				completed = false;
				
				microStep();
				
				doCompletion = false;
			} while (getCompleted());
			
			clearInEvents();
		} while (nextEvent());
		
		isExecuting = false;
	}
	
	protected boolean nextEvent() {
		if(!inEventQueue.isEmpty()) {
			inEventQueue.poll().run();
			return true;
		}
		return false;
	}
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public synchronized boolean isStateActive(State state) {
	
		switch (state) {
		case MICROWAVE_UNIT__OFF_:
			return stateVector[0] == State.MICROWAVE_UNIT__OFF_;
		case MICROWAVE_UNIT__ON_:
			return stateVector[0].ordinal() >= State.
					MICROWAVE_UNIT__ON_.ordinal()&& stateVector[0].ordinal() <= State.MICROWAVE_UNIT__ON__DOORSTATUS_DOOROPENED.ordinal();
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_IDLE_STANDBY:
			return stateVector[0] == State.MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_IDLE_STANDBY;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER:
			return stateVector[0].ordinal() >= State.
					MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER.ordinal()&& stateVector[0].ordinal() <= State.MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_START.ordinal();
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_PAUSE:
			return stateVector[0] == State.MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_PAUSE;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_START:
			return stateVector[0] == State.MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_START;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_ADDTIMER:
			return stateVector[0] == State.MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_ADDTIMER;
		case MICROWAVE_UNIT__ON__DOORSTATUS_DOORCLOSED:
			return stateVector[1] == State.MICROWAVE_UNIT__ON__DOORSTATUS_DOORCLOSED;
		case MICROWAVE_UNIT__ON__DOORSTATUS_DOOROPENED:
			return stateVector[1] == State.MICROWAVE_UNIT__ON__DOORSTATUS_DOOROPENED;
		default:
			return false;
		}
	}
	
	public synchronized void setTimerService(ITimerService timerService) {
		this.timerService = timerService;
	}
	
	public ITimerService getTimerService() {
		return timerService;
	}
	
	public synchronized void raiseTimeEvent(int eventID) {
		inEventQueue.add(() -> {
			timeEvents[eventID] = true;
		});
		runCycle();
	}
	
	public Device device() {
		return device;
	}
	
	public Door door() {
		return door;
	}
	
	
	private String message;
	
	public synchronized String getMessage() {
		synchronized(Microwave_Unit.this) {
			return message;
		}
	}
	
	public void setMessage(String value) {
		synchronized(Microwave_Unit.this) {
			this.message = value;
		}
	}
	
	private boolean food_inside_sensed;
	
	public synchronized boolean getFood_inside_sensed() {
		synchronized(Microwave_Unit.this) {
			return food_inside_sensed;
		}
	}
	
	public void setFood_inside_sensed(boolean value) {
		synchronized(Microwave_Unit.this) {
			this.food_inside_sensed = value;
		}
	}
	
	private boolean in_use;
	
	public synchronized boolean getIn_use() {
		synchronized(Microwave_Unit.this) {
			return in_use;
		}
	}
	
	public void setIn_use(boolean value) {
		synchronized(Microwave_Unit.this) {
			this.in_use = value;
		}
	}
	
	private void effect_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_tr1() {
		exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer();
		enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby_default();
	}
	
	/* Entry action for state '<off>'. */
	private void entryAction_Microwave_Unit__off_() {
		device.setIsOn(false);
	}
	
	/* Entry action for state '<on>'. */
	private void entryAction_Microwave_Unit__on_() {
		device.setIsOn(true);
	}
	
	/* Entry action for state 'idle/standby'. */
	private void entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby() {
		setMessage("Microwave is on STANDBY...");
	}
	
	/* Entry action for state 'startTimer'. */
	private void entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer() {
		if (door.getClosed()) {
			setMessage("Smart devices main function is here");
		}
	}
	
	/* Entry action for state 'pause'. */
	private void entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause() {
		timerService.setTimer(this, 0, (1l * 1000l), false);
		
		setIn_use(false);
	}
	
	/* Entry action for state 'start'. */
	private void entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start() {
		timerService.setTimer(this, 1, (1l * 1000l), false);
		
		timerService.setTimer(this, 2, (1l * 1000l), true);
		
		setMessage("Food is heating up...");
		
		setIn_use(true);
	}
	
	private void entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_addTimer() {
		device.setTimer(device.getTimer() + 10l);
		
		completed = true;
	}
	
	/* Entry action for state 'DoorClosed'. */
	private void entryAction_Microwave_Unit__on__doorStatus_DoorClosed() {
		door.setClosed(true);
	}
	
	/* Entry action for state 'DoorOpened'. */
	private void entryAction_Microwave_Unit__on__doorStatus_DoorOpened() {
		door.setClosed(false);
		
		device.raisePause();
	}
	
	/* Exit action for state 'pause'. */
	private void exitAction_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause() {
		timerService.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'start'. */
	private void exitAction_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start() {
		timerService.unsetTimer(this, 1);
		
		timerService.unsetTimer(this, 2);
	}
	
	/* 'default' enter sequence for state <off> */
	private void enterSequence_Microwave_Unit__off__default() {
		entryAction_Microwave_Unit__off_();
		stateVector[0] = State.MICROWAVE_UNIT__OFF_;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state <on> */
	private void enterSequence_Microwave_Unit__on__default() {
		entryAction_Microwave_Unit__on_();
		enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_default();
		enterSequence_Microwave_Unit__on__doorStatus_default();
	}
	
	/* 'default' enter sequence for state idle/standby */
	private void enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby_default() {
		entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby();
		stateVector[0] = State.MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_IDLE_STANDBY;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state startTimer */
	private void enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_default() {
		entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer();
		enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_default();
	}
	
	/* 'default' enter sequence for state pause */
	private void enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause_default() {
		entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause();
		stateVector[0] = State.MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_PAUSE;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state start */
	private void enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start_default() {
		entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start();
		stateVector[0] = State.MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_START;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state addTimer */
	private void enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_addTimer_default() {
		entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_addTimer();
		stateVector[0] = State.MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_ADDTIMER;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state DoorClosed */
	private void enterSequence_Microwave_Unit__on__doorStatus_DoorClosed_default() {
		entryAction_Microwave_Unit__on__doorStatus_DoorClosed();
		stateVector[1] = State.MICROWAVE_UNIT__ON__DOORSTATUS_DOORCLOSED;
		stateConfVectorPosition = 1;
	}
	
	/* 'default' enter sequence for state DoorOpened */
	private void enterSequence_Microwave_Unit__on__doorStatus_DoorOpened_default() {
		entryAction_Microwave_Unit__on__doorStatus_DoorOpened();
		stateVector[1] = State.MICROWAVE_UNIT__ON__DOORSTATUS_DOOROPENED;
		stateConfVectorPosition = 1;
	}
	
	/* 'default' enter sequence for region Microwave_Unit */
	private void enterSequence_Microwave_Unit_default() {
		react_Microwave_Unit__entry_Default();
	}
	
	/* 'default' enter sequence for region MicrowaveUnitProcedure */
	private void enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_default() {
		react_Microwave_Unit__on__MicrowaveUnitProcedure__entry_Default();
	}
	
	/* 'default' enter sequence for region microwav */
	private void enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_default() {
		react_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav__entry_Default();
	}
	
	/* 'default' enter sequence for region doorStatus */
	private void enterSequence_Microwave_Unit__on__doorStatus_default() {
		react_Microwave_Unit__on__doorStatus__entry_Default();
	}
	
	/* Default exit sequence for state <off> */
	private void exitSequence_Microwave_Unit__off_() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state <on> */
	private void exitSequence_Microwave_Unit__on_() {
		exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure();
		exitSequence_Microwave_Unit__on__doorStatus();
	}
	
	/* Default exit sequence for state idle/standby */
	private void exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state startTimer */
	private void exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer() {
		exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav();
	}
	
	/* Default exit sequence for state pause */
	private void exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
		
		exitAction_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause();
	}
	
	/* Default exit sequence for state start */
	private void exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
		
		exitAction_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start();
	}
	
	/* Default exit sequence for state addTimer */
	private void exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_addTimer() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state DoorClosed */
	private void exitSequence_Microwave_Unit__on__doorStatus_DoorClosed() {
		stateVector[1] = State.$NULLSTATE$;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for state DoorOpened */
	private void exitSequence_Microwave_Unit__on__doorStatus_DoorOpened() {
		stateVector[1] = State.$NULLSTATE$;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for region Microwave_Unit */
	private void exitSequence_Microwave_Unit() {
		switch (stateVector[0]) {
		case MICROWAVE_UNIT__OFF_:
			exitSequence_Microwave_Unit__off_();
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_IDLE_STANDBY:
			exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby();
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_PAUSE:
			exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause();
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_START:
			exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start();
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_ADDTIMER:
			exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_addTimer();
			break;
		default:
			break;
		}
		
		switch (stateVector[1]) {
		case MICROWAVE_UNIT__ON__DOORSTATUS_DOORCLOSED:
			exitSequence_Microwave_Unit__on__doorStatus_DoorClosed();
			break;
		case MICROWAVE_UNIT__ON__DOORSTATUS_DOOROPENED:
			exitSequence_Microwave_Unit__on__doorStatus_DoorOpened();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region MicrowaveUnitProcedure */
	private void exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure() {
		switch (stateVector[0]) {
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_IDLE_STANDBY:
			exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby();
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_PAUSE:
			exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause();
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_START:
			exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start();
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_ADDTIMER:
			exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_addTimer();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region microwav */
	private void exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav() {
		switch (stateVector[0]) {
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_PAUSE:
			exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause();
			break;
		case MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_STARTTIMER_MICROWAV_START:
			exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region doorStatus */
	private void exitSequence_Microwave_Unit__on__doorStatus() {
		switch (stateVector[1]) {
		case MICROWAVE_UNIT__ON__DOORSTATUS_DOORCLOSED:
			exitSequence_Microwave_Unit__on__doorStatus_DoorClosed();
			break;
		case MICROWAVE_UNIT__ON__DOORSTATUS_DOOROPENED:
			exitSequence_Microwave_Unit__on__doorStatus_DoorOpened();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_Microwave_Unit__entry_Default() {
		enterSequence_Microwave_Unit__off__default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav__entry_Default() {
		enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_Microwave_Unit__on__MicrowaveUnitProcedure__entry_Default() {
		enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_Microwave_Unit__on__doorStatus__entry_Default() {
		enterSequence_Microwave_Unit__on__doorStatus_DoorClosed_default();
	}
	
	/* The reactions of exit default. */
	private void react_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav__exit_Default() {
		effect_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_tr1();
	}
	
	private long react(long transitioned_before) {
		return transitioned_before;
	}
	
	private long microwave_Unit__off__react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (device.on) {
					exitSequence_Microwave_Unit__off_();
					enterSequence_Microwave_Unit__on__default();
					react(0l);
					
					transitioned_after = 0l;
				}
			}
			if (transitioned_after==transitioned_before) {
				transitioned_after = react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long microwave_Unit__on__react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (device.off) {
					exitSequence_Microwave_Unit__on_();
					enterSequence_Microwave_Unit__off__default();
					react(0l);
					
					transitioned_after = 1l;
				}
			}
			if (transitioned_after==transitioned_before) {
				transitioned_after = react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long microwave_Unit__on__MicrowaveUnitProcedure_idle_standby_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (((device.start) && (door.getClosed()))) {
					exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby();
					enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_default();
					transitioned_after = 0l;
				} else {
					if (device.addTimer) {
						exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby();
						enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_addTimer_default();
						transitioned_after = 0l;
					} else {
						if (device.resetTimer) {
							exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby();
							device.setTimer(0l);
							
							enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby_default();
							transitioned_after = 0l;
						}
					}
				}
			}
		}
		return transitioned_after;
	}
	
	private long microwave_Unit__on__MicrowaveUnitProcedure_startTimer_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (device.pause) {
					exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer();
					enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby_default();
					transitioned_after = 0l;
				}
			}
		}
		return transitioned_after;
	}
	
	private long microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (device.getTimer()>0l) {
					exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause();
					enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start_default();
					microwave_Unit__on__MicrowaveUnitProcedure_startTimer_react(0l);
					
					transitioned_after = 0l;
				} else {
					if (((timeEvents[0]) && (device.getTimer()<=0l))) {
						exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause();
						device.raisePause();
						
						timeEvents[0] = false;
						enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause_default();
						microwave_Unit__on__MicrowaveUnitProcedure_startTimer_react(0l);
						
						transitioned_after = 0l;
					}
				}
			}
			if (transitioned_after==transitioned_before) {
				transitioned_after = microwave_Unit__on__MicrowaveUnitProcedure_startTimer_react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<0l) {
				if (device.getTimer()<=0l) {
					exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start();
					device.raisePause();
					
					enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_pause_default();
					microwave_Unit__on__MicrowaveUnitProcedure_startTimer_react(0l);
					
					transitioned_after = 0l;
				} else {
					if (((timeEvents[1]) && (getFood_inside_sensed()))) {
						exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start();
						device.timer--;
						
						timeEvents[1] = false;
						enterSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start_default();
						microwave_Unit__on__MicrowaveUnitProcedure_startTimer_react(0l);
						
						transitioned_after = 0l;
					} else {
						if (((timeEvents[2]) && (!getFood_inside_sensed()))) {
							exitSequence_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav_start();
							device.raisePause();
							
							setMessage("No food inside !");
							
							timeEvents[2] = false;
							react_Microwave_Unit__on__MicrowaveUnitProcedure_startTimer_microwav__exit_Default();
							transitioned_after = 0l;
						}
					}
				}
			}
			if (transitioned_after==transitioned_before) {
				transitioned_after = microwave_Unit__on__MicrowaveUnitProcedure_startTimer_react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long microwave_Unit__on__MicrowaveUnitProcedure_addTimer_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (getDoCompletion()) {
			stateVector[0] = State.$NULLSTATE$;
			stateConfVectorPosition = 0;
			
			entryAction_Microwave_Unit__on__MicrowaveUnitProcedure_idle_standby();
			stateVector[0] = State.MICROWAVE_UNIT__ON__MICROWAVEUNITPROCEDURE_IDLE_STANDBY;
			stateConfVectorPosition = 0;
		}
		return transitioned_after;
	}
	
	private long microwave_Unit__on__doorStatus_DoorClosed_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<1l) {
				if (door.open) {
					exitSequence_Microwave_Unit__on__doorStatus_DoorClosed();
					enterSequence_Microwave_Unit__on__doorStatus_DoorOpened_default();
					microwave_Unit__on__react(0l);
					
					transitioned_after = 1l;
				}
			}
			if (transitioned_after==transitioned_before) {
				transitioned_after = microwave_Unit__on__react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	private long microwave_Unit__on__doorStatus_DoorOpened_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (!getDoCompletion()) {
			if (transitioned_after<1l) {
				if (door.close) {
					exitSequence_Microwave_Unit__on__doorStatus_DoorOpened();
					enterSequence_Microwave_Unit__on__doorStatus_DoorClosed_default();
					microwave_Unit__on__react(0l);
					
					transitioned_after = 1l;
				}
			}
			if (transitioned_after==transitioned_before) {
				transitioned_after = microwave_Unit__on__react(transitioned_before);
			}
		}
		return transitioned_after;
	}
	
	/* Can be used by the client code to trigger a run to completion step without raising an event. */
	public synchronized void triggerWithoutEvent() {
		runCycle();
	}
}
