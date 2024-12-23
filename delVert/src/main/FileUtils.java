package main;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class FileUtils {
    private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

    public static String selectInputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл модели");
        int userSelection = fileChooser.showOpenDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Выбранный файл: " + selectedFile.getAbsolutePath());
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    public static boolean validateInputFile(String pathStr) {
        if (pathStr == null) {
            return false;
        }

        Path path = Path.of(pathStr);
        if (!Files.exists(path)) {
            LOGGER.severe("Входной файл не существует: " + path);
            return false;
        }
        if (!Files.isReadable(path)) {
            LOGGER.severe("Нет прав на чтение файла: " + path);
            return false;
        }
        return true;
    }
} 