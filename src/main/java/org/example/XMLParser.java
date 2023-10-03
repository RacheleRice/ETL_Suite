package org.example;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {

    public List<String> getXmlFiles(String directoryPath) {
        System.out.println("Scanning directory for XML files...");
        List<String> xmlFiles = new ArrayList<>();
        File directory = new File("C:\\Users\\truth\\OneDrive\\Desktop\\watershed\\Pf990Db\\pf990\\TY2013");
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".xml")) {
                xmlFiles.add(file.getAbsolutePath());
            }
        }
        return xmlFiles;
    }

    public List<GrantInfo> parseXML(File xmlFile) {
        System.out.println("Parsing XML file: " + xmlFile.getName());

        List<GrantInfo> grants = new ArrayList<>();
        int usGrantCount = 0;
        int foreignGrantCount = 0;
        int totalGrantCount = 0;

        try {
            Document doc = setupDocument(xmlFile);
            Element root = doc.getDocumentElement();
            Element filerElement = (Element) root.getElementsByTagName("Filer").item(0);

            //populate filer information that's common across all grants
            GrantInfo filerInfo = populateFilerInfo(filerElement, doc);

            NodeList grantNodes = doc.getElementsByTagName("GrantOrContributionPdDurYrGrp");
            totalGrantCount = grantNodes.getLength();

            for (int i = 0; i < grantNodes.getLength(); i++) {
                Node node = grantNodes.item(i);
                if (isValidNode(node)) {
                    Element grantElement = (Element) node;
                    if (isForeignGrant(grantElement)) {
                        foreignGrantCount++;
                        continue; //skip foreign grants
                    }
                    usGrantCount++;
                    GrantInfo grantInfo = populateGrantInfo(grantElement, doc);
                    grants.add(grantInfo); //add populated grant info to list
                    }
                }

            System.out.println("Total grants: " + totalGrantCount);
            System.out.println("US grants: " + usGrantCount);
            System.out.println("Foreign grants: " + foreignGrantCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return grants;
    }
    private Document setupDocument(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("Setting up XML document...");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        System.out.println("Document setup complete.");
        return dBuilder.parse(xmlFile);
    }

    private boolean isValidNode(Node node) {
        return node != null && node.getNodeType() == Node.ELEMENT_NODE;
    }

    private boolean isForeignGrant(Element grantElement) {
        return (grantElement.getElementsByTagName("RecipientUSAddress").getLength() == 0) ||
                (grantElement.getElementsByTagName("RecipientUSAddress").item(0) == null);
    }

    private GrantInfo populateFilerInfo(Element filerElement, Document doc) {
        System.out.println("Populating filer information...");
        GrantInfo filerInfo = new GrantInfo();
        Element root = doc.getDocumentElement();

        filerInfo.setTaxPeriodEndDate(Date.valueOf(root.getElementsByTagName("TaxPeriodEndDt").item(0).getTextContent()));
        filerInfo.setReturnTypeCode(root.getElementsByTagName("ReturnTypeCd").item(0).getTextContent());
        filerInfo.setTaxPeriodBeginDate(Date.valueOf(root.getElementsByTagName("TaxPeriodBeginDt").item(0).getTextContent()));
        filerInfo.setEin(filerElement.getElementsByTagName("EIN").item(0).getTextContent());
        filerInfo.setFilerName(((Element) filerElement.getElementsByTagName("BusinessName").item(0)).getElementsByTagName("BusinessNameLine1").item(0).getTextContent());
        filerInfo.setFilerStreet(((Element) filerElement.getElementsByTagName("USAddress").item(0)).getElementsByTagName("AddressLine1").item(0).getTextContent());
        filerInfo.setFilerCity(((Element) filerElement.getElementsByTagName("USAddress").item(0)).getElementsByTagName("City").item(0).getTextContent());
        filerInfo.setFilerState(((Element) filerElement.getElementsByTagName("USAddress").item(0)).getElementsByTagName("State").item(0).getTextContent());
        filerInfo.setFilerZip(((Element) filerElement.getElementsByTagName("USAddress").item(0)).getElementsByTagName("ZIPCode").item(0).getTextContent());

        System.out.println("Filer information populated.");
        return filerInfo;
    }

    private GrantInfo populateGrantInfo(Element grantElement, Document doc) {
        System.out.println("Populating grant information...");
        GrantInfo grantInfo = new GrantInfo();

        NodeList businessNameNodes = grantElement.getElementsByTagName("RecipientBusinessName");
        if (businessNameNodes.getLength() > 0 && businessNameNodes.item(0) != null) {
            grantInfo.setRecipientName(getElementText((Element) businessNameNodes.item(0), "BusinessNameLine1"));
        }

        grantInfo.setGrantAmount(Integer.parseInt(grantElement.getElementsByTagName("Amt").item(0).getTextContent()));


        Element usAddress = (Element) grantElement.getElementsByTagName("RecipientUSAddress").item(0);
        if (usAddress != null) {
            grantInfo.setRecipientStreet(usAddress.getElementsByTagName("AddressLine1").item(0).getTextContent());
            grantInfo.setRecipientCity(usAddress.getElementsByTagName("City").item(0).getTextContent());
            grantInfo.setRecipientState(usAddress.getElementsByTagName("State").item(0).getTextContent());
            grantInfo.setRecipientZip(usAddress.getElementsByTagName("ZIPCode").item(0).getTextContent());
        }
            grantInfo.setRecipientRelationship(grantElement.getElementsByTagName("Relationship").item(0).getTextContent());
            grantInfo.setRecipientFoundationStatus(grantElement.getElementsByTagName("FoundationStatus").item(0).getTextContent());
            grantInfo.setGrantPurpose(grantElement.getElementsByTagName("PurposeOfGrantTxt").item(0).getTextContent());

        System.out.println("Grant information populated.");
            return grantInfo;
    }

    private String getElementText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        } else {
            return null;
        }
    }

}
