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
    private final Map<String, String> patternToStandardizeGrantPurpose;



    public String cleanFilerName(String filerName) {
        return filerName != null ? filerName.toUpperCase().trim() : null;
    }

    public String cleanRecipientName(String recipientName) {
        return recipientName != null ? recipientName.toLowerCase().trim() : null;
    }

    public DataTransformer() {
        //initialize the foundationNames map with mappings of non-standardized foundation names to standardized foundation names
        foundationNames = new HashMap<>();
        foundationNames.put("JOHN D AND CATHERINE T MACARTHUR", "JOHN D AND CATHERINE T MACARTHUR FOUNDATION");
        foundationNames.put("JOHN D AND CATHERINE T MACARTHUR FOUNDATION (CONSOLIDATED)", "JOHN D AND CATHERINE T MACARTHUR FOUNDATION");
//add more mappings (pf names that need to be standardized)
        this.patternToStandardizeRecipientName = new HashMap<>();
        this.patternToStandardizeGrantPurpose = new HashMap<>();
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
    private String convertSQLPatternToJavaRegex(String sqlPattern) {
        //convert SQL pattern to Java regex pattern
        String regexPattern = sqlPattern
                .replace(".", "\\.")
                .replace("?", "\\?")
                .replace("*", "\\*")
                .replace("+", "\\+")
                .replace("|", "\\|")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("^", "\\^")
                .replace("$", "\\$");
        //replace SQL '%' with Java '.*' and SQL '_' with Java '.'
            regexPattern = regexPattern
                .replace("%", ".*")
                .replace("_", ".");
        //Since SQL patterns are case-insensitive, add case-insensitive flag to Java regex
        regexPattern = "(?i)" + regexPattern;

        return regexPattern;
    }
    public String getStandardizedRecipientNameByPattern(String recipientName) {
        if (recipientName == null) {
            return null;
        }
        //convert recipient name to lowercase to simulate ILIKE behavior in SQL
        String cleanedRecipientName = recipientName.trim().toLowerCase();
        for (Map.Entry<String, String> entry : this.patternToStandardizeRecipientName.entrySet()) {
            // Your patterns from the CSV might need to be adapted if they use SQL '%', '_',
            // here assumed direct match or simple regex can be applied
            String patternRegex = convertSQLPatternToJavaRegex(entry.getKey().toLowerCase());
            if (cleanedRecipientName.matches(patternRegex)) {
                return entry.getValue(); //return standardized name from config file
            }
        }
        return recipientName; //return original name if no match found
    }
    public String getStandardizedGrantPurposeNameByPattern(String grantPurpose) {
        if (grantPurpose == null) {
            return null;
        }
        //convert grant purpose to lowercase to simulate ILIKE behavior in SQL
        String cleanedGrantPurpose = grantPurpose.trim().toLowerCase();
        for (Map.Entry<String, String> entry : this.patternToStandardizeGrantPurpose.entrySet()) {
            // Your patterns from the CSV might need to be adapted if they use SQL '%', '_',
            // here assumed direct match or simple regex can be applied
            String patternRegex = convertSQLPatternToJavaRegex(entry.getKey().toLowerCase());
            if (cleanedGrantPurpose.matches(patternRegex)) {
                return entry.getValue(); //return standardized name from config file
            }
        }
        return grantPurpose; //return original name if no match found
    }

}
