package com.lbrce.hostel.util;

import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

public final class FileUploadUtil {

    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of(".jpg", ".jpeg", ".png", ".pdf");

    private FileUploadUtil() {
        // Prevent object creation.
    }

    public static String uploadFile(
            Part filePart,
            String uploadDirectory) throws IOException {

        if (filePart == null
                || filePart.getSize() == 0) {
            throw new IllegalArgumentException(
                    "Please select a file to upload.");
        }

        String submittedName = Paths.get(
                        filePart.getSubmittedFileName())
                .getFileName()
                .toString();

        String originalName = submittedName.toLowerCase();

        int dotIndex = originalName.lastIndexOf('.');

        if (dotIndex < 0) {
            throw new IllegalArgumentException(
                    "Files must have an extension.");
        }

        String extension = originalName.substring(dotIndex);

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "Only JPG, JPEG, PNG, and PDF files are allowed.");
        }

        // Limit uploads to 5 MB.
        long maxSize = 5L * 1024 * 1024;

        if (filePart.getSize() > maxSize) {
            throw new IllegalArgumentException(
                    "File size must not exceed 5 MB.");
        }

        Path uploadPath = Paths.get(uploadDirectory)
                .toAbsolutePath()
                .normalize();

        Files.createDirectories(uploadPath);

        // Generate a unique filename to avoid overwriting files.
        String newFileName =
                UUID.randomUUID() + extension;

        Path destination = uploadPath
                .resolve(newFileName)
                .normalize();

        if (!destination.startsWith(uploadPath)) {
            throw new IOException(
                    "Invalid upload destination.");
        }

        filePart.write(destination.toString());

        return newFileName;
    }
}