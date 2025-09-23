/**
 * @author K. Rege
 * @version 1.0 -- GraphicPanel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */
 
package ch.zhaw.ads;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.function.*;


public class GraphicPanel extends JPanel {
	String figure;

	public void setFigure(String figure) {
		this.figure = figure;
		paint(getGraphics());
	}

	private void drawFig(Graphics g, String fig, double x, double y, double width, double height, String style) {
		int w = getWidth();
		int h = getHeight();
		int ix0 = (int) (w * x);
		int iy0 = (int) (h * y);
		int ix1 = (int) (w * (x + width));
		int iy1 = (int) (h * (y + height));
		if (fig.equals("rect")) {
    		if (style.equals("draw")) g.drawRect(ix0, h - iy1, ix1 - ix0, iy1 - iy0);
    		else g.fillRect(ix0, h - iy1, ix1 - ix0, iy1 - iy0);
        } else {
            if (style.equals("draw")) g.drawOval(ix0, h - iy1, ix1 - ix0, iy1 - iy0);
    		else g.fillOval(ix0, h - iy1, ix1 - ix0, iy1 - iy0);
        }
	}
	
	private double next(StringTokenizer tok) {
	    double val = Double.parseDouble(tok.nextToken()); 
	    tok.nextToken(); 
	    return val;
	}

	private void drawFigure(Graphics g) {       
		if (figure != null) {
			int w = getWidth();
			int h = getHeight();
			g.setColor(Color.black);
			StringTokenizer lineTok = new StringTokenizer(figure, "\n");
			while (lineTok.hasMoreTokens()) {
			    String line = lineTok.nextToken();
    			StringTokenizer tok = new StringTokenizer(line, " <>=/,\"");
    			if (tok.hasMoreTokens()) {
    				String fig = tok.nextToken();
    				if (fig.equals("text")) {
    					tok.nextToken();
    					double x = next(tok);
    					double y = next(tok);		
    					String s = line.substring(line.indexOf('>')+1,line.lastIndexOf('<'));   
    					Font saved = g.getFont();
    					g.setFont(saved.deriveFont((float)(11*getHeight()/350)));
    					g.drawString(s,(int) (x * w), h - (int) (y * h));		
    					g.setFont(saved);		    
    				} else if (fig.equals("ellipse")) {
    					tok.nextToken();
    					double cx = next(tok);
    					double cy = next(tok);
    					double rx = next(tok);
    					double ry = next(tok);
    					String style = tok.nextToken();
    					drawFig(g,fig, cx-rx, cy-ry, 2*rx, 2*ry, style);
    				} else if (fig.equals("line")) {
    					tok.nextToken();
    					double x1 = next(tok);
    					double y1 = next(tok);
    					double x2 = next(tok);
    					double y2 = Double.parseDouble(tok.nextToken());
    					g.drawLine((int) (x1 * w), h - (int) (y1 * h),
    							(int) (x2 * w), h - (int) (y2 * h));
    				} else if (fig.equals("rect")) {
    					tok.nextToken();
    					double x = next(tok);
    					double y = next(tok);
    					double width = next(tok);
    					double height = next(tok);
    					String style = tok.nextToken();
    					drawFig(g,fig, x, y, width, height, style);
    				} else if (fig.equals("color")) {
    					tok.nextToken();
    					int red = Integer.parseInt(tok.nextToken());
    					tok.nextToken();
    					int green = Integer.parseInt(tok.nextToken());
    					tok.nextToken();
    					int blue = Integer.parseInt(tok.nextToken());
    					g.setColor(new Color(red, green, blue));
    				}
    			}
    	    }
		}
	}

	private void clear(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		g.setColor(new Color(240, 240, 240));
		g.fillRect(0, 0, w, h);
	}

	public void paint(Graphics g) {
		clear(g);
		drawFigure(g);
	}
}
