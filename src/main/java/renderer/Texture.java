/*******************************************************************************
 Copyright (c)  17/03/22, 20:53  Giuseppe-Bianc
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

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
	private final String filepath;
	private final int texID;
	private static final String ERT = "Error: (Texture) ";

	/**
	 * It sets the texture parameters.
	 */
	private void setlTexParameter() {
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	}

	/**
	 * It loads an image from a filepath and creates a texture from it.
	 */
	public Texture(String filepath) {
		this.filepath = filepath;
		texID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texID);
		this.setlTexParameter();

		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

		if (image != null) {
			if (channels.get(0) == 3) {
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
						0, GL_RGB, GL_UNSIGNED_BYTE, image);
			} else if (channels.get(0) == 4) {
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
						0, GL_RGBA, GL_UNSIGNED_BYTE, image);
			} else {
				assert false : ERT + "Unknown number of channels '" + channels.get(0) + "'";
			}
		} else {
			assert false : ERT + "Could not load image '" + filepath + "'";
		}

		stbi_image_free(image);
	}

	/**
	 * Binds the texture to the current OpenGL context
	 */
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, texID);
	}

	/**
	 * Unbinds the texture from the current OpenGL context
	 */
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	/**
	 * The function returns the filepath of the file that was uploaded
	 *
	 * @return The filepath.
	 */
	public String getFilepath() {
		return filepath;
	}
}
