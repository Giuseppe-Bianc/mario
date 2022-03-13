/*******************************************************************************
 Copyright (c)  2022/3/13
 Giuseppe Bianconi

 ******************************************************************************/
package gengine;

// external

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

//internal
import util.Time;

//static
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private final int width;
	private final int height;
	private final String title;
	private long glfwWindow;
	private float r;
	private float g;
	private float b;
	private float a;
	private boolean fadeToBlack = false;
	private static Window window = null;

	private Window () {
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
	public static Window get () {
		if (Window.window == null) {
			Window.window = new Window();
		}

		return Window.window;
	}

	public void run () {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		init();
		loop();
		glfwDestroyWindow(glfwWindow);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	/**
	 * responsabile per l' inizializzazione della finestra
	 */
	public void init () {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW.");
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

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
	}


	public void loop () {
		float beginTime = Time.getTime();
		float endTime;
		float dt = -1.0f;

		while (!glfwWindowShouldClose(glfwWindow)) {
			// Poll events
			glfwPollEvents();

			glClearColor(r, g, b, a);
			glClear(GL_COLOR_BUFFER_BIT);

			glfwSwapBuffers(glfwWindow);

			endTime = Time.getTime();
			dt = endTime - beginTime;
			beginTime = endTime;
		}
	}
}