/*
 * GameObject.cpp
 *
 *  Created on: Aug 10, 2013
 *      Author: Sean's Computer
 */

#include "GameObject.h"

GameObject::GameObject() {
	// TODO Auto-generated constructor stub
	this->id = 0;
	this->minX = 0;
	this->minY = 0;
	this->minZ = 0;
	this->maxX = 0;
	this->maxY = 0;
	this->maxZ = 0;
	this->visitedFlag = 0;
	this->isoDepth = 0;
}

GameObject::GameObject(const GameObject& other) {
	this->id = other.id;
	this->minX = other.minX;
	this->minY = other.minY;
	this->minZ = other.minZ;
	this->maxX = other.maxX;
	this->maxY = other.maxY;
	this->maxZ = other.maxZ;
	this->visitedFlag = other.visitedFlag;
	this->isoDepth = other.isoDepth;
}

GameObject::~GameObject() {
	// TODO Auto-generated destructor stub
}

