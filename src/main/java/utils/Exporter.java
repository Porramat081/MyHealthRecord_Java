package utils;

import model.HealthRecord;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Exporter {
    /* Utility class for exporting health records to a CSV file. */
    public static void exportCSV(ArrayList<HealthRecord> inputArr) throws IOException {
        /*
          Opens a save-file dialog, then writes all records as CSV rows with a header line.
          Appends .csv if the user omits it.
        */
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Ensure .csv extension
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                fileToSave = new File(filePath + ".csv");
            }

            // Save the CSV
            try (PrintWriter pw = new PrintWriter(new FileWriter(fileToSave))) {
                // Write header
                pw.println("id,username,bloodPressure,weight,temperature,note,createdAt,editedAt");
                // Write CSV row
                for(HealthRecord hr : inputArr){
                    pw.println(hr.formatRecordForCSV());
                }
            }
        }
    }
}
