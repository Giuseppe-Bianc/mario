/*******************************************************************************
 Copyright (c)  17/03/22, 14:09  Giuseppe-Bianc
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
import org.lwjgl.BufferUtils;
import renderer.Shader;
import renderer.Texture;
import util.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends gengine.Scene {

	private int vertexID, fragmentID, shaderProgram;

	private final float[] vertexArray = {
			100f, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1, 1,
			0f, 100f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0, 0,
			100f, 100f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1, 0,
			0f, 0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0, 1
	};

	private final int[] elementArray = {
			2, 1, 0,
			0, 1, 3
	};

	private int vaoID, vboID, eboID;

	private Shader defaultShader;
	private Texture testTexture;

	public LevelEditorScene() {

	}

	/**
	 * Initialize the OpenGL state for rendering
	 */
	@Override
	public void init() {
		this.camera = new gengine.Camera(new Vector2f(-200, -300));
		defaultShader = new Shader("assets/shaders/default.glsl");
		defaultShader.compile();
		this.testTexture = new Texture("assets/images/testImage.png");
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
		vertexBuffer.put(vertexArray).flip();

		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

		IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
		elementBuffer.put(elementArray).flip();

		eboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

		int positionsSize = 3;
		int colorSize = 4;
		int uvSize = 2;
		int vertexSizeBytes = (positionsSize + colorSize + uvSize) * Float.BYTES;
		glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
		glEnableVertexAttribArray(0);

		glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
		glEnableVertexAttribArray(1);

		glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
		glEnableVertexAttribArray(2);
	}

	/**
	 * - Upload the texture to the GPU. - Upload the projection and view matrices to the GPU. -
	 * Upload the time to the GPU. - Bind the VAO. - Enable the vertex and element array attributes.
	 * - Draw the triangles. - Disable the vertex and element array attributes. - Unbind the VAO
	 *
	 * @param dt The time since the last update call.
	 */
	@Override
	public void update(float dt) {
		defaultShader.use();

		defaultShader.uploadTexture("TEX_SAMPLER", 0);
		glActiveTexture(GL_TEXTURE0);
		testTexture.bind();

		defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
		defaultShader.uploadMat4f("uView", camera.getViewMatrix());
		defaultShader.uploadFloat("uTime", Time.getTime());
		glBindVertexArray(vaoID);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
		defaultShader.detach();
	}
}
