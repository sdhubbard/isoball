
#include "com_game_isoball_util_NativeDrawingUtil.h"

#include  <jni.h>
#include <stdio.h>
#include <vector>
#include <android/log.h>
#include <GameObject.h>


int sortDepth = 0;



JNIEXPORT jlongArray JNICALL Java_com_game_isoball_util_NativeDrawingUtil_DepthSortGameObjects
  (JNIEnv *env, jclass, jobjectArray gameObjectsInput) {
	//__android_log_print(ANDROID_LOG_DEBUG,"isoball","Getting size now.",5);
	int inputSize = (env)->GetArrayLength(gameObjectsInput);
	jlongArray gameObjectsOutput = (env)->NewLongArray(inputSize);
	std::vector<GameObject*> goVector;
	jclass clas = (env)->FindClass("com/game/isoball/gameObjects/GameObject");
	jfieldID idField = (env)->GetFieldID(clas,"id","J");
	jfieldID minXField = (env)->GetFieldID(clas,"minX","F");
	jfieldID minYField = (env)->GetFieldID(clas,"minY","F");
	jfieldID minZField = (env)->GetFieldID(clas,"minZ","F");
	jfieldID maxXField = (env)->GetFieldID(clas,"maxX","F");
	jfieldID maxYField = (env)->GetFieldID(clas,"maxY","F");
	jfieldID maxZField = (env)->GetFieldID(clas,"maxZ","F");


	for(int index = 0; index < inputSize; index++) {
		GameObject* gameObject = new GameObject();
		jobject javaGO = (env)->GetObjectArrayElement(gameObjectsInput, index);

		gameObject->id = (env)->GetLongField(javaGO,idField);
		gameObject->minX = (env)->GetFloatField(javaGO, minXField);
		gameObject->minY = (env)->GetFloatField(javaGO, minYField);
		gameObject->minZ = (env)->GetFloatField(javaGO, minZField);
		gameObject->maxX = (env)->GetFloatField(javaGO, maxXField);
		gameObject->maxY = (env)->GetFloatField(javaGO, maxYField);
		gameObject->maxZ = (env)->GetFloatField(javaGO, maxZField);
		goVector.push_back(gameObject);
	}

	for(int index = 0; index < inputSize; index++) {
		GameObject* gameObject = goVector[index];

		for(int secondIndex = 0; secondIndex < inputSize; secondIndex++) {
			GameObject* secondGameObject = goVector[secondIndex];

			if(gameObject == secondGameObject) {
				continue;
			}

			if((secondGameObject->maxX + secondGameObject->maxY + secondGameObject->maxZ) <
					(gameObject->maxX + gameObject->maxY + gameObject->maxZ)) {

				gameObject->gameObjectsBehind.push_back(secondGameObject);
			}
		}
	}

	sortDepth = 0;
	for(int index = 0; index < inputSize; index++) {
		GameObject* gameObject = goVector[index];

		visitNode(gameObject);
	}

	for(int index = 0; index < inputSize; index++) {
		GameObject* gameObject = goVector[index];
		int isoDepth = gameObject->isoDepth;
		jlong id = gameObject->id;

		delete gameObject;
		if(isoDepth > inputSize) {
			continue;
		}

		(env)->SetLongArrayRegion(gameObjectsOutput,isoDepth,jsize(1), &id);
	}

	return gameObjectsOutput;
}

void visitNode(GameObject *gameObject) {
	int behindSize = gameObject->gameObjectsBehind.size();

	if (gameObject->visitedFlag != 0) {
		return;
	}

	gameObject->visitedFlag = 1;

	for(int index = 0; index < behindSize; ++index) {
		//Equivalent to a Null Check
		if (gameObject->gameObjectsBehind[index] == 0) {
			break;
		} else {
			visitNode(gameObject->gameObjectsBehind[index]);
			gameObject->gameObjectsBehind[index] == 0;
		}
	}


	gameObject->isoDepth = sortDepth;
	sortDepth = sortDepth + 1;
}

JNIEXPORT jint JNICALL Java_com_game_isoball_util_NativeDrawingUtil_GetAnInt
  (JNIEnv *, jclass, jint) {
	//__android_log_print(ANDROID_LOG_DEBUG,"isoball","Returning int now");

	return 1;
}
