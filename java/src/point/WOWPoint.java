package point;

public record WOWPoint(int x, int y) {
    public static class Builder {
        private int x;
        private int y;

        public Builder x(int x) {
            this.x = x;
            return this;
        }

        public Builder y(int y) {
            this.y = y;
            return this;
        }

        public WOWPoint build() {
            return new WOWPoint(x, y);
        }
    }
}
