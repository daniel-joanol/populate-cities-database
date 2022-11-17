package com.danieljoanol.populatecitiesdatabase.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.danieljoanol.populatecitiesdatabase.entity.City;
import com.danieljoanol.populatecitiesdatabase.entity.Country;
import com.danieljoanol.populatecitiesdatabase.entity.Region;
import com.danieljoanol.populatecitiesdatabase.enumerator.FilesEnum;
import com.danieljoanol.populatecitiesdatabase.repository.CityRepository;
import com.danieljoanol.populatecitiesdatabase.repository.CountryRepository;
import com.danieljoanol.populatecitiesdatabase.repository.RegionRepository;

@Service
public class PopulateDBServiceImpl implements PopulateDBService {

    @Value("{files.path}")
    private String path;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void getStream(FilesEnum fileEnum) {

        String fileName = "";

        switch (fileEnum) {
            case ES:
                fileName = "es.xlsx";
                break;

            case BR:
                fileName = "br.xlsx";
                break;

            case US:
                fileName = "us.xlsx";
                break;

            default:
                fileName = "es.xlsx";
        }

        File file = new File(path + fileName);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        readFile(stream);
    }

    public void readFile(FileInputStream stream) {

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int line = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                String city = null;
                String country = null;
                String region = null;

                while (cellIterator.hasNext()) {
                    
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {

                        case 0:
                            city = cell.getStringCellValue();
                            break;

                        case 3:
                            country = cell.getStringCellValue();
                            break;

                        case 5:
                            region = cell.getStringCellValue();
                            break;

                        default:
                            break;
                    }
                }

                if (line > 0) {
                    saveData(country, region, city);
                }

                line++;
            }

            workbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveData(String countryName, String regionName, String cityName) {

        Country country = countryRepository.findByName(countryName);
        if (country == null) {
            country = new Country(countryName);
            country = countryRepository.save(country);
        }

        Region region = regionRepository.findByName(cityName);
        if (region == null) {
            region = new Region(regionName, country.getId());
            region = regionRepository.save(region);
        }

        City city = cityRepository.findByName(cityName);
        if (city == null) {
            city = new City(cityName, region.getId());
            city = cityRepository.save(city);
        }
    }

}
