package fr.motormingle.model;

public enum Status {
    PENDING,
    ACCEPTED,
    REJECTED;

    @Override
    public String toString() {
        return this.name();
    }
}
