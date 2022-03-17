/*******************************************************************************
 Copyright (c)  17/03/22, 14:04  Giuseppe-Bianc
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

public class SpriteRenderer extends Component {

	private boolean firstTime = false;

	@Override
	public void start() {
		System.out.println("I am starting");
	}

	@Override
	public void update(float dt) {
		if (!firstTime) {
			System.out.println("I am updating");
			firstTime = true;
		}
	}
}
