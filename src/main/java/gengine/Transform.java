/*******************************************************************************
 Copyright (c)  20/03/22, 16:02  Giuseppe-Bianc
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

public class Transform {
	public Vector2f position, scale;

	public Transform() {
		this.init(new Vector2f(), new Vector2f());
	}

	public Transform(Vector2f position) {
		this.init(position, new Vector2f());
	}

	public Transform(Vector2f position, Vector2f scale) {
		this.init(position, scale);
	}

	/**
	 * This function initializes the position and scale of the object
	 *
	 * @param position The position of the object in the world.
	 * @param scale    The scale of the object.
	 */
	public void init(Vector2f position, Vector2f scale) {
		this.position = position;
		this.scale = scale;
	}

	/**
	 * Creates a new Transform object with the same position and scale as this Transform
	 *
	 * @return A new Transform object.
	 */
	public Transform copy() {
		return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
	}

	/**
	 * Copy the position and scale of this Transform to another Transform
	 *
	 * @param to The Transform to copy the values to.
	 */
	public void copy(Transform to) {
		to.position.set(this.position);
		to.scale.set(this.scale);
	}

	/**
	 * Returns true if the given object is equal to this object
	 *
	 * @param o the object to compare against
	 * @return true if the position and the scale are  equals, false if null and if not instance of
	 * Transform.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Transform t)) return false;

		return t.position.equals(this.position) && t.scale.equals(this.scale);
	}
}
