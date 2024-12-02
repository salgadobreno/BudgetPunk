package br.com.budgetpunk.bpcore.ledger.decorators.formatters;

public abstract class Formatted<T> {
    public Formatted(T obj) {
    }

    public String toString() {
        throw new RuntimeException("Implement this");
    }
}
