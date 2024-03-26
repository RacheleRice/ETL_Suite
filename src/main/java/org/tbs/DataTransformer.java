package org.tbs;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DataTransformer {
    GrantInfo grantInfo = new GrantInfo();
//    DataTransformer transformer = new DataTransformer();
    private final Map<String, String> foundationNames;
    private final Map<String, String> patternToStandardizeRecipientName;



    public String cleanFilerName(String filerName) {
        return filerName != null ? filerName.toUpperCase().trim() : null;
    }

    public String cleanRecipientName(String recipientName) {
        return recipientName != null ? recipientName.toLowerCase().trim() : null;
    }

    public DataTransformer() {
        foundationNames = new HashMap<>();
        foundationNames.put("JOHN D AND CATHERINE T MACARTHUR", "JOHN D AND CATHERINE T MACARTHUR FOUNDATION");
        foundationNames.put("JOHN D AND CATHERINE T MACARTHUR FOUNDATION (CONSOLIDATED)", "JOHN D AND CATHERINE T MACARTHUR FOUNDATION");
//add more mappings (pf names that need to be standardized)
        this.patternToStandardizeRecipientName = new HashMap<>();
        loadConfiguration();
    }

    public String standardizeFilerName(String filerName) {
        if (filerName == null) {
            return null;
        }
        String standardized = foundationNames.getOrDefault(filerName.toUpperCase().trim(), filerName.toUpperCase().trim());
        return standardized;
    }
    private void loadConfiguration() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("grant_external_configuration_file.csv");
        if (inputStream == null) {
            throw new RuntimeException("Configuration file not found");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                this.patternToStandardizeRecipientName.put(record.get("Pattern"), record.get("Standardized_Recipient_Name"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading configuration file", e);
        }

    }
    public String getStandardizedRecipientNameByPattern(String originalRecipientName) {
        if (originalRecipientName == null) {
            return null;
        }
        //convert recipient name to lowercase to simulate ILIKE behavior in SQL
        String lowerCaseRecipientName = originalRecipientName.toLowerCase();
        for (Map.Entry<String, String> entry : this.patternToStandardizeRecipientName.entrySet()) {
            if (originalRecipientName.matches(entry.getKey())) {
                return entry.getValue();
            }
        }
        return originalRecipientName;
    }

}
