package com.danieljoanol.populatecitiesdatabase.service;

import java.io.FileInputStream;
import java.util.List;

import com.danieljoanol.populatecitiesdatabase.enumerator.FilesEnum;

public interface PopulateDBService {

    void readFile(List<FileInputStream> streams);

    void getStream(FilesEnum fileEnum);

    void saveData(String countryName, Double latitud, Double longitud,
            String regionName, String ISO, String cityName);

}
