import Figures.RegularPolygon;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

class GUI extends JFrame {

    private DrawingPanel drawingPanel;

    private JList list;
    DefaultListModel<String> model;

    private boolean toMove;

    GUI() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Paint");
        toMove = false;

        Container content = getContentPane();

        model = new DefaultListModel<>();
        model.addElement(" Segment");   //0
        model.addElement(" Ray");       //1
        model.addElement(" Line");      //2
        model.addElement(" Polyline");  //3
        model.addElement(" Circle");    //4
        model.addElement(" Ellipse");   //5
        model.addElement(" Rectangle"); //6
        model.addElement(" Triangle");  //7
        model.addElement(" Polygon");   //8
        model.addElement(" RegularPolygon"); //9
        model.addElement(" Rhomb");     //10


        list = new JList(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFixedCellHeight(40);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(140,this.getHeight()));
        content.add(scrollPane,BorderLayout.WEST);

        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                switch (list.getSelectedIndex()){
                    case 0:
                        drawingPanel.setFigureType(DrawingPanel.Figures.SEGMENT);
                        break;
                     case 1:
                        drawingPanel.setFigureType(DrawingPanel.Figures.RAY);
                        break;
                     case 2:
                        drawingPanel.setFigureType(DrawingPanel.Figures.LINE);
                        break;
                     case 3:
                        drawingPanel.setFigureType(DrawingPanel.Figures.POLYLINE);
                        break;
                     case 4:
                        drawingPanel.setFigureType(DrawingPanel.Figures.CIRCLE);
                        break;
                     case 5:
                        drawingPanel.setFigureType(DrawingPanel.Figures.ELLIPSE);
                        break;
                     case 6:
                        drawingPanel.setFigureType(DrawingPanel.Figures.RECTANGLE);
                        break;
                      case 7:
                        drawingPanel.setFigureType(DrawingPanel.Figures.TRIANGLE);
                        break;
                      case 8:
                        drawingPanel.setFigureType(DrawingPanel.Figures.POLYGON);
                        break;
                      case 9:
                        drawingPanel.setFigureType(DrawingPanel.Figures.REGULARPOLYGON);
                        break;
                     case 10:
                        drawingPanel.setFigureType(DrawingPanel.Figures.RHOMB);
                        break;
                    default:
                        drawingPanel.setFigureType(DrawingPanel.Figures.SEGMENT);
                        break;

                }
            }
        });

        JPanel drawPart = new JPanel();
        drawingPanel = new DrawingPanel(this);
        JScrollPane pane = new JScrollPane(drawingPanel);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setPreferredSize(new Dimension(700, 500));
        drawPart.add(pane, BorderLayout.CENTER);

        //--------------------------move---------------------------
        JPanel movePanel = new JPanel();
        JButton buttonMove = new JButton("Move");
        movePanel.add(buttonMove);

        buttonMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                toMove = !toMove;
                drawingPanel.setToMove(toMove);
            }
        });
        drawPart.add(movePanel);

        //--------------colors--------------------------------------
        JPanel colorPanel = new JPanel();
        drawPart.add(colorPanel, BorderLayout.NORTH);
        JButton buttonRed = new JButton("Red");
        colorPanel.add(buttonRed);
        buttonRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawingPanel.setColor(Color.RED);
            }
        });
        JButton buttonGreen = new JButton("Green");
        colorPanel.add(buttonGreen);
        buttonGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawingPanel.setColor(Color.GREEN);
            }
        });
        JButton buttonBlue = new JButton("Blue");
        colorPanel.add(buttonBlue);
        buttonBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawingPanel.setColor(Color.BLUE);
            }
        });

        content.add(drawPart);

        //-----------------menu------------------------------------------

        JMenuItem menuExit = new JMenuItem("Exit", KeyEvent.VK_E);
        JMenu menuOptions = new JMenu("Options");
        menuOptions.setMnemonic(KeyEvent.VK_O);//Alt+O
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuOptions);
//        menuOptions.addSeparator();
        menuOptions.add(menuExit);
        setJMenuBar(menuBar);

        menuExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        //----------------------------------------------------------------

        setVisible(true);
        pack();
    }
}