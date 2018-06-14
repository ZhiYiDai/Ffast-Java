package cn.ffast.core.utils;

import java.util.Collection;
import java.util.Iterator;


public class CollectionUtils {

    public static <E> String arraryToString(Collection<E> collection) {
        Iterator<E> it = collection.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (; ; ) {
            E e = it.next();
            sb.append(e == collection ? "(this Collection)" : e);
            if (!it.hasNext()) {
                return sb.toString();
            }
            sb.append(',');
        }
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

}
