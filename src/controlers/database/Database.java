package controlers.database;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class Database<T> {
    protected File f;
    protected RandomAccessFile file;

    protected final int FIX = 20;
    public final int SIZE_OF_RECORD;
    protected T t;

    public Database(String path, int SIZE_OF_RECORD, T t) throws IOException {
        f = new File(path);
        f.createNewFile();
        file = new RandomAccessFile(f, "rwd");
        this.SIZE_OF_RECORD = SIZE_OF_RECORD;
        this.t = t;
    }

    public void writeRecord(String str) throws IOException {
        file.writeBytes(str);
        file.writeByte('\n');
    }

    public abstract T readRecord(T t) throws IOException;

    public void writeString(String str) throws IOException {
        file.writeBytes(fixStringToWrite(str));
    }

    public abstract boolean search(String str, long pos, String state) throws IOException;

    protected String readRecords(long seek, int n) throws IOException {
        file.seek(seek);
        StringBuilder str = new StringBuilder();
        if (n == -1) {
            while (file.getFilePointer() < file.length()) {
                str.append(readRecord(t).toString());
                str.append("\n");
            }
        } else if (n > 0) {
            while (n > 0) {
                str.append(readRecord(t).toString());
                n--;
            }
        } else {
            return null;
        }
        return str.toString();
    }
    protected String readRecords(long seek) throws IOException {
        return readRecords(seek, -1);
    }

    public void removeRecord(String flightId) throws IOException {
        long currentPos = file.getFilePointer();
        String flights = readRecords(currentPos + SIZE_OF_RECORD);
        file.seek(currentPos);
        file.writeBytes(flights);
        file.setLength(file.length() - SIZE_OF_RECORD);
    }

    public void setSeek(long bytes) throws IOException {
        file.seek(bytes);
    }

    public long getCursor() throws IOException {
        return file.getFilePointer();
    }

    public long length() throws IOException {
        return file.length();
    }

    public String fixStringToWrite(String str) {
        return String.format("%20s", str).substring(0, FIX);
    }

    public String readFixedString() throws IOException {
        byte[] bytes = new byte[FIX];
        file.read(bytes);
        return (new String(bytes)).trim();
    }

    public void close() throws IOException {
        file.close();
    }
}
