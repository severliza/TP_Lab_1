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
    private Color borderColor;
    private ArrayList<Figure> figures;
    private int prevX, prevY;
    private boolean isDrawing;
    private Figures figureType;
    private boolean toMove;
    private int indexToMove;

    private int count;

    public enum Figures {
        LINE , RAY, SEGMENT, POLYLINE, POLYGON, ELLIPSE, CIRCLE, RECTANGLE, REGULARPOLYGON, RHOMB, SQUARE, TRIANGLE
    }

    {
        color = Color.GREEN;
        borderColor = Color.BLACK;
        figures = new ArrayList<>();
        isDrawing = false;
        figureType = Figures.SEGMENT;
        toMove = false;
        indexToMove = -1;
        count=5;
    }

    DrawingPanel(JFrame frame) {
        this.frame = frame;
        this.setSize(frame.getWidth(), frame.getHeight());

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }
    public void setToMove(boolean toMove) { this.toMove = toMove; }
    public void setFigureType(Figures figureType) { this.figureType = figureType; }
    public void setCount(int count) { this.count = count; }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent me) {
            prevX = me.getX();
            prevY = me.getY();

            if(!toMove){

                switch (figureType){
                    case SEGMENT:
                        figures.add(new Segment(borderColor, new Point(prevX, prevY), new Point(prevX, prevY)));
                        break;
                    case RAY:
                        figures.add(new Ray(borderColor, new Point(prevX, prevY), new Point(prevX, prevY)));
                        break;
                    case LINE:
                        figures.add(new Line(borderColor, new Point(prevX, prevY), new Point(prevX, prevY)));
                        break;
                    case POLYLINE: //PolyLine!
                        figures.add(new Ray(borderColor, new Point(prevX, prevY), new Point(prevX, prevY)));
                        break;
                    case CIRCLE: //не отличается от элипса
                        figures.add(new Circle(borderColor, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case ELLIPSE:
                        figures.add(new Ellipse(borderColor, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case RECTANGLE:
                        figures.add(new Rectangle(borderColor, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case TRIANGLE: //triangle
                        //figures.add(new Triangle(borderColor, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case POLYGON: //polygon
                        //figures.add(new Polygon(borderColor, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    case REGULARPOLYGON: //regularPolygon
                        figures.add(new RegularPolygon(borderColor, new Point(prevX, prevY), color, new Point(prevX, prevY), count));
                        break;
                    case RHOMB: //rhomb
                        figures.add(new Rhomb(borderColor, new Point(prevX, prevY), color, new Point(prevX, prevY)));
                        break;
                    default:
                        figures.add(new RegularPolygon(borderColor, new Point(prevX, prevY), color, new Point(prevX, prevY), count));
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