package gui;

//https://www.mkyong.com/swing/java-swing-jfilechooser-example/

import javax.swing.JFileChooser;

public class FileChooser {
	String filePath;

	// Opens a dialog to select a <type> to open, returns path of file.
	public static String open(String character) {
		
		String filePath = null;
		
		//needs to change to a different value
		JFileChooser jfc = new JFileChooser("D:\\Steam\\steamapps\\common\\SlayTheSpire\\runs\\" + character);
		jfc.setDialogTitle("Select a run file");

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			filePath = jfc.getSelectedFile().getPath();
		}
		return filePath;
	}
	
	// Opens a dialog to select a <type> to open, returns path of file.
//	public static String save(String type, String extension, String name) throws NullPointerException {
//	    boolean acceptable = false;
//	    String filePath = null;
//	    
//	    do {
//	        filePath = null;
//	        File f = null;
//	        
//	        JFileChooser jfc = new JFileChooser("D:\\Steam\\steamapps\\common\\SlayTheSpire\\runs\\");
//	        jfc.setDialogTitle("Save the " + type);
//			jfc.setAcceptAllFileFilterUsed(false);
//			FileNameExtensionFilter filter = new FileNameExtensionFilter(type + " files", extension);
//			jfc.addChoosableFileFilter(filter);
//			jfc.setSelectedFile(new File(name));
//			
//	        if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
//	            filePath = jfc.getSelectedFile().getAbsolutePath();
//	            if (filePath.endsWith(extension)) {
//	            	f = jfc.getSelectedFile();
//	            	
//	            }
//	            else {
//	            	f = new File(filePath + "." + extension);
//	            	filePath = f.getAbsolutePath();
//	            }
//	
//	            if (f.exists()) {
//	                int result = JOptionPane.showConfirmDialog(null, filePath.split("\\\\")[filePath.split("\\\\").length-1] + " exists, overwrite?",
//	                        "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
//	                if (result == JOptionPane.YES_OPTION) {
//	                    acceptable = true;
//	                }
//	            } else {
//	                acceptable = true;
//	            }
//	        } else {
//	        	acceptable = true;
//	        	return null;
//	        }
//	    } while (!acceptable);
//	    
//	    return filePath;
//	    
//	}
}
