package com.ws.wsclient.commons.io;

import java.io.ByteArrayOutputStream;

public class MaxSizeByteArrayOutputStream extends ByteArrayOutputStream {

    private int maxSize;

    /**
     * create new instance
     *
     * @param maxSize max number of bytes
     */
    public MaxSizeByteArrayOutputStream(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public synchronized void write(int b) {
        if (size() < maxSize) {
            super.write(b);
        }
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) {
        if (size() < maxSize) {
            int maxLength = Math.min(len, maxSize - size());
            super.write(b, off, maxLength);
        }
    }
}
