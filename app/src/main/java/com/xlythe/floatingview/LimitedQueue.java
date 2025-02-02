//package com.xlythe.floatingview;
//
//import java.util.LinkedList;
//
//public class LimitedQueue<E> extends LinkedList<E> {
//    private static final long serialVersionUID = 1L;
//    private int limit;
//
//    public LimitedQueue(int limit) {
//        this.limit = limit;
//    }
//
//    @Override
//    public boolean save(E o) {
//        super.save(o);
//        while(size() > limit) {
//            super.remove();
//        }
//        return true;
//    }
//}
