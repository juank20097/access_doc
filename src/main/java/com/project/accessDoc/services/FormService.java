package com.project.accessDoc.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.project.accessDoc.entities.Form;
import com.project.accessDoc.entities.Permission;
import com.project.accessDoc.repo.FormRepo;
import com.project.accessDoc.repo.PermissionRepo;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Service
public class FormService {
    @Autowired
    private FormRepo formRepository;

    @Autowired
    private PermissionRepo permissionRepository;

    public void createFormAndPermissions(Map<String, Object> jsonData) {
        // Obtener la fecha actual
        Date currentDate = new Date();
        String baseDocumentName = "GRI-GTIC-P01-F02_SolicitudPermisos_" + new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
        String documentName = generateUniqueDocumentName(baseDocumentName); // Generar nombre único

        // Crear el objeto Form
        Form form = new Form();
        form.setDate(currentDate);
        form.setName(documentName);
        form.setStatus("1"); // Establecer el estado como "1"

        // Guardar el Form en la base de datos
        Form savedForm = formRepository.save(form);

        // Extraer los permisos del JSON
        List<Map<String, Object>> permissions = (List<Map<String, Object>>) jsonData.get("permissions");

        // Crear y guardar cada Permission en la base de datos
        for (Map<String, Object> permissionData : permissions) {
            Permission permission = new Permission();
            permission.setIpOrigin((String) permissionData.get("ipOrigin"));
            permission.setDescriptionOrigin((String) permissionData.get("descriptionOrigin"));
            permission.setAreaOrigin((String) permissionData.get("areaOrigin"));
            permission.setIpDestination((String) permissionData.get("ipDestination"));
            permission.setDescriptionDestination((String) permissionData.get("descriptionDestination"));
            permission.setAreaDestination((String) permissionData.get("areaDestination"));
            permission.setProtocol((String) permissionData.get("protocol"));
            permission.setPorts((String) permissionData.get("ports"));
            permission.setDuration((String) permissionData.get("duration"));
            permission.setForm(savedForm); // Asignar el form a este permiso

            // Guardar el Permission en la base de datos
            permissionRepository.save(permission);
        }
    }

    public void generateExcel(Map<String, Object> jsonData) throws IOException {
        // Ruta del archivo base.xlsx en resources
        ClassPathResource resource = new ClassPathResource("base.xlsx");

        // Abrir el archivo Excel base
        FileInputStream fileInputStream = new FileInputStream(resource.getFile());
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        // Obtener la fecha actual
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Extraer los permisos del JSON
        List<Map<String, Object>> permissions = (List<Map<String, Object>>) jsonData.get("permissions");

        // Escribir la fecha en la celda C3
        Row dateRow = sheet.getRow(2); // Fila 3 (índice 2)
        Cell dateCell = dateRow.getCell(2); // Columna C (índice 2)
        dateCell.setCellValue(date);

        // Escribir los permisos en las filas correspondientes
        int startRow = 41; // Comienza en la fila 42 (índice 41)

        for (Map<String, Object> permission : permissions) {
            // Crear una nueva fila
            Row row = sheet.createRow(startRow);

            // Escribir los datos del permiso en las columnas de A a I
            row.createCell(0).setCellValue((String) permission.get("ipOrigin"));
            row.createCell(1).setCellValue((String) permission.get("descriptionOrigin"));
            row.createCell(2).setCellValue((String) permission.get("areaOrigin"));
            row.createCell(3).setCellValue((String) permission.get("ipDestination"));
            row.createCell(4).setCellValue((String) permission.get("descriptionDestination"));
            row.createCell(5).setCellValue((String) permission.get("areaDestination"));
            row.createCell(6).setCellValue((String) permission.get("protocol"));
            row.createCell(7).setCellValue((String) permission.get("ports"));
            row.createCell(8).setCellValue((String) permission.get("duration"));

            startRow++;
        }

        // Guardar el nuevo archivo Excel
        String baseFileName = "GRI-GTIC-P01-F02_SolicitudPermisos_" + date;
        String fileExtension = ".xlsx";
        String outputFilePath = System.getProperty("user.dir") + File.separator + baseFileName + fileExtension;

        // Comprobar si el archivo ya existe y modificar el nombre si es necesario
        int counter = 1;
        while (new File(outputFilePath).exists()) {
            outputFilePath = System.getProperty("user.dir") + File.separator + baseFileName + "_" + counter + fileExtension;
            counter++;
        }

        // Guardar el archivo Excel
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFilePath));
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
        fileInputStream.close();

        System.out.println("Archivo Excel generado y guardado en: " + outputFilePath);
    }

    private String generateUniqueDocumentName(String baseDocumentName) {
        String fileExtension = ".xlsx";
        String outputFilePath = System.getProperty("user.dir") + File.separator + baseDocumentName + fileExtension;

        // Comprobar si el archivo ya existe y modificar el nombre si es necesario
        int counter = 1;
        while (new File(outputFilePath).exists()) {
            outputFilePath = System.getProperty("user.dir") + File.separator + baseDocumentName + "_" + counter + fileExtension;
            counter++;
        }

        // Extraer y devolver el nombre único
        return new File(outputFilePath).getName();
    }
}
