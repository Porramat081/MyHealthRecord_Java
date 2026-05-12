package utils;

import model.HealthRecord;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Exporter {


    public static void exportCSV(ArrayList<HealthRecord> inputArr) throws IOException {
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

            // 2. Save the CSV
            try (PrintWriter pw = new PrintWriter(new FileWriter(fileToSave))) {
                // Write header
                pw.println("id,username,bloodPressure,weight,temperature,note,createdAt,editedAt");
                for(HealthRecord hr : inputArr){
                    pw.println(hr.formatRecordForCSV());
                }

                System.out.println("File saved to: " + fileToSave.getAbsolutePath());
            }
        }
    }
}
