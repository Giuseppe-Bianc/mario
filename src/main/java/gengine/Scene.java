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

import renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

	protected Renderer renderer = new Renderer();
	protected Camera camera;
	private boolean isRunning = false;
	protected List<GameObject> gameObjects = new ArrayList<>();

	public Scene() {

	}

	/**
	 * This function is called when the game model is first started
	 */
	public void init() {

	}

	/**
	 * For each game object in the gameObjects array, call the start method on the game object
	 */
	public void start() {
		for (GameObject go : gameObjects) {
			go.start();
			this.renderer.add(go);
		}
		isRunning = true;
	}

	/**
	 * Add a game object to the scene
	 *
	 * @param go The GameObject to add to the scene.
	 */
	public void addGameObjectToScene(GameObject go) {
		if (!isRunning) {
			gameObjects.add(go);
		} else {
			gameObjects.add(go);
			go.start();
			this.renderer.add(go);
		}
	}

	/**
	 * "Update the game state."<p> The update function is called once per frame, and is where the
	 * game logic is placed. This is where you define the rules of your game, and how your game
	 * should react to different events
	 *
	 * @param dt The time in seconds since the last update.
	 */
	public abstract void update(float dt);

	/**
	 * Returns the camera that is used to render the scene
	 *
	 * @return The camera object.
	 */
	public Camera camera() {
		return this.camera;
	}
}
