package org.tbs;

import java.io.File;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        System.out.println("Starting the application");
        //Initialize extractor and XMLParser objects to extract data from XML files and parse them respectively
        DataExtractor extractor = new DataExtractor();
        XMLParser xmlParser = new XMLParser();

        System.out.println("Scanning the directories for XML files...");
        String[] directories = {
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\ETL_Suite\\pf990\\TY2013",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\ETL_Suite\\pf990\\TY2014",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\ETL_Suite\\pf990\\TY2015",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\ETL_Suite\\pf990\\TY2016",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\ETL_Suite\\pf990\\TY2017",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\ETL_Suite\\pf990\\TY2018",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\ETL_Suite\\pf990\\TY2019",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\ETL_Suite\\pf990\\TY2020",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\ETL_Suite\\pf990\\TY2021",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\ETL_Suite\\pf990\\TY2022"
        };


        //Get list of XML files from the directory using the getXmlFiles method from the XMLParser class
        List<String> xmlFiles = xmlParser.getXmlFiles(directories);
        System.out.println("Found " + xmlFiles.size() + " XML files.");

        //Iterate through each XML file and parse it using the parseXML method from the XMLParser class
        for (String xmlFilePath : xmlFiles) {
            System.out.println("Parsing XML file: " + xmlFilePath);
            File xmlFileObj = new File(xmlFilePath);
            List<GrantInfo> grants = xmlParser.parseXML(xmlFileObj);
            System.out.println("Parsed " + grants.size() + " grants from file.");

            //Insert each parsed GrantInfo into the database using the insertGrantInfo method from the DataExtractor class
            for (GrantInfo grant : grants) {
                System.out.println("Inserting grant into database: " + grant.getRecipientName());
                extractor.insertGrantInfo(grant);
            }
        }
    //call executeQueryFromCSV to create and populate the matching_grants table using the executeQueryFromCSV method from the DataExtractor class
        try {
            System.out.println("Executing query from CSV...");
            extractor.executeQueryFromCSV();
        } catch (Exception e) {
            System.out.println("Error executing query from CSV: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Application completed successfully.");

    }

}































