/*******************************************************************************
 Copyright (c)  19/03/22, 16:35  Giuseppe-Bianc
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 ******************************************************************************/
package renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * This class is used to create and upload shaders to the GPU
 */
public class Shader {

	private static final String QT = "'", ERR = "ERROR: ", ERR2 = ERR + QT;
	private static final String SPC = "'\n\t", LEND = "\r\n";
	private static final String SCF = "shader compilation failed.";
	private static final String TYP = "#type", VRT = "vertex", FRG = "fragment";
	private static final String RNG = "a-z", UNT = "Unexpected token '";
	private int shaderProgramID;
	private boolean beingUsed = false;

	private String vertexSource;
	private String fragmentSource;
	private final String filepath;

	/**
	 * We are creating a new shader object. We are also setting the filepath of the shader.
	 */
	public Shader(String filepath) {
		this.filepath = filepath;
		try {
			String source = new String(Files.readAllBytes(Paths.get(filepath)));
			String[] splitString = source.split("(" + TYP + ")( )+([" + RNG + RNG.toUpperCase() + "]+)");

			int index = source.indexOf(TYP) + 6;
			int eol = source.indexOf(LEND, index);
			String firstPattern = source.substring(index, eol).trim();

			index = source.indexOf(TYP, eol) + 6;
			eol = source.indexOf(LEND, index);
			String secondPattern = source.substring(index, eol).trim();

			if (firstPattern.equals(VRT)) {
				vertexSource = splitString[1];
			} else if (firstPattern.equals(FRG)) {
				fragmentSource = splitString[1];
			} else {
				throw new IOException(UNT + firstPattern + QT);
			}

			if (secondPattern.equals(VRT)) {
				vertexSource = splitString[2];
			} else if (secondPattern.equals(FRG)) {
				fragmentSource = splitString[2];
			} else {
				throw new IOException(UNT + secondPattern + QT);
			}
		} catch (IOException e) {
			e.printStackTrace();
			assert false : ERR + "Could not open file for shader: '" + filepath + QT;
		}
	}

	/**
	 * Compile the shaders and link them into a shader program
	 */
	public void compile() {
		int vertexID, fragmentID;

		vertexID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexID, vertexSource);
		glCompileShader(vertexID);

		int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
			System.out.println(ERR2 + filepath + SPC + VRT + SCF);
			System.out.println(glGetShaderInfoLog(vertexID, len));
			assert false : "";
		}

		fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentID, fragmentSource);
		glCompileShader(fragmentID);

		success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
			System.out.println(ERR2 + filepath + SPC + FRG + SCF);
			System.out.println(glGetShaderInfoLog(fragmentID, len));
			assert false : "";
		}

		shaderProgramID = glCreateProgram();
		glAttachShader(shaderProgramID, vertexID);
		glAttachShader(shaderProgramID, fragmentID);
		glLinkProgram(shaderProgramID);

		success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
		if (success == GL_FALSE) {
			int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
			System.out.println(ERR2 + filepath + SPC + "Linking of shaders failed.");
			System.out.println(glGetProgramInfoLog(shaderProgramID, len));
			assert false : "";
		}
	}

	/**
	 * If the shader is not being used, then use it
	 */
	public void use() {
		if (!beingUsed) {
			glUseProgram(shaderProgramID);
			beingUsed = true;
		}
	}

	/**
	 * Detach the shader from the OpenGL pipeline
	 */
	public void detach() {
		glUseProgram(0);
		beingUsed = false;
	}


	/**
	 * It uploads a 4x4 matrix to the GPU
	 *
	 * @param varName The name of the variable in the shader program.
	 * @param mat4    The matrix to upload.
	 */
	public void uploadMat4f(String varName, Matrix4f mat4) {
		int varLocation = glGetUniformLocation(shaderProgramID, varName);
		use();
		FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
		mat4.get(matBuffer);
		glUniformMatrix4fv(varLocation, false, matBuffer);
	}

	/**
	 * Uploads a 3x3 matrix to the shader
	 *
	 * @param varName The name of the variable in the shader program.
	 * @param mat3    The matrix to upload.
	 */
	public void uploadMat3f(String varName, Matrix3f mat3) {
		int varLocation = glGetUniformLocation(shaderProgramID, varName);
		use();
		FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
		mat3.get(matBuffer);
		glUniformMatrix3fv(varLocation, false, matBuffer);
	}


	/**
	 * Uploads a Vector4f to the shader
	 *
	 * @param varName The name of the variable in the shader program.
	 * @param vec     The vector to upload.
	 */
	public void uploadVec4f(String varName, Vector4f vec) {
		int varLocation = glGetUniformLocation(shaderProgramID, varName);
		use();
		glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
	}

	/**
	 * Uploads a Vector3f to the shader
	 *
	 * @param varName The name of the variable in the shader program.
	 * @param vec     The vector to upload.
	 */
	public void uploadVec3f(String varName, Vector3f vec) {
		int varLocation = glGetUniformLocation(shaderProgramID, varName);
		use();
		glUniform3f(varLocation, vec.x, vec.y, vec.z);
	}


	/**
	 * Uploads a Vector2f to the shader
	 *
	 * @param varName The name of the variable in the shader program.
	 * @param vec     The vector to upload.
	 */
	public void uploadVec2f(String varName, Vector2f vec) {
		int varLocation = glGetUniformLocation(shaderProgramID, varName);
		use();
		glUniform2f(varLocation, vec.x, vec.y);
	}

	/**
	 * Uploads a float value to a uniform variable in the shader program
	 *
	 * @param varName The name of the variable to be uploaded.
	 * @param val     The value to be uploaded.
	 */
	public void uploadFloat(String varName, float val) {
		int varLocation = glGetUniformLocation(shaderProgramID, varName);
		use();
		glUniform1f(varLocation, val);
	}

	/**
	 * Uploads an integer value to a uniform variable in the shader program
	 *
	 * @param varName The name of the variable in the shader program.
	 * @param val     The value to be uploaded.
	 */
	public void uploadInt(String varName, int val) {
		int varLocation = glGetUniformLocation(shaderProgramID, varName);
		use();
		glUniform1i(varLocation, val);
	}

	/**
	 * Uploads a texture to the GPU
	 *
	 * @param varName The name of the variable in the shader program.
	 * @param slot    The texture slot to bind the texture to.
	 */
	public void uploadTexture(String varName, int slot) {
		int varLocation = glGetUniformLocation(shaderProgramID, varName);
		use();
		glUniform1i(varLocation, slot);
	}

	public void uploadIntArray(String varName, int[] array) {
		int varLocation = glGetUniformLocation(shaderProgramID, varName);
		use();
		glUniform1iv(varLocation, array);
	}
}

