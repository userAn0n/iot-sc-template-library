/** Generated by itemis CREATE code generator. */
package smarthub.java;

import com.yakindu.core.IEventDriven;
import com.yakindu.core.IStatemachine;
import com.yakindu.core.ITimed;
import com.yakindu.core.ITimerService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LEDLight_Unit implements ITimed, IEventDriven {
	public static class Device {
		private LEDLight_Unit parent;
		
		public Device(LEDLight_Unit parent) {
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
		
	}
	
	public static class Bulb {
		private LEDLight_Unit parent;
		
		public Bulb(LEDLight_Unit parent) {
			this.parent = parent;
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
		
	}
	
	public static class Brightness {
		private LEDLight_Unit parent;
		
		public Brightness(LEDLight_Unit parent) {
			this.parent = parent;
		}
		private boolean up;
		
		
		public void raiseUp() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					up = true;
				});
				parent.runCycle();
			}
		}
		
		private boolean down;
		
		
		public void raiseDown() {
			synchronized(parent) {
				parent.inEventQueue.add(() -> {
					down = true;
				});
				parent.runCycle();
			}
		}
		
		private long level;
		
		public synchronized long getLevel() {
			synchronized(parent) {
				return level;
			}
		}
		
		public void setLevel(long value) {
			synchronized(parent) {
				this.level = value;
			}
		}
		
	}
	
	protected Device device;
	
	protected Bulb bulb;
	
	protected Brightness brightness;
	
	public enum State {
		_LED_LIGHT_UNIT___OFF_,
		_LED_LIGHT_UNIT___ON_,
		_LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_STANDBY,
		_LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON,
		_LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_BRIGHTNESSLEVEL_CHANGE_BRIGHTNESS_LEVEL,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[1];
	
	private ITimerService timerService;
	
	private final boolean[] timeEvents = new boolean[2];
	
	private BlockingQueue<Runnable> inEventQueue = new LinkedBlockingQueue<Runnable>();
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		synchronized(LEDLight_Unit.this) {
			return isExecuting;
		}
	}
	
	protected void setIsExecuting(boolean value) {
		synchronized(LEDLight_Unit.this) {
			this.isExecuting = value;
		}
	}
	public LEDLight_Unit() {
		device = new Device(this);
		bulb = new Bulb(this);
		brightness = new Brightness(this);
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		
		device.setIsOn(false);
		
		bulb.setIsOn(false);
		
		brightness.setLevel(1l);
		
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
		
		enterSequence__LED_Light_Unit__default();
		isExecuting = false;
	}
	
	public synchronized void exit() {
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		exitSequence__LED_Light_Unit_();
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public synchronized boolean isActive() {
		return stateVector[0] != State.$NULLSTATE$;
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
		brightness.up = false;
		brightness.down = false;
		timeEvents[0] = false;
		timeEvents[1] = false;
	}
	
	private void microStep() {
		switch (stateVector[0]) {
		case _LED_LIGHT_UNIT___OFF_:
			_LED_Light_Unit___Off__react(-1l);
			break;
		case _LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_STANDBY:
			_LED_Light_Unit___On__Bulb_Status_Bulb_on_standby_react(-1l);
			break;
		case _LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_BRIGHTNESSLEVEL_CHANGE_BRIGHTNESS_LEVEL:
			_LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level_react(-1l);
			break;
		default:
			break;
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
			microStep();
			
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
		case _LED_LIGHT_UNIT___OFF_:
			return stateVector[0] == State._LED_LIGHT_UNIT___OFF_;
		case _LED_LIGHT_UNIT___ON_:
			return stateVector[0].ordinal() >= State.
					_LED_LIGHT_UNIT___ON_.ordinal()&& stateVector[0].ordinal() <= State._LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_BRIGHTNESSLEVEL_CHANGE_BRIGHTNESS_LEVEL.ordinal();
		case _LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_STANDBY:
			return stateVector[0] == State._LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_STANDBY;
		case _LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON:
			return stateVector[0].ordinal() >= State.
					_LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON.ordinal()&& stateVector[0].ordinal() <= State._LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_BRIGHTNESSLEVEL_CHANGE_BRIGHTNESS_LEVEL.ordinal();
		case _LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_BRIGHTNESSLEVEL_CHANGE_BRIGHTNESS_LEVEL:
			return stateVector[0] == State._LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_BRIGHTNESSLEVEL_CHANGE_BRIGHTNESS_LEVEL;
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
	
	public Bulb bulb() {
		return bulb;
	}
	
	public Brightness brightness() {
		return brightness;
	}
	
	
	/* Entry action for state '<Off>'. */
	private void entryAction__LED_Light_Unit___Off_() {
		device.setIsOn(false);
	}
	
	/* Entry action for state 'Bulb_on_standby'. */
	private void entryAction__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby() {
		timerService.setTimer(this, 0, 500l, true);
	}
	
	/* Entry action for state 'Bulb_on'. */
	private void entryAction__LED_Light_Unit___On__Bulb_Status_Bulb_on() {
		timerService.setTimer(this, 1, 500l, true);
	}
	
	/* Exit action for state '<Off>'. */
	private void exitAction__LED_Light_Unit___Off_() {
		device.setIsOn(true);
	}
	
	/* Exit action for state 'Bulb_on_standby'. */
	private void exitAction__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby() {
		timerService.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'Bulb_on'. */
	private void exitAction__LED_Light_Unit___On__Bulb_Status_Bulb_on() {
		timerService.unsetTimer(this, 1);
	}
	
	/* 'default' enter sequence for state <Off> */
	private void enterSequence__LED_Light_Unit___Off__default() {
		entryAction__LED_Light_Unit___Off_();
		stateVector[0] = State._LED_LIGHT_UNIT___OFF_;
	}
	
	/* 'default' enter sequence for state <On> */
	private void enterSequence__LED_Light_Unit___On__default() {
		enterSequence__LED_Light_Unit___On__Bulb_Status_default();
	}
	
	/* 'default' enter sequence for state Bulb_on_standby */
	private void enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby_default() {
		entryAction__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby();
		stateVector[0] = State._LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_STANDBY;
	}
	
	/* 'default' enter sequence for state Bulb_on */
	private void enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_default() {
		entryAction__LED_Light_Unit___On__Bulb_Status_Bulb_on();
		enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_default();
	}
	
	/* 'default' enter sequence for state Change_Brightness_Level */
	private void enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level_default() {
		stateVector[0] = State._LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_BRIGHTNESSLEVEL_CHANGE_BRIGHTNESS_LEVEL;
	}
	
	/* 'default' enter sequence for region <LED_Light_Unit> */
	private void enterSequence__LED_Light_Unit__default() {
		react__LED_Light_Unit___entry_Default();
	}
	
	/* 'default' enter sequence for region Bulb_Status */
	private void enterSequence__LED_Light_Unit___On__Bulb_Status_default() {
		react__LED_Light_Unit___On__Bulb_Status__entry_Default();
	}
	
	/* 'default' enter sequence for region BrightnessLevel */
	private void enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_default() {
		react__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel__entry_Default();
	}
	
	/* Default exit sequence for state <Off> */
	private void exitSequence__LED_Light_Unit___Off_() {
		stateVector[0] = State.$NULLSTATE$;
		
		exitAction__LED_Light_Unit___Off_();
	}
	
	/* Default exit sequence for state <On> */
	private void exitSequence__LED_Light_Unit___On_() {
		exitSequence__LED_Light_Unit___On__Bulb_Status();
	}
	
	/* Default exit sequence for state Bulb_on_standby */
	private void exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby() {
		stateVector[0] = State.$NULLSTATE$;
		
		exitAction__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby();
	}
	
	/* Default exit sequence for state Bulb_on */
	private void exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on() {
		exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel();
		exitAction__LED_Light_Unit___On__Bulb_Status_Bulb_on();
	}
	
	/* Default exit sequence for state Change_Brightness_Level */
	private void exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level() {
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for region <LED_Light_Unit> */
	private void exitSequence__LED_Light_Unit_() {
		switch (stateVector[0]) {
		case _LED_LIGHT_UNIT___OFF_:
			exitSequence__LED_Light_Unit___Off_();
			break;
		case _LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_STANDBY:
			exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby();
			break;
		case _LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_BRIGHTNESSLEVEL_CHANGE_BRIGHTNESS_LEVEL:
			exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level();
			exitAction__LED_Light_Unit___On__Bulb_Status_Bulb_on();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region Bulb_Status */
	private void exitSequence__LED_Light_Unit___On__Bulb_Status() {
		switch (stateVector[0]) {
		case _LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_STANDBY:
			exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby();
			break;
		case _LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_BRIGHTNESSLEVEL_CHANGE_BRIGHTNESS_LEVEL:
			exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level();
			exitAction__LED_Light_Unit___On__Bulb_Status_Bulb_on();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region BrightnessLevel */
	private void exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel() {
		switch (stateVector[0]) {
		case _LED_LIGHT_UNIT___ON__BULB_STATUS_BULB_ON_BRIGHTNESSLEVEL_CHANGE_BRIGHTNESS_LEVEL:
			exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react__LED_Light_Unit___entry_Default() {
		enterSequence__LED_Light_Unit___Off__default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__LED_Light_Unit___On__Bulb_Status__entry_Default() {
		enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel__entry_Default() {
		enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level_default();
	}
	
	private long react(long transitioned_before) {
		return transitioned_before;
	}
	
	private long _LED_Light_Unit___Off__react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (device.on) {
				exitSequence__LED_Light_Unit___Off_();
				enterSequence__LED_Light_Unit___On__default();
				react(0l);
				
				transitioned_after = 0l;
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _LED_Light_Unit___On__react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (device.off) {
				exitSequence__LED_Light_Unit___On_();
				enterSequence__LED_Light_Unit___Off__default();
				react(0l);
				
				transitioned_after = 0l;
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _LED_Light_Unit___On__Bulb_Status_Bulb_on_standby_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (((timeEvents[0]) && (bulb.getIsOn()))) {
				exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby();
				timeEvents[0] = false;
				enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_default();
				_LED_Light_Unit___On__react(0l);
				
				transitioned_after = 0l;
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = _LED_Light_Unit___On__react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _LED_Light_Unit___On__Bulb_Status_Bulb_on_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (((timeEvents[1]) && (!bulb.getIsOn()))) {
				exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on();
				timeEvents[1] = false;
				enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_standby_default();
				_LED_Light_Unit___On__react(0l);
				
				transitioned_after = 0l;
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = _LED_Light_Unit___On__react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0l) {
			if (brightness.down) {
				exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level();
				brightness.level--;
				
				enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level_default();
				_LED_Light_Unit___On__Bulb_Status_Bulb_on_react(0l);
				
				transitioned_after = 0l;
			} else {
				if (brightness.up) {
					exitSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level();
					brightness.level++;
					
					enterSequence__LED_Light_Unit___On__Bulb_Status_Bulb_on_BrightnessLevel_Change_Brightness_Level_default();
					_LED_Light_Unit___On__Bulb_Status_Bulb_on_react(0l);
					
					transitioned_after = 0l;
				}
			}
		}
		if (transitioned_after==transitioned_before) {
			transitioned_after = _LED_Light_Unit___On__Bulb_Status_Bulb_on_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	/* Can be used by the client code to trigger a run to completion step without raising an event. */
	public synchronized void triggerWithoutEvent() {
		runCycle();
	}
}
