import javax.swing.*;
import java.io.File;
import java.util.*;

public class Main{
    static JFrame f;
    public static void main(String[] args){
        f = new JFrame("Scouting Data Utility");

        JPanel j = new JPanel();
        f.add(j);

        ArrayList<String> directories = new ArrayList<String>();
        for (File root : File.listRoots()) {
            directories.add(root.getAbsolutePath());
        }
        JComboBox driveSelector = new JComboBox(directories.toArray());
        j.add(driveSelector);

        f.setSize(400, 400);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
