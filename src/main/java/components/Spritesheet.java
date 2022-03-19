/*******************************************************************************
 Copyright (c)  19/03/22, 23:13  Giuseppe-Bianc
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

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {

	private final Texture texture;
	private final List<Sprite> sprites;

	public Spritesheet(Texture texture, int spriteWidth, int spriteHeight, int numSprites, int spacing) {
		this.sprites = new ArrayList<>();

		this.texture = texture;
		int currentX = 0;
		int currentY = texture.getHeight() - spriteHeight;
		for (int i = 0; i < numSprites; i++) {
			float topY = (currentY + spriteHeight) / (float) texture.getHeight();
			float rightX = (currentX + spriteWidth) / (float) texture.getWidth();
			float leftX = currentX / (float) texture.getWidth();
			float bottomY = currentY / (float) texture.getHeight();

			Vector2f[] texCoords = {
					new Vector2f(rightX, topY),
					new Vector2f(rightX, bottomY),
					new Vector2f(leftX, bottomY),
					new Vector2f(leftX, topY)
			};
			Sprite sprite = new Sprite(this.texture, texCoords);
			this.sprites.add(sprite);

			currentX += spriteWidth + spacing;
			if (currentX >= texture.getWidth()) {
				currentX = 0;
				currentY -= spriteHeight + spacing;
			}
		}
	}

	public Sprite getSprite(int index) {
		return this.sprites.get(index);
	}
}
