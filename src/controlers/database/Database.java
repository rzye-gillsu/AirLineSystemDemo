package controlers.database;

import java.io.IOException;

public interface Database<T> {
    int FIX = 20;
    int FIXED_SIZE = FIX * 2;

    void writeRecord(String str) throws IOException;
    T readRecord(T t) throws IOException;

    void writeString(String username) throws IOException;

    boolean search(String str) throws IOException;

//    void update(String str, String state) throws IOException;

    String readRecords(long seek, int n) throws IOException;
    String readRecords(long seek) throws IOException;

    void removeRecord(String str) throws IOException;

    void setSeek(long bytes) throws IOException;

    long getCursor() throws IOException;

    long length() throws IOException;

    int numberOfRecords() throws IOException;

    String fixStringToWrite(String str);

    String readFixedString() throws IOException;

    void close() throws IOException;
}
