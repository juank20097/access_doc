package com.project.accessDoc.controller;

import com.project.accessDoc.entities.Permission;
import com.project.accessDoc.services.FormService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Form", description = "Froms API")
@RestController
@RequestMapping("/api/form")
public class FormController {

	@Autowired
	private FormService formService;

	@Operation(description = "This service generates a form and associated permissions, and then creates an Excel file.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "JSON data containing form and permissions information.", required = true, content = @Content(schema = @Schema(example = "[{\"ipOrigin\": \"192.168.1.1\", \"descriptionOrigin\": \"Descripción de origen 1\", \"areaOrigin\": \"Área de origen 1\", \"ipDestination\": \"192.168.1.2\", \"descriptionDestination\": \"Descripción de destino 1\", \"areaDestination\": \"Área de destino 1\", \"protocol\": \"TCP\", \"ports\": \"80,443\", \"duration\": \"3600\"}]"))), responses = {
			@ApiResponse(responseCode = "200", description = "Form generated successfully.", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\": \"El archivo Excel ha sido generado exitosamente.\"}"))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Error al generar el archivo Excel: mensaje del error\"}"))) })
	@CrossOrigin(origins = "*")
	@PostMapping("/excel/{dni}/{name}")
	public ResponseEntity<Map<String, String>> generateForm(@PathVariable("dni") String dni,@PathVariable("name") String name,@RequestBody ArrayList<Permission> permissions) {
		Map<String, String> response = new HashMap<>();
		try {
			// Primero, guardar el form y los permisos en la base de datos
			String DocumentName=formService.createFormAndPermissions(name,dni,permissions);
			// Después, generar el Excel
			formService.generateExcel(name,dni,permissions);
			response.put("message", "El archivo Excel ha sido generado exitosamente.");
			response.put("document", DocumentName+".xlsx");
			return ResponseEntity.ok(response);
		} catch (IOException e) {
			response.put("error", "Error al generar el archivo Excel: " + e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}
}
