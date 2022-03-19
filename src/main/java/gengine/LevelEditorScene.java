/*******************************************************************************
 Copyright (c)  19/03/22, 22:22  Giuseppe-Bianc
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

import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import util.AssetPool;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

	private static final String PR = "assets/", OBJP = "Object ";
	private static final String PRT = PR + "images/", TIM = PRT + "testImage.png";
	private static final String SHPT = PR + "shaders/default.glsl", TIM2 = PRT + "testImage2.png";
	private static final int UNN = 100;

	public LevelEditorScene() {

	}

	/**
	 * Initialize the OpenGL state for rendering
	 */
	@Override
	public void init() {
		loadResources();
		this.camera = new Camera(new Vector2f(-250, 0));

		Spritesheet sprites = AssetPool.getSpritesheet(PRT + "spritesheet.png");

		GameObject obj1 = new GameObject(OBJP + "1", new Transform(new Vector2f(UNN, UNN), new Vector2f(UNN, UNN)));
		obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
		this.addGameObjectToScene(obj1);

		GameObject obj2 = new GameObject(OBJP + "2", new Transform(new Vector2f(UNN * 4, UNN), new Vector2f(UNN, UNN)));
		obj2.addComponent(new SpriteRenderer(sprites.getSprite(7)));
		this.addGameObjectToScene(obj2);

	}

	private void loadResources() {
		AssetPool.getShader(SHPT);

		AssetPool.addSpritesheet(PRT + "spritesheet.png", new components.Spritesheet(AssetPool.getTexture(PRT + "spritesheet.png"),
				16, 16, 26, 0));
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
		if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
			camera.position.x += 100f * dt;
		} else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
			camera.position.x -= 100f * dt;
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
			camera.position.y += 100f * dt;
		} else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
			camera.position.y -= 100f * dt;
		}

		for (GameObject go : this.gameObjects) {
			go.update(dt);
		}

		this.renderer.render();
	}
}
