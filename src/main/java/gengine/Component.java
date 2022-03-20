/*******************************************************************************
 Copyright (c)  20/03/22, 13:03  Giuseppe-Bianc
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

/**
 * @author Giuseppe-Bianc
 */
public abstract class Component {

	/**
	 * simple gameObject
	 */
	public gengine.GameObject gameObject = null;

	/**
	 * This function is called when the game is started
	 */
	public void start() {

	}

	/**
	 * The update function is called once per frame, and is where the game state is updated
	 *
	 * @param dt The time in seconds since the last update.
	 */
	public abstract void update(float dt);
}
