package apm1001;

import org.apache.commons.csv.*;
import edu.duke.*;

import java.io.File;

class Names {

    void test() {

//        FileResource file = new FileResource();
//        CSVParser parser = file.getCSVParser(false);
//        totalBirths(parser);


//        System.out.println("Rank of Male name William in 1881:  " +
//                getRank(2014,"Addison", "F"));


//        System.out.println("Male Name of 2 rank in 1881:  " +
//                getName(1982,450, "M"));


//        whatIsNameInYear("Owen", 1974, 2014, "M");


        System.out.println("Year of Highest rank of Female name Ida: " +
                getYearOfHighestRank("Mich", "M"));


//        System.out.println("Average rank of Female name Ida: " +
//                getAverageRank("Robert", "M"));


//        System.out.println("Total births ranked higher than Ida: " +
//                getTotalBirthsRankedHigher(1990, "Drew", "M"));
    }

    void totalBirths (CSVParser parser) {
        int total = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalNames = 0;
        int totalBoysNames = 0;
        int totalGirlsNames = 0;

        for (CSVRecord record : parser) {
            int current = Integer.parseInt(record.get(2));
            total += current;
            totalNames += 1;
            if (record.get(1).equals("M")) {
                totalBoys += current;
                totalBoysNames += 1;
            }
            else {
                totalGirls += current;
                totalGirlsNames += 1;
            }
        }

        System.out.println("Total boys: " + totalBoys);
        System.out.println("Total girls: " + totalGirls);
        System.out.println("Total babies: " + total);
        System.out.println("Total boys names: " + totalBoysNames);
        System.out.println("Total girls names: " + totalGirlsNames);
        System.out.println("Total babies names: " + totalNames);
    }

    int getRank (int year, String name, String gender) {
        if (year < 1880 || year > 2014) {
            return -1;
        }
        int rank = -1;
        FileResource file = new FileResource("testing/us_babynames_by_year/yob" +
                Integer.toString(year) + ".csv");
        CSVParser parser = file.getCSVParser(false);

        int currentRank = 0;
        if (gender.equals("F")) {
            for (CSVRecord record : parser) {
                currentRank++;
                if (record.get(0).equals(name) && record.get(1).equals(gender)) {
                    rank = currentRank;
                }
            }
        }
        else if (gender.equals("M")){
            for (CSVRecord record : parser) {
                if (record.get(1).equals("F")) {
                    continue;
                }
                currentRank++;
                if (record.get(0).equals(name) && record.get(1).equals(gender)) {
                    rank = currentRank;
                }
            }
        }

        return rank;
    }

    String getName (int year, int rank, String gender) {
        if (year < 1880 || year > 2014) {
            return "INVALID YEAR";
        }
        String name = null;
        FileResource file = new FileResource("testing/us_babynames_by_year/yob" +
                Integer.toString(year) + ".csv");
        CSVParser parser = file.getCSVParser(false);
        int currentRank = 0;

        if (gender.equals("F")) {
            for (CSVRecord record : parser) {
                currentRank++;
                if (currentRank == rank && record.get(1).equals(gender)) {
                    name = record.get(0);
                }
            }
        }
        else if (gender.equals("M")){
            for (CSVRecord record : parser) {
                if (record.get(1).equals("F")) {
                    continue;
                }
                currentRank++;
                if (currentRank == rank && record.get(1).equals(gender)) {
                    name = record.get(0);
                }
            }
        }
        if (name == null) {
            return "NO NAME";
        } else return name;
    }

    void whatIsNameInYear (String name, int year, int newYear, String gender) {
        int rank = getRank(year, name, gender);
        if (rank != -1) {
            String newName = getName(newYear, rank, gender);
            System.out.println(name + " born in " + year + " would be " + newName +
                    " if she was born in " + newYear);
        }
        else {
            System.out.println("The name not found");
        }
    }

    int getYearOfHighestRank (String name, String gender) {
        DirectoryResource dir = new DirectoryResource();
        int highestRank = Integer.MAX_VALUE;
        int yearOfHighestRank = -1;
        for (File f : dir.selectedFiles()) {
            int year = Integer.parseInt(f.getName().substring(3, 7));
            int currentRank = getRank(year, name, gender);
            if (highestRank > currentRank && currentRank != -1) {
                yearOfHighestRank = year;
                highestRank = currentRank;
            }
        }
        return yearOfHighestRank;
    }

    double getAverageRank (String name, String gender) {
        DirectoryResource dir = new DirectoryResource();
        double sumOfRanks = 0;
        int count = 0;
        for (File f : dir.selectedFiles()) {
            int year = Integer.parseInt(f.getName().substring(3, 7));
            int currentRank = getRank(year, name, gender);
            sumOfRanks += currentRank;
            count++;
        }
        if (count == 0) {
            return -1.0;
        }
        else {
            return sumOfRanks / count;
        }
    }

    int getTotalBirthsRankedHigher (int year, String name, String gender) {
        int rank = getRank(year, name, gender);

        FileResource file = new FileResource("testing/us_babynames_by_year/yob" +
                Integer.toString(year) + ".csv");
        CSVParser parser = file.getCSVParser(false);

        int totalBirths = 0;
        int currentRank = 0;
        if (gender.equals("F")) {
            for (CSVRecord record : parser) {
                currentRank++;
                if (currentRank < rank) {
                    totalBirths += Integer.parseInt(record.get(2));
                }

            }
        }
        else if (gender.equals("M")){
            for (CSVRecord record : parser) {
                if (record.get(1).equals("F")) {
                    continue;
                }
                currentRank++;
                if (currentRank < rank) {
                    totalBirths += Integer.parseInt(record.get(2));
                }
            }
        }
        return totalBirths;
    }

}

public class Main {

    public static void main(String[] args) {
	// write your code here
        Names test = new Names();
        test.test();
    }
}
