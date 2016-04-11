package com.br.widgettest.ui.extensions;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Breno on 1/17/2016.
 */
public class CyclableList<T> extends ArrayList<T> {

    int currIndex = 0;
    public enum Direction {FORWARD, BACKWARD}

    public CyclableList(Collection<T> collection) {
        super(collection);
    }

    public T cycle(Direction direction) {
        if (direction == Direction.FORWARD) {
            if (currIndex == size() - 1) {
                currIndex = 0;
            } else {
                currIndex++;
            }
        } else {
            if (currIndex == 0) {
                currIndex = size() - 1;
            } else {
                currIndex--;
            }
        }

        return get(currIndex);
    }
}
