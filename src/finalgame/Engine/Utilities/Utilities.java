/**
 * (c) 2011 Patrick Long
 * All rights reserved.
 */

package finalgame.Engine.Utilities;

import finalgame.Graphics.GraphicsConstants;

public final class Utilities {
	private static Utilities u = new Utilities();
	
	public static Size defaultTankSize = u.new Size(5.0, 4.0, 3.0);
	public static Stats defaultTankStats = u.new Stats(1.0, 1.0, 3.0, 1.0);
	
	public class Size {
		public double length;
		public double width;
		public double height;
		
		public Size(double l, double w, double h) {
			this.length = l;
			this.width = w;
			this.height = h;
		}
	}

	public class Stats {
		public double atk;
		public double def;
		public double speed;
		public double reload;
		
		public Stats(double a, double d, double s, double r) {
			this.atk = a;
			this.def = d;
			this.speed = s;
			this.reload = r;
		}
	}
	
	public class Square {
		private Location topleft, bottomright;
		
		/**
		 * 
		 * @param tl Top left coordinate
		 * @param br Bottom right coordinate
		 */
		public Square(Location tl, Location br) {
			topleft = tl;
			bottomright = br;
		}
		
		/**
		 * 
		 * @param c1 Left x coordinate
		 * @param c2 Top y coordinate
		 * @param c3 If allcoord is true, right x coordinate. Otherwise, the width.
		 * @param c4 If allcoord is true, bottom y coordinate. Otherwise, the hight.
		 * @param allcoords Whether variables are in form (x1, y1), (x2, y2) or (x, y), width, height
		 */
		public Square(double c1, double c2, double c3, double c4, boolean allcoords) {
			topleft = new Location(c1, c2);
			if(allcoords) {
				bottomright = new Location(c3, c4);
			} else {
				bottomright = new Location(c1 + c3, c2 + c4);
			}
		}
		
		public double x1() {
			return topleft.x();
		}
		public double x2() {
			return topleft.y();
		}
		public double y1() {
			return bottomright.x();
		}
		public double y2() {
			return bottomright.y();
		}
		public Location topleft() {
			return topleft;
		}
		public Location bottomright() {
			return bottomright;
		}
		public Location center() {
			return new Location((x1() + x2()) / 2, (y1() + y2()) / 2);
		}
		public double width() {
			return x2() - x1();
		}
		public double height() {
			return y2() - y1();
		}
	}
	
	public static double rationalize(double coord) {
		coord %= 256.0;
		while(coord < 0) coord += 256.0;
		coord %= 256.0;
		return coord;
	}
	
	public static int rationalize(int coord) {
		coord %= 256;
		while(coord < 0) coord += 256;
		coord %= 256;
		return coord;
	}
	
	public static Location getRenderOffset(Location myLoc, Location otherLoc, int radius, int blocksize) {
    	double other_x = otherLoc.x(), other_y = otherLoc.y();
    	double myX = myLoc.x(), myY = myLoc.y();
		double xdiff = other_x - myX, ydiff = other_y - myY; //normal x and y offset
		//cross-axis x and y offset
		double xdiff_r = other_x - (myX - 256), ydiff_r = other_y - (myY - 256); //i am on right
		double xdiff_l = (other_x - 256) - myX, ydiff_l = (other_y - 256) - myY; //i am on left
		double x_offset, y_offset; //position to render other

		//get x reb
		if(xdiff >= -radius - 5 && xdiff <= radius + 5) { //normal
			x_offset = GraphicsConstants.WIDTH / 2  + xdiff * blocksize;
		} else if (xdiff_r >= -radius - 5 && xdiff_r <= radius + 5) { //i am to left of axis, other is to right
			x_offset = GraphicsConstants.WIDTH / 2  + xdiff_r * blocksize;
		} else if (xdiff_l >= -radius - 5 && xdiff_l <= radius + 5) { //opposite
			x_offset = GraphicsConstants.WIDTH / 2  + xdiff_l * blocksize;
		} else { //not close enough
			x_offset = -999;
		}

		if (ydiff >= -radius - 5 && ydiff <= radius + 5) { //normal
			y_offset = GraphicsConstants.HEIGHT / 2  + ydiff * blocksize;
		} else if (ydiff_r >= -radius - 5 && ydiff_r <= radius + 5) { //i am above axis, other below
			y_offset = GraphicsConstants.HEIGHT / 2  + ydiff_r * blocksize;
		} else if (ydiff_l >= -radius - 5 && ydiff_l <= radius + 5) { //opposite
			y_offset = GraphicsConstants.HEIGHT / 2  + ydiff_l * blocksize;
		} else {
			y_offset = -999;
		}
		Location renderOffset = new Location(x_offset, y_offset);
		return renderOffset;
    }
	
	public static int getStringWidth(String text) {
		return text.length() * GraphicsConstants.LETTER_WIDTH;
	}
}
