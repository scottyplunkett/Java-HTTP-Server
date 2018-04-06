package com.scottyplunkett.cycles;

public interface Cycle extends Runnable {
    public void cycle();
    Message reduce();
    Message deduce();
    Message produce();
    Message induce();
}
