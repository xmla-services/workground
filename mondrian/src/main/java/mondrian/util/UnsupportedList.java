/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link java.util.List} where all methods throw
 * an UnsupportedOperationException exception except for the
 * <code>isEmpty</code> method. The <code>iterator</code> and
 * <code>listIterator</code> methods can be easily implemented in
 * derived classes by using the helper inner classes:
 * <code>Itr</code> and <code>ListItr</code>.
 * These iterators are all read only,
 * their <code>remove</code>, <code>add</code> and <code>set</code>
 * methods throw the
 * UnsupportedOperationException exception.
 * <p>
 * This class can be used for List implementations that only implement
 * a subset of all the methods.
 *
 * @author Richard Emberson
 * @since Jan 16, 2007
 */
public abstract class UnsupportedList<T> implements List<T> {
    private static final Logger LOGGER =
        LoggerFactory.getLogger(UnsupportedList.class);

    protected UnsupportedList() {
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public int size() {
        throw new UnsupportedOperationException(new StringBuilder(getClass().getName()).append(".size").toString());
    }

    public T get(int index) {
        throw new UnsupportedOperationException(new StringBuilder(getClass().getName()).append(".get").toString());
    }

    public T set(int index, T element) {
        throw new UnsupportedOperationException(new StringBuilder(getClass().getName()).append(".set").toString());
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".toArray").toString());
    }

    public void add(int index, T element) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".add").toString());
    }

    public T remove(int index) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".remove").toString());
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".indexOf").toString());
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".lastIndexOf").toString());
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".subList").toString());
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".contains").toString());
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".toArray").toString());
    }

    public boolean add(T o) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".add").toString());
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".remove").toString());
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".containsAll").toString());
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".addAll").toString());
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".addAll").toString());
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".removeAll").toString());
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".retainAll").toString());
    }

    public void clear() {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".clear").toString());
    }

    public boolean equals(Object o) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".equals").toString());
    }

    public int hashCode() {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".hashCode").toString());
    }

    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".listIterator").toString());
    }

    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".listIterator").toString());
    }

    public Iterator<T> iterator() {
        throw new UnsupportedOperationException(
            new StringBuilder(getClass().getName()).append(".iterator").toString());
    }



    protected class Itr implements Iterator<T> {
        protected int cursor;
        protected int lastRet;

        public Itr() {
            this.cursor = 0;
            this.lastRet = -1;
        }

        public boolean hasNext() {
            return (cursor != size());
        }

        public T next() {
            try {
                T next = get(cursor);
                lastRet = cursor++;
                return next;
            } catch (IndexOutOfBoundsException e) {
                LOGGER.error(
                    new StringBuilder("UnsupportedList.Itr.next: cursor=")
                        .append(cursor)
                        .append(", size=")
                        .append(size()).toString(), e);
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException(
                new StringBuilder(getClass().getName()).append(".remove").toString());
        }
    }

    protected class ListItr extends Itr implements ListIterator<T> {
        public ListItr(int index) {
            this.cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public T previous() {
            try {
                int i = cursor - 1;
                T previous = get(i);
                lastRet = cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        public void set(T o) {
/*
            if (lastRet == -1)
                throw new IllegalStateException();
            try {
                MemberList.this.set(lastRet, o);
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
*/
            throw new UnsupportedOperationException(
                new StringBuilder(getClass().getName()).append(".set").toString());
        }

        public void add(T o) {
            throw new UnsupportedOperationException(
                new StringBuilder(getClass().getName()).append(".add").toString());
        }
    }

    /**
     * Iterator for arrays of a priori unknown size.
     */
    protected class ItrUnknownSize extends Itr {
        public ItrUnknownSize() {
            super();
        }

        public boolean hasNext() {
            try {
                get(cursor);
                return true;
            } catch (IndexOutOfBoundsException e) {
                return false;
            }
        }
    }
}

// End UnsupportedList.java

