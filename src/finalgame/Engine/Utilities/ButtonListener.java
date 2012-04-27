/**
 * (c) 2011 Patrick Long
 * All rights reserved.
 */

package finalgame.Engine.Utilities;

public interface ButtonListener {
	public void doCallback();
	public void doCallback(Object caller);
	public void doCallback(Object caller, int eventCode);
}
