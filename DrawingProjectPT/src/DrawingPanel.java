import Figures.*;
import Figures.Polygon;
import Figures.Rectangle;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {
    private Frame frame;
    private Color color;
    private ArrayList<Figure> figures;
    private int prevX, prevY;
    private boolean isDrawing;
    //private int index;
    private Figures figureType;
    private boolean toMove;
    private int indexToMove;

    public enum Figures {
        LINE , RAY, SEGMENT, POLYLINE, POLYGON, ELLIPSE, CIRCLE, RECTANGLE, REGULARPOLYGON, RHOMB, SQUARE, TRIANGLE
    }

    {
        color = Color.GREEN;
        figures = new ArrayList<>();
        isDrawing = false;
        //index = 0;
        figureType = Figures.SEGMENT;
        toMove = false;
        indexToMove = -1;
    }

    DrawingPanel(JFrame frame) {
        this.frame = frame;
        this.setSize(frame.getWidth(), frame.getHeight());

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    //public void setIndex(int index) { this.index = index; }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setToMove(boolean toMove) { this.toMove = toMove; }
    public void setFigureType(Figures figureType) { this.figureType = figureType; }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent me) {
            prevX = me.getX();
            prevY = me.getY();

            if(!toMove){

                switch (figureType){
                    case SEGMENT:
                        figures.add(new Segment(Color.BLACK, new Point(prevX, prevY), new Point(prevX, prevY)));
                        break;
                    case RAY:
                        figures.add(new Ray(Color.BLACK, new Point(prevX, prevY), new Point(prevX, prevY)));
                        break;
                    case LINE:
                        figures.add(new Line(Color.BLACK, new Point(prevX, prevY), new Point(prevX, prevY)));
                        break;
                    case POLYLINE: //PolyLine!
                        figures.add(new Ray(Color.BLACK, new Point(prevX, prevY), new Point(prevX, prevY)));
                        break;
                    case CIRCLE: //не отличается от элипса
                        figures.add(new Circle(Color.BLACK, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case ELLIPSE:
                        figures.add(new Ellipse(Color.BLACK, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case RECTANGLE:
                        figures.add(new Rectangle(Color.BLACK, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case TRIANGLE: //triangle
                        //figures.add(new Triangle(Color.BLACK, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case POLYGON: //polygon
                        //figures.add(new Polygon(Color.BLACK, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case REGULARPOLYGON: //regularPolygon
                        //figures.add(new RegularPolygon(Color.BLACK, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case RHOMB: //rhomb
                        figures.add(new Rhomb(Color.BLACK, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    default:
                        figures.add(new RegularPolygon(Color.BLACK, new Point(prevX, prevY), color, new Point(prevX, prevY), 5));
                        break;
                }
                isDrawing = true;
            }
            else{
                Figure f;
                for (int i = figures.size() - 1; i >= 0; i--) {
                    f = figures.get(i);
                    if(f.contains(me.getPoint())){
                        indexToMove = i;
                        break;
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            isDrawing = false;
            indexToMove = -1;
        }
    }

    private class MouseMotionHandler implements MouseMotionListener {
        public void mouseDragged(MouseEvent me) {

            if(!toMove){
                prevX = me.getX();
                prevY = me.getY();

                Figure f = figures.get(figures.size() - 1);

                switch (figureType){
                    case RAY:
                        Ray r = (Ray)f;
                        r.setSecondPoint(new Point(prevX, prevY));
                        break;
                    case LINE:
                        f.setLocation(new Point(prevX, prevY));
                        break;
                    default:
                        f.setLocation(new Point(prevX, prevY));
                        break;
                }
            }else{
                if(indexToMove>=0){
                    Figure f = figures.get(indexToMove);
                    f.move(me.getX() - prevX, me.getY() - prevY);

                    prevX = me.getX();
                    prevY = me.getY();
                }
            }
        }

        public void mouseMoved(MouseEvent e) {
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaint();

        Graphics2D f = (Graphics2D) g;
        f.setStroke(new BasicStroke(0.6f));

        for (Figure figure: figures) {
            figure.draw(g, frame);
        }

        f.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2, new float[] {5f, 5f}, 0f));
        f.setColor(Color.GRAY);
        f.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (!figures.isEmpty() && isDrawing && figures.get(figures.size() - 1) instanceof Shape2D) {
            ((Shape2D) figures.get(figures.size() - 1)).drawServiceLines(g);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(frame.getWidth(), frame.getHeight());
    }
}