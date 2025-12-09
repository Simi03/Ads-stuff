package ch.zhaw.ads;

import java.util.*;
import java.lang.reflect.*;

public class Storage {
    public static StringBuffer log = new StringBuffer();
    private static List<Collectable> root = new LinkedList<Collectable>();
    private static List<Collectable> heap = new LinkedList<Collectable>();
    private static List<Collectable> finalizeStage = new LinkedList<Collectable>();
    private static boolean finalizer;

    /* add  root object */
    public static void addRoot(Collectable obj) {
        root.add(obj);
    }

    public static void clear() {
        root.clear();
        heap.clear();
        finalizeStage.clear();
    }

    public static void enableFinalizer(boolean on) {
        finalizer = on;
    }

    private static String getPackage() {
        Package pack = Storage.class.getPackage();
        if (pack != null) {
            return pack.getName() + ".";
        } else {
            return "";
        }
    }

    // create a collectable object of class cls
    public static Collectable _new(String cls, Object arg) {
        Collectable obj = null;
        try {
            // create an object and call constructor
            Constructor cst = Class.forName(getPackage() + cls).getConstructor(
                    new Class[] { arg.getClass()});
            obj = (Collectable) cst.newInstance(new Object[] { arg});
            // add object to heap
            heap.add(obj);
            log.append("new " + obj.toString() + "\n");
        } catch (Exception ex) {
            throw new RuntimeException(
                    "error creating object " + getPackage() + cls);
        }
        return (Collectable) obj;
    }

    /* remove object from heap */
    public static void delete(Collectable obj) {
        if (heap.remove(obj)) {
            log.append("delete " + obj.toString() + "\n");
        } else {
            throw new RuntimeException(
                    "error trying to delete object not in heap " + obj.toString());
        }
    }

    /* get all root objects */
    public static Iterable<Collectable> getRoot() {
        return new LinkedList<Collectable>(root);
    }

    /* get heap */
    public static Iterable<Collectable> getHeap() {
        return new LinkedList<Collectable>(heap);
    }

    /* get references to collectables of an object */
    public static Iterable<Collectable> getRefs(Collectable obj) {
        // get all fields of an object
        Field[] fields = obj.getClass().getFields();
        List<Collectable> fieldList = new LinkedList<Collectable>();
        for (int i = 0; i < fields.length; i++) {
            try {
                Object o = fields[i].get(obj);
                if (o instanceof Collectable) {
                    fieldList.add((Collectable) o);
                }
            } catch (Exception ex) {}
        }
        return fieldList;
    }

    /* dump an iterator */
    public static void dump(String s, Iterable itr) {
        log.append(s);
        for (Object o: itr) {
            log.append(" " + o.toString());
        }
        log.append("\n");
    }

    public static String getLog() {
        return log.toString();
    }

    private static void mark(Collectable obj) {
        if(obj.isMarked()) return;

        obj.setMark(true);
        getRefs(obj).forEach(Storage::mark);
    }

    private static void sweep() {
        if (finalizer) {
            sweepWithFinalizer();
        } else {
            getHeap().forEach(i -> {
                if(i.isMarked()) {
                    i.setMark(false);
                } else {
                    delete(i);
                }
            });
        }
    }

    private static void sweepWithFinalizer() {
            getHeap().forEach(i -> {
                if(finalizeStage.contains(i) && !i.isMarked()) {
                    delete(i);
                    finalizeStage.remove(i);
                    return;
                }

                if(i.isMarked()) {
                    i.setMark(false);
                } else {
                    finalizeStage.add(i);
                    i.finalize();
                }
            });
    }

    public static void gc() {
        log.append("collect\n");
        getRoot().forEach(Storage::mark);
        sweep();
    }

}