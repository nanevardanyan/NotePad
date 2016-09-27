import javax.swing.*;
import java.awt.*;


public class NotepadFrame extends JFrame {

    Menu notepadMenu;
    public TextArea textArea;
    private String fileName; // the title of the frame.

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public NotepadFrame(){

        notepadMenu = new Menu(this);
        textArea = new TextArea();

        add(notepadMenu, BorderLayout.NORTH);
        add(textArea, BorderLayout.CENTER);

        setSize(500, 400);
        setLocation(100, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        new NotepadFrame();
    }
}
