package me.project.funding.etc;

public enum MemberType {
    NORMAL(0), CREATOR(1), ADMIN(2), DEL(-1);

    private final int value;
    MemberType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
