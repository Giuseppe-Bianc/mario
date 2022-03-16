/*******************************************************************************
 Copyright (c)  17/03/22, 00:20  Giuseppe-Bianc
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
	private boolean keyPressed[] = new boolean[350];

	private KeyListener() {

	}

	public static KeyListener get() {
		if (KeyListener.instance == null) {
			KeyListener.instance = new KeyListener();
		}

		return KeyListener.instance;
	}

	public static void keyCallback(long window, int key, int scancode, int action, int mods) {
		if (action == GLFW_PRESS) {
			get().keyPressed[key] = true;
		} else if (action == GLFW_RELEASE) {
			get().keyPressed[key] = false;
		}
	}

	public static boolean isKeyPressed(int keyCode) {
		return get().keyPressed[keyCode];
	}
}
