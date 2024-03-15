package org.tbs;

import java.util.HashMap;
import java.util.Map;

public class DataTransformer {
    GrantInfo grantInfo = new GrantInfo();
//    DataTransformer transformer = new DataTransformer();
    private final Map<String, String> foundationNames;


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
    }

    public String standardizeFilerName(String filerName) {
        if (filerName == null) {
            return null;
        }
        String standardized = foundationNames.getOrDefault(filerName.toUpperCase().trim(), filerName.toUpperCase().trim());
        return standardized;
    }

}
