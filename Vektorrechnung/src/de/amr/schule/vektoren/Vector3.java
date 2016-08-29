package de.amr.schule.vektoren;

import static java.lang.Math.sqrt;

/**
 * 3-dimensionale Vektoren mit Komponenten vom Typ <code>double</code>.
 */
public class Vector3 {

	private final double x1;
	private final double x2;
	private final double x3;

	@Override
	public String toString() {
		return String.format("(%.2f, %.2f, %.2f)", x1, x2, x3);
	}

	/**
	 * Erzeugt den Nullvektor.
	 */
	public Vector3() {
		x1 = x2 = x3 = 0;
	}

	/**
	 * Erzeugt Vektor mit gegebenen Koordinaten.
	 * 
	 * @param x
	 *          x-Koordinate
	 * @param y
	 *          y-Koordinate
	 * @param z
	 *          z-Koordinate
	 */
	public Vector3(double x, double y, double z) {
		this.x1 = x;
		this.x2 = y;
		this.x3 = z;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Vector3) {
			if (other == this) {
				return true;
			}
			Vector3 v = (Vector3) other;
			return v.x1 == x1 && v.x2 == x2 && v.x3 == x3;
		}
		return false;
	}

	public double x1() {
		return x1;
	}

	public double x2() {
		return x2;
	}

	public double x3() {
		return x3;
	}

	/**
	 * Addition von Vektoren.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return Summenvektor u + v
	 */
	public static Vector3 plus(Vector3 u, Vector3 v) {
		return new Vector3(u.x1 + v.x1, u.x2 + v.x2, u.x3 + v.x3);
	}

	/**
	 * Subtraktion von Vektoren.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return Differenzvektor u - v
	 */
	public static Vector3 minus(Vector3 u, Vector3 v) {
		return plus(u, inv(v));
	}

	/**
	 * Skalare Multiplikation zweier Vektoren.
	 * 
	 * @param s
	 *          Skalar
	 * @param v
	 *          Vektor
	 * @return s-faches von v
	 */
	public static Vector3 times(double s, Vector3 v) {
		return new Vector3(s * v.x1, s * v.x2, s * v.x3);
	}

	/**
	 * Gegenvektor.
	 * 
	 * @param v
	 *          Vektor
	 * @return Gegenvektor zu v
	 */
	public static Vector3 inv(Vector3 v) {
		return times(-1, v);
	}

	/**
	 * Skalarprodukt zweier Vektoren.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return Skalarprodukt u * v
	 */
	public static double dot(Vector3 u, Vector3 v) {
		return u.x1 * v.x1 + u.x2 * v.x2 + u.x3 * v.x3;
	}

	/**
	 * Kreuzprodukt zweier Vektoren.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return u x v Kreuzproduktvektor
	 */
	public static Vector3 cross(Vector3 u, Vector3 v) {
		return new Vector3(u.x2 * v.x3 - u.x3 * v.x2, -u.x1 * v.x3 + u.x3 * v.x1,
				u.x1 * v.x2 - u.x2 * v.x1);
	}

	/**
	 * Spatprodukt von 3 Vektoren.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @param w
	 *          Vektor
	 * @return Spatprodukt <code>(u x v) * w</code>
	 */
	public static double spat(Vector3 u, Vector3 v, Vector3 w) {
		return dot(cross(u, v), w);
	}

	/**
	 * Länge eines Vektors.
	 * 
	 * @param v
	 *          Vektor
	 * @return Länge des Vektors
	 */
	public static double length(Vector3 v) {
		return sqrt(dot(v, v));
	}

	/**
	 * Quadrat der Länge eines Vektors.
	 * 
	 * @param v
	 *          Vektor
	 * @return Quadrat der Länge des Vektors
	 */
	public static double lengthSqr(Vector3 v) {
		return dot(v, v);
	}

	/**
	 * Testet Vektoren auf Kollinearität.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return ob die Vektoren kollinear sind
	 */
	public static boolean collinear(Vector3 u, Vector3 v) {
		return cross(u, v).equals(new Vector3());
	}

	// Methoden

	public Vector3 plus(Vector3 v) {
		return plus(this, v);
	}

	public Vector3 minus(Vector3 v) {
		return minus(this, v);
	}

	public Vector3 inv() {
		return inv(this);
	}

	public Vector3 times(double s) {
		return times(s, this);
	}

	public double dot(Vector3 v) {
		return dot(this, v);
	}

	public Vector3 cross(Vector3 v) {
		return cross(this, v);
	}

}
