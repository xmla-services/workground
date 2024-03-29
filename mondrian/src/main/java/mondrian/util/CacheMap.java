/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2007-2017 Hitachi Vantara and others
// Copyright (C) 2007-2007 Tasecurity Group S.L, Spain
// All Rights Reserved.
*/

package mondrian.util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Map with limited size to be used as cache.
 *
 * @author lcanals, www.tasecurity.net
 */
public class CacheMap<S, T> implements Map<S, T> {
    private LinkedNode head;
    private LinkedNode tail;
    private final Map<S, Pair<S, T>> map;
    private final int maxSize;

    /**
     * Creates an empty map with limited size.
     *
     * @param size Maximum number of mapped elements.
     */
    public CacheMap(final int size) {
        this.head = new LinkedNode(null, null);
        this.tail = new LinkedNode(head, null);
        this.map = new WeakHashMap<>(size);
        this.maxSize = size;
    }

    @Override
	public void clear() {
        this.head = new LinkedNode(null, null);
        this.tail = new LinkedNode(head, null);
        map.clear();
    }

    @Override
	public boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    @Override
	public boolean containsValue(final Object value) {
        return this.values().contains(value);
    }

    @Override
	public Set entrySet() {
        final Set<Map.Entry<S, T>> set = new HashSet<>();
        for (final Map.Entry<S, Pair<S, T>> entry : this.map.entrySet()) {
            set.add(
                new Map.Entry<S, T>() {
                    @Override
					public boolean equals(Object s) {
                        if (s instanceof Map.Entry en) {
                            return en.getKey().equals(
                                entry.getKey())
                                && en.getValue().equals(
                                    entry.getValue().value);
                        } else {
                            return false;
                        }
                    }

                    @Override
					public S getKey() {
                        return entry.getKey();
                    }

                    @Override
					public T getValue() {
                        return entry.getValue().value;
                    }

                    @Override
					public int hashCode() {
                        return entry.hashCode();
                    }

                    @Override
                    @SuppressWarnings("java:S2293")
					public T setValue(final T x) {
                        return entry.setValue(
                            new Pair<S, T>(
                                x,
                                new LinkedNode(head, entry.getKey()))).value;
                    }
                });
        }
        return set;
    }

    @Override
	public T get(final Object key) {
        final Pair<S, T> pair = map.get(key);
        if (pair != null) {
            final LinkedNode<S> node = pair.getNode();
            if (node == null) {
                map.remove(key);
                return null;
            }
            node.moveTo(head);
            return pair.value;
        } else {
            return null;
        }
    }

    @Override
	public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
	public Set<S> keySet() {
        return map.keySet();
    }

    @Override
	public T put(final S key, final T value) {
        final Pair<S, T> pair =
            new Pair<>(value, new LinkedNode(head, key));
        final Pair<S, T> obj = map.put(key, pair);
        if (map.size() > maxSize) {
            tail.getPrevious().remove();
            map.remove(key);
        }
        if (obj != null) {
            return obj.value;
        } else {
            return null;
        }
    }

    @Override
	public void putAll(final Map t) {
        throw new UnsupportedOperationException();
    }

    @Override
	public T remove(final Object key) {
        final Pair<S, T> pair = map.get(key);
        if (pair == null) {
            return null;
        }
        pair.getNode().remove();
        return map.remove(key).value;
    }

    @Override
	public int size() {
        return map.size();
    }

    @Override
	public Collection<T> values() {
        final List<T> vals = new ArrayList<>();
        for (final Pair<S, T> pair : map.values()) {
            vals.add(pair.value);
        }
        return vals;
    }

    @Override
	public int hashCode() {
        return map.hashCode();
    }

    @Override
	public String toString() {
        return new StringBuilder("Ordered keys: ").append(head.toString()).append("\n")
                .append("Map:").append(map.toString()).toString();
    }

    @Override
	public boolean equals(Object o) {
        if (o instanceof CacheMap cacheMap) {
            return map.equals(cacheMap.map);
        }
        return false;
    }

    //
    // PRIVATE STUFF ------------------
    //

    /**
     * Pair of linked key - value
     */
    private static final class Pair<S, T> implements java.io.Serializable {
        private final T value;
        private final WeakReference<LinkedNode<S>> node;
        private Pair(final T value, final LinkedNode<S> node) {
            this.node = new WeakReference<>(node);
            this.value = value;
        }

        private LinkedNode<S> getNode() {
            return node.get();
        }

        @Override
		public boolean equals(final Object o) {
            return o != null && o.equals(this.value);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }


    /**
     * Represents a node in a linked list.
     */
    private static class LinkedNode<S> implements java.io.Serializable {
        private LinkedNode<S> next;
        private LinkedNode<S> prev;
        private S key;

        public LinkedNode(final LinkedNode<S> prev, final S key) {
            this.key = key;
            insertAfter(prev);
        }

        public void remove() {
            if (this.prev != null) {
                this.prev.next = this.next;
            }
            if (this.next != null) {
                this.next.prev = this.prev;
            }
        }

        public void moveTo(final LinkedNode<S> prev) {
            remove();
            insertAfter(prev);
        }

        public LinkedNode<S> getPrevious() {
            return this.prev;
        }

        @Override
		public String toString() {
            if (this.next != null) {
                if (key != null) {
                    return new StringBuilder(key.toString()).append(", ").append(this.next.toString()).toString();
                } else {
                    return new StringBuilder("<null>, ").append(this.next.toString()).toString();
                }
            } else {
                if (key != null) {
                    return key.toString();
                } else {
                    return "<null>";
                }
            }
        }

        private void insertAfter(final LinkedNode<S> prev) {
            if (prev != null) {
                this.next = prev.next;
            } else {
                this.prev = null;
            }
            this.prev = prev;

            if (prev != null) {
                if (prev.next != null) {
                    prev.next.prev = this;
                }
                prev.next = this;
            }
        }
    }
}
