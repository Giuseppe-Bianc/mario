/*******************************************************************************
 Copyright (c)  19/03/22, 15:28  Giuseppe-Bianc
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 ******************************************************************************/
package components;

import org.joml.Vector2f;
import renderer.Texture;

public class Sprite {

	private Texture texture;
	private Vector2f[] texCoords;

	public Sprite(Texture texture) {
		this.texture = texture;
		Vector2f[] texCoords = {
				new Vector2f(1, 1),
				new Vector2f(1, 0),
				new Vector2f(0, 0),
				new Vector2f(0, 1)
		};
		this.texCoords = texCoords;
	}

	public Sprite(Texture texture, Vector2f[] texCoords) {
		this.texture = texture;
		this.texCoords = texCoords;
	}

	public Texture getTexture() {
		return this.texture;
	}

	public Vector2f[] getTexCoords() {
		return this.texCoords;
	}
}
