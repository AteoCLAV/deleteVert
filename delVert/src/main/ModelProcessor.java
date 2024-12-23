package main;

import model.Model;
import objWriter.ObjWriter;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ModelProcessor {
    private static final Logger LOGGER = Logger.getLogger(ModelProcessor.class.getName());

    public List<Integer> getVerticesToRemove() {
        List<Integer> verticesToRemove = new ArrayList<>();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите номера вершин для удаления (введите -1 для завершения):");

            while (true) {
                if (!scanner.hasNextInt()) {
                    LOGGER.warning("Введено не числовое значение. Пропускаем...");
                    scanner.next();
                    continue;
                }

                int vertexIndex = scanner.nextInt();
                if (vertexIndex == -1) {
                    break;
                }
                if (vertexIndex < 0) {
                    LOGGER.warning("Пропущен отрицательный индекс: " + vertexIndex);
                    continue;
                }
                verticesToRemove.add(vertexIndex);
            }
        }
        return verticesToRemove;
    }

    public boolean validateVertexIndices(Model model, List<Integer> indices) {
        int maxVertex = model.vertices.size() - 1;
        for (int index : indices) {
            if (index < 0 || index > maxVertex) {
                LOGGER.warning("Индекс вершины вне допустимого диапазона: " + index +
                        ". Допустимый диапазон: 0-" + maxVertex);
                return false;
            }
        }
        return true;
    }

    public String getSavePath(String inputPath) {
        int option = JOptionPane.showOptionDialog(null,
                "Выберите действие:",
                "Сохранение модели",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Перезаписать текущую", "Создать новую"},
                "Создать новую");

        if (option == 0) {
            return inputPath;
        } else {
            JFileChooser saveChooser = new JFileChooser();
            saveChooser.setDialogTitle("Выберите путь для сохранения");
            saveChooser.setSelectedFile(new File("modified_model.obj"));
            
            if (saveChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String outputPath = saveChooser.getSelectedFile().getAbsolutePath();
                if (!outputPath.toLowerCase().endsWith(".obj")) {
                    outputPath += ".obj";
                }
                return outputPath;
            }
        }
        return null;
    }
} 