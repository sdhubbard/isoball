/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <vector>
#include <GameObject.h>
/* Header for class com_game_isoball_util_NativeDrawingUtil */

#ifndef _Included_com_game_isoball_util_NativeDrawingUtil
#define _Included_com_game_isoball_util_NativeDrawingUtil
#ifdef __cplusplus
extern "C" {
#endif

/*
struct GameObject {
	GameObject();
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
*/

/*
 * Class:     com_game_isoball_util_NativeDrawingUtil
 * Method:    DepthSortGameObjects
 * Signature: ([Lcom/game/isoball/gameObjects/GameObject;)[J
 */
JNIEXPORT jlongArray JNICALL Java_com_game_isoball_util_NativeDrawingUtil_DepthSortGameObjects
  (JNIEnv *, jclass, jobjectArray);

/*
 * Class:     com_game_isoball_util_NativeDrawingUtil
 * Method:    GetAnInt
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_game_isoball_util_NativeDrawingUtil_GetAnInt
  (JNIEnv *, jclass, jint);

void visitNode(GameObject *gameObject);

#ifdef __cplusplus
}
#endif
#endif
