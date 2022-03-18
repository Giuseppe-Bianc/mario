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
package renderer;

import components.SpriteRenderer;
import gengine.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
	private final int MAX_BATCH_SIZE = 1000;
	private List<RenderBatch> batches;

	public Renderer() {
		this.batches = new ArrayList<>();
	}

	public void add(@org.jetbrains.annotations.NotNull GameObject go) {
		SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
		if (spr != null) {
			add(spr);
		}
	}

	private void add(SpriteRenderer sprite) {
		boolean added = false;
		for (RenderBatch batch : batches) {
			if (batch.hasRoom()) {
				batch.addSprite(sprite);
				added = true;
				break;
			}
		}

		if (!added) {
			RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
			newBatch.start();
			batches.add(newBatch);
			newBatch.addSprite(sprite);
		}
	}

	public void render() {
		for (RenderBatch batch : batches) {
			batch.render();
		}
	}
}
