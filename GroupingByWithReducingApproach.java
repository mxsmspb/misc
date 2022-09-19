import java.util.*;
import java.util.stream.Collectors;

public class GroupingByWithReducingApproach {

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

            private boolean hasNoKey() {
                return this.left == null;
            }

            public GroupedPair<T, U> addGroupedPair(GroupedPair<T, U> grPair) {

                if (hasNoKey()) {
                    // The same identity object (new GroupedPair()) created for Collectors.reducing
                    // is used for all values in the map, not one per key,
                    // so we return grPair itself instead of this here.
                    // Otherwise, we will have the same reduced value for all the keys.
                    return grPair;
                }
                else {
                    // isKeysEqual will always return true, when called from Stream.toMap
                    // or from Collectors below
                    if (isKeysEqual(grPair)) {
                        this.set.addAll(grPair.set);
                    }
                    return this;
                }
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
                Collectors.groupingBy(Pair::left,
                        Collectors.reducing(new GroupedPair<Integer, String>(),
                                GroupedPair::new, GroupedPair::addGroupedPair))
        ).values();

        System.out.println(grPairs);

        // [GroupedPair{left=1, set=[A, B]}, GroupedPair{left=2, set=[B, C]}, GroupedPair{left=3, set=[C]}]

    }
}

