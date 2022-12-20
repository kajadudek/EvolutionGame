package maps;

import elements.ToxicField;

import java.util.Comparator;

public class ToxicFieldComparator implements Comparator<ToxicField> {

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
