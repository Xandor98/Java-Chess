package de.xandor98.chess.networking;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UTFOutputStream {

    private OutputStream outputStream;

    public UTFOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeUTF8(String text) throws IOException {
        byte[] bytes = text.getBytes("UTF-8");
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        // Transform to network order
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byteBuffer.putInt(bytes.length);
        outputStream.write(byteBuffer.array());
        outputStream.write(bytes);
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void close() throws IOException {
        this.outputStream.close();
    }
}
