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
	Image img;

	JButton getPictureButton  = new JButton("Get Picture");

	public static void main(String[] args) {
		new ImageFileFilterImageScale();
	}

	public ImageFileFilterImageScale() {
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel picPanel = new PicturePanel();
		this.add(picPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		getPictureButton.addActionListener(this);
		buttonPanel.add(getPictureButton);
		this.add(buttonPanel, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String file = getImageFile();
		if (file != null) {
			Toolkit kit = Toolkit.getDefaultToolkit();
			img = kit.getImage(file);
			//      img = img.getScaledInstance(300, -1, Image.SCALE_SMOOTH);
			//      this.repaint();
		}

		ImageIcon icon = new ImageIcon(img);

		JLabel labelImage = new JLabel(icon);
		add(labelImage);
		revalidate();
		repaint();
	}

	private String getImageFile() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ImageFilter());
		int result = fc.showOpenDialog(null);
		File file = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			return file.getPath();
		} else
			return null;

	}

	class PicturePanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, this);
		}
	}

}
class ImageFilter extends javax.swing.filechooser.FileFilter {
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
		String name = f.getName();
		if (name.matches(".*((.jpg)|(.gif)|(.png))"))
			return true;
		else
			return false;
	}

	public String getDescription() {
		return "Image files (*.jpg, *.gif, *.png)";
	}
}