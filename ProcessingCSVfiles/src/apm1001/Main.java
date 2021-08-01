package apm1001;

import edu.duke.*;
import org.apache.commons.csv.*;

import java.io.File;

class CountryExports {

    void tester(){
        FileResource file = new FileResource("exports/exportdata.csv");
        CSVParser parser = file.getCSVParser();

//        System.out.println("Test countryInfo method: ");
//        System.out.println(countryInfo(parser, "Nauru"));
//        parser = file.getCSVParser();

//        System.out.println("Test listExportsTwoProducts method: ");
//        listExportsTwoProducts(parser, "cotton", "flowers");
//        parser = file.getCSVParser();

//        System.out.println("Test numberOfExports method: ");
//        System.out.println(numberOfExports(parser, "cocoa"));
//        parser = file.getCSVParser();
//
//        System.out.println("Test bigExports method: ");
//        bigExports(parser, "$999,999,999,999");

    }

    public String countryInfo(CSVParser parser, String country){

        for(CSVRecord record : parser){
            String currentCountry = record.get("Country");
            if(currentCountry.equals(country)){

                String info = country + ": " + record.get("Exports") + ": "
                        + record.get("Value (dollars)");
                return info;
            }
        }
        return "NOT FOUND";
    }

    void listExportsTwoProducts(CSVParser parser, String exportItem1, String exportItem2){

        for(CSVRecord record : parser){
            String exports = record.get("Exports");
            if(exports.contains(exportItem1) && exports.contains(exportItem2)){
                System.out.println(record.get("Country"));
            }
        }
    }
    
    int numberOfExports(CSVParser parser, String exportItem){
        int count = 0;
        for (CSVRecord record : parser) {
            String export = record.get("Exports");
            if (export.contains(exportItem)) {
                count++;
            }
        }
        return count;
    }

    void bigExports (CSVParser parser, String amount) {
        int lengthAmount = amount.length();
        for (CSVRecord record : parser) {
            String currentAmount = record.get("Value (dollars)");
            if (currentAmount.length() > lengthAmount) {
                System.out.println(record.get("Country") + " " + currentAmount);
            }
        }
    }
}

class FindingTheHottestDayInYear {


    void test () {
        FileResource file = new FileResource("nc_weather/2013/weather-2013-09-02.csv");
        CSVParser parser = file.getCSVParser();


//        System.out.println("Min temp: " + coldestHourInFile(parser).get("TemperatureF"));


//        String nameOfColdestFile = fileWithColdestTemperature();
//        FileResource f = new FileResource("nc_weather/2013/" + nameOfColdestFile);
//        parser = f.getCSVParser();
//        System.out.println("Name of the file with coldest temperature: " +
//                nameOfColdestFile + "\nMin Temp: " + coldestHourInFile(parser).get("TemperatureF"));


//        CSVRecord test3 = lowestHumidityInFile(parser);
//        System.out.println("Lowest Humidity was " + test3.get("Humidity") +
//                " at " + test3.get("DateUTC"));


//        CSVRecord test4 = lowestHumidityInManyFiles();
//        System.out.println("Lowest humidity was " + test4.get("Humidity") + " at " + test4.get("DateUTC"));


//        System.out.println("Average temperature in file is " + averageTemperatureInFile(parser));


//        double test6 = averageTemperatureWithHighHumidityInFile(parser, 80);
//        if (test6 == 0) {
//            System.out.println("No temperatures with that humidity");
//        }
//        else {
//            System.out.println("Average Temp when high Humidity is " + test6);
//        }
    }

    CSVRecord coldestHourInFile (CSVParser parser) {
        CSVRecord largestSoFar = null;
        for (CSVRecord record : parser) {
            if ((int) Double.parseDouble(record.get("TemperatureF")) != -9999){
                if (largestSoFar == null) {
                    largestSoFar = record;
                }
                else {
                    if (Double.parseDouble(record.get("TemperatureF")) <
                            Double.parseDouble(largestSoFar.get("TemperatureF"))) {
                        largestSoFar = record;
                    }
                }
            }
        }

        return largestSoFar;
    }

    String fileWithColdestTemperature () {
        DirectoryResource dir = new DirectoryResource();
        String name = null;
        double minTemp = Double.MAX_VALUE;
        for (File f : dir.selectedFiles()) {
            FileResource file = new FileResource(f);
            CSVParser parser = file.getCSVParser();
            double currTemp = Double.parseDouble(coldestHourInFile(parser).get("TemperatureF"));
            if (currTemp < minTemp) {
                name = f.getName();
                minTemp = currTemp;
            }
        }
        return name;
    }

    CSVRecord lowestHumidityInFile (CSVParser parser) {
        CSVRecord lowestSoFar = null;
        for (CSVRecord record : parser) {
            lowestSoFar = lowestBetweenTwoRecords(lowestSoFar, record);
        }
        return lowestSoFar;
    }

    CSVRecord lowestHumidityInManyFiles () {
        DirectoryResource dir = new DirectoryResource();
        CSVRecord lowestSoFar = null;
        double minTemp = Double.MAX_VALUE;
        for (File f : dir.selectedFiles()) {
            FileResource file = new FileResource(f);
            CSVParser parser = file.getCSVParser();

            CSVRecord currentValue = lowestHumidityInFile(parser);
            lowestSoFar = lowestBetweenTwoRecords(lowestSoFar, currentValue);

        }
        return lowestSoFar;
    }

    CSVRecord lowestBetweenTwoRecords (CSVRecord first, CSVRecord second) {
        if (!second.get("Humidity").equals("N/A")) {
            if (first == null) {
                first = second;
            } else {
                if (Integer.parseInt(second.get("Humidity")) <
                        Integer.parseInt(first.get("Humidity"))) {
                    first = second;
                }
            }
        }
        return first;
    }

    double averageTemperatureInFile (CSVParser parser) {
        double sumOfTemp = 0;
        int count = 0;
        for (CSVRecord record : parser) {
            double currentTemp = Double.parseDouble(record.get("TemperatureF"));
            if ((int) currentTemp != -9999){
                count++;
                sumOfTemp += currentTemp;
            }
        }
        return sumOfTemp / count;
    }

    double averageTemperatureWithHighHumidityInFile (CSVParser parser, int value) {
        double sumOfTemp = 0;
        int count = 0;
        for (CSVRecord record : parser) {
            if (Integer.parseInt(record.get("Humidity")) >= value){
                double currentTemp = Double.parseDouble(record.get("TemperatureF"));
                if ((int) currentTemp != -9999){
                    count++;
                    sumOfTemp += currentTemp;
                }
            }
        }
        if (count == 0) {
            return 0;
        }
        else {
            return sumOfTemp / count;
        }
    }
}

public class Main {

    public static void main(String[] args) {
	// write your code here
//        CountryExports test1 = new CountryExports();
//        test1.tester();

        FindingTheHottestDayInYear testTemp = new FindingTheHottestDayInYear();
        testTemp.test();
    }
}
