package com.detectiveos.util;

import com.detectiveos.config.AppConfig;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;

public final class FileManager {
    private FileManager(){}
    public static Path writeReport(String fileName,String content) throws IOException {
        Path dir=Paths.get(AppConfig.get("app.report.directory"));
        Files.createDirectories(dir);
        Path path=dir.resolve(fileName);
        Files.writeString(path,content,StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        return path;
    }
    public static void appendLog(String line) throws IOException {
        Path dir=Paths.get("reports"); Files.createDirectories(dir);
        Files.writeString(dir.resolve("investigation.log"),LocalDateTime.now()+" | "+line+System.lineSeparator(),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
    }
}
