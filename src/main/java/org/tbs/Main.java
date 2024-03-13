package org.tbs;

import java.io.File;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        //Initialize extractor and XMLParser
        DataExtractor extractor = new DataExtractor();
        XMLParser xmlParser = new XMLParser();

        String[] directories = {
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2013",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2014",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2015",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2016",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2017",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2018",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2019",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2020",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2021",
            "C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2022"
        };


        //Get list of XML files from the directory
        List<String> xmlFiles = xmlParser.getXmlFiles(directories);

        //Iterate through each XML file and parse it
        for (String xmlFilePath : xmlFiles) {
            File xmlFileObj = new File(xmlFilePath);
            List<GrantInfo> grants = xmlParser.parseXML(xmlFileObj);

            //Insert each parsed GrantInfo into the database
            for (GrantInfo grant : grants) {
                extractor.insertGrantInfo(grant);
            }
        }
    //call executeQueryFromCSV to create and populate the matching_grants table
        try {
            extractor.executeQueryFromCSV();
        } catch (Exception e) {
            System.out.println("Error executing query from CSV: " + e.getMessage());
            e.printStackTrace();
        }

    }

}































