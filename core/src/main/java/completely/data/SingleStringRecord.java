package completely.data;

import java.util.Collections;
import java.util.List;

/**
 * An implementation of {@link Indexable} that contains just one string field and returns the same score (0) for all.
 */
public class SingleStringRecord implements Indexable {

    private final String string;

    public SingleStringRecord(String string) {
        this.string = string;
    }

    @Override
    public List<String> getFields() {
        return Collections.singletonList(string);
    }

    @Override
    public double getScore() {
        return 0;
    }


    @Override
    public String toString() {
        return "SingleStringRecord{" +
                "string='" + string + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleStringRecord that = (SingleStringRecord) o;

        if (string != null ? !string.equals(that.string) : that.string != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return string != null ? string.hashCode() : 0;
    }
}
