import java.util.*;
import java.util.stream.Collectors;

public class ToMapApproach {

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

            public GroupedPair(Pair<T, U> pair) {
                this.left = pair.left;
                this.set = new HashSet<>();
                this.set.add(pair.right);
            }

            private boolean isKeysEqual(GroupedPair<T, U> grPair) {
                return this.left.equals(grPair.left);
            }

            public GroupedPair<T, U> addGroupedPair(GroupedPair<T, U> grPair) {
                // isKeysEqual will always return true, when called from Stream.toMap
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
                // toMap(key, creating new value, merging with other value)
                Collectors.toMap(Pair::left, GroupedPair::new, GroupedPair::addGroupedPair)
        ).values();

        System.out.println(grPairs);

        // [GroupedPair{left=1, set=[A, B]}, GroupedPair{left=2, set=[B, C]}, GroupedPair{left=3, set=[C]}]

    }
}
