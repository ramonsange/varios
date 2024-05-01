package com.mycompany.renamepictures;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RenamePictures {

    public static void main(String[] args) {
        // Directory containing the images
        String directoryPath = "C:\\Users\\Ramon\\Downloads\\caca";

        // Get all files in the directory
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            // Format for renaming
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            SimpleDateFormat newFormat = new SimpleDateFormat("dd_MM_yyyy_HHmm");

            for (File file : files) {
                if (file.isFile()) {
                    String oldName = file.getName();
                    String[] parts = oldName.split("_");
                    
                    // Extracting date and time from the original file name
                    String dateString = parts[1];
                    String timeString = parts[2].split("\\.")[0]; // remove file extension
                    
                    try {
                        Date date = originalFormat.parse(dateString + "_" + timeString);
                        String newFileName = newFormat.format(date) + ".jpg";
                        
                        // Renaming the file
                        File newFile = new File(directoryPath + "/" + newFileName);
                        file.renameTo(newFile);
                        System.out.println("Renamed: " + oldName + " to " + newFileName);
                    } catch (Exception e) {
                        System.out.println("Error renaming file: " + oldName);
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Directory does not exist or is not accessible.");
        }
    }
}
