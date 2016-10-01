package de.amr.schule.vektoren;

import static java.lang.Math.sqrt;

/**
 * 3D-Vektor mit Koordinaten vom Typ <code>double</code>.
 */
public class Vec3 {

	/**
	 * Nullvektor.
	 */
	public static final Vec3 NULL = new Vec3(0, 0, 0);

	/**
	 * Einheitsvektor in x-Richtung.
	 */
	public static final Vec3 EX = new Vec3(1, 0, 0);

	/**
	 * Einheitsvektor in y-Richtung.
	 */
	public static final Vec3 EY = new Vec3(0, 1, 0);

	/**
	 * Einheitsvektor in z-Richtung.
	 */
	public static final Vec3 EZ = new Vec3(0, 0, 1);

	/**
	 * X-Koordinate.
	 */
	public final double x;

	/**
	 * Y-Koordinate.
	 */
	public final double y;

	/**
	 * Z-Koordinate.
	 */
	public final double z;

	public static boolean aboutEqual(double x, double y) {
		return Math.abs(x) - Math.abs(y) <= 1e-15;
	}

	@Override
	public String toString() {
		return String.format("(%.2f, %.2f, %.2f)", x, y, z);
	}

	/**
	 * Erzeugt den Nullvektor.
	 */
	public Vec3() {
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
	public Vec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param v
	 *          vector to copy
	 */
	public Vec3(Vec3 v) {
		this(v.x, v.y, v.z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vec3 other = (Vec3) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	/**
	 * Addition beliebig vieler Vektoren.
	 * 
	 * @param vs
	 *          Vektoren {@code v1, ..., vN}
	 * @return Summenvektor {@code v1 + ... + vN} oder Nullvektor, falls {@code N = 0}
	 */
	public static Vec3 sum(Vec3... vs) {
		if (vs.length == 0) {
			return NULL;
		}
		double x = 0, y = 0, z = 0;
		for (Vec3 v : vs) {
			x += v.x;
			y += v.y;
			z += v.z;
		}
		return new Vec3(x, y, z);
	}

	/**
	 * Subtraktion von Vektoren.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return Differenzvektor {@code u - v}
	 */
	public static Vec3 diff(Vec3 u, Vec3 v) {
		return new Vec3(u.x - v.x, u.y - v.y, u.z - v.z);
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
	public static Vec3 times(double s, Vec3 v) {
		return new Vec3(s * v.x, s * v.y, s * v.z);
	}

	/**
	 * Gegenvektor.
	 * 
	 * @param v
	 *          Vektor
	 * @return Gegenvektor zu v
	 */
	public static Vec3 inv(Vec3 v) {
		return new Vec3(-v.x, -v.y, -v.z);
	}

	/**
	 * Skalarprodukt zweier Vektoren.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return Skalarprodukt {@code u * v}
	 */
	public static double dot(Vec3 u, Vec3 v) {
		return u.x * v.x + u.y * v.y + u.z * v.z;
	}

	/**
	 * Winkel zwischen zwei Vektoren.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return der eingeschlossene Winkel
	 */
	public static double angle(Vec3 u, Vec3 v) {
		return Math.acos(dot(u, v) / (u.length() * v.length()));
	}

	/**
	 * Kreuzprodukt zweier Vektoren.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return Kreuzproduktvektor {@code u x v}
	 */
	public static Vec3 cross(Vec3 u, Vec3 v) {
		return new Vec3(u.y * v.z - u.z * v.y, -u.x * v.z + u.z * v.x, u.x * v.y - u.y * v.x);
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
	 * @return Spatprodukt {@code (u x v) * w}
	 */
	public static double spat(Vec3 u, Vec3 v, Vec3 w) {
		return dot(cross(u, v), w);
	}

	/**
	 * Länge eines Vektors.
	 * 
	 * @param v
	 *          Vektor
	 * @return Länge des Vektors
	 */
	public static double length(Vec3 v) {
		return sqrt(dot(v, v));
	}

	/**
	 * Quadrat der Länge eines Vektors.
	 * 
	 * @param v
	 *          Vektor
	 * @return Quadrat der Länge des Vektors
	 */
	public static double lengthSqr(Vec3 v) {
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
	public static boolean collinear(Vec3 u, Vec3 v) {
		return cross(u, v).equals(NULL);
	}

	/**
	 * Testet Vektoren auf Orthogonalität.
	 * 
	 * @param u
	 *          Vektor
	 * @param v
	 *          Vektor
	 * @return ob die Vektoren orthogonal sind
	 */
	public static boolean orthogonal(Vec3 u, Vec3 v) {
		return dot(u, v) == 0;
	}

	// Methoden

	/**
	 * 
	 * @param v
	 *          zu addierender Vektor
	 * @return Summe von diesem und dem zu addierenden Vektor
	 */
	public Vec3 add(Vec3 v) {
		return sum(this, v);
	}

	/**
	 * 
	 * @param v
	 *          zu subtrahierender Vektor
	 * @return Differenz von diesem und dem zu subtrahierenden Vektor
	 */
	public Vec3 sub(Vec3 v) {
		return diff(this, v);
	}

	/**
	 * @return Gegenvektor zu diesem Vektor
	 */
	public Vec3 inv() {
		return inv(this);
	}

	/**
	 * 
	 * @param s
	 *          Skalarer Faktor
	 * @return Skalares Vielfaches von diesem Vektor
	 */
	public Vec3 times(double s) {
		return times(s, this);
	}

	/**
	 * 
	 * @param v
	 *          Vektor, mit dem das Skalarprodukt gebildet werden soll
	 * @return Skalarprodukt von diesem mit dem gegebenen Vektor
	 */
	public double dot(Vec3 v) {
		return dot(this, v);
	}

	/**
	 * 
	 * @param v
	 *          Vektor, mit dem das Kreuzprodukt gebildet werden soll
	 * @return Kreuzproduktvektor
	 */
	public Vec3 cross(Vec3 v) {
		return cross(this, v);
	}

	/**
	 * 
	 * @return Länge dieses Vektors
	 */
	public double length() {
		return length(this);
	}
}
