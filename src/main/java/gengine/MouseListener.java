/*******************************************************************************
 Copyright (c)  18/03/22, 19:10  Giuseppe-Bianc
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 ******************************************************************************/
package gengine;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {
	private static MouseListener instance;
	private double scrollX, scrollY, xPos, yPos, lastY, lastX;
	private final boolean[] mouseButtonPressed = new boolean[3];
	private boolean isDragging;

	private MouseListener() {
		this.scrollX = this.scrollY = this.xPos = this.yPos = this.lastX = this.lastY = 0.0;
	}

	/**
	 * The get() method creates a new instance of the MouseListener class if one doesn't already
	 * exist, and returns a reference to that instance
	 *
	 * @return The MouseListener instance.
	 */
	public static MouseListener get() {
		if (MouseListener.instance == null) {
			MouseListener.instance = new MouseListener();
		}

		return MouseListener.instance;
	}

	/**
	 * This function is called when the mouse is moved
	 *
	 * @param window The window that received the event.
	 * @param x_pos  The x position of the mouse in window coordinates.
	 * @param y_pos  The y position of the mouse in the window.
	 */
	public static void mousePosCallback(long window, double x_pos, double y_pos) {
		get().lastX = get().xPos;
		get().lastY = get().yPos;
		get().xPos = x_pos;
		get().yPos = y_pos;
		get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
	}

	/**
	 * If the mouse button is pressed, set the corresponding mouseButtonPressed array element to
	 * true. If the mouse button is released, set the corresponding mouseButtonPressed array element
	 * to false
	 *
	 * @param window The window that received the event.
	 * @param button The button that was pressed or released.
	 * @param action The action the button was just pressed or released.
	 * @param mods   A bitfield describing which modifier keys were held down.
	 */
	public static void mouseButtonCallback(long window, int button, int action, int mods) {
		if (action == GLFW_PRESS) {
			if (button < get().mouseButtonPressed.length) {
				get().mouseButtonPressed[button] = true;
			}
		} else if (action == GLFW_RELEASE) {
			if (button < get().mouseButtonPressed.length) {
				get().mouseButtonPressed[button] = false;
				get().isDragging = false;
			}
		}
	}

	/**
	 * This function is called when the mouse wheel is scrolled.
	 *
	 * @param window  The window that received the event.
	 * @param xOffset The amount of scrolling in the x direction.
	 * @param yOffset The amount of scrolling in the y direction.
	 */
	public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
		get().scrollX = xOffset;
		get().scrollY = yOffset;
	}

	/**
	 * This function is called at the end of each frame.
	 */
	public static void endFrame() {
		get().scrollX = get().scrollY = 0;
		get().lastX = get().xPos;
		get().lastY = get().yPos;
	}

	/**
	 * the x position of the player
	 *
	 * @return The x position of the player.
	 */
	public static float getX() {
		return (float) get().xPos;
	}

	/**
	 * the y position of the mouse
	 *
	 * @return The y position of the player.
	 */
	public static float getY() {
		return (float) get().yPos;
	}

	/**
	 * Returns the change in x position since the last time the function was called
	 *
	 * @return The difference between the lastX and xPos.
	 */
	public static float getDx() {
		return (float) (get().lastX - get().xPos);
	}

	/**
	 * Returns the change in the y position of the player since the last frame
	 *
	 * @return The difference between the lastY and yPos.
	 */
	public static float getDy() {
		return (float) (get().lastY - get().yPos);
	}

	/**
	 * Returns the current scroll position of the view
	 *
	 * @return The scrollX property of the current WebView instance.
	 */
	public static float getScrollX() {
		return (float) get().scrollX;
	}

	/**
	 * Returns the current vertical scroll position of the view
	 *
	 * @return The scrollY property of the current WebView instance.
	 */
	public static float getScrollY() {
		return (float) get().scrollY;
	}

	/**
	 * Returns true if the mouse is currently being dragged
	 *
	 * @return A boolean value.
	 */
	public static boolean isDragging() {
		return get().isDragging;
	}

	/**
	 * Returns true if the specified mouse button is pressed
	 *
	 * @param button The button to check.
	 * @return A boolean value.
	 */
	public static boolean mouseButtonDown(int button) {
		if (button < get().mouseButtonPressed.length) {
			return get().mouseButtonPressed[button];
		} else {
			return false;
		}
	}
}
