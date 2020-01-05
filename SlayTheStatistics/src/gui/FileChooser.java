package gui;

//https://www.mkyong.com/swing/java-swing-jfilechooser-example/

import javax.swing.JFileChooser;

public class FileChooser {

	// Opens a dialog to select a <type> to open, returns path of file.
	public static String open(String dir, String title, boolean directory) {
		
		String filePath = null;
		
		//needs to change to a different value
		JFileChooser jfc = new JFileChooser(dir);
		jfc.setDialogTitle(title);
		if (directory) {
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			filePath = jfc.getSelectedFile().getPath();
		}
		return filePath;
	}
}
