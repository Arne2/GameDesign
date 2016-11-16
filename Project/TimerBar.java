/**
 * Class TimerBar: a sub-class of the Bar class whose value will be incremented
 * or decremented each time a specified number of frames sets or a specified
 * number of time sets has passed. It is the responsibility of another class
 * (whether the world or another actor class) to check if count has finished,
 * and act upon that condition.
 */
public class TimerBar extends Bar
{
	private boolean	countDownward	= true;							// flag
																	// determining
																	// direction
																	// of count
																	// (up or
																	// down)
	private boolean	countingFrames	= true;							// flag
																	// determining
																	// what is
																	// counted
																	// (frames
																	// or
																	// nilliseconds)
	private int		delayCount		= 100;							// the
																	// number of
																	// frames or
																	// milliseconds
																	// that pass
																	// before
																	// the value
																	// of the
																	// bar
																	// changes
	private int		count			= 0;							// the
																	// current
																	// number of
																	// frames or
																	// milliseconds
																	// that have
																	// passed
																	// since the
																	// last
																	// value
																	// change
	private boolean	countExpired	= false;						// flag
																	// indicating
																	// the the
																	// counter
																	// had run
																	// its
																	// course
	private boolean	isRunning		= false;						// flag
																	// indicating
																	// whether
																	// the
																	// counter
																	// is
																	// currently
																	// counting
																	// or not
	private long	startTime		= System.currentTimeMillis();	// used in
																	// getting
																	// the
																	// actual
																	// passage
																	// of time
																	// in
																	// milliseconds

	/**
	 * Constructor TimerBar: creates the specified timer bar
	 * 
	 * @param 'refText':
	 *            the text used for the title of the bar
	 * @param 'uomText':
	 *            the text used for the unit of measure for the value text of
	 *            the bar
	 * @param 'maxValue':
	 *            the number of frame or millisecond sets that must pass for the
	 *            timer to run its course
	 * @param 'delayCt':
	 *            the number of frames or milliseconds in each set that is
	 *            counted
	 * @param 'ctDownward':
	 *            a flag determining the direction of count (down or up)
	 * @param 'ctByFrame':
	 *            a flag indicating whether the bar counts frames or
	 *            milliseconds
	 */
	public TimerBar(String refText, String uomText, int maxValue, int delayCt, boolean ctDownward, boolean ctByFrames)
	{
		super(refText, uomText, ctDownward ? maxValue : 0, maxValue);
		countDownward = ctDownward;
		countingFrames = ctByFrames;
		delayCount = delayCt;
		count = delayCount;
	}

	/**
	 * Method 'act': runs the timer
	 */
	public void act()
	{
		if (countExpired || !isRunning)
			return;
		if (countingFrames)
			count++;
		else
		{
			long endTime = System.currentTimeMillis();
			count += endTime - startTime;
			startTime = endTime;
		}
		if (count >= delayCount)
		{
			setValue(getValue() + (countDownward ? -1 : 1));
			if ((countDownward && getValue() == 0) || (!countDownward && getValue() == getMaximumValue()))
				countExpired = true;
			count = 0;
		}
	}

	/**
	 * Method 'begin': resets and starts the timer
	 */
	public void begin()
	{
		count = 0;
		setValue(countDownward ? getMaximumValue() : 0);
		countExpired = false;
		isRunning = true;
	}

	/**
	 * Method 'stop': stops the timer (can be 'restart'ed from current state)
	 */
	public void stop()
	{
		isRunning = false;
	}

	/**
	 * Method 'restart': restarts the counter if it has been 'stop'ped
	 */
	public void restart()
	{
		if (!countExpired)
			isRunning = true;
	}

	/**
	 * Method 'getCountExpired': gets the expiration state of the timer (has it
	 * finished running its course or not)
	 *
	 * @return: the expiration state of the timer
	 */
	public boolean getCountExpired()
	{
		return countExpired;
	}

	/**
	 * Method 'getCountDownward': gets the current direction of count for the
	 * timer (up or down)
	 *
	 * @return: the current counting direction the timer is using
	 */
	public boolean getCountDownward()
	{
		return countDownward;
	}

	/**
	 * Method 'setCountDownward': sets the counting direction the timer is to
	 * use (can be changed in mid-stream of counting)
	 *
	 * @param 'ctDownward':
	 *            the new direction that the timer is to count
	 */
	public void setCountDownward(boolean ctDownward)
	{
		if (!isRunning)
			countDownward = ctDownward;
	}

	/**
	 * Method 'getDelayCount': gets the current number of frames or milliseconds
	 * used for each set that is counted
	 *
	 * @return: the current number of frames or milliseconds used between each
	 *          change in the value of the bar
	 */
	public int getDelayCount()
	{
		return delayCount;
	}

	/**
	 * Method 'setDelayCount': sets the number of frames or milliseconds to use
	 * for each set that is to be counted
	 *
	 * @param 'delayCt':
	 *            the new number of frames or milliseconds to use between each
	 *            change in the value of the bar
	 */
	public void setDelayCount(int delayCt)
	{
		if (!isRunning)
			delayCount = delayCt;
	}
}
