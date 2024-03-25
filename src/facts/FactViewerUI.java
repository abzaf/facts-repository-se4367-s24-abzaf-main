package facts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class FactViewerUI extends JFrame {

	private FactList factList;
	private JLabel factLabel;
	private JButton nextButton;
	private JTextField searchField;
	private JComboBox<String> searchModeComboBox;
	private JButton searchButton;
	private JButton addButton;

	public FactViewerUI() {
		factList = new FactList();
		setupUI();
		loadFacts();
		updateFactLabel(factList.getRandom());
	}

	private void setupUI() {
		setTitle("Fact Viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel factPanel = new JPanel();
		factPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		factLabel = new JLabel();
		factLabel.setFont(new Font("Arial", Font.BOLD, 16));
		factPanel.add(factLabel);

		nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Fact randomFact = factList.getRandom();
				updateFactLabel(randomFact);
			}
		});
		factPanel.add(nextButton);

		addButton = new JButton("Add Fact");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddFactDialog();
			}
		});
		factPanel.add(addButton);

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		searchField = new JTextField(20);
		searchPanel.add(searchField);

		String[] searchModes = {"Author", "Text", "Type", "All"};
		searchModeComboBox = new JComboBox<>(searchModes);
		searchPanel.add(searchModeComboBox);

		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchString = searchField.getText();
				int searchMode = searchModeComboBox.getSelectedIndex();
				FactList searchResult = factList.search(searchString, searchMode);
				if (searchResult.getSize() > 0) {
					Fact randomFact = searchResult.getRandom(); // Get a random fact from search results
					updateFactLabel(randomFact);
				} else {
					JOptionPane.showMessageDialog(null, "No matching facts found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		searchPanel.add(searchButton);

		add(factPanel, BorderLayout.CENTER);
		add(searchPanel, BorderLayout.SOUTH);

		setSize(400, 200);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void loadFacts() {
		Parser parser = new Parser("data/facts.xml");
		factList = parser.getFactList();
	}

	private void updateFactLabel(Fact fact) {
		String factText = "<html><b>Author:</b> " + fact.getAuthor() + "<br/>" +
				"<b>Type:</b> " + fact.getType() + "<br/>" +
				"<b>Fact:</b> " + fact.getText() + "</html>";
		factLabel.setText(factText);
	}

	private void showAddFactDialog() {
		JTextField authorField = new JTextField(10);
		JTextField typeField = new JTextField(10);
		JTextField textField = new JTextField(20);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Author:"));
		panel.add(authorField);
		panel.add(new JLabel("Type:"));
		panel.add(typeField);
		panel.add(new JLabel("Fact:"));
		panel.add(textField);

		int result = JOptionPane.showConfirmDialog(null, panel,
				"Add New Fact", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			String author = authorField.getText();
			String type = typeField.getText();
			String text = textField.getText();

			if (author.isEmpty() || type.isEmpty() || text.isEmpty()) {
				JOptionPane.showMessageDialog(null, "All fields are required. Please enter values for all fields.",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				Fact newFact = new Fact();
				newFact.setAuthor(author);
				newFact.setType(type);
				newFact.setText(text);

				factList.set(newFact);
				updateFactLabel(newFact);
				saveFactsToXML(); // Save the updated fact list to XML
				JOptionPane.showMessageDialog(null, "Fact added successfully!");
			}
		}
	}

	private void saveFactsToXML() {
		factList.saveFactsToXML("data/facts.xml");
	}

	public static void main(String[] args) {
		FactViewerUI viewer = new FactViewerUI();
	}
}
