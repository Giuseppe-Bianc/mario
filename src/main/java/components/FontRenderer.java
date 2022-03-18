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
package components;

import gengine.Component;

public class FontRenderer extends Component {

	/**
	 * It checks to see if the game object has a SpriteRenderer component.
	 */
	@Override
	public void start() {
		if (gameObject.getComponent(components.SpriteRenderer.class) != null) {
			System.out.println("Found Font Renderer!");
		}
	}

	/**
	 * This function is called once per frame
	 *
	 * @param dt The time in seconds since the last update.
	 */
	@Override
	public void update(float dt) {

	}
}
