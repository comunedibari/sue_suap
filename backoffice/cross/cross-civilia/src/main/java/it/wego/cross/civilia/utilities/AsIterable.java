package it.wego.cross.civilia.utilities;

import java.util.Iterator;

public class AsIterable<T> implements Iterable<T> {
    private Iterator<T> iterator;
    public AsIterable(Iterator<T> iterator) {
        this.iterator = iterator;
    }
    public Iterator<T> iterator() {
        return iterator;
    }
}