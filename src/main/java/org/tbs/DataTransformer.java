package org.tbs;

public class DataTransformer {

    DataTransformer transformer = new DataTransformer();
    GrantInfo grantInfo = new GrantInfo();

    public String cleanFilerName(String filerName) {
        return filerName != null ? filerName.toUpperCase().trim() : null;
    }

    public String cleanRecipientName(String recipientName) {
        return recipientName != null ? recipientName.toLowerCase().trim() : null;
    }

}
