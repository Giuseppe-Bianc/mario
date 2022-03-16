/*******************************************************************************
 Copyright (c)  16/03/22, 22:28  Giuseppe-Bianc
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 ******************************************************************************//
package gengine;

public abstract class Scene {

	protected Camera camera;

	public Scene () {

	}

	public void init () {

	}

	public abstract void update (float dt);
}