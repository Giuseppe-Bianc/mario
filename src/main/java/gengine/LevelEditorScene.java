/*******************************************************************************
 Copyright (c)  15/03/22, 22:17  Giuseppe-Bianc
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

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class LevelEditorScene extends Scene {
	private String vertexShaderSrc = "#version 330 core\n" +
			"layout (location=0) in vec3 aPos;\n" +
			"layout (location=1) in vec4 aColor;\n\n" +
			"out vec4 fColor;\n\n" +
			"void main(){\n" +
			"\tfColor = aColor;\n" +
			"\tgl_Position = vec4(aPos, 1.0);\n" +
			"}";
	private String fragmentShaderSrc = "#version 330 core\n\n" +
			"in vec4 fColor;\n" +
			"out vec4 color;\n\n" +
			"void main(){\n" +
			"\tcolor = fColor;\n" +
			"}";

	private int vertexID, fragmentID, shaderProgram;

	private float[] vertexArray = {
			0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
			-0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
			0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
			-0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
	};

	private int[] elementArray = {
			2, 1, 0, // Top right triangle
			0, 1, 3 // bottom left triangle
	};

	private int vaoID, vboID, eboID;

	public LevelEditorScene () {

	}

	@Override
	public void init () {
		vertexID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexID, vertexShaderSrc);
		glCompileShader(vertexID);

		int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
			System.out.println("ERROR: 'defaultShader.glsl'\n\tVertex shader compilation failed.");
			System.out.println(glGetShaderInfoLog(vertexID, len));
			assert false : "";
		}

		fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentID, fragmentShaderSrc);
		glCompileShader(fragmentID);

		success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
			System.out.println("ERROR: 'defaultShader.glsl'\n\tFragment shader compilation failed.");
			System.out.println(glGetShaderInfoLog(fragmentID, len));
			assert false : "";
		}

		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexID);
		glAttachShader(shaderProgram, fragmentID);
		glLinkProgram(shaderProgram);

		success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
		if (success == GL_FALSE) {
			int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
			System.out.println("ERROR: 'defaultShader.glsl'\n\tLinking of shaders failed.");
			System.out.println(glGetProgramInfoLog(shaderProgram, len));
			assert false : "";
		}

		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		java.nio.FloatBuffer vertexBuffer = org.lwjgl.BufferUtils.createFloatBuffer(vertexArray.length);
		vertexBuffer.put(vertexArray).flip();

		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

		java.nio.IntBuffer elementBuffer = org.lwjgl.BufferUtils.createIntBuffer(elementArray.length);
		elementBuffer.put(elementArray).flip();

		eboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

		int positionsSize = 3;
		int colorSize = 4;
		int floatSizeBytes = 4;
		int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
		glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
		glEnableVertexAttribArray(0);

		glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
		glEnableVertexAttribArray(1);
	}

	@Override
	public void update (float dt) {
		glUseProgram(shaderProgram);
		glBindVertexArray(vaoID);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);

		glBindVertexArray(0);

		glUseProgram(0);
	}
}