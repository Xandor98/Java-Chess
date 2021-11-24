package de.xandor98.chess.networking;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UTFInputStream {

    private InputStream inputStream;

    public UTFInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String readUTF8() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(this.readNBytes(4));
        // Java always use hostorder. See javadoc
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byte[] bytes = this.readNBytes(byteBuffer.getInt(0));
        return new String(bytes, "UTF-8");
    }

    public void close() throws IOException {
        this.inputStream.close();
    }

    private byte[] readNBytes(int n) throws IOException {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        byte buffer[] = new byte[n];
        int readCount = 0;
        int lastReadCount;
        while (readCount < n) {
            lastReadCount = this.inputStream.read(buffer, readCount, n - readCount);
            if (lastReadCount == -1) {
                throw new EOFException(String.format("[Fehler]: Konnte nur %d von %d angefordeten Bytes lesen", readCount, n));
            }
            readCount += lastReadCount;

        }

        return buffer;
    }
}