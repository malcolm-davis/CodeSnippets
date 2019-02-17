package code.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelSample {

    public static void main(String[] args) {
        System.out.println("Start: ReadExcelSample ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ReadExcelSample test = new ReadExcelSample();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        File file = new File("C:\\dev\\projects\\ecx\\VintonOhDirec.xlsx");

        // Name--
        // Phone Number--
        // Star Code Pattern--
        // Category/Tab--
        // Bool--
        // Common Place?--
        // Bool--
        List<DirectoryEntry>directoryEntries = new ArrayList<DirectoryEntry>();
        List<String>directoryTypes = new ArrayList<String>();
        try {
            FileInputStream excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            int rowCnt=0;
            while (iterator.hasNext()) {
                rowCnt++;
                if(rowCnt<3) {
                    continue ;
                }
                Row currentRow = iterator.next();

                // iterate over cells on the row
                int columnCnt=0;
                String name = null;
                String phoneNumber = null;
                String starCodePattern = null;
                String categoryTab = null;

                Iterator<Cell> columnIterator = currentRow.iterator();
                while (columnIterator.hasNext()) {
                    Cell currentCell = columnIterator.next();

                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    String value = "";
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        value = currentCell.getStringCellValue();
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        value = "" + currentCell.getNumericCellValue();
                    }

                    columnCnt++;
                    switch(columnCnt) {
                        case 1:
                            name = value;
                            break;
                        case 2:
                            phoneNumber = value;
                            break;
                        case 3:
                            starCodePattern = value;
                            break;
                        case 4:
                            categoryTab = value;
                            if( !directoryTypes.contains(categoryTab) ) {
                                directoryTypes.add(categoryTab);
                            }
                            break;
                    }
                }

                if(StringUtils.isNotBlank(name)) {
                    directoryEntries.add(new DirectoryEntry(name, phoneNumber, starCodePattern, categoryTab));
                }
            }

            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String string : directoryTypes) {
            System.out.println(string);
        }

        for(DirectoryEntry entry : directoryEntries) {
            System.out.println(entry);
        }
    }

    class DirectoryEntry {
        private final String m_name;
        private final String m_phoneNumber;
        private final String m_starCodePattern;
        private final String m_categoryTab;

        public String getName() {
            return m_name;
        }

        public String getPhoneNumber() {
            return m_phoneNumber;
        }

        public String getStarCodePattern() {
            return m_starCodePattern;
        }

        public String getCategoryTab() {
            return m_categoryTab;
        }

        @Override
        public String toString() {
            return "DirectoryEntry [name=" + m_name
                    + ", phoneNumber=" + m_phoneNumber
                    + ", starCodePattern=" + m_starCodePattern
                    + ", categoryTab=" + m_categoryTab + "]";
        }

        DirectoryEntry(String name, String phoneNumber,  String starCodePattern, String categoryTab) {
            m_name = name;
            m_phoneNumber = phoneNumber;
            m_starCodePattern = starCodePattern;
            m_categoryTab = categoryTab;

        }
    }

}
