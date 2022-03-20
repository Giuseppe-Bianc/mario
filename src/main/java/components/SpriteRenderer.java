/*******************************************************************************
 Copyright (c)  20/03/22, 15:49  Giuseppe-Bianc
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

import gengine.Component;
import gengine.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

public class SpriteRenderer extends Component {

	private Vector4f color;
	private Sprite sprite;
	private Transform lastTransform;
	private boolean isDirty = false;

	public SpriteRenderer(Vector4f color) {
		this.color = color;
		this.sprite = new Sprite(null);
	}

	public SpriteRenderer(Sprite sprite) {
		this.sprite = sprite;
		this.color = new Vector4f(1, 1, 1, 1);
	}

	/**
	 * The start() function is called when the SpriteRenderer is started.
	 */
	@Override
	public void start() {
		this.lastTransform = gameObject.transform.copy();
	}

	/**
	 * This function is called once per frame
	 *
	 * @param dt The time in seconds since the last update.
	 */
	@Override
	public void update(float dt) {
		if (!this.lastTransform.equals(this.gameObject.transform)) {
			this.gameObject.transform.copy(this.lastTransform);
			isDirty = true;
		}
	}

	/**
	 * Returns the color of the light
	 *
	 * @return The color of the light.
	 */
	public Vector4f getColor() {
		return this.color;
	}

	public Texture getTexture() {
		return sprite.getTexture();
	}

	public Vector2f[] getTexCoords() {
		return sprite.getTexCoords();
	}

	public void setSprite(components.Sprite sprite) {
		this.sprite = sprite;
		this.isDirty = true;
	}

	public void setColor(Vector4f color) {
		if (!this.color.equals(color)) {
			this.isDirty = true;
			this.color.set(color);
		}
	}

	public boolean isDirty() {
		return this.isDirty;
	}

	public void setClean() {
		this.isDirty = false;
	}
}

