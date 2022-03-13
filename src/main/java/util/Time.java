/*******************************************************************************
 Copyright (c)  2022/3/13
 Giuseppe Bianconi

 ******************************************************************************/

package util;

public class Time {
	public static float timeStarted = System.nanoTime();

	public static float getTime () {
		return (float) ((System.nanoTime() - timeStarted) * 1E-9);
	}
}
