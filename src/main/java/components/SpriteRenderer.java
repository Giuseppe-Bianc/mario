/*******************************************************************************
 Copyright (c)  18/03/22, 18:33  Giuseppe-Bianc
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
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

	private Vector4f color;

	public SpriteRenderer(Vector4f color) {
		this.color = color;
	}


	/**
	 * The start() function is called when the SpriteRenderer is started.
	 */
	@Override
	public void start() {

	}

	/**
	 * This function is called once per frame
	 *
	 * @param dt The time in seconds since the last update.
	 */
	@Override
	public void update(float dt) {

	}

	/**
	 * Returns the color of the light
	 *
	 * @return The color of the light.
	 */
	public Vector4f getColor() {
		return color;
	}
}
