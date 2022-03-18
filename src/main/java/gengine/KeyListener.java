/*******************************************************************************
 Copyright (c)  18/03/22, 19:06  Giuseppe-Bianc
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

public class KeyListener {
	private static KeyListener instance;
	private final boolean[] keyPressed = new boolean[350];

	private KeyListener() {

	}

	/**
	 * The get() function is a static method that returns the singleton instance of the KeyListener
	 * class.
	 * <p>
	 * If the instance has not been created yet, it creates it
	 *
	 * @return The instance of the KeyListener class.
	 */
	public static KeyListener get() {
		if (KeyListener.instance == null) {
			KeyListener.instance = new KeyListener();
		}

		return KeyListener.instance;
	}

	/**
	 * When a key is pressed, the keyPressed array is set to true for that key. When a key is
	 * released, the keyPressed array is set to false for that key
	 *
	 * @param window   The window that received the event.
	 * @param key      The key that was pressed or released.
	 * @param scancode The scancode of the key.
	 * @param action   The action the key triggered.
	 * @param mods     A bitfield describing which modifier keys were held down.
	 */
	public static void keyCallback(long window, int key, int scancode, int action, int mods) {
		if (action == GLFW_PRESS) {
			get().keyPressed[key] = true;
		} else if (action == GLFW_RELEASE) {
			get().keyPressed[key] = false;
		}
	}

	/**
	 * Returns true if the specified key is pressed
	 *
	 * @param keyCode The key code of the key you want to check.
	 * @return A boolean value.
	 */
	public static boolean isKeyPressed(int keyCode) {
		return get().keyPressed[keyCode];
	}
}
