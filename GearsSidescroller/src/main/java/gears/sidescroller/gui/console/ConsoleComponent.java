package gears.sidescroller.gui.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class ConsoleComponent extends JComponent implements ActionListener, OutputWriter {
    private final JTextArea outputDisplay;
    private final JScrollPane scroll;
    private final JTextField input;
    private final CommandParser parser;
    
    public ConsoleComponent(CommandParser parser){
        setLayout(new BorderLayout());
        style(this);
        
        this.parser = parser;
        
        outputDisplay = new JTextArea();
        outputDisplay.setColumns(80);
        outputDisplay.setRows(20);
        style(outputDisplay);
        
        scroll = new JScrollPane(outputDisplay);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        style(scroll);
        add(scroll, BorderLayout.CENTER);
        
        input = new JTextField();
        input.setColumns(80);
        input.addActionListener(this);
        style(input);
        add(input, BorderLayout.PAGE_END);
    }
    
    private void style(JComponent component){
        component.setBackground(Color.black);
        component.setForeground(Color.green);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            parser.parse(input.getText());
        } catch(InvalidCommandException | IllegalArgumentException ex){
            writeLine(ex.getMessage());
        }
        input.setText("");
        repaint();
    }

    @Override
    public void write(String message) {
        outputDisplay.append(message);
        repaint();
    }

    @Override
    public void writeLine(String message) {
        write(message + '\n');
    }
}
