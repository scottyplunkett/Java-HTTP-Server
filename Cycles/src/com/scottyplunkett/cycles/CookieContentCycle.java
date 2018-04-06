package com.scottyplunkett.cycles;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CookieContentCycle implements Cycle {
    Socket channel;
    InputStream in;
    OutputStream out;

    CookieContentCycle(Socket socket, InputStream channelIn, OutputStream channelOut) {

    }
    @Override
    public void cycle() {
        reduce();
        deduce();
        produce();
        induce();
    }

    @Override
    public Message reduce() {
        return null;
    }

    @Override
    public Message deduce() {
        return null;
    }

    @Override
    public Message produce() {
        return null;
    }

    @Override
    public Message induce() {
        return null;
    }

    @Override
    public void run() {
        cycle();
    }
}
