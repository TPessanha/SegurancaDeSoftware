package Resources;

public enum Role {
    ADMIN, USER;

    Role() {
    }

    public static Role fromValue(String value) {
        return valueOf(value);
    }

    @Override
    public String toString() {
        return name();
    }
}