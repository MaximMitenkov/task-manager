package org.mitenkov.export;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Task;
import org.mitenkov.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskExport {

    private final TaskRepository taskRepository;

    private final HttpServletResponse response;

    private final File file = new File("C:\\Users\\mitma\\Documents\\", "tasks.xlsx");

    @SneakyThrows
    public void exportTasksInFile() {
        List<Task> tasks = taskRepository.findAll();

        try (Workbook book = new XSSFWorkbook()) {
            Sheet sheet = book.createSheet("Tasks");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Title");
            row.createCell(1).setCellValue("Priority");
            row.createCell(2).setCellValue("Deadline");
            row.createCell(3).setCellValue("Version");
            row.createCell(4).setCellValue("User");
            row.createCell(5).setCellValue("Type");

            for (Task task : tasks) {
                Row newRow = sheet.createRow(tasks.indexOf(task) + 1);
                newRow.createCell(0).setCellValue(task.getTitle());
                newRow.createCell(1).setCellValue(task.getPriority().toString());
                newRow.createCell(2).setCellValue(task.getDeadline().toString());
                if (task instanceof Bug bug) {
                    newRow.createCell(3).setCellValue(bug.getVersion());
                    newRow.createCell(5).setCellValue("Bug");
                } else {
                    newRow.createCell(3).setCellValue("-");
                    newRow.createCell(5).setCellValue("Feature");
                }
                newRow.createCell(4).setCellValue(task.getUser().getId());
            }
            book.write(new FileOutputStream(file));
        }
    }

    public void doGet() throws ServletException, IOException {
        ServletOutputStream out = response.getOutputStream();
        byte[] byteArray = Files.readAllBytes(file.toPath());
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setContentLength(byteArray.length);
        out.write(byteArray);
        out.flush();
        out.close();
    }
}
