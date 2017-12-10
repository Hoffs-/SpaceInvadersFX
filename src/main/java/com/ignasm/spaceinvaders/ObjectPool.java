package com.ignasm.spaceinvaders;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public abstract class ObjectPool<T> {
    private final FixedLinkedList<T> releasedObjects;
    private final FixedLinkedList<T> activeObjects;
    private final int poolSize;

    public ObjectPool(int size) {
        poolSize = size;
        releasedObjects = new FixedLinkedList<>(size);
        activeObjects = new FixedLinkedList<>(size);
    }

    abstract public T acquireObject();

    abstract public void releaseObject(T object);

    abstract protected T createObject();

    public int getObjectCount() {
        return releasedObjects.size() + activeObjects.size();
    }

    public int getActiveCount() {
        return activeObjects.size();
    }

    public int getReleasedCount() {
        return releasedObjects.size();
    }

    public FixedLinkedList<T> getActiveObjects() {
        return activeObjects;
    }

    public FixedLinkedList<T> getReleasedObjects() {
        return releasedObjects;
    }

    public int getPoolSize() {
        return poolSize;
    }
}
