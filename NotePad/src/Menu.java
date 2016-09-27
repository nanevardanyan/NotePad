import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicTextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class Menu extends JMenuBar {

    NotepadFrame frame;
    private JFileChooser fileChooser = new JFileChooser();

    private boolean fileIsOpen;


    public Menu(NotepadFrame frame) {
        this.frame = frame;
        fileChooser.setAcceptAllFileFilterUsed(false);

        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open...");
        final JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem saveAsFile = new JMenuItem("Save as...");
        final JMenuItem exitFile = new JMenuItem("Exit");

        fileMenu.add(newFile);
        fileMenu.addSeparator();
        fileMenu.add(openFile);
        fileMenu.addSeparator();
        fileMenu.add(saveFile);
        fileMenu.addSeparator();
        fileMenu.add(saveAsFile);
        fileMenu.addSeparator();
        fileMenu.add(exitFile);
        fileMenu.addSeparator();

        JMenu editMenu = new JMenu("Edit");
        final JMenuItem selectAll = new JMenuItem("Select All");

        editMenu.add(selectAll);

        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFileActionPerformed();
            }
        });

        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileActionPerformed();
            }
        });

        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileActionPerformed();
            }
        });

        exitFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitFileActionPerformed();
            }
        });

        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAllActionPerformed();
            }
        });


        add(fileMenu);
        add(editMenu);
    }

    private void selectAllActionPerformed() {
         Highlighter h = new BasicTextUI.BasicHighlighter();
        try {
            h.addHighlight(0,0, DefaultHighlighter.DefaultPainter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        //select mtd that also highlighted

    }

    private void openFileActionPerformed() {

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(frame);
        if (result != JFileChooser.CANCEL_OPTION) {

            File file = fileChooser.getSelectedFile();
            try {
                String text = readFile(file);
                frame.textArea.setText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String readFile(File file) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                result.append(currentLine + "\n");
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            close(bufferedReader);
        }
//        FileReader fr = null;
//        try {
//            fr = new FileReader(file);
//            char[] buff = new char[(int) file.length()];
//            fr.read(buff);
//            result = new String(buff);
//        } catch (IOException io) {
//            System.out.println("Exception!!!");
//        } finally {
//            close(fr);
//        }
//        return result;
    }


    private void exitFileActionPerformed() {
        int result = -1;
        if (fileIsOpen) askToSave();
        if (result != JOptionPane.CANCEL_OPTION) {
            if (result == JOptionPane.OK_OPTION) {
                save();
            } else {
                System.exit(0);
            }
        }
    }

    private int askToSave() {
        return JOptionPane.showConfirmDialog(frame, "Do you want to save changes ? ", "Notepad",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    private void saveFileActionPerformed() {
        save();
        System.exit(0);
    }

    private void save() {
        int result = fileChooser.showSaveDialog(frame);
        if (result != JFileChooser.CANCEL_OPTION) {
            ObjectOutputStream out = null;
            try {
                File fileName = fileChooser.getSelectedFile();
                out = new ObjectOutputStream(new FileOutputStream(fileName));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Exception: Reading Problem has occurred.");
            } finally {
                try {
                    if (out != null) out.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, "Exception: Cannot close the file.");
                }
            }
        }
    }

    private void newFileActionPerformed() {
        int reply;

        if (!frame.textArea.getText().equals("")) {
            reply = JOptionPane.showConfirmDialog(frame, "Do you want to save changes ?", "NotePad",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (reply == JOptionPane.YES_OPTION) {
                save();
                frame.textArea.setText("");
            } else if (reply == JOptionPane.NO_OPTION) {
                frame.textArea.setText("");
            }

        }


    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
