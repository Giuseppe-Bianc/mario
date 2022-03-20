/*******************************************************************************
 Copyright (c)  20/03/22, 12:55  Giuseppe-Bianc
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 ******************************************************************************/
package util;

import components.Spritesheet;
import renderer.Shader;
import renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Giuseppe-Bianc
 */
public class AssetPool {
	private static final Map<String, Shader> shaders = new HashMap<>();
	private static final Map<String, Texture> textures = new HashMap<>();
	private static final Map<String, Spritesheet> spritesheets = new HashMap<>();

	/**
	 * Given a resource name, return the shader object that corresponds to that resource
	 *
	 * @param resourceName The name of the shader file.
	 * @return The Shader object.
	 */
	public static Shader getShader(String resourceName) {
		File file = new File(resourceName);
		if (AssetPool.shaders.containsKey(file.getAbsolutePath())) {
			return AssetPool.shaders.get(file.getAbsolutePath());
		} else {
			Shader shader = new Shader(resourceName);
			shader.compile();
			AssetPool.shaders.put(file.getAbsolutePath(), shader);
			return shader;
		}
	}

	/**
	 * Given a resource name, return the texture with that name
	 *
	 * @param resourceName The path to the file.
	 * @return A Texture object.
	 */
	public static Texture getTexture(String resourceName) {
		File file = new File(resourceName);
		if (AssetPool.textures.containsKey(file.getAbsolutePath())) {
			return AssetPool.textures.get(file.getAbsolutePath());
		} else {
			Texture texture = new Texture(resourceName);
			AssetPool.textures.put(file.getAbsolutePath(), texture);
			return texture;
		}
	}

	/**
	 * Adds a spritesheet to the AssetPool
	 *
	 * @param resourceName The name of the file to load.
	 * @param spritesheet  The spritesheet to add to the AssetPool.
	 */
	public static void addSpritesheet(String resourceName, Spritesheet spritesheet) {
		File file = new File(resourceName);
		if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
			AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
		}
	}

	/**
	 * Given a resource name, return the spritesheet that has been loaded with that name
	 *
	 * @param resourceName The name of the spritesheet file.
	 * @return A spritesheet object.
	 */
	public static Spritesheet getSpritesheet(String resourceName) {
		File file = new File(resourceName);
		if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
			assert false : "Error: Tried to access spritesheet '" + resourceName + "' and it has not been added to asset pool.";
		}
		return AssetPool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
	}
}
