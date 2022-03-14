/*******************************************************************************
 Copyright (c)  2022/3/14
 Giuseppe Bianconi

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
