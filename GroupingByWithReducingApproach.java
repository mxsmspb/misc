import java.util.*;
import java.util.stream.Collectors;

public class GroupingByWithReducingApproach {

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

            public GroupedPair() {
                key = null;
                set = null;
            }

            public GroupedPair(Pair<K, V> pair) {
                this.key = pair.key;
                this.set = new HashSet<>();
                this.set.add(pair.value);
            }

            private boolean isKeyEquals(GroupedPair<K, V> grPair) {
                return this.key.equals(grPair.key);
            }

            private boolean hasNoKey() {
                return this.key == null;
            }

            public GroupedPair<K, V> addGroupedPair(GroupedPair<K, V> grPair) {

                if (hasNoKey()) {
                    // The same identity object (new GroupedPair()) created for Collectors.reducing
                    // is used for all values in the map, not one per key,
                    // so we return grPair itself instead of this here.
                    // Otherwise, we will have the same reduced value for all the keys.
                    return grPair;
                }
                else {
                    // isKeyEquals will always return true, when called from Stream.toMap
                    // or from Collectors below
                    if (isKeyEquals(grPair)) {
                        this.set.addAll(grPair.set);
                    }
                    return this;
                }
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
                Collectors.groupingBy(Pair::key,
                        Collectors.reducing(new GroupedPair<Integer, String>(),
                                GroupedPair::new, GroupedPair::addGroupedPair))
        ).values();

        System.out.println(grPairs);

        // [GroupedPair{key=1, set=[A, B]}, GroupedPair{key=2, set=[B, C]}, GroupedPair{key=3, set=[C]}]

    }
}
