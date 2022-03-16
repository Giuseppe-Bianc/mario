/*******************************************************************************
 Copyright (c)  16/03/22, 22:40  Giuseppe-Bianc
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
import renderer.*;
import util.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class LevelEditorScene extends Scene {

	private int vertexID, fragmentID, shaderProgram;

	private float[] vertexArray = {
			100f, 0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1, 1, // Bottom right 0
			0f, 100f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0, 0, // Top left     1
			100f, 100f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1, 0, // Top right    2
			0f, 0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0, 1  // Bottom left  3
	};

	// IMPORTANT: Must be in counter-clockwise order
	private int[] elementArray = {
			2, 1, 0, // Top right triangle
			0, 1, 3 // bottom left triangle
	};

	private int vaoID, vboID, eboID;

	private Shader defaultShader;
	private Texture testTexture;

	public LevelEditorScene () {

	}

	@Override
	public void init () {
		this.camera = new Camera(new Vector2f(-200, -300));
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

	@Override
	public void update (float dt) {
//        camera.position.x -= dt * 50.0f;
//        camera.position.y -= dt * 20.0f;

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
