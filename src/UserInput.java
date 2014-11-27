package Sequescence;

import java.io.*;

public class UserInput {

    // Read a string from the keyboard
    public static String readString() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1);

    // Declare and initialize the string
        String string = "";

        try {
            string = br.readLine();
        }
        catch (IOException ex) {
            System.out.println(ex);
        }

        return string;
    }

    // Read an int value from the keyboard
    public static int readInt() {
        return (int)(Integer.parseInt(readString()));
    }

    // Read a double value from the keyboard
    public static double readDouble() {
        return Double.parseDouble(readString());
    }
}
