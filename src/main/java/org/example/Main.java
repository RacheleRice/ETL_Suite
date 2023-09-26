package org.example;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Main {
    public static void main(String[] args) {
        try {
            //connect to postgres
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/xml990pf", "postgres", "postgres1");
            String insertSQL = "INSERT INTO grants (ein, business_name, return_type_code, tax_period_begin_date, tax_period_end_date, recipient_name, recipient_address, recipient_city, recipient_state, recipient_zip, recipient_foundation_status, grant_purpose, grant_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
//region
            //Create table if it doesn't exist
//            String createTableSQL = "CREATE TABLE IF NOT EXISTS grants (" +
//                    "id SERIAL PRIMARY KEY," +
//                    "ein VARCHAR(15)," +
//                    "business_name VARCHAR(255)," +
//                    "return_type_code VARCHAR(10)," +
//                    "tax_period_begin_date DATE," +
//                    "tax_period_end_date DATE," +
//                    "recipient_name VARCHAR(255)," +
//                    "recipient_address VARCHAR(255)," +
//                    "recipient_city VARCHAR(100)," +
//                    "recipient_state VARCHAR(10)," +
//                    "recipient_zip VARCHAR(10)," +
//                    "recipient_foundation_status VARCHAR(50)," +
//                    "grant_purpose TEXT," +
//                    "grant_amount INT" +
//                    ");";
//
            //execute sql query to create the table
//            stmt.executeUpdate(createTableSQL);
//            System.out.println("Grants Table created successfully!");
//endregion

            //initialize XML parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

//            XML file URLs
            String[] xmlUrls = {
//                    open society institute 2021 - 2013
                    "https://pp-990-xml.s3.us-east-1.amazonaws.com/202233199349109788_public.xml?response-content-disposition=inline&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA266MJEJYTM5WAG5Y%2F20230924%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230924T063908Z&X-Amz-Expires=1800&X-Amz-SignedHeaders=host&X-Amz-Signature=174ef8146dff9eb8a2cfb243ef3f250ce2e4bd459c1fb7f8152ea50a92c5a16d",
                    "https://pp-990-xml.s3.us-east-1.amazonaws.com/202123529349100402_public.xml?response-content-disposition=inline&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA266MJEJYTM5WAG5Y%2F20230924%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230924T065451Z&X-Amz-Expires=1800&X-Amz-SignedHeaders=host&X-Amz-Signature=a7aeef28e3324245c6f3baf98796426079743eba5dc7d5b8dde56e64b55f000e",
                    "https://pp-990-xml.s3.us-east-1.amazonaws.com/202013219349105066_public.xml?response-content-disposition=inline&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA266MJEJYTM5WAG5Y%2F20230924%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230924T065526Z&X-Amz-Expires=1800&X-Amz-SignedHeaders=host&X-Amz-Signature=ee8d63a129d2c927e7912b6957ebc5534b6592780076dd687a28012015ee1afb",
                    "https://pp-990-xml.s3.us-east-1.amazonaws.com/201903199349105990_public.xml?response-content-disposition=inline&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA266MJEJYTM5WAG5Y%2F20230924%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230924T065546Z&X-Amz-Expires=1800&X-Amz-SignedHeaders=host&X-Amz-Signature=657c3fdcbe768651d0a8b681bbfe63aba505116ca40b28bfc406c66b579485c9",
                    "https://pp-990-xml.s3.us-east-1.amazonaws.com/201833189349103443_public.xml?response-content-disposition=inline&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA266MJEJYTM5WAG5Y%2F20230924%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230924T065604Z&X-Amz-Expires=1800&X-Amz-SignedHeaders=host&X-Amz-Signature=aea6bf4b77286c62c8c09f929c41167c3c2a28a9be24ae8d34afe67ba6195db5",
                    "https://pp-990-xml.s3.us-east-1.amazonaws.com/201713189349101901_public.xml?response-content-disposition=inline&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA266MJEJYTM5WAG5Y%2F20230924%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230924T065623Z&X-Amz-Expires=1800&X-Amz-SignedHeaders=host&X-Amz-Signature=a036947f4242afb53a29a18434abcf0e2711e94682c6fa940451fcefad65fb58",
                    "https://pp-990-xml.s3.us-east-1.amazonaws.com/201613199349101416_public.xml?response-content-disposition=inline&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA266MJEJYTM5WAG5Y%2F20230924%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230924T065644Z&X-Amz-Expires=1800&X-Amz-SignedHeaders=host&X-Amz-Signature=64e6af4aa3a6ac4f73ca5c50e8df8a87ced547099c2f5dbb3a998a9af8dbe9c9",
                    "https://pp-990-xml.s3.us-east-1.amazonaws.com/201503179349100345_public.xml?response-content-disposition=inline&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA266MJEJYTM5WAG5Y%2F20230924%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230924T065723Z&X-Amz-Expires=1800&X-Amz-SignedHeaders=host&X-Amz-Signature=2c087e3fbc074440e9bde982422c9fe8bc489860ed9252711a5cb920dd10db67",
                    "https://pp-990-xml.s3.us-east-1.amazonaws.com/201423189349101912_public.xml?response-content-disposition=inline&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA266MJEJYTM5WAG5Y%2F20230924%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230924T065750Z&X-Amz-Expires=1800&X-Amz-SignedHeaders=host&X-Amz-Signature=a0e193103394ae996836535f1e6540ab7497a14baf5aecaf127676af3b88c2c7"
//                    next pf

    
            };

            for (String xmlUrl : xmlUrls) {
                URL url = new URL(xmlUrl);

                // Introduce a delay (1 second) between requests
                Thread.sleep(1000); // Sleep for 1 second (adjust as needed)

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Add the User-Agent header
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                Document doc = builder.parse(connection.getInputStream());
                Element root = doc.getDocumentElement();


                //Extract filer info
                String ein = root.getElementsByTagName("EIN").item(0).getTextContent();
                String businessName = root.getElementsByTagName("BusinessNameLine1").item(0).getTextContent();
                String returnTypeCode = root.getElementsByTagName("ReturnTypeCd").item(0).getTextContent();
                String taxPeriodBeginDate = root.getElementsByTagName("TaxPeriodBeginDt").item(0).getTextContent();
                String taxPeriodEndDate = root.getElementsByTagName("TaxPeriodEndDt").item(0).getTextContent();

                //loop through each grant
                NodeList grantNodes = root.getElementsByTagName("GrantOrContributionPdDurYrGrp");
                for (int i = 0; i <grantNodes.getLength(); i++) {
                    Element grantElement = (Element) grantNodes.item(i);

                    String recipientName = grantElement.getElementsByTagName("BusinessNameLine1").item(0).getTextContent();
                    String addressLine1 = grantElement.getElementsByTagName("AddressLine1").item(0).getTextContent();
                    String city = grantElement.getElementsByTagName("City").item(0).getTextContent();
                    String state = grantElement.getElementsByTagName("State").item(0).getTextContent();
                    String zipCode = grantElement.getElementsByTagName("ZIPCode").item(0).getTextContent();
                    String foundationStatus = grantElement.getElementsByTagName("RecipientFoundationStatusTxt").item(0).getTextContent();
                    String purpose = grantElement.getElementsByTagName("GrantOrContributionPurposeTxt").item(0).getTextContent();
                    String amount = grantElement.getElementsByTagName("Amt").item(0).getTextContent();

                    //prepare the sql statement for insertion, and execute

                    pstmt.setString(1, ein);
                    pstmt.setString(2, businessName);
                    pstmt.setString(3, returnTypeCode);
                    pstmt.setDate(4, java.sql.Date.valueOf(taxPeriodBeginDate));
                    pstmt.setDate(5, java.sql.Date.valueOf(taxPeriodEndDate));
                    pstmt.setString(6, recipientName);
                    pstmt.setString(7, addressLine1);
                    pstmt.setString(8, city);
                    pstmt.setString(9, state);
                    pstmt.setString(10, zipCode);
                    pstmt.setString(11, foundationStatus);
                    pstmt.setString(12, purpose);
                    pstmt.setInt(13, Integer.parseInt(amount));

                    pstmt.executeUpdate();


                    }

                }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



