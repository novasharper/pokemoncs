package finalgame.Engine.Utilities;

import finalgame.Graphics.*;

public class Location {
	private double _x;
	private double _y;
	private double _rot;
	private int _z;
	
	public Location(double x, double y, double rot, int z) {
		this._x = x;
		this._y = y;
		this._z = z;
		this._rot = rot;
	}
	
	public Location(double x, double y, double rot) {
		this(x, y, rot, 0);
	}
	
	public Location(double x, double y) {
		this(x, y, 0);
	}
	
	public Location(Location other) {
		this._x = other._x;
		this._y = other._y;
		this._rot = other._rot;
	}
	
	public double x() { return this._x; }
	public double y() { return this._y; }
	public double rot() { return this._rot; }
	public int z() { return this._z; }
	
	public void x(double x) { this._x = x; }
	public void y(double y) { this._y = y; }
	public void rot(double rot) { this._rot = rot; }
	public void z(int z) { this._z = z; }
}
