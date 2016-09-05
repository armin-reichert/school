package de.amr.schule.vektoren;

import static java.lang.Math.sqrt;

/**
 * 3-dimensionale Vektoren mit Komponenten vom Typ <code>double</code>.
 */
public class Vector3 {

	public static final Vector3 NULL = new Vector3(0, 0, 0);
	public static final Vector3 EX = new Vector3(1, 0, 0);
	public static final Vector3 EY = new Vector3(0, 1, 0);
	public static final Vector3 EZ = new Vector3(0, 0, 1);

	public final double x;
	public final double y;
	public final double z;

	@Override
	public String toString() {
		return String.format("(%.2f, %.2f, %.2f)", x, y, z);
	}

	/**
	 * Erzeugt den Nullvektor.
	 */
	public Vector3() {
		x = y = z = 0;
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
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param v
	 *          a vector
	 */
	public Vector3(Vector3 v) {
		this(v.x, v.y, v.z);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Vector3) {
			if (other == this) {
				return true;
			}
			Vector3 v = (Vector3) other;
			return v.x == x && v.y == y && v.z == z;
		}
		return false;
	}

	/**
	 * Addition von zwei Vektoren.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return Summenvektor u + v
	 */
	public static Vector3 sum(Vector3 u, Vector3 v) {
		return new Vector3(u.x + v.x, u.y + v.y, u.z + v.z);
	}

	/**
	 * Addition beliebig vieler Vektoren.
	 * 
	 * @param vs
	 *          Vektoren
	 * @return Summe der Vektoren
	 */
	public static Vector3 sum(Vector3... vs) {
		Vector3 sum = NULL;
		for (Vector3 v : vs) {
			sum = sum.add(v);
		}
		return sum;
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
	public static Vector3 diff(Vector3 u, Vector3 v) {
		return new Vector3(u.x - v.x, u.y - v.y, u.z - v.z);
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
		return new Vector3(s * v.x, s * v.y, s * v.z);
	}

	/**
	 * Gegenvektor.
	 * 
	 * @param v
	 *          Vektor
	 * @return Gegenvektor zu v
	 */
	public static Vector3 inv(Vector3 v) {
		return new Vector3(-v.x, -v.y, -v.z);
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
		return u.x * v.x + u.y * v.y + u.z * v.z;
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
		return new Vector3(u.y * v.z - u.z * v.y, -u.x * v.z + u.z * v.x, u.x * v.y - u.y * v.x);
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
		return cross(u, v).equals(NULL);
	}

	public static boolean orthogonal(Vector3 u, Vector3 v) {
		return dot(u, v) == 0;
	}

	// Methoden

	public Vector3 add(Vector3 v) {
		return sum(this, v);
	}

	public Vector3 sub(Vector3 v) {
		return diff(this, v);
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
