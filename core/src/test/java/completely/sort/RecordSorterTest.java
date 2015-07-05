package completely.sort;

import completely.data.SingleStringWithScoreRecord;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RecordSorterTest {

    @Test
    public void testSort() throws Exception {
        RecordSorter<SingleStringWithScoreRecord> recordSorter = new RecordSorter<>(new RecordWeighter<SingleStringWithScoreRecord>() {
            @Override
            public double weight(String query, SingleStringWithScoreRecord record) {
                double pointsForStringMatch = pointsForString(query, record.getString());
                double pointsForRecordImportance = pointsForImportance(record.getScore());
                return (pointsForStringMatch + pointsForRecordImportance) / 2; //mean
            }
            private double pointsForString(String query, String record) {
                if (query.equals(record)) return 1d;
                if (record.startsWith(query)) return 0.8d;
                return 0;
            }
            private double pointsForImportance(double score) {
                return score;
            }
        });

        List<SingleStringWithScoreRecord> found = new ArrayList<>();
        found.add(new SingleStringWithScoreRecord("foobar", 0.5));
        found.add(new SingleStringWithScoreRecord("fööbar", 1));
        found.add(new SingleStringWithScoreRecord("foo", 0.2));

        List<WeightedRecord<SingleStringWithScoreRecord>> result = recordSorter.sort("foo", found);
        assertEquals(result.size(), found.size());
        assertEquals(result.get(1).getRecord().getString(), "foobar");
        assertEquals(result.get(0).getRecord().getString(), "foo");
        assertEquals(result.get(2).getRecord().getString(), "fööbar");
    }

}