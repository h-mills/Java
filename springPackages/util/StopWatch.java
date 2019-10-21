package project.common.util;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * nono time �� �������� �۾� ���� �ð��� ���Ѵ�.
 * @author  bbaeggar
 */
public class StopWatch {

	// ---------------------------------------------------------------------
	// Instance data
	// ---------------------------------------------------------------------

	/** Start time of the current task */
	private long startTime;

	/** Total running time */
	private long runningTime;

	/** List of TaskInfo objects */
	private List taskList = new LinkedList();

	/** Name of the current task */
	private String currentTask;

	/**
	 * Is the stopwatch currently running?
	 * @uml.property  name="running"
	 */
	private boolean running;

	/**
	 * Identifier of this stop watch. Handy when we have output from multiple
	 * stop watches and need to distinguish between them in log or console
	 * output.
	 */
	private String id = "";

	/**
	 * @uml.property  name="keepTaskList"
	 */
	private boolean keepTaskList = true;

	/**
	 * @uml.property  name="lastTaskInfo"
	 * @uml.associationEnd  
	 */
	private TaskInfo lastTaskInfo;

	/**
	 * @uml.property  name="taskCount"
	 */
	private int taskCount;

	// ---------------------------------------------------------------------
	// Constructors
	// ---------------------------------------------------------------------

	/**
	 * Construct a new stop watch. Does not start any task.
	 */
	public StopWatch() {
	}

	/**
	 * Construct a new stop watch with the given id. Does not start any task.
	 * 
	 * @param id
	 *            identifier for this stop watch. Handy when we have output from
	 *            multiple stop watches and need to distinguish between them.
	 */
	public StopWatch(String id) {
		this.id = id;
	}

	/**
	 * Determines whether TaskInfo array is built over time. Set this to false when using a stopwatch for millions of intervals, or the task info structure will consume excessive memory. Default is true.
	 * @param  keepTaskList
	 * @uml.property  name="keepTaskList"
	 */
	public void setKeepTaskList(boolean keepTaskList) {
		this.keepTaskList = keepTaskList;
	}

	/**
	 * @return
	 * @uml.property  name="keepTaskList"
	 */
	public boolean getKeepTaskList() {
		return this.keepTaskList;
	}

	// ---------------------------------------------------------------------
	// Public methods
	// ---------------------------------------------------------------------

	/**
	 * Start a named task. The results are undefined if stop() or timing methods
	 * are called without invoking this method.
	 * 
	 * @param task
	 *            name of the task to start
	 */
	public void start(String task) throws IllegalStateException {
		if (this.running) {
			throw new IllegalStateException("Can't start StopWatch: it's already running");
		}
		this.startTime = System.nanoTime();
		this.currentTask = task;
		this.running = true;
	}

	/**
	 * Stop the current task. The results are undefined if timing methods are
	 * called without invoking at least one pair start()/stop() methods.
	 */
	public void stop() throws IllegalStateException {
		if (!this.running) {
			throw new IllegalStateException("Can't stop StopWatch: it's not running");
		}
		long lastTime = System.nanoTime() - this.startTime;
		this.runningTime += lastTime;
		this.lastTaskInfo = new TaskInfo(this.currentTask, lastTime);
		if (this.keepTaskList) {
			this.taskList.add(lastTaskInfo);
		}
		++this.taskCount;
		this.running = false;
		this.currentTask = null;
	}

	/**
	 * Returns the total time in milliseconds for all tasks.
	 */
	public long getTotalTime() {
		return runningTime;
	}

	/**
	 * Returns the time taken by the last operation.
	 */
	public long getLastInterval() throws IllegalStateException {
		if (lastTaskInfo == null)
			throw new IllegalStateException("No tests run: can't get last interval");
		return lastTaskInfo.getTime();
	}

	/**
	 * Returns the total time in seconds for all tasks.
	 */
	public double getTotalTimeSecs() {
		return ((double) runningTime) / 1000000000.0;
	}

	/**
	 * Returns the number of tasks timed.
	 * @uml.property  name="taskCount"
	 */
	public int getTaskCount() {
		return taskCount;
	}

	/**
	 * Returns an array of the data for tasks performed.
	 */
	public TaskInfo[] getTaskInfo() {
		if (!this.keepTaskList) {
			throw new UnsupportedOperationException("Task info is not being kept!");
		}
		return (TaskInfo[]) taskList.toArray(new TaskInfo[0]);
	}

	/**
	 * Returns whether the stopwatch is currently running.
	 * @uml.property  name="running"
	 */
	public boolean isRunning() {
		return this.running;
	}

	/**
	 * Returns a short description of the total running time.
	 */
	public String shortSummary() {
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(9);
		nf.setGroupingUsed(false);
		
		return ("StopWatch '" + id + "': running time (secs) = " + nf.format(getTotalTimeSecs()) + "\n");
	}

	/**
	 * Returns a string with a table describing all tasks performed. For custom
	 * reporting, call getTaskInfo() and use the task info directly.
	 */
	public String prettyPrint() {
		StringBuffer sb = new StringBuffer(shortSummary());
		if (!this.keepTaskList) {
			sb.append("No task info kept");
		}
		else {
			TaskInfo[] tasks = getTaskInfo();
			sb.append("-----------------------------------------\n");
			sb.append("nano time   %     Task name\n");
			sb.append("-----------------------------------------\n");
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMinimumIntegerDigits(5);
			nf.setGroupingUsed(false);
			NumberFormat pf = NumberFormat.getPercentInstance();
			pf.setMinimumIntegerDigits(3);
			pf.setGroupingUsed(false);
			for (int i = 0; i < tasks.length; i++) {
				sb.append(nf.format(tasks[i].getTime()) + " / ");
				sb.append(pf.format(tasks[i].getTimeSecs() / getTotalTimeSecs()) + " / ");
				sb.append(tasks[i].getTaskName() + "\n");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Returns an informative string describing all tasks performed For custom
	 * reporting, call getTaskInfo() and use the task info directly.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(shortSummary());
		if (this.keepTaskList) {
			TaskInfo[] tasks = getTaskInfo();
			for (int i = 0; i < tasks.length; i++) {
				if (i > 0)
					sb.append("; ");
				sb.append("[" + tasks[i].getTaskName() + "] took " + tasks[i].getTimeSecs());
				long percent = Math.round((100.0 * tasks[i].getTimeSecs()) / getTotalTimeSecs());
				sb.append("=" + percent + "%");
			}
		}
		else {
			sb.append("Not keeping task info");
		}
		return sb.toString();
	}

	// ---------------------------------------------------------------------
	// Inner classes
	// ---------------------------------------------------------------------

	/**
	 * Inner class to hold data about one task executed within the stopwatch
	 */
	public static class TaskInfo {

		private String task;

		/**
		 * @uml.property  name="time"
		 */
		private long time;

		private TaskInfo(String task, long time) {
			this.task = task;
			this.time = time;
		}

		/**
		 * Return the name of this task.
		 */
		public String getTaskName() {
			return task;
		}

		/**
		 * Return the time in milliseconds this task took.
		 * @uml.property  name="time"
		 */
		public long getTime() {
			return time;
		}

		/**
		 * Return the time in seconds this task took.
		 */
		public double getTimeSecs() {
			return ((double) time) / 1000000000.0;
		}
	}
	
	public String getTimeString() {
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(9);
		nf.setGroupingUsed(false);
		
		return nf.format(getTotalTimeSecs());
	}

	public static void main(String[] args) {
		try {
			StopWatch sw = new StopWatch("bbaeggar");
			sw.start("test1");
			Thread.sleep(500);
			sw.stop();			
			sw.start("test2");
			Thread.sleep(1000);
			sw.stop();	
			sw.start("test3");
			Thread.sleep(2000);
			sw.stop();	
			sw.start("test4");
			Thread.sleep(100);
			sw.stop();	
			sw.start("test5");
			Thread.sleep(400);
			sw.stop();	
			
			System.out.println("prettyPrint() : " + sw.prettyPrint() );
			System.out.println(sw.getTimeString());
		}
		catch (Exception e) {
		}

	}
}
