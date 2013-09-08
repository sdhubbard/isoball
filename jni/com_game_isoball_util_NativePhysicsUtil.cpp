
#include "com_game_isoball_util_NativePhysicsUtil.h"

#include <jni.h>
#include <Box2D.h>
#include <map>
#include <android/log.h>
#include <vector>
#include <exception>

b2World* physicsWorld;
std::map<jlong, b2Body*> bodyMap;

JNIEXPORT void JNICALL Java_com_game_isoball_util_NativePhysicsUtil_InitializeWorld(JNIEnv *env, jclass) {
	b2Vec2 vector (0.0, 0.0);

	physicsWorld = new b2World(vector);
}

JNIEXPORT void JNICALL Java_com_game_isoball_util_NativePhysicsUtil_UpdateWorld
  (JNIEnv *, jclass) {
	physicsWorld->Step(1.0f/60.0f, 12, 4);
}

JNIEXPORT void JNICALL Java_com_game_isoball_util_NativePhysicsUtil_AddBall
  (JNIEnv *, jclass, jlong id, jfloat tileX, jfloat tileY) {
	b2BodyDef* bodyDef = new b2BodyDef();
	b2Body* body;
	b2CircleShape* ball = new b2CircleShape();
	b2FixtureDef* fixtureDef = new b2FixtureDef();

	ball->m_radius = .5f;
	bodyDef->type = b2_dynamicBody;
	bodyDef->position = b2Vec2(tileX, tileY);
	body = physicsWorld->CreateBody(bodyDef);
	fixtureDef->shape = ball;
	body->CreateFixture(fixtureDef);

	bodyMap[id] = body;
}

JNIEXPORT void JNICALL Java_com_game_isoball_util_NativePhysicsUtil_SetBallVelocity
(JNIEnv *, jclass, jlong id, jfloat velocityX, jfloat velocityY) {
	bodyMap[id]->ApplyForceToCenter(b2Vec2(velocityX, velocityY));
}

JNIEXPORT jfloatArray JNICALL Java_com_game_isoball_util_NativePhysicsUtil_GetBallPosition
  (JNIEnv *env, jclass, jlong id) {
	jfloatArray position = (env)->NewFloatArray(2);
	b2Body* body = bodyMap[id];

	(env)->SetFloatArrayRegion(position,0,1,&body->GetPosition().x);
	(env)->SetFloatArrayRegion(position,1,1,&body->GetPosition().y);

	return position;
}

JNIEXPORT void JNICALL Java_com_game_isoball_util_NativePhysicsUtil_AddEdge
  (JNIEnv *, jclass, jfloat startX, jfloat startY, jfloat endX, jfloat endY) {
	b2BodyDef* bodyDef = new b2BodyDef();
	b2Body* body;
	b2EdgeShape* borderLine = new b2EdgeShape();

	bodyDef->position.Set(0,0);
	body = physicsWorld->CreateBody(bodyDef);
	borderLine->Set(b2Vec2(startX, startY), b2Vec2(endX, endY));
	body->CreateFixture(borderLine,0);
}

JNIEXPORT void JNICALL Java_com_game_isoball_util_NativePhysicsUtil_AddCircularLauncher
  (JNIEnv *, jclass, jfloat tileX, jfloat tileY) {
	b2BodyDef* bodyDef = new b2BodyDef();
	b2Body* body;
	b2CircleShape* circleShape = new b2CircleShape();

	bodyDef->position = b2Vec2(tileX + 1.5f, tileY + 1.5f);
	body = physicsWorld->CreateBody(bodyDef);
	circleShape->m_radius = 1.5f;
	body->CreateFixture(circleShape, 0);
}


JNIEXPORT jobject JNICALL Java_com_game_isoball_util_NativePhysicsUtil_GetBallPositions
  (JNIEnv *env, jclass) {
	jclass clas = (env)->FindClass("com/game/isoball/util/BallPositionHolder");
	jmethodID midInit = (env)->GetMethodID(clas, "<init>", "()V");
	jobject bPHolder = (env)->NewObject(clas,midInit);
	jlongArray idArray = (env)->NewLongArray(bodyMap.size());
	jfloatArray xValuesArray = (env)->NewFloatArray(bodyMap.size());
	jfloatArray yValuesArray = (env)->NewFloatArray(bodyMap.size());
	jfieldID idArrayField = (env)->GetFieldID(clas,"ids","[J");
	jfieldID xValuesField = (env)->GetFieldID(clas,"xValues","[F");
	jfieldID yValuesField = (env)->GetFieldID(clas,"yValues","[F");
	int index = 0;

	for(std::map<jlong, b2Body*>::iterator iter = bodyMap.begin(); iter != bodyMap.end(); ++iter) {
		jlong currentId = iter->first;
		b2Body* body = iter->second;

		(env)->SetLongArrayRegion(idArray,index,1,&currentId);
		(env)->SetFloatArrayRegion(xValuesArray,index,1,&body->GetPosition().x);
		(env)->SetFloatArrayRegion(yValuesArray,index,1,&body->GetPosition().y);

		index++;
	}

	(env)->SetObjectField(bPHolder, idArrayField, idArray);
	(env)->SetObjectField(bPHolder, xValuesField, xValuesArray);
	(env)->SetObjectField(bPHolder, yValuesField, yValuesArray);

	return bPHolder;
}

JNIEXPORT jlongArray JNICALL Java_com_game_isoball_util_NativePhysicsUtil_GetTouching
(JNIEnv *env, jclass, jlong id) {
	b2Body* body = bodyMap[id];
	std::vector<jlong> idVector;
	b2ContactEdge *currentEdge = body->GetContactList();
	jlongArray idArray;

	//__android_log_print(ANDROID_LOG_DEBUG,"isoball","Iterating through edges.");
	//__android_log_print(ANDROID_LOG_DEBUG,"isoball","Vector size is %d.",idVector.size());
	while(currentEdge) {
		for(std::map<jlong, b2Body*>::iterator iter = bodyMap.begin(); iter != bodyMap.end(); ++iter) {
			//__android_log_print(ANDROID_LOG_DEBUG,"isoball","Iter first is %L",iter->first);
			if(iter->second == body) {
				continue;
			}

			if(iter->second == currentEdge->contact->GetFixtureA()->GetBody() ||
					iter->second == currentEdge->contact->GetFixtureB()->GetBody()) {
				//__android_log_print(ANDROID_LOG_DEBUG,"isoball","Iter first Match is %L", iter->first);
				idVector.push_back(iter->first);
				break;
			}
		}

		currentEdge = currentEdge->next;
	}

	//__android_log_print(ANDROID_LOG_DEBUG,"isoball","Populating long Array.");
	//__android_log_print(ANDROID_LOG_DEBUG,"isoball","Vector size is %d.",idVector.size());

	if(idVector.size() == 0) {
		idArray = (env)->NewLongArray(1);
		//__android_log_print(ANDROID_LOG_DEBUG,"isoball","Returning Empty Array.");
		return idArray;
	}

	idArray = (env)->NewLongArray(idVector.size());

	for(int index = 0; index < idVector.size(); index++) {
		jlong currentId = idVector.at(index);
		//__android_log_print(ANDROID_LOG_DEBUG,"isoball","id is:%n", currentId);
		(env)->SetLongArrayRegion(idArray,index,1, &currentId);
	}
	//__android_log_print(ANDROID_LOG_DEBUG,"isoball","Returning Long Array.");
	return idArray;
}
