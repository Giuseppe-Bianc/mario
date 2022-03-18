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

import org.joml.Vector2f;

public class LevelEditorScene extends gengine.Scene {

	private static final float UNN = 100.0f;

	public LevelEditorScene() {

	}

	/**
	 * Initialize the OpenGL state for rendering
	 */
	@Override
	public void init() {
		this.camera = new gengine.Camera(new Vector2f(-250, 0));

		int xOffset = 10, yOffset = 10;

		float totalWidth = (float) (600 - xOffset * 2);
		float totalHeight = (float) (300 - yOffset * 2);
		float sizeX = totalWidth / UNN;
		float sizeY = totalHeight / UNN;
		float padding = 3;

		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {
				float xPos = xOffset + (x * sizeX) + (padding * x);
				float yPos = yOffset + (y * sizeY) + (padding * y);

				gengine.GameObject go = new gengine.GameObject("Obj" + x + "" + y, new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
				go.addComponent(new components.SpriteRenderer(new org.joml.Vector4f(xPos / totalWidth, yPos / totalHeight, 1, 1)));
				this.addGameObjectToScene(go);
			}
		}
	}

	/**
	 * Upload the texture to the GPU.<p> Upload the projection and view matrices to the GPU.<P>
	 * Upload the time to the GPU.<P> Bind the VAO.<P> Enable the vertex and element array
	 * attributes.<P> Draw the triangles. - Disable the vertex and element array attributes. -
	 * Unbind the VAO.
	 *
	 * @param dt The time since the last update call.
	 */
	@Override
	public void update(float dt) {
		System.out.println("FPS: " + (1.0f / dt));

		for (gengine.GameObject go : this.gameObjects) {
			go.update(dt);
		}

		this.renderer.render();
	}
}
