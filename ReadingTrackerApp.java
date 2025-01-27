import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class ReadingTrackerApp extends JFrame {
    private DefaultTableModel tableModel;
    private ArrayList<Book> books = new ArrayList<>();

    public ReadingTrackerApp() {
        setTitle("Reading Tracker App");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(60, 80, 120)); 

        JLabel titleLabel = new JLabel("Reading Tracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10)); 

        JLabel imageLabel = new JLabel();
        ImageIcon scaledIcon = new ImageIcon(
                new ImageIcon("images/reads.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        imageLabel.setIcon(scaledIcon);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20)); // Padding

        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(imageLabel, BorderLayout.EAST);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Table Section
        String[] columnNames = { "Title", "Author", "Total Chapters", "Cover Image", "Actions" };
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setFont(new Font("Arial", Font.PLAIN, 14)); 
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE); 
                }
                return c;
            }
        };

        table.setRowHeight(120); 
        table.setIntercellSpacing(new Dimension(15, 15));

        table.getColumnModel().getColumn(4).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.setBackground(Color.WHITE);

            JButton editButton = new JButton("Edit");
            editButton.setBackground(new Color(102, 178, 255)); 
            editButton.setForeground(Color.WHITE);
            editButton.setPreferredSize(new Dimension(80, 30)); 
            editButton.addActionListener(e -> openEditBookDialog(row));

            JButton deleteButton = new JButton("Delete");
            deleteButton.setBackground(new Color(255, 102, 102)); 
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setPreferredSize(new Dimension(120, 30)); 
            deleteButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    books.remove(row);
                    updateTable();
                }
            });

            panel.add(editButton);
            panel.add(deleteButton);

            return panel;
        });

        table.getColumnModel().getColumn(3).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            if (value instanceof ImageIcon) {
                JLabel label = new JLabel((ImageIcon) value);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
            return new JLabel();
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton addButton = new JButton("Add Book");
        addButton.setBackground(new Color(102, 204, 102)); 
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> openAddBookDialog());

        JButton clearButton = new JButton("Clear All");
        clearButton.setBackground(new Color(255, 153, 51));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> {
            books.clear();
            updateTable();
            JOptionPane.showMessageDialog(this, "Book deleted.");
        });

        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void openAddBookDialog() {
        JDialog dialog = new JDialog(this, "Edit Book", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(20);

        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField(20);

        JLabel totalChaptersLabel = new JLabel("Total Chapters:");
        JTextField totalChaptersField = new JTextField(20);

        JLabel imageLabel = new JLabel("Book Image:");
        JButton chooseImageButton = new JButton("Choose Image");
        JLabel imagePathLabel = new JLabel("(No file chosen)");

        JLabel pdfLabel = new JLabel("Book PDF:");
        JButton choosePdfButton = new JButton("Choose PDF");
        JLabel pdfPathLabel = new JLabel("(No file chosen)");

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        chooseImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(
                    new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "jpeg"));
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                imagePathLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        choosePdfButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF files", "pdf"));
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                pdfPathLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String totalChaptersStr = totalChaptersField.getText();
            String imagePath = imagePathLabel.getText();
            String pdfPath = pdfPathLabel.getText();

            if (title.isEmpty() || author.isEmpty() || totalChaptersStr.isEmpty()
                    || imagePath.equals("(No file chosen)") || pdfPath.equals("(No file chosen)")) {
                JOptionPane.showMessageDialog(dialog, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int totalChapters = Integer.parseInt(totalChaptersStr);
                books.add(new Book(title, author, totalChapters, imagePath, pdfPath));
                updateTable();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Total Chapters must be a number!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(titleLabel, gbc);

        gbc.gridx = 1;
        dialog.add(titleField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        dialog.add(authorLabel, gbc);

        gbc.gridx = 1;
        dialog.add(authorField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        dialog.add(totalChaptersLabel, gbc);

        gbc.gridx = 1;
        dialog.add(totalChaptersField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        dialog.add(imageLabel, gbc);

        gbc.gridx = 1;
        dialog.add(chooseImageButton, gbc);

        gbc.gridy++;
        dialog.add(imagePathLabel, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        dialog.add(pdfLabel, gbc);

        gbc.gridx = 1;
        dialog.add(choosePdfButton, gbc);

        gbc.gridy++;
        dialog.add(pdfPathLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, gbc);

        dialog.setVisible(true);
    }

    private void openEditBookDialog(int rowIndex) {
        Book book = books.get(rowIndex);
        openAddBookDialog(); 
        
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Book book : books) {
            tableModel.addRow(new Object[] { book.getTitle(), book.getAuthor(), book.getTotalChapters(),
                    new ImageIcon(book.getImagePath()), "" });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReadingTrackerApp::new);
    }
}

class Book {
    private String title;
    private String author;
    private int totalChapters;
    private String imagePath;
    private String pdfPath;

    public Book(String title, String author, int totalChapters, String imagePath, String pdfPath) {
        this.title = title;
        this.author = author;
        this.totalChapters = totalChapters;
        this.imagePath = imagePath;
        this.pdfPath = pdfPath;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalChapters() {
        return totalChapters;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getPdfPath() {
        return pdfPath;
    }
}
