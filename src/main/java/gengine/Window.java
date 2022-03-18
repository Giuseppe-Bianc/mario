/*******************************************************************************
 Copyright (c)  18/03/22, 19:05  Giuseppe-Bianc
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

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

import static java.util.Objects.requireNonNull;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private final int width, height;
	private final String title;
	private long glfwWindow;
	public float r, g, b, a;
	private static Window window = null;
	private static Scene currentScene;

	private Window() {
		this.width = 960;
		this.height = 540;
		this.title = "Mario";
		this.r = this.b = this.g = 0;
		this.a = 1;
	}

	/**
	 * Change the current scene to a new scene
	 *
	 * @param newScene The scene to switch to.
	 */
	public static void changeScene(int newScene) {
		switch (newScene) {
			case 0:
				currentScene = new gengine.LevelEditorScene();
				currentScene.init();
				currentScene.start();
				break;
			case 1:
				currentScene = new gengine.LevelScene();
				currentScene.init();
				currentScene.start();
				break;
			default:
				assert false : "Unknown scene '" + newScene + "'";
				break;
		}
	}


	/**
	 * The get() function returns a singleton instance of the Window object if it exists, otherwise
	 * it creates a new Window object and returns it
	 *
	 * @return The Window object.
	 */
	public static Window get() {
		if (Window.window == null) {
			Window.window = new Window();
		}

		return Window.window;
	}

	/**
	 * Returns the current scene
	 *
	 * @return currentScene The current scene.
	 */
	public static Scene getScene() {
		return get().currentScene;
	}

	/**
	 * Initialize the GLFW library, create a window, and start the main loop
	 */
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		init();
		loop();
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);
		glfwTerminate();
		requireNonNull(glfwSetErrorCallback(null)).free();
	}

	/**
	 * Set the window hint for the OpenGL context
	 */
	private static void setGlfwWindowHint() {
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
	}

	/**
	 * Initialize the GLFW library and create a window
	 */
	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW.");
		}

		glfwDefaultWindowHints();
		setGlfwWindowHint();

		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW.");
		}

		glfwDefaultWindowHints();
		setGlfwWindowHint();

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

	/**
	 * It loops through the game loop.
	 */
	public void loop() {
		float beginTime = Time.getTime(), endTime, dt = -1.0f;

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