/*******************************************************************************
 Copyright (c)  2022/3/14
 Giuseppe Bianconi 

 ******************************************************************************/

package gengine;

public class LevelScene extends Scene {
	public LevelScene () {
		System.out.println("Inside level scene");
		Window.get().r = 1;
		Window.get().g = 1;
		Window.get().b = 1;
	}

	@Override
	public void update (float dt) {

	}
}
