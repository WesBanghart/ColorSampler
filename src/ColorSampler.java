import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedHashMap;

public class ColorSampler {

    static JPanel display;
    protected JList listColor;
    JButton save;
    JButton reset;

    JFrame jframe;

    JButton minusRed;
    JButton minusGreen;
    JButton minusBlue;

    JButton plusRed;
    JButton plusGreen;
    JButton plusBlue;

    JLabel red;
    JLabel green;
    JLabel blue;

    JTextField tfred;
    JTextField tfgreen;
    JTextField tfblue;

    LinkedHashMap<String, int[]> colors = new LinkedHashMap<>();

    String title;

    ColorSampler(String name) throws IOException {
        title = name;
        jframe = new JFrame(name);
        populateColorMap(false);
        jframe.addWindowListener(new WindowDestroyer());
        addComponenetsToPane();
        listColor.addListSelectionListener(new ColorHandler());

        //Sets the default Color List index to 10 (this avoids a null pointer exception)
        listColor.setSelectedIndex(10);

        plusRed.addActionListener(new ActionHandler());
        minusRed.addActionListener(new ActionHandler());
        plusGreen.addActionListener(new ActionHandler());
        minusGreen.addActionListener(new ActionHandler());
        plusBlue.addActionListener(new ActionHandler());
        minusBlue.addActionListener(new ActionHandler());
        save.addActionListener(new ActionHandler());
        reset.addActionListener(new ActionHandler());

        jframe.setVisible(true);


    }

    private class WindowDestroyer extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    private class ColorHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if(e.getSource() == listColor) {
                if(!e.getValueIsAdjusting()) {
                    int i = listColor.getSelectedIndex();
                    String s = listColor.getSelectedValue().toString();
                    System.out.println("Position " + i + " Selected: " + s);
                    //TODO updata with save() option (setRGBValue cannot be hardcodded)
                    switch (i) {
                        case 0:
                            setRGBValue(colors.get("Red")[0], colors.get("Red")[1], colors.get("Red")[2]);
                            break;
                        case 1:
                            setRGBValue(colors.get("Green")[0], colors.get("Green")[1], colors.get("Green")[2]);
                            break;
                        case 2:
                            setRGBValue(colors.get("Blue")[0], colors.get("Blue")[1], colors.get("Blue")[2]);
                            break;
                        case 3:
                            setRGBValue(colors.get("Yellow")[0], colors.get("Yellow")[1], colors.get("Yellow")[2]);
                            break;
                        case 4:
                            setRGBValue(colors.get("Cyan")[0], colors.get("Cyan")[1], colors.get("Cyan")[2]);
                            break;
                        case 5:
                            setRGBValue(colors.get("Magenta")[0], colors.get("Magenta")[1], colors.get("Magenta")[2]);
                            break;
                        case 6:
                            setRGBValue(colors.get("Orange")[0], colors.get("Orange")[1], colors.get("Orange")[2]);
                            break;
                        case 7:
                            setRGBValue(colors.get("Pink")[0], colors.get("Pink")[1], colors.get("Pink")[2]);
                            break;
                        case 8:
                            setRGBValue(colors.get("Grey")[0], colors.get("Grey")[1], colors.get("Grey")[2]);
                            break;
                        case 9:
                            setRGBValue(colors.get("Black")[0], colors.get("Black")[1], colors.get("Black")[2]);
                            break;
                        case 10:
                            setRGBValue(colors.get("White")[0], colors.get("White")[1], colors.get("White")[2]);
                            break;
                        default:
                            setRGBValue(colors.get("White")[0], colors.get("White")[1], colors.get("White")[2]);
                            break;
                    }
                }
            }
        }
    }

    private class ActionHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            jframe.setTitle(title + "*");
            if(e.getSource() == minusRed) {
                if(Integer.parseInt(tfred.getText()) > 0) {
                    int i = Integer.parseInt(tfred.getText());
                    i-=5;
                    tfred.setText(String.valueOf(i));
                    setRGBValue(i ,Integer.parseInt(tfgreen.getText()), Integer.parseInt(tfblue.getText()));
                }
            }
            if(e.getSource() == plusRed) {
                if(Integer.parseInt(tfred.getText()) < 255) {
                    int i = Integer.parseInt(tfred.getText());
                    i+=5;
                    tfred.setText(String.valueOf(i));
                    setRGBValue(i, Integer.parseInt(tfgreen.getText()), Integer.parseInt(tfblue.getText()));
                }
            }
            if(e.getSource() == minusGreen) {
                if(Integer.parseInt(tfgreen.getText()) > 0) {
                    int i = Integer.parseInt(tfgreen.getText());
                    i-=5;
                    tfgreen.setText(String.valueOf(i));
                    setRGBValue(Integer.parseInt(tfred.getText()) ,i, Integer.parseInt(tfblue.getText()));
                }
            }
            if(e.getSource() == plusGreen) {
                if(Integer.parseInt(tfgreen.getText()) < 255) {
                    int i = Integer.parseInt(tfgreen.getText());
                    i+=5;
                    tfgreen.setText(String.valueOf(i));
                    setRGBValue(Integer.parseInt(tfred.getText()) ,i, Integer.parseInt(tfblue.getText()));
                }
            }
            if(e.getSource() == minusBlue) {
                if(Integer.parseInt(tfblue.getText()) > 0) {
                    int i = Integer.parseInt(tfblue.getText());
                    i-=5;
                    tfblue.setText(String.valueOf(i));
                    setRGBValue(Integer.parseInt(tfred.getText()) ,Integer.parseInt(tfgreen.getText()), i);
                }
            }
            if(e.getSource() == plusBlue) {
                if(Integer.parseInt(tfblue.getText()) < 255) {
                    int i = Integer.parseInt(tfblue.getText());
                    i+=5;
                    tfblue.setText(String.valueOf(i));
                    setRGBValue(Integer.parseInt(tfred.getText()) ,Integer.parseInt(tfgreen.getText()), i);
                }
            }
            if(e.getSource() == save) {
                jframe.setTitle(title);
                //Update Hashmap data
                colors.get(listColor.getSelectedValue().toString())[0] = Integer.parseInt(tfred.getText());
                colors.get(listColor.getSelectedValue().toString())[1] = Integer.parseInt(tfgreen.getText());
                colors.get(listColor.getSelectedValue().toString())[2] = Integer.parseInt(tfblue.getText());
                //Write to output file with new data
                try {
                    writeNewValues();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(e.getSource() == reset) {
                jframe.setTitle(title);
                try {
                    populateColorMap(true);
                    writeNewValues();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //listColor.getSelectedIndex();
                try {
                    setRGBValue(colors.get(listColor.getSelectedValue())[0],colors.get(listColor.getSelectedValue())[1],colors.get(listColor.getSelectedValue())[2]);
                } catch (NullPointerException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void writeNewValues() throws IOException{
        FileOutputStream outputStream = new FileOutputStream("src/output.txt");
        PrintWriter writer = new PrintWriter(outputStream);
        for(String key: colors.keySet()) {
            writer.println(key + " " + colors.get(key)[0] + " " + colors.get(key)[1] + " " + colors.get(key)[2]);
        }
        writer.flush();
        outputStream.close();
    }

    //Implement File I/O to populate list
    private void populateColorMap(boolean reset) throws IOException{
        FileInputStream inputStream;
        if(reset) {
            inputStream = new FileInputStream("src/input.txt");
        } else {
            inputStream = new FileInputStream("src/output.txt");
        }
        InputStreamReader reader = new InputStreamReader(inputStream);
        StreamTokenizer tokenizer = new StreamTokenizer(reader);


        while(tokenizer.nextToken() != tokenizer.TT_EOF) {
            String s;
            int[] a = new int[3];

            s = tokenizer.sval;
            tokenizer.nextToken();
            a[0] = (int) tokenizer.nval;
            tokenizer.nextToken();
            a[1] = (int) tokenizer.nval;
            tokenizer.nextToken();
            a[2] = (int) tokenizer.nval;
            System.out.println(s + " " + a[0] + " " + a[1] + " " + a[2] );
            colors.put(s, a);
        }
        inputStream.close();
    }

    private void addComponenetsToPane() {


         save = new JButton("Save");
         reset = new JButton("Reset");

         minusRed = new JButton("-");
         minusGreen = new JButton("-");
         minusBlue = new JButton("-");

         plusRed = new JButton("+");
         plusGreen = new JButton("+");
         plusBlue = new JButton("+");

         red = new JLabel( "Red");
         green = new JLabel("Green");
         blue = new JLabel("Blue");

         tfred = new JTextField();
         tfgreen = new JTextField();
         tfblue = new JTextField();
         display = new JPanel();

         listColor = new JList();

         JPanel left = new JPanel();
         JPanel midLeft = new JPanel();
         JPanel bottomLeft = new JPanel();

        jframe.setSize(300, 300);

        left.setMinimumSize(new Dimension(150,300));

        jframe.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = .5;
        c.weighty = 1;
        c.insets = new Insets(10,0,10,10);
        c.fill = GridBagConstraints.BOTH;
        jframe.add(new JScrollPane(listColor), c);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .5;
        jframe.add(left, c);

        left.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = .7;
        c.insets = new Insets(0,10,0,0);

        left.add(display, c);

        c.insets = new Insets(10,10,0,0);
        c.weighty = .2;
        c.gridy = 1;
        left.add(midLeft, c);

        //MIDLEFT SUBCOMP

        midLeft.setLayout(new GridLayout(3,4));
        midLeft.add(red);
        midLeft.add(tfred);
        midLeft.add(minusRed);
        midLeft.add(plusRed);

        midLeft.add(green);
        midLeft.add(tfgreen);
        midLeft.add(minusGreen);
        midLeft.add(plusGreen);

        midLeft.add(blue);
        midLeft.add(tfblue);
        midLeft.add(minusBlue);
        midLeft.add(plusBlue);

        c.weighty = .1;
        c.gridy = 2;
        c.insets = new Insets(0,10,0,0);
        left.add(bottomLeft, c);

        //BOTTOMLEFT SUBCOMP

        bottomLeft.setLayout(new GridLayout(1,2));

        bottomLeft.add(save);

        bottomLeft.add(reset);


        try {
            listColor.setListData(colors.keySet().toArray());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void setRGBValue(Integer red, Integer green, Integer blue) {
        display.setBackground(new Color(red, green, blue));
        System.out.println(red + " " + green + " " + blue);
        tfred.setText(red.toString());
        tfgreen.setText(green.toString());
        tfblue.setText(blue.toString());
    }

    public static void main(String argv[]) throws IOException {

        new ColorSampler("Color Sampler");

    }

}
