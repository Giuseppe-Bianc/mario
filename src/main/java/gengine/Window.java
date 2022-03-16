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

// external

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private final int width;
	private final int height;
	private final String title;
	private long glfwWindow;
	public float r, g, b, a;
	private static Window window = null;
	private static gengine.Scene currentScene;

	private Window() {
		this.width = 960;
		this.height = 540;
		this.title = "Mario";
		this.r = this.b = this.g = this.a = 1;
	}

	/**
	 * ritorna la finestra non nulla che rappresenta l' unica istanza possibile
	 *
	 * @return Window.window istanza della finestra
	 */
	public static Window get() {
		if (Window.window == null) {
			Window.window = new Window();
		}

		return Window.window;
	}

	public static void changeScene(int newScene) {
		switch (newScene) {
			case 0:
				currentScene = new LevelEditorScene();
				currentScene.init();
				break;
			case 1:
				currentScene = new gengine.LevelScene();
				currentScene.init();
				break;
			default:
				assert false : "Unknown scene '" + newScene + "'";
				break;
		}
	}


	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		init();
		loop();
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);
		glfwTerminate();
		java.util.Objects.requireNonNull(glfwSetErrorCallback(null)).free();
	}

	private static void setGlfwWindowHint() {
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
	}

	/**
	 * responsabile per l' inizializzazione della finestra
	 */
	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW.");
		}

		glfwDefaultWindowHints();

		glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		if (glfwWindow == NULL) {
			throw new IllegalStateException("Failed to create the GLFW window.");
		}

		glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
		glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
		glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
		glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

		glfwMakeContextCurrent(glfwWindow);
		glfwSwapInterval(1);
		glfwShowWindow(glfwWindow);
		GL.createCapabilities();
		Window.changeScene(0);
	}


	public void loop() {
		float beginTime = Time.getTime();
		float endTime;
		float dt = -1.0f;

		while (!glfwWindowShouldClose(glfwWindow)) {
			glfwPollEvents();

			glClearColor(r, g, b, a);
			glClear(GL_COLOR_BUFFER_BIT);

			if (dt >= 0) {
				currentScene.update(dt);
			}

			glfwSwapBuffers(glfwWindow);

			endTime = Time.getTime();
			dt = endTime - beginTime;
			beginTime = endTime;
		}
	}
}