package code.javafx;

import javafx.beans.property.SimpleStringProperty;

public class PropertyWrapper {

    public SimpleStringProperty keyProperty() {
        if (key == null) {
            key = new SimpleStringProperty(this, "key");
        }
        return key;
    }

    public SimpleStringProperty valueProperty() {
        if (value == null) {
            value = new SimpleStringProperty(this, "value");
        }
        return value;
    }

    public SimpleStringProperty issueProperty() {
        if (issue == null) {
            issue = new SimpleStringProperty(this, "issue");
        }
        return issue;
    }

    PropertyWrapper(String key, String value, String issue) {
        this.key = new SimpleStringProperty(key);
        this.value = new SimpleStringProperty(value);
        this.issue = new SimpleStringProperty(issue);
    }

    public String getKey() {
        return key.get();
    }

    public void setKey(String pkey) {
        key.set(pkey);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String pvalue) {
        value.set(pvalue);
    }

    public String getIssue() {
        return issue.get();
    }

    public void setIssue(String pissue) {
        issue.set(pissue);
    }


    private SimpleStringProperty key;
    private SimpleStringProperty value;
    private SimpleStringProperty issue;

}
