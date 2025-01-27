import javax.swing.*;
import java.awt.*;

public class BookForm extends JDialog {
    public BookForm(BookManager bookManager) {
        setTitle("Add Book");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JLabel chaptersLabel = new JLabel("Total Chapters:");
        JTextField chaptersField = new JTextField();

        JLabel imageLabel = new JLabel("Cover Image:");
        JButton imageButton = new JButton("Choose Image");
        JLabel imagePathLabel = new JLabel("(No file chosen)");

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                imagePathLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        saveButton.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                int totalChapters = Integer.parseInt(chaptersField.getText());
                String imagePath = imagePathLabel.getText();
                bookManager.addBook(new Book(title, author, totalChapters, imagePath, ""));
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Chapters must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        add(titleLabel);
        add(titleField);
        add(authorLabel);
        add(authorField);
        add(chaptersLabel);
        add(chaptersField);
        add(imageLabel);
        add(imageButton);
        add(saveButton);
        add(cancelButton);

        setVisible(true);
    }
}