/*******************************************************************************
 Copyright (c)  19/03/22, 23:16  Giuseppe-Bianc
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

public class AssetPool {
	private static final Map<String, Shader> shaders = new HashMap<>();
	private static final Map<String, Texture> textures = new HashMap<>();
	private static final Map<String, Spritesheet> spritesheets = new HashMap<>();

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

	public static void addSpritesheet(String resourceName, Spritesheet spritesheet) {
		File file = new File(resourceName);
		if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
			AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
		}
	}

	public static Spritesheet getSpritesheet(String resourceName) {
		File file = new File(resourceName);
		if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
			assert false : "Error: Tried to access spritesheet '" + resourceName + "' and it has not been added to asset pool.";
		}
		return AssetPool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
	}
}
