import java.io.*;
import java.util.Scanner;

/**
 * The CreateNotes class provides functionality to create and manage notes in a specified directory and file.
 *
 * <p>This class offers the following features:
 * <ul>
 *     <li>Specify the directory and file for note storage.</li>
 *     <li>Create a new file or overwrite an existing one.</li>
 *     <li>Add user-entered notes to the file.</li>
 *     <li>View the contents of the file.</li>
 * </ul>
 *
 * @author Million Eyassu
 * @version 1.0
 * @since 2023-11-03
 */
public class CreateNotes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("Welcome to the Note Creator!");

        // Get and validate directory name from the user
        String strDirectoryName;
        while (true) {
            System.out.print("Enter the directory name: ");
            strDirectoryName = scanner.nextLine().trim();
            if (!strDirectoryName.isEmpty()) {
                File directory = new File(strDirectoryName);
                if (!directory.exists() || !directory.isDirectory()) {
                    System.out.println("The specified directory does not exist. Please enter a valid directory name.");
                } else {
                    break;
                }
            }
            System.out.println("Please enter a valid directory name.");
        }

        // Get and validate file name with a .txt extension from the user
        String strFileName;
        while (true) {
            System.out.print("Enter the file name with a .txt extension: ");
            strFileName = scanner.nextLine().trim();
            if (!strFileName.isEmpty() && strFileName.endsWith(".txt")) {
                break;
            }
            System.out.println("Please enter a valid file name with a .txt extension.");
        }

        // Create File objects for the specified directory and file
      //  File directory = new File(strDirectoryName);
        File file = new File(strDirectoryName, strFileName);

        // Check if the file already exists
        if (file.exists()) {
            while (true) {
                System.out.println("File already exists.");
                System.out.print("Do you want to overwrite the file? (yes/no): ");
                String strOverwriteChoice = scanner.nextLine().trim().toLowerCase();
                if (strOverwriteChoice.equals("yes")) {
                    // User wants to overwrite the file
                    if (!file.delete()) {
                        System.out.println("File deletion failed. Exiting.");
                        return;
                    }
                    break;
                } else if (strOverwriteChoice.equals("no")) {
                    // User doesn't want to overwrite the file
                    System.out.println("File not overwritten. Exiting.");
                    return;
                } else {
                    System.out.println("Please enter 'yes' or 'no'.");
                }
            }
        }

        // Create a new file
        try {
            if (file.createNewFile()) {
                System.out.println("File created.");
            } else {
                System.out.println("File creation failed.");
                return;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
            return;
        }

        // Prompt the user to enter notes
        System.out.println("Enter your notes. Press Enter twice to finish:");

        // Write user's notes to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            String strInput;
            while (true) {
                strInput = ReadStringFromUser();
                if (strInput.isEmpty()) {
                    break;
                }
                writer.println(strInput);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
            return;
        }

        // Display the contents of the file
        System.out.println("Contents of the file:");

        // Read and print the contents of the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String strLine;
            while ((strLine = reader.readLine()) != null) {
                System.out.println(strLine);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file.");
            e.printStackTrace();
        }
    }

    
    /**
     * Reads a string from the user via the console input.
     *
     * @return The string entered by the user.
     */
    public static String ReadStringFromUser() {
        String strBuffer = "";

        try {
            BufferedReader burInput = new BufferedReader(new InputStreamReader(System.in));
            strBuffer = burInput.readLine();
        } catch (Exception excError) {
            System.out.println(excError.toString());
        }

        return strBuffer;
    }
}
