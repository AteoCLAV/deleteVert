package main;

import model.Model;
import objWriter.ObjWriter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            String inputPath = FileUtils.selectInputFile();
            if (inputPath == null || !FileUtils.validateInputFile(inputPath)) {
                System.out.println("Операция выбора файла отменена или файл недоступен.");
                return;
            }

            Model model = ObjReader.read(inputPath);
            if (model.vertices.isEmpty()) {
                LOGGER.warning("Модель не содержит вершин");
                return;
            }

            ModelProcessor processor = new ModelProcessor();
            List<Integer> verticesToRemove = processor.getVerticesToRemove();
            
            if (verticesToRemove.isEmpty()) {
                LOGGER.info("Не выбрано вершин для удаления");
                return;
            }
            
            if (!processor.validateVertexIndices(model, verticesToRemove)) {
                LOGGER.severe("Указаны некорректные индексы вершин");
                return;
            }

            Model.removeVertices(model, verticesToRemove);

            String outputPath = processor.getSavePath(inputPath);
            if (outputPath == null) {
                LOGGER.info("Операция сохранения отменена");
                return;
            }

            ObjWriter objWriter = new ObjWriter();
            objWriter.write(model, outputPath);
            LOGGER.info("Измененная модель записана в файл: " + outputPath);

            Model modifiedModel = ObjReader.read(outputPath);
            if (!model.equals(modifiedModel)) {
                LOGGER.warning("Модель изменилась после записи: " + outputPath);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка: " + e.getMessage(), e);
        }
    }
}