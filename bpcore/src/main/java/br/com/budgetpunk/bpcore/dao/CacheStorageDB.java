//package br.com.budgetpunk.bpcore.dao;
//
//import com.br.widgettest.core.Category;
//import com.br.widgettest.core.Entry;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
///**
// * Created by Breno on 2/9/2016.
// */
//class CacheStorageDB implements DB {
//    private static final String TAG = "CacheStorageDB";
//
//    private final DB origin;
//    private static List<Entry> entryListCache;
//    private static List<Category> categoryListCache;
//
//    public CacheStorageDB(DB origin) {
//        this.origin = origin;
//    }
//
//    @Override
//    public void persist(Entity entity, List list) {
//        this.origin.persist(entity, list);
//        switch (entity) {
//            case Entry:
//                entryListCache = null;
//                break;
//            case Category:
//                categoryListCache = null;
//                break;
//            default:
//                throw new IllegalArgumentException(String.format("I don't know what %s is", entity));
//        }
//    }
//
//    @Override
//    public <T> List<T> get(Entity entity) {
//        switch (entity) {
//            case Category:
//                if (categoryListCache == null) {
//                    categoryListCache = this.origin.get(entity);
//                }
//                return new ArrayList<T>((Collection<? extends T>) categoryListCache);
//            case Entry:
//                if (entryListCache == null) {
//                    entryListCache = this.origin.get(entity);
//                }
//                return new ArrayList<T>((Collection<? extends T>) entryListCache);
//            default:
//                throw new IllegalArgumentException(String.format("I don't know what %s is", entity));
//        }
//    }
//}
