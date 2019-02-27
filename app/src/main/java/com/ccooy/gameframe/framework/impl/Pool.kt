package com.ccooy.gameframe.framework.impl

class Pool<T>(private val factory: PoolObjectFactory<T>, private val maxSize: Int) {
    private val freeObjects: MutableList<T>

    interface PoolObjectFactory<T> {
        fun createObject(): T
    }

    init {
        this.freeObjects = ArrayList(maxSize)
    }

    fun newObject(): T? {
        return if (freeObjects.isEmpty())
            factory.createObject()
        else
            freeObjects.removeAt(freeObjects.size - 1)
    }

    fun free(mObject: T) {
        if (freeObjects.size < maxSize)
            freeObjects.add(mObject)
    }
}