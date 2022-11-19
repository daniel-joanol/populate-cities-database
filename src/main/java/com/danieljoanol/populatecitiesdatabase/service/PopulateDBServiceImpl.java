package com.danieljoanol.populatecitiesdatabase.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @Value("${files.path}")
    private String path;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void getStream(FilesEnum fileEnum) {

        List<String> fileNames = new ArrayList<>();

        switch (fileEnum) {
            case ES:
                fileNames.add("es.xlsx");
                break;

            case BR:
                fileNames.add("br.xlsx");
                break;

            case ALL:
                fileNames.add("es.xlsx");
                fileNames.add("br.xlsx");
                break;

            default:
                fileNames.add("es.xlsx");
        }

        List<File> files = new ArrayList<>();
        for (String name : fileNames) {
            File file = new File(path + name);
            files.add(file);
        }

        List<FileInputStream> streams = new ArrayList<>();
        try {

            for (File file : files) {
                FileInputStream stream = new FileInputStream(file);
                streams.add(stream);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        readFile(streams);
    }

    @Override
    public void readFile(List<FileInputStream> streams) {

        try {

            for (FileInputStream stream : streams) {
                XSSFWorkbook workbook = new XSSFWorkbook(stream);
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();
                int line = 0;

                while (rowIterator.hasNext()) {

                        Row row = rowIterator.next();
                        Iterator<Cell> cellIterator = row.cellIterator();
                        String city = null;
                        Double latitud = null;
                        Double longitud = null;
                        String country = null;
                        String ISO = null;
                        String region = null;
                    
                    if (line > 0) {

                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();

                            switch (cell.getColumnIndex()) {

                                case 0:
                                    city = cell.getStringCellValue();
                                    break;

                                case 1:
                                    latitud = cell.getNumericCellValue();
                                    break;

                                case 2:
                                    longitud = cell.getNumericCellValue();
                                    break;

                                case 3:
                                    country = cell.getStringCellValue();
                                    break;

                                case 4:
                                    ISO = cell.getStringCellValue();
                                    break;

                                case 5:
                                    region = cell.getStringCellValue();
                                    break;

                                default:
                                    break;
                            }
                        }

                        saveData(country, latitud, longitud, region, ISO, city);
                    }

                    line++;
                }

                workbook.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveData(String countryName, Double latitud,
            Double longitud, String regionName, String ISO, String cityName) {

        Country country = countryRepository.findByName(countryName);
        if (country == null) {
            country = new Country(countryName, ISO);
            country = countryRepository.save(country);
        }

        Region region = regionRepository.findByName(regionName);
        if (region == null) {
            region = new Region(regionName, country.getId());
            region = regionRepository.save(region);
        }

        City city = cityRepository.findByName(cityName);
        if (city == null) {
            city = City.builder().name(cityName)
                    .regionId(region.getId()).latitud(latitud)
                    .longitud(longitud).build();
            city = cityRepository.save(city);
        }
    }

}
