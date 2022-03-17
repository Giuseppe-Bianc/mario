/*******************************************************************************
 Copyright (c)  17/03/22, 14:04  Giuseppe-Bianc
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

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

	protected Camera camera;
	private boolean isRunning = false;
	protected List<GameObject> gameObjects = new ArrayList<>();

	public Scene() {

	}

	/**
	 * This function is called when the model is initialized.
	 */
	public void init() {

	}

	/**
	 * Start all the game objects in the game
	 */
	public void start() {
		for (GameObject go : gameObjects) {
			go.start();
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
		}
	}

	/**
	 * This function is called once per frame
	 *
	 * @param dt The time in seconds since the last update.
	 */
	public abstract void update(float dt);
}