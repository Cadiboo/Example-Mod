package cadiboo.examplemod.util;

import com.google.common.base.MoreObjects;

public class Position implements Comparable<Position> {

	public double	x;
	public double	y;
	public double	z;

	public Position(final double x_, final double y_, final double z_) {
		this.x = x_;
		this.y = y_;
		this.z = z_;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void add(final double x_, final double y_, final double z_) {
		this.x += x_;
		this.y += y_;
		this.z += z_;
	}

	/**
	 * Calculate the cross product of this and the given Vector
	 */
	public Position crossProduct(Position vec) {
		return new Position(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
	}

	public double distanceTo(int xIn, int yIn, int zIn) {
		double d0 = this.getX() - xIn;
		double d1 = this.getY() - yIn;
		double d2 = this.getZ() - zIn;
		return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
	}

	/**
	 * Calculate squared distance to the given coordinates
	 */
	public double distanceToSq(double toX, double toY, double toZ) {
		double d0 = this.getX() - toX;
		double d1 = this.getY() - toY;
		double d2 = this.getZ() - toZ;
		return d0 * d0 + d1 * d1 + d2 * d2;
	}

	/**
	 * Compute square of distance from point x, y, z to center of this Block
	 */
	public double distanceToCenterSq(double xIn, double yIn, double zIn) {
		double d0 = this.getX() + 0.5D - xIn;
		double d1 = this.getY() + 0.5D - yIn;
		double d2 = this.getZ() + 0.5D - zIn;
		return d0 * d0 + d1 * d1 + d2 * d2;
	}

	/**
	 * Calculate squared distance to the given Vector
	 */
	public double distanceToSq(Position to) {
		return this.distanceToSq(to.getX(), to.getY(), to.getZ());
	}

	@Override
	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof Position)) {
			return false;
		} else {
			Position vec3i = (Position) p_equals_1_;

			if (this.getX() != vec3i.getX()) {
				return false;
			} else if (this.getY() != vec3i.getY()) {
				return false;
			} else {
				return this.getZ() == vec3i.getZ();
			}
		}
	}

	@Override
	public int hashCode() {
		return (int) ((Math.round(this.getY()) + Math.round(this.getZ()) * 31) * 31 + Math.round(this.getX()));
	}

	@Override
	public int compareTo(Position p_compareTo_1_) {
		if (this.getY() == p_compareTo_1_.getY()) {
			return (int) (this.getZ() == p_compareTo_1_.getZ() ? Math.round(this.getX() - p_compareTo_1_.getX()) : Math.round(this.getZ() - p_compareTo_1_.getZ()));
		} else {
			return (int) Math.round(this.getY() - p_compareTo_1_.getY());
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
	}

}
