package com.danieljoanol.populatecitiesdatabase.service;

import java.io.FileInputStream;

import com.danieljoanol.populatecitiesdatabase.enumerator.FilesEnum;

public interface PopulateDBService {

    void readFile(FileInputStream stream);

    void getStream(FilesEnum fileEnum);

    void saveData(String countryName, String regionName, String cityName);

}
