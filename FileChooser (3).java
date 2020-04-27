import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


/*
 * Autor: Arkadzi Zaleuski 250929
 * Data: 09-12-2019
 * 
 * 
*/


public class FileChooser extends JFileChooser  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileChooser(String message) {
		super(FileSystemView.getFileSystemView().getHomeDirectory());
		
		this.setDialogTitle(message);
		this.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	//Tworzenie filtru do ułatwiena poszukiwania pliku.
	  FileNameExtensionFilter filter = new FileNameExtensionFilter("Text and binary files","txt","bin");
	  this.addChoosableFileFilter(filter);
	 
	}
	
	public String getPath() {
		String path = "";
		int returnValue = this.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			path = this.getSelectedFile().getPath();
		}
		return path;
	}

}
