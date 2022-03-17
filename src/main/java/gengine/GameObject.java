/*******************************************************************************
 Copyright (c)  17/03/22, 11:57  Giuseppe-Bianc
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

import java.util.ArrayList;
import java.util.List;

public class GameObject {

	private String name;
	private List<Component> components;

	public GameObject(String name) {
		this.name = name;
		this.components = new ArrayList<>();
	}

	/**
	 * Returns a component of the specified type
	 *
	 * @param componentClass The class of the component you want to get.
	 * @return The component that is of the specified type.
	 */
	public <T extends Component> T getComponent(Class<T> componentClass) {
		for (Component c : components) {
			if (componentClass.isAssignableFrom(c.getClass())) {
				try {
					return componentClass.cast(c);
				} catch (ClassCastException e) {
					e.printStackTrace();
					assert false : "Error: Casting component.";
				}
			}
		}

		return null;
	}

	/**
	 * Remove all components of a given type from the entity
	 *
	 * @param componentClass The class of the component to be removed.
	 */
	public <T extends Component> void removeComponent(Class<T> componentClass) {
		for (int i = 0; i < components.size(); i++) {
			Component c = components.get(i);
			if (componentClass.isAssignableFrom(c.getClass())) {
				components.remove(i);
				return;
			}
		}
	}

	/**
	 * Adds a component to the game object
	 *
	 * @param c The component to add to the game object.
	 */
	public void addComponent(Component c) {
		this.components.add(c);
		c.gameObject = this;
	}

	/**
	 * For each component in the list of components, call the update function on that component
	 *
	 * @param dt The time since the last update.
	 */
	public void update(float dt) {
		for (int i = 0; i < components.size(); i++) {
			components.get(i).update(dt);
		}
	}

	/**
	 * Start all the components in the system
	 */
	public void start() {
		for (int i = 0; i < components.size(); i++) {
			components.get(i).start();
		}
	}
}
