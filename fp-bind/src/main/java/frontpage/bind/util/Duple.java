package frontpage.bind.util;

/**
 * @author willstuckey
 * @date 10/2/16
 * <p></p>
 */
public class Duple<L, R> {
    private L left;
    private R right;
    public Duple(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }
}
