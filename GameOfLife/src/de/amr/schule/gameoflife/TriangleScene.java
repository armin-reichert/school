package de.amr.schule.gameoflife;

/**
 * Game of life scene.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class TriangleScene extends GameOfLifeScene {

	public TriangleScene(GameOfLifeApp app) {
		super(app);
	}

	@Override
	protected void reset() {
		world.reset();
		int a = world.getGridSize() / 4;
		int h = (int) (a * Math.sqrt(2) / 2);
		int topX = world.getGridSize() / 2;
		int topY = world.getGridSize() / 4 - h / 2;
		int xl = topX, xr = topX, y = topY;
		world.set(topY, topX);
		for (int i = 0; i < a; ++i) {
			world.set(y, xl);
			world.set(y, xr);
			xl--;
			xr++;
			y++;
		}
		for (int x = xl; x <= xr; ++x) {
			world.set(y, x);
		}
	}
}