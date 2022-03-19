/*******************************************************************************
 Copyright (c)  19/03/22, 16:48  Giuseppe-Bianc
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
import gengine.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatch {
	private static final boolean NRM = false;
	private static final String SHPT = "assets/shaders/default.glsl";
	private static final int POS_SIZE = 2;
	private static final int COLOR_SIZE = 4;
	private static final int TEX_COORDS_SIZE = 2;
	private static final int TEX_ID_SIZE = 1;

	private final int POS_OFFSET = 0;
	private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
	private final int TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
	private final int TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;
	private final int VERTEX_SIZE = 9;
	private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

	private SpriteRenderer[] sprites;
	private int numSprites;
	private boolean hasRoom;
	private final float[] vertices;
	private int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7};

	private List<Texture> textures;
	private int vaoID, vboID;
	private int maxBatchSize;
	private Shader shader;


	public RenderBatch(int maxBatchSize) {
		shader = util.AssetPool.getShader(SHPT);
		this.sprites = new SpriteRenderer[maxBatchSize];
		this.maxBatchSize = maxBatchSize;

		vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];

		this.numSprites = 0;
		this.hasRoom = true;
		this.textures = new ArrayList<>();
	}

	public void start() {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

		int eboID = glGenBuffers();
		int[] indices = generateIndices();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, NRM, VERTEX_SIZE_BYTES, POS_OFFSET);
		glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, NRM, VERTEX_SIZE_BYTES, COLOR_OFFSET);
		glVertexAttribPointer(2, TEX_COORDS_SIZE, GL_FLOAT, NRM, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET);
		glVertexAttribPointer(3, TEX_ID_SIZE, GL_FLOAT, NRM, VERTEX_SIZE_BYTES, TEX_ID_OFFSET);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
	}

	public void addSprite(SpriteRenderer spr) {
		int index = this.numSprites;
		this.sprites[index] = spr;
		this.numSprites++;

		if (spr.getTexture() != null) {
			if (!textures.contains(spr.getTexture())) {
				textures.add(spr.getTexture());
			}
		}

		loadVertexProperties(index);

		if (numSprites >= this.maxBatchSize) {
			this.hasRoom = false;
		}
	}

	public void render() {
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

		// Use shader
		shader.use();
		shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
		shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());
		for (int i = 0; i < textures.size(); i++) {
			glActiveTexture(GL_TEXTURE0 + i + 1);
			textures.get(i).bind();
		}
		shader.uploadIntArray("uTextures", texSlots);

		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glDrawElements(GL_TRIANGLES, this.numSprites * 6, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);

		for (int i = 0; i < textures.size(); i++) {
			textures.get(i).unbind();
		}
		shader.detach();
	}

	private void loadVertexProperties(int index) {
		SpriteRenderer sprite = this.sprites[index];

		int offset = index * 4 * VERTEX_SIZE;

		Vector4f color = sprite.getColor();
		Vector2f[] texCoords = sprite.getTexCoords();

		int texId = 0;
		if (sprite.getTexture() != null) {
			for (int i = 0; i < textures.size(); i++) {
				if (textures.get(i) == sprite.getTexture()) {
					texId = i + 1;
					break;
				}
			}
		}

		float xAdd = 1.0f, yAdd = xAdd;
		for (int i = 0; i < 4; i++) {
			if (i == 1) {
				yAdd = 0.0f;
			} else if (i == 2) {
				xAdd = 0.0f;
			} else if (i == 3) {
				yAdd = 1.0f;
			}

			vertices[offset] = sprite.gameObject.transform.position.x + (xAdd * sprite.gameObject.transform.scale.x);
			vertices[offset + 1] = sprite.gameObject.transform.position.y + (yAdd * sprite.gameObject.transform.scale.y);
			vertices[offset + 2] = color.x;
			vertices[offset + 3] = color.y;
			vertices[offset + 4] = color.z;
			vertices[offset + 5] = color.w;
			vertices[offset + 6] = texCoords[i].x;
			vertices[offset + 7] = texCoords[i].y;
			vertices[offset + 8] = texId;

			offset += VERTEX_SIZE;
		}
	}

	private int[] generateIndices() {
		int[] elements = new int[6 * maxBatchSize];
		for (int i = 0; i < maxBatchSize; i++) {
			loadElementIndices(elements, i);
		}

		return elements;
	}

	private void loadElementIndices(int[] elements, int index) {
		int offsetArrayIndex = 6 * index;
		int offset = 4 * index;
		elements[offsetArrayIndex] = offset + 3;
		elements[offsetArrayIndex + 1] = offset + 2;
		elements[offsetArrayIndex + 2] = offset + 0;
		elements[offsetArrayIndex + 3] = offset + 0;
		elements[offsetArrayIndex + 4] = offset + 2;
		elements[offsetArrayIndex + 5] = offset + 1;
	}

	public boolean hasRoom() {
		return this.hasRoom;
	}

	public boolean hasTextureRoom() {
		return this.textures.size() < 8;
	}

	public boolean hasTexture(Texture tex) {
		return this.textures.contains(tex);
	}
}