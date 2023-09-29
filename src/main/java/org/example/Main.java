package org.example;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Main {
    public static void main(String[] args)  {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            //connect to postgres
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/xml990pf", "postgres", "postgres1");
            System.out.println("Connected to PostgreSQL database!");
            Statement stmt = conn.createStatement();

//region
            //create grants table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS grants (" +
                    "id SERIAL PRIMARY KEY," +
                    "tax_period_end_date DATE," +
                    "return_type_code VARCHAR(10)," +
                    "tax_period_begin_date DATE," +
                    "ein VARCHAR(15)," +
                    "filer_name VARCHAR(255)," +
                    "filer_street VARCHAR(255)," +
                    "filer_city VARCHAR(100)," +
                    "filer_state VARCHAR(10)," +
                    "filer_zip VARCHAR(15)," +
                    "recipient_name VARCHAR(255)," +
                    "grant_amount INT," +
                    "recipient_street VARCHAR(255)," +
                    "recipient_city VARCHAR(100)," +
                    "recipient_state VARCHAR(10)," +
                    "recipient_zip VARCHAR(15)," +
                    "recipient_relationship VARCHAR(50)," +
                    "recipient_foundation_status VARCHAR(50)," +
                    "grant_purpose TEXT" +
                    ");";

            //execute sql query to create the table
            stmt.execute(createTableSQL);
            System.out.println("Grants Table created successfully!");
//endregion
            //create insert sql query
            String insertSQL = "INSERT INTO grants (" +
                    "tax_period_end_date," +
                    "return_type_code," +
                    "tax_period_begin_date," +
                    "ein," +
                    "filer_name," +
                    "filer_street," +
                    "filer_city," +
                    "filer_state," +
                    "filer_zip," +
                    "recipient_name," +
                    "grant_amount," +
                    "recipient_street," +
                    "recipient_city," +
                    "recipient_state," +
                    "recipient_zip," +
                    "recipient_relationship," +
                    "recipient_foundation_status," +
                    "grant_purpose" +
                    ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
//            PreparedStatement pstmt = null;
            pstmt = conn.prepareStatement(insertSQL);


            //initialize XML parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();

            //scan directory for xml files
//            File folder = new File("C:\\Users\\truth\\IdeaProjects\\Pf990Db\\pf990\\rockefeller_2013.xml");
//            File[] listOfFiles = folder.listFiles();
            File file = new File("C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2013\\rockefeller_2013.xml");
            //loop through each xml file in the directory
//            assert listOfFiles != null;
//            for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".xml")) {

                //create a new grantInfo object for each xml file
                GrantInfo grantInfo = new GrantInfo();
                String xmlFilePath = file.getAbsolutePath();
                Document doc = null;
                //parse xml file
                doc = builder.parse(new File(xmlFilePath));
                Element root = doc.getDocumentElement();
                Element filerElement = (Element) root.getElementsByTagName("Filer").item(0);

                NodeList grantNodes = doc.getElementsByTagName("GrantOrContributionPdDurYrGrp");
                int usGrantCount = 0;

                for (int i = 0; i < grantNodes.getLength(); i++) {
                    Node node = grantNodes.item(i);
                    if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                        Element grantElement = (Element) node;
                        if ((grantElement.getElementsByTagName("RecipientUSAddress").getLength() > 0) &&
                                (grantElement.getElementsByTagName("RecipientUSAddress").item(0) != null)) {
                            usGrantCount++;
                        }
                    }
                }

                //extract and populate filer info ONCE per xml file
                grantInfo.taxPeriodEndDate = Date.valueOf(root.getElementsByTagName("TaxPeriodEndDt").item(0).getTextContent());
                grantInfo.returnTypeCode = root.getElementsByTagName("ReturnTypeCd").item(0).getTextContent();
                grantInfo.taxPeriodBeginDate = Date.valueOf(root.getElementsByTagName("TaxPeriodBeginDt").item(0).getTextContent());

                grantInfo.ein = filerElement.getElementsByTagName("EIN").item(0).getTextContent();
                grantInfo.filerName = ((Element) filerElement.getElementsByTagName("BusinessName").item(0)).getElementsByTagName("BusinessNameLine1").item(0).getTextContent();
                grantInfo.filerStreet = ((Element) filerElement.getElementsByTagName("USAddress").item(0)).getElementsByTagName("AddressLine1").item(0).getTextContent();
                grantInfo.filerCity = ((Element) filerElement.getElementsByTagName("USAddress").item(0)).getElementsByTagName("City").item(0).getTextContent();
                grantInfo.filerState = ((Element) filerElement.getElementsByTagName("USAddress").item(0)).getElementsByTagName("State").item(0).getTextContent();
                grantInfo.filerZip = ((Element) filerElement.getElementsByTagName("USAddress").item(0)).getElementsByTagName("ZIPCode").item(0).getTextContent();

                //Loop to extract and populate GRANT info for each Grant element
                grantNodes = root.getElementsByTagName("GrantOrContributionPdDurYrGrp");
                System.out.println("Number of grants: " + grantNodes.getLength());

                for (int i = 0; i < grantNodes.getLength(); i++) {

                    //connect to postgres
                    Node node = grantNodes.item(i);
                    if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                        Element grantElement = (Element) grantNodes.item(i);

                        // Skip foreign grants
                        if (grantElement.getElementsByTagName("RecipientForeignAddress").getLength() > 0) {
                            continue;
                        }

                        System.out.println("processing grant " + i + " of " + grantNodes.getLength());
                        // Extract grant info and populate the GrantInfo object
                        NodeList recipientBusinessNameList = grantElement.getElementsByTagName("RecipientBusinessName");
                        if (recipientBusinessNameList.getLength() > 0 && recipientBusinessNameList.item(0) != null) {
                            Element recipientBusinessNameElement = (Element) recipientBusinessNameList.item(0);
                            NodeList businessNameLine1List = recipientBusinessNameElement.getElementsByTagName("BusinessNameLine1");
                            if (businessNameLine1List.getLength() > 0 && businessNameLine1List.item(0) != null) {
                                grantInfo.recipientName = businessNameLine1List.item(0).getTextContent();
                            }
                        }
                        else {
                            grantInfo.recipientName = "Unknown recipient name";
                        }
                        grantInfo.grantAmount = Integer.parseInt(grantElement.getElementsByTagName("Amt").item(0).getTextContent());
                        if ((grantElement.getElementsByTagName("RecipientUSAddress").getLength() > 0) &&
                                (grantElement.getElementsByTagName("RecipientUSAddress").item(0) != null)) {
                            //Handle US-based grants
                                Element usAddressElement = (Element) grantElement.getElementsByTagName("RecipientUSAddress").item(0);
                                grantInfo.recipientStreet = ((Element) grantElement.getElementsByTagName("RecipientUSAddress").item(0)).getElementsByTagName("AddressLine1").item(0).getTextContent();
                                grantInfo.recipientCity = ((Element) grantElement.getElementsByTagName("RecipientUSAddress").item(0)).getElementsByTagName("City").item(0).getTextContent();
                                grantInfo.recipientState = ((Element) grantElement.getElementsByTagName("RecipientUSAddress").item(0)).getElementsByTagName("State").item(0).getTextContent();
                                grantInfo.recipientZip = ((Element) grantElement.getElementsByTagName("RecipientUSAddress").item(0)).getElementsByTagName("ZIPCode").item(0).getTextContent();
                                grantInfo.recipientRelationship = grantElement.getElementsByTagName("RecipientRelationshipTxt").item(0).getTextContent();
                                grantInfo.recipientFoundationStatus = grantElement.getElementsByTagName("RecipientFoundationStatusTxt").item(0).getTextContent();
                                grantInfo.grantPurpose = grantElement.getElementsByTagName("GrantOrContributionPurposeTxt").item(0).getTextContent();

                                pstmt.setString(10, grantInfo.recipientName);
                                pstmt.setInt(11, grantInfo.grantAmount);
                                pstmt.setString(12, grantInfo.recipientStreet);
                                pstmt.setString(13, grantInfo.recipientCity);
                                pstmt.setString(14, grantInfo.recipientState);
                                pstmt.setString(15, grantInfo.recipientZip);
                                pstmt.setString(16, grantInfo.recipientRelationship);
                                pstmt.setString(17, grantInfo.recipientFoundationStatus);
                                pstmt.setString(18, grantInfo.grantPurpose);
                            }





                        //insert filer and grantInfo into db
                        pstmt.setDate(1, grantInfo.taxPeriodEndDate);
                        pstmt.setString(2, grantInfo.returnTypeCode);
                        pstmt.setDate(3, grantInfo.taxPeriodBeginDate);
                        pstmt.setString(4, grantInfo.ein);
                        pstmt.setString(5, grantInfo.filerName);
                        pstmt.setString(6, grantInfo.filerStreet);
                        pstmt.setString(7, grantInfo.filerCity);
                        pstmt.setString(8, grantInfo.filerState);
                        pstmt.setString(9, grantInfo.filerZip);



                        int rowsInserted = pstmt.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("A new grant was inserted successfully!");
                        }


                    }
                }
            }
        } // Closing curly brace for the main try block
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }  //
        finally {
            try {
                if (conn != null) conn.close();
                if (pstmt != null) pstmt.close();
            }
            catch (SQLException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }
}































