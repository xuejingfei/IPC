// IBinderPool.aidl
package com.xjf.bindpool;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}