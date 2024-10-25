package com.project.accessDoc.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
	
	private String DocumentName="";

	public String createFormAndPermissions(String name, String dni,ArrayList<Permission> listPermissions) {
		DocumentName = "GRI-GTIC-P01-F02_SolicitudPermisos_"
				+ new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
		Form form = new Form();
		form.setDate(new Date());
		form.setName(DocumentName);
		form.setOwner(name);
		form.setDni(dni);
		form.setStatus("1");
		Form savedForm = formRepository.save(form);

		for (Permission permission : listPermissions) {
			permission.setForm(savedForm);
			permissionRepository.save(permission);
		}
		return DocumentName;
	}

	public void generateExcel(String name, String dni,ArrayList<Permission> listPermissions) throws IOException {
		ClassPathResource resource = new ClassPathResource("base.xlsx");
		// Abrir el archivo Excel base
		InputStream fileInputStream = resource.getInputStream();
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheetAt(0);

		String date = new SimpleDateFormat("dd MMM yyyy").format(new Date());

		// Escribir la fecha en la celda C3
		Row dateRow = sheet.getRow(2); // Fila 3 (índice 2)
		Cell dateCell = dateRow.getCell(2); // Columna C (índice 2)
		dateCell.setCellValue(date);
		
		Row dateRowN = sheet.getRow(12); // Fila 13 (índice 12)
		Cell dateCellN = dateRowN.getCell(2); // Columna C (índice 2)
		dateCellN.setCellValue(name.toUpperCase());
		
		Row dateRowDni = sheet.getRow(13); // Fila 13 (índice 13)
		Cell dateCellDni = dateRowDni.getCell(2); // Columna C (índice 2)
		dateCellDni.setCellValue(dni);

		// Escribir los permisos en las filas correspondientes
		int startRow = 41; // Comienza en la fila 42 (índice 41)

		for (Permission permission : listPermissions) {
			// Crear una nueva fila
			Row row = sheet.createRow(startRow);

			// Crear un estilo que ajuste el texto
			CellStyle style = sheet.getWorkbook().createCellStyle();
			style.setWrapText(true); // Habilita el ajuste de texto
			
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(permission.getIpOrigin());
			cell0.setCellStyle(style);
			
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(permission.getDescriptionOrigin());
			cell1.setCellStyle(style);

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(permission.getAreaOrigin());
			cell2.setCellStyle(style);

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(permission.getIpDestination());
			cell3.setCellStyle(style);

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(permission.getDescriptionDestination());
			cell4.setCellStyle(style);

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(permission.getAreaDestination());
			cell5.setCellStyle(style);

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(permission.getProtocol());
			cell6.setCellStyle(style);

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(permission.getPorts());
			cell7.setCellStyle(style);

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(permission.getDuration());
			cell8.setCellStyle(style);

			startRow++;
		}

		String outputDirectoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "doc";
		String outputFilePath = outputDirectoryPath + File.separator + DocumentName + ".xlsx";

		File directory = new File(outputDirectoryPath);
		if (!directory.exists()) {
		    directory.mkdirs(); 
		}
		// Guardar el archivo Excel
		FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFilePath));
		workbook.write(fileOutputStream);
		workbook.close();
		fileOutputStream.close();
		fileInputStream.close();

		System.out.println("Archivo Excel generado y guardado en: " + outputFilePath);
	}
}
