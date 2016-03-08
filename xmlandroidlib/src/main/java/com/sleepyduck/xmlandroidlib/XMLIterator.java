package com.sleepyduck.xmlandroidlib;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by fredrik.metcalf on 2016-03-08.
 */
public class XMLIterator implements Iterator<XMLElement> {
    private final XMLElement mRoot;
    private int mIndex = -1;
    private Iterator<XMLElement> mChildIterator;

    public XMLIterator(XMLElement root) {
        mRoot = root;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mRoot.getChildren().size() ||
                (mChildIterator != null && mChildIterator.hasNext());
    }

    @Override
    public XMLElement next() {
        if (mChildIterator != null) {
            if (mChildIterator.hasNext()) {
                return mChildIterator.next();
            } else {
                mChildIterator = null;
                return next();
            }
        } else if (mIndex == -1) {
            mIndex++;
            return mRoot;
        } else if (mIndex < mRoot.getChildren().size()) {
            mChildIterator = mRoot.getChildAt(mIndex).iterator();
            mIndex++;
            return mChildIterator.next();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
