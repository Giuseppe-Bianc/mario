/*******************************************************************************
 Copyright (c)  17/03/22, 14:09  Giuseppe-Bianc
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

	@Override
	public void start() {
		if (gameObject.getComponent(SpriteRenderer.class) != null) {
			System.out.println("Found Font Renderer!");
		}
	}

	@Override
	public void update(float dt) {

	}
}
