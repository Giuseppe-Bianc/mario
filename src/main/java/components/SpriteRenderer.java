/*******************************************************************************
 Copyright (c)  18/03/22, 16:53  Giuseppe-Bianc
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


	@Override
	public void start() {

	}

	@Override
	public void update(float dt) {

	}

	public Vector4f getColor() {
		return color;
	}
}
