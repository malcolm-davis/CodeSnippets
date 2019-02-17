package code.javafx;

import javafx.beans.property.SimpleStringProperty;

public class DirectoryEntry {
    @SuppressWarnings("hiding")
    DirectoryEntry(String name, String phoneNumber,  String starCodePattern, String categoryTab) {
        this.name = new SimpleStringProperty(name);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.starCodePattern = new SimpleStringProperty(starCodePattern);
        this.categoryTab = new SimpleStringProperty(categoryTab);
    }

    public String getName() {
        return name.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getStarCodePattern() {
        return starCodePattern.get();
    }

    public String getCategoryTab() {
        return categoryTab.get();
    }

    @Override
    public String toString() {
        return "DirectoryEntry [name=" + getName()
        + ", phoneNumber=" + getPhoneNumber()
        + ", starCodePattern=" + getStarCodePattern()
        + ", categoryTab=" + getCategoryTab() + "]";
    }


    private final SimpleStringProperty  name;
    private final SimpleStringProperty  phoneNumber;
    private final SimpleStringProperty  starCodePattern;
    private final SimpleStringProperty  categoryTab;
}
