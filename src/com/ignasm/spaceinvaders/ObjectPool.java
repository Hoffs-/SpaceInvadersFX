package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.objects.Entity;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public abstract class ObjectPool<T extends Entity> {
    protected final FixedLinkedList<T> objects;
    private final int poolSize;

    public ObjectPool(int size) {
        poolSize = size;
        objects = new FixedLinkedList<>(size);
    }

    abstract public T acquireObject();

    abstract public T createObject();

    public int getObjectCount() {
        return objects.size();
    }

    public int getPoolSize() {
        return poolSize;
    }
}
