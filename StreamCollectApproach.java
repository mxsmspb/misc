import java.util.*;
import java.util.stream.Collectors;

public class StreamCollectApproach {

    public static void main(String[] argv) {

        record Pair<T, U>(T left, U right) { }

        List<Pair<Integer,String>> pairList = Arrays.asList(
                new Pair<>(1,"A"),
                new Pair<>(1,"B"),
                new Pair<>(2,"B"),
                new Pair<>(2,"C"),
                new Pair<>(3,"C")
        );

        class GroupedPair<T, U> {
            private T left;
            private Set<U> set;

            public GroupedPair() { }

            public GroupedPair(Pair<T, U> pair) {
                this.left = pair.left;
                this.set = new HashSet<>();
                this.set.add(pair.right);
            }

            private boolean isKeysEqual(GroupedPair<T, U> grPair) {
                return this.left.equals(grPair.left);
            }

            public GroupedPair<T, U> addGroupedPair(GroupedPair<T, U> grPair) {
                // isKeysEqual will always return true, when called from Stream.collect
                if (isKeysEqual(grPair)) {
                    this.set.addAll(grPair.set);
                }
                return this;
            }

            @Override
            public String toString() {
                return "GroupedPair{" +
                        "left=" + left +
                        ", set=" + set +
                        '}';
            }
        }

        Collection<GroupedPair<Integer, String>> grPairs = pairList.stream().collect(
                HashMap<Integer, GroupedPair<Integer, String>>::new,
                (map, pair) -> {
                    // We also can use Stream.map before collect to eliminate mapping Pair->GroupedPair here
                    GroupedPair<Integer, String> grPair = new GroupedPair<>(pair);
                    if (!map.containsKey(pair.left)) {
                        map.put(pair.left, grPair);
                    }
                    else {
                        map.get(pair.left).addGroupedPair(grPair);
                    }
                }, Map::putAll
        ).values();

        System.out.println(grPairs);

        // [GroupedPair{left=1, set=[A, B]}, GroupedPair{left=2, set=[B, C]}, GroupedPair{left=3, set=[C]}]

    }
}

