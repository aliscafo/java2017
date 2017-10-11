package ru.spbau.erokhina.hometask4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * A class that has access to function that unzips files.
 */
public class UnzipFiles {
    private String extension;

    /**
     * Constructor for UnzipFiles class.
     * @param fileExtension - extension of files.
     */
    UnzipFiles (String fileExtension) {
        extension = fileExtension;
    }

    /**
     * Method that unzips files in all archive (in this path) that match with given regex.
     * @param path - given path of archives.
     * @param regEx - given regex.
     */
    public void UnzipFilesRegex(String path, String regEx) throws IOException {
        File pathFolder = new File(path);
        File[] files = pathFolder.listFiles();

        for (File file : files) {
            if (!file.getName().endsWith(extension)) {
                continue;
            }

            ZipFile zipFile = new ZipFile(file);
            try(ZipInputStream inputStream =
                    new ZipInputStream(new FileInputStream(zipFile.getName()))) {

                for (ZipEntry entry = inputStream.getNextEntry(); entry != null; entry = inputStream.getNextEntry()) {
                    if (!entry.getName().matches(regEx)) {
                        continue;
                    }

                    String archiveDirectoryPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);
                    File folder = new File(archiveDirectoryPath);

                    if (!folder.exists()) {
                        folder.mkdir();
                    }

                    File newFile = new File(folder + File.separator + entry.getName());
                    new File(newFile.getParent()).mkdirs();
                    newFile.createNewFile();

                    try(FileOutputStream outputStream = new FileOutputStream(newFile)) {
                        byte[] buff = new byte[1024];

                        int size;
                        while ((size = inputStream.read(buff)) > 0) {
                            outputStream.write(buff, 0, size);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                inputStream.closeEntry();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
