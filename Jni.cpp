#include "Jni.h"

JNIEXPORT jint JNICALL Java_Jni_stampaMat
  (JNIEnv * e, jobject o, jobjectArray mat[][]){
  	return mat[1][2];
  }
JNIEXPORT jint JNICALL Java_Jni_getString
  (JNIEnv *, jobject){
  	return "Ciaoo";
  }
