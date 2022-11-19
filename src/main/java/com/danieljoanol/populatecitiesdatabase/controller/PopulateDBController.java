package com.danieljoanol.populatecitiesdatabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danieljoanol.populatecitiesdatabase.enumerator.FilesEnum;
import com.danieljoanol.populatecitiesdatabase.service.PopulateDBService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/populate")
@Api(value = "Populate DB Controller", description = "Controller to populate countries, regions and cities on xlsx files from https://simplemaps.com/")
public class PopulateDBController {
    
    @Autowired
    private PopulateDBService populateDBService;

    @Operation(summary = "Populated", description = "Populate DBs based on the name provided")
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "500", description = "SystemError")
    @PostMapping("/")
    public ResponseEntity<String> populateDBs(FilesEnum fileEnum) {
        populateDBService.getStream(fileEnum);
        return ResponseEntity.status(HttpStatus.CREATED).body("DBs populated");
    }
}
