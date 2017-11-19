package com.ignasm.spaceinvaders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class FixedLinkedList<T> extends LinkedList<T> {
    private int sizeLimit = 0;
    public FixedLinkedList(int size) {
        sizeLimit = size;
    }

    /***
     * Adds an object if List is not overfilled.
     * @param o Object to add
     * @return boolean if Object was added
     */
    @Override
    public boolean add(T o) {
        return (size() < sizeLimit) && super.add(o);
    }

    /***
     * Not allowed method
     * @param c Collection of objects
     * @return false
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    /***
     * Not allowed method
     * @param index location to insert
     * @param c Collection of objects
     * @return false
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }
}
