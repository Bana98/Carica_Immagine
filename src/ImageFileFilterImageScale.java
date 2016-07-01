import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageFileFilterImageScale extends JFrame implements ActionListener 
{
	Image img;  //l'immagine che voglio visualizzare

	JButton getPictureButton  = new JButton("Get Picture");  //bottone che quando viene cliccato apre un fileChooser

	public static void main(String[] args) {  //MAIN
		new ImageFileFilterImageScale();
	}

	public ImageFileFilterImageScale() //costruttore
	{
		this.setSize(320, 320);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SPONGI");

		JPanel picPanel = new PicturePanel();  //creazione picture panel (istanza della classe sotto)
		this.add(picPanel, BorderLayout.CENTER);  //aggiunge al frame il picture panel. Usa il BorderLayout (io ho sempre usato il FormatLayout, lo creo io il Layout)

		JPanel buttonPanel = new JPanel();  //crea il "contenitore"
		getPictureButton.addActionListener(this);   //aggiunge l'evento al bottone creato sopra
		buttonPanel.add(getPictureButton);   //al contenitore viene associato il bottone, quindi ora il bottone sarebbe buttonPanel
		this.add(buttonPanel, BorderLayout.SOUTH);  //aggiunge alla finestra il bottone, a sud del Layout scelto

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) //evento quando premuto il bottone
	{
		String file = getImageFile();  //in una stringa mette il file immagine che si sceglierà (è il metodo sotto)
		if (file != null) //se il file è diverso da null
		{
			Toolkit kit = Toolkit.getDefaultToolkit();  //serve per di caricare l'immagine
			img = kit.getImage(file);  //la mia immagine sarà data dal kit + il get dell'immagine selezionata (il file selezionato)
		}

		ImageIcon icon = new ImageIcon(img);  //crea un ImageIcon nel quale mette l'immagine
		JLabel labelImage = new JLabel(icon); //crea un label nel quale mette l'icona (nell'icona ci avavo messo l'immagine in questione)
		add(labelImage);   //aggiunge al frame il label 
		revalidate();  //con questi due metodi della classe JFrame fa il refresh del frame, sennò l'immagine non appare (revalidate e repaint)
		repaint();
	}

	private String getImageFile()  //metodo per scegliere l'immagine che si vuole visualizzare
	{
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ImageFilter());  //The file filter is used by the file chooser to filter out files from the user's view. ImageFilter è la classe sotto, dice quale tipo di file devi scegliere
		int result = fc.showOpenDialog(null);  //mostra la finestra che serve per aprire il file selezionato
		File file = null;  //dichiara un file 
		if (result == JFileChooser.APPROVE_OPTION) //approva la scelta
		{
			file = fc.getSelectedFile();  //nel file ci mette il file selezionato dall'utente
			return file.getPath();  //ritorna il percorso del file (per aprirlo presumo)
		} else
			return null;

	}

	class PicturePanel extends JPanel //crea la classe usata sopra che serve effettivamente per stampare/disegnare il tutto nella finestra
	{
		public void paint(Graphics g)
		{
			g.drawImage(img, 0, 0, this);
		}
	}

}
	class ImageFilter extends javax.swing.filechooser.FileFilter 
	{
		public boolean accept(File f) 
		{
			if (f.isDirectory())
				return true;
			String name = f.getName(); //name è il nome del file selezionato dall'utente
			if (name.matches(".*((.jpg)|(.gif)|(.png))"))  //il file selezionato deve avere queste estensioni
				return true;  //se il nome del file + l'estensione sono ok, allora ritorna "il file"
			else
				return false;  //sennò non ritorna un fico secco in un bel tubo di niente
		}
	
		public String getDescription() 
		{
			return "Image files (*.jpg, *.gif, *.png)";
		}
	}