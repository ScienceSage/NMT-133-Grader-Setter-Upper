package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import backend.Creator;
import backend.Untar;

public class Window {
	
	private static File files[] = null;
	private static File wordFile = null;
	
	public static void createWindow() {
		
		JFrame frame = new JFrame("Grader Setup");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel textLabel = new JLabel("Select Folder", SwingConstants.CENTER);
		textLabel.setPreferredSize(new Dimension(300, 100));
		frame.getContentPane().add(textLabel, BorderLayout.CENTER);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		frame.add(controlPanel);
		tarSelectButton(frame, controlPanel);
		wordSelectButton(frame, controlPanel);
		runButton(frame, controlPanel);
		
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
	
	private static void tarSelectButton(JFrame frame, JPanel panel) {
		JButton tarSelectButton = new JButton("Select Tar Files");
		
		tarSelectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				files = createFileChooser(frame);
	        }          
		});
		
		panel.add(tarSelectButton);
	}
	
	private static void wordSelectButton(JFrame frame, JPanel panel) {
		JButton wordSelectButton = new JButton("Select the Grading Rubric");
		
		wordSelectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordFile = createFileChooser(frame)[0];
	        }          
		});
		
		panel.add(wordSelectButton);
	}
	
	private static void runButton(JFrame frame, JPanel panel) {
		JButton runButton = new JButton("Run");
		
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Untar(files, wordFile);
			} 
		});
		
		panel.add(runButton);
	}
	
	private static File[] createFileChooser(JFrame frame) {
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(true);
//		FileNameExtensionFilter filter = new FileNameExtensionFilter(".tar.gz", "gz");
//		chooser.setFileFilter(filter);
		try {
			chooser.setCurrentDirectory(new File(Creator.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		// Response to button click
		chooser.showOpenDialog(frame);
//		System.out.println(chooser.getSelectedFile().getName());
		return chooser.getSelectedFiles();
	}
	
}
