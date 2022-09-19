import java.util.*;

public class StreamCollectApproach {

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
                // isKeyEquals will always return true, when called from Stream.collect
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
                HashMap<Integer, GroupedPair<Integer, String>>::new,
                (map, pair) -> {
                    // We also can use Stream.map before collect to eliminate mapping Pair->GroupedPair here
                    GroupedPair<Integer, String> grPair = new GroupedPair<>(pair);
                    if (!map.containsKey(pair.key())) {
                        map.put(pair.key(), grPair);
                    }
                    else {
                        map.get(pair.key()).addGroupedPair(grPair);
                    }
                }, Map::putAll
        ).values();

        System.out.println(grPairs);

        // [GroupedPair{key=1, set=[A, B]}, GroupedPair{key=2, set=[B, C]}, GroupedPair{key=3, set=[C]}]

    }
}

