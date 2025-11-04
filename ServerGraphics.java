package ch.zhaw.ads;


import java.awt.*;


public class ServerGraphics {

    private StringBuffer b;
    private static ServerGraphics theGraphics;

    public static ServerGraphics instance() {
        if (theGraphics == null) {
            theGraphics = new ServerGraphics();
        }
        return theGraphics;
    }

    public ServerGraphics() {
        clear();
        theGraphics = this;
    }

    public void clear() {
        b = new StringBuffer();
    }

    public String getTrace() {
        return new String(b);
    }

    private double round(double d) {
        return Math.round(d * 10000) / 10000.0;
    }

    private void drawFigure(String fig, String c1, String c2, String c3, String c4, double v1, double v2, double v3, double v4, String style) {
        b.append("<");
        b.append(fig); b.append(" ");
        b.append(c1); b.append("=\"");
        b.append(Double.toString(round(v1))); b.append("\" ");
        b.append(c2); b.append("=\"");
        b.append(Double.toString(round(v2))); b.append("\" ");
        b.append(c3); b.append("=\"");
        b.append(Double.toString(round(v3))); b.append("\" ");
        b.append(c4); b.append("=\"");
        b.append(Double.toString(round(v4))); b.append("\"");
        if (!"".equals(style)) {
            b.append(" style=\""); b.append(style); b.append("\"");
        }
        b.append("/>\n");
    }

    public void drawOval(double x, double y, double w, double h) {
        drawFigure("ellipse","cx","cy","rx","ry",x+w/2,y+h/2,w/2,h/2,"draw");
    }

    public void fillOval(double x, double y, double w, double h) {
        drawFigure("ellipse","cx","cy","rx","ry",x+w/2,y+h/2,w/2,h/2,"fill");
    }

    public void drawLine(double x1, double y1, double x2, double y2) {
        drawFigure("line","x1","y1","x2","y2",x1,y1,x2,y2,"");
    }

    public void drawRect(double x, double y, double w, double h) {
        drawFigure("rect","x","y","width","height",x,y,w,h,"draw");
    }

    public void fillRect(double x, double y, double w, double h) {
        drawFigure("rect","x","y","width","height",x,y,w,h,"fill");
    }

    public void drawString(String s, double x, double y) {
        b.append("<text x=\"");
        b.append(Double.toString(round(x)));
        b.append("\" y=\"");
        b.append(Double.toString(round(y)));
        b.append("\"/>");
        b.append(s);
        b.append("</text>\n");
    }

    public void setColor(Color c) {
        b.append("<color red=\"");
        b.append(Integer.toString(c.getRed()));
        b.append("\" green=\"");
        b.append(Integer.toString(c.getGreen()));
        b.append("\" blue=\"");
        b.append(Integer.toString(c.getBlue()));
        b.append("\"/>\n");
    }

    public void drawPath(String from, String to, boolean line) {
        double scale = 11;
        double xh0 = from.charAt(0) - '0';
        double yh0 = from.charAt(2) - '0';
        double xh1 = to.charAt(0) - '0';
        double yh1 = to.charAt(2) - '0';
        double x0 = Math.min(xh0, xh1) / scale;
        double y0 = Math.min(yh0, yh1) / scale;
        double x1 = Math.max(xh0, xh1) / scale;
        double y1 = Math.max(yh0, yh1) / scale;
        double w = 1 / scale;
        if (line) {
            drawLine(x0 + w / 2, y0 + w / 2, x1 + w / 2, y1 + w / 2);
        } else {
            if (Math.abs(y0 - y1) < 1E-10) {
                fillRect(x0, y0, x1 - x0 + w, w);
            } else {
                fillRect(x0, y0, w, y1 - y0 + w);
            }
        }
    }

}