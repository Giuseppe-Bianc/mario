/*******************************************************************************
 Copyright (c)  18/03/22, 19:10  Giuseppe-Bianc
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

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
	private final Matrix4f projectionMatrix, viewMatrix;
	public Vector2f position;

	public Camera(Vector2f position) {
		this.position = position;
		this.projectionMatrix = new Matrix4f();
		this.viewMatrix = new Matrix4f();
		adjustProjection();
	}

	/**
	 * The projection matrix is a 4x4 matrix that defines the transformation from 3D world space to
	 * 2D screen space. <p> The ortho function sets up a projection matrix that defines a parallel
	 * projection. <p> The first two parameters define the range of the x and y coordinates. <p> The
	 * last two parameters define the near and far clipping planes. <p> The near and far clipping
	 * planes are the minimum and maximum distances from the camera to the screen. <p> The near
	 * clipping plane must be less than the far clipping plane.  <p> The near clipping plane must be
	 * greater than zero.<p> The far clipping plane must be greater than the near clipping plane.<p>
	 * The near and far clipping planes must be greater than zero.<p> The near and far clipping
	 * planes must be less than 100.<p> The near and far clipping planes must be greater than zero.
	 * <p>The near and far clipping planes must be
	 */
	public void adjustProjection() {
		projectionMatrix.identity();
		projectionMatrix.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f);
	}

	/**
	 * This function returns a view matrix that is used to transform the world into the camera's
	 * view
	 *
	 * @return The view matrix.
	 */
	public Matrix4f getViewMatrix() {
		Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
		Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
		this.viewMatrix.identity();
		viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
				cameraFront.add(position.x, position.y, 0.0f),
				cameraUp);

		return this.viewMatrix;
	}

	/**
	 * Returns the projection matrix
	 *
	 * @return The projection matrix.
	 */
	public Matrix4f getProjectionMatrix() {
		return this.projectionMatrix;
	}
}