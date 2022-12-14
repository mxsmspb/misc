import java.util.*;

import static java.util.stream.Collectors.*;

public class ToMapApproach {

    public static void main(String[] argv) {

        record Pair<K, V>(K key, V value) { }

        List<Pair<Integer,String>> pairList = Arrays.asList(
            new Pair<>(1,"A"),
            new Pair<>(1,"B"),
            new Pair<>(2,"B"),
            new Pair<>(2,"C"),
            new Pair<>(3,"C")
        );

        class GroupedPair<K, V> {
            final private K key;
            final private Set<V> set;

            public GroupedPair(Pair<K, V> pair) {
                this.key = pair.key();
                this.set = new HashSet<>();
                this.set.add(pair.value());
            }

            private boolean isKeyEquals(GroupedPair<K, V> grPair) {
                return this.key.equals(grPair.key);
            }

            public GroupedPair<K, V> addGroupedPair(GroupedPair<K, V> grPair) {
                // isKeyEquals will always return true, when called from Stream.toMap
                if (isKeyEquals(grPair)) {
                    this.set.addAll(grPair.set);
                }
                return this;
            }

            @Override
            public String toString() {
                return "GroupedPair{" +
                        "key=" + key +
                        ", set=" + set +
                        '}';
            }
        }

        Collection<GroupedPair<Integer, String>> grPairs = pairList.stream().collect(
                // toMap(key, creating new value, merging with other value)
                toMap(Pair::key, GroupedPair::new, GroupedPair::addGroupedPair)
        ).values();

        // Optionally we can use collectingAndThen
        /*Collection<GroupedPair<Integer, String>> grPairs = pairList.stream().collect(
                collectingAndThen(toMap(Pair::key, GroupedPair::new, GroupedPair::addGroupedPair), Map::values));*/

        System.out.println(grPairs);

        // [GroupedPair{key=1, set=[A, B]}, GroupedPair{key=2, set=[B, C]}, GroupedPair{key=3, set=[C]}]

    }
}
