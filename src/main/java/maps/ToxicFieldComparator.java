package maps;

import elements.ToxicField;
import java.util.Comparator;

public class ToxicFieldComparator implements Comparator<ToxicField> {

    /**
     * Compares counter of deaths on Vector2d fields in HashMap deathCounter.
     * Used in ToxicFields variant to pick where next grass should grow.
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return
     */
    @Override
    public int compare(ToxicField o1, ToxicField o2) {
        if (o1.deaths > o2.deaths) {
            return 1;
        } else if (o1.deaths < o2.deaths) {
            return -1;
        }
        return 0;
    }
}
