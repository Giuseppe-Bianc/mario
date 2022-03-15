/*******************************************************************************
 Copyright (c)  2022  Giuseppe Bianconi
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

public class LevelEditorScene extends Scene {
	private boolean changingScene = false;
	private float timeToChangeScene = 2.0f;

	public LevelEditorScene () {
		System.out.println("Inside level editor scene");
	}

	@Override
	public void update (float dt) {

		if (!changingScene && KeyListener.isKeyPressed(java.awt.event.KeyEvent.VK_SPACE)) {
			changingScene = true;
		}

		if (changingScene && timeToChangeScene > 0) {
			timeToChangeScene -= dt;
			Window.get().r -= dt * 5.0f;
			Window.get().g -= dt * 5.0f;
			Window.get().b -= dt * 5.0f;
		} else if (changingScene) {
			Window.changeScene(1);
		}
	}
}
