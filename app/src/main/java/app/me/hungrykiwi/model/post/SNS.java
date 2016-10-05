package app.me.hungrykiwi.model.post;

/**
 * SNS
 * Created by peterlee on 2016-09-13.
 */
public class SNS {
    private Type type;
    public enum Type{
        STANDARD, ADVERTISEMENT;
    }

    public Type getType() {
        return this.type;
    }
    public void setType(Type type) {
        this.type = type;
    }
}
