/*
 * GameObject.h
 *
 *  Created on: Aug 10, 2013
 *      Author: Sean's Computer
 */
#include <vector>
#include  <jni.h>

#ifndef GAMEOBJECT_H_
#define GAMEOBJECT_H_

class GameObject {
public:
	GameObject();
	virtual ~GameObject();
	GameObject(const GameObject& other);

	jlong id;
	float minX;
	float minY;
	float minZ;
	float maxX;
	float maxY;
	float maxZ;
	int visitedFlag;
	int isoDepth;
	std::vector<GameObject*> gameObjectsBehind;
};

#endif /* GAMEOBJECT_H_ */
