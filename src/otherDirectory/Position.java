package otherDirectory;


public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Position that = (Position) obj;
        boolean isEqual = that.x == this.x && that.y == this.y && that.hashCode() == this.hashCode();
        return isEqual;
    }

    @Override
    public int hashCode() {
        return (x * y) % 107;
    }
}
