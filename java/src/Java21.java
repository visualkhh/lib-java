import java.util.ArrayList;
import java.util.SequencedCollection;

record Point(int x, int y) {
}

public class Java21 {
    // switch pattern matching
    public String describeObject(Object obj) {
        return switch (obj) {
            case Integer i -> "정수: " + i;
            case String s -> "문자열: " + s;
            case PointBase p -> "좌표: " + p.x() + "," + p.y();
            default -> "알 수 없는 타입";
        };
    }

    // record pattern
    public void printPoint(Object obj) {
        switch (obj) {
            case PointBase(int x, int y) ->
                    System.out.printf("좌표: x=%d, y=%d%n", x, y);
            default -> throw new IllegalStateException("Unexpected value: " + obj);
        }
    }

    // 순서 있는 컬렉션 (Sequenced Collections)
    public SequencedCollection<String> sequencedCollection() {
        SequencedCollection<String> list = new ArrayList<>();
        list.addFirst("첫 번째 요소");
        list.addLast("마지막 요소");
        String first = list.getFirst();
        String last = list.getLast();
        System.out.printf("첫 번째 요소: %s, 마지막 요소: %s%n", first, last);
        return list;
    }


    // 문자열 Preview
    public String textBlock() {
        return """
                Hello, World!
                """;
    }

    public String emoji() {
        int codePoint = 0x1F600; // 😀 이모지
        boolean isEmoji = Character.isEmoji(codePoint);
        return """
                \u1F600
                """;
    }

}
