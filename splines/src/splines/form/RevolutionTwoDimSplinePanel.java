package splines.form;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import splines.SplineCurve;
import egl.math.Vector2;

public class RevolutionTwoDimSplinePanel extends SplinePanel{
	private static final float AXES_WIDTH = 2.0f;

	/**
	 * Pixel radius of the control points for splines
	 */
	public static float VERTEX_RADIUS = 10.0f;

	/**
	 * Length of the normals in world space
	 */
	public static float NORMAL_LENGTH = 0.1f;

	/**
	 * Length of the tangents in world space
	 */
	public static float TANGENT_LENGTH = 0.25f;

	/**
	 * Pixel width of the spline line
	 */
	public static float SPLINE_WIDTH = 3f;

	/**
	 * The underlying spline
	 */
	public SplineCurve spline;

	/**
	 * The index of a selected point being moved
	 */
	public int selected;

	/**
	 * Constructor
	 * @param index Index of the SplinePanel in the RevolutionSplineScreen
	 * @param spline The underlying BSpline
	 */
	public RevolutionTwoDimSplinePanel(int index, SplineCurve spline) {
		this.index = index;
		this.spline = spline;
		this.selected = -1;
	}

	/**
	 * Draws all the spline elements - control points, splines and normals
	 * @param panelWidth The new width of the panel
	 * @param panelHeight The new height of the panel
	 */
	public void draw() {
		GL11.glViewport(this.index * RevolutionSplinePanel.panelWidth, 0, RevolutionSplinePanel.panelWidth, RevolutionSplinePanel.panelHeight);
		drawAxes();
		drawControlPoints();
		drawSpline(RevolutionControlFrame.drawNormals, RevolutionControlFrame.drawTangents);
	}

	public void drawAxes() {
		
		// draw faint gridlines
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3d(0.3, 0.3, 0.3);
		GL11.glVertex2f(-2*halfSide, 0);
		GL11.glVertex2f(2*halfSide, 0);
		GL11.glVertex2f(0, -2*halfSide);
		GL11.glVertex2f(0, 2*halfSide);
		GL11.glEnd();
		
		// Draw the usual RGB axes, but only X and Y (R and G)
		GL11.glLineWidth(AXES_WIDTH);

		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3d(0.8, 0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(0, halfSide/2);
		GL11.glColor3d(0, 0.6, 0);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(halfSide/2, 0);
		GL11.glEnd();

		GL11.glLineWidth(1.0f);
	}

	/**
	 * Draws all the control points and the line joining them from the underlying BSpline
	 */
	public void drawControlPoints() {
		
		// Draw all the control point vertices
		GL11.glPointSize(RevolutionTwoDimSplinePanel.VERTEX_RADIUS);
		GL11.glBegin(GL11.GL_POINTS);
		for(int i = 0; i < this.spline.getControlPoints().size(); i++) {
			if (i == selected) {
				GL11.glColor3d(1, 0, 0);
			} else if(i == 0) {
				GL11.glColor3d(0, 1, 0);
			}
			Vector2 v = this.spline.getControlPoints().get(i);
			GL11.glVertex2f(v.x * halfSide, v.y * halfSide);
			if (i == selected) {
				GL11.glColor3d(1, 1, 1);
			} else if(i == 0) {
				GL11.glColor3d(1, 1, 1);
			}
		}
		GL11.glEnd();

		// Draw the line connecting all the control points
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3d(1, 1, 0);
		ArrayList<Vector2> points = this.spline.getControlPoints();
		for(int i= 1; i < points.size(); i++) {
			if (i == selected || (i - 1) == selected) {
				GL11.glColor3d(1, 0, 0);
			}
			GL11.glVertex2f(points.get(i - 1).x * halfSide, points.get(i - 1).y * halfSide);
			GL11.glVertex2f(points.get(i).x * halfSide, points.get(i).y * halfSide);
			if (i == selected || (i - 1) == selected) {
				GL11.glColor3d(1, 1, 0);
			}
		}
		if (this.spline.isClosed()) {
			if (0 == selected || (points.size() - 1) == selected) {
				GL11.glColor3d(1, 0, 0);
			}
			GL11.glVertex2f(points.get(points.size() - 1).x * halfSide, points.get(points.size() - 1).y * halfSide);
			GL11.glVertex2f(points.get(0).x * halfSide, points.get(0).y * halfSide);
			if (0 == selected || (points.size() - 1) == selected) {
				GL11.glColor3d(1, 1, 0);
			}
		}
		GL11.glColor3d(1, 1, 1);
		GL11.glEnd();
	}

	/**
	 * Draws the underlying spline with or without normals
	 * @param drawNormals Whether or not we want to draw normals on the spline
	 */
	public void drawSpline(boolean drawNormals, boolean drawTangents) {
		GL11.glLineWidth(SPLINE_WIDTH);
		GL11.glBegin(GL11.GL_LINES);
		ArrayList<Vector2> points = this.spline.getPoints();
		for(int i= 1; i < points.size(); i++) {
			GL11.glVertex2f(points.get(i - 1).x * halfSide, points.get(i - 1).y * halfSide);
			GL11.glVertex2f(points.get(i).x * halfSide, points.get(i).y * halfSide);
		}
		if (this.spline.isClosed()) {
			GL11.glVertex2f(points.get(points.size() - 1).x * halfSide, points.get(points.size() - 1).y * halfSide);
			GL11.glVertex2f(points.get(0).x * halfSide, points.get(0).y * halfSide);
		}
		GL11.glEnd();
		GL11.glLineWidth(1f);
		if (drawNormals) {
			drawNormals();
		}
		if (drawTangents) {
			drawTangents();
		}
	}

	/**
	 * Draws the normals of the underlying BSpline
	 */
	public void drawNormals() {
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3d(0, 0, 1);
		ArrayList<Vector2> points = this.spline.getPoints();
		ArrayList<Vector2> normals = this.spline.getNormals();
		for(int i= 0; i < points.size(); i++) {
			GL11.glVertex2f(points.get(i).x * halfSide, points.get(i).y * halfSide);
			GL11.glVertex2f((points.get(i).x + (normals.get(i).x * NORMAL_LENGTH)) * halfSide, (points.get(i).y + (normals.get(i).y * NORMAL_LENGTH)) * halfSide);
		}
		GL11.glColor3d(1, 1, 1);
		GL11.glEnd();
	}

	/**
	 * Draws the tangents of the underlying BSpline
	 */
	public void drawTangents() {
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3d(0, 1, 0);
		ArrayList<Vector2> points = this.spline.getPoints();
		ArrayList<Vector2> tangents = this.spline.getTangents();
		for(int i= 0; i < points.size(); i++) {
			GL11.glVertex2f(points.get(i).x * halfSide, points.get(i).y * halfSide);
			GL11.glVertex2f((points.get(i).x + (tangents.get(i).x * TANGENT_LENGTH)) * halfSide, (points.get(i).y + (tangents.get(i).y * TANGENT_LENGTH)) * halfSide);
		}
		GL11.glColor3d(1, 1, 1);
		GL11.glEnd();
	}

	/**
	 * Set the selected field to the point indicated by the mouse click(or -1 if nothing is selected)
	 * @param mouseX, mouseY - X and Y coordinates of the mouse click relative to the screen, but on this panel
	 */
	public void selectWithMouse(int mouseX, int mouseY) {
		this.selected = getSelectedWithMouseClick(mouseX, mouseY);
	}

	/**
	 * Transform a mouse click into world coordinated and return the point that it points to (or -1 if nothing)
	 * @param mouseX, mouseY - X and Y coordinates of the mouse click relative to the screen, but on this panel
	 * @return The index of the point selected by the mouse click, or -1 if no point is selected
	 */
	public int getSelectedWithMouseClick(int mouseX, int mouseY) {
		Vector2 mouseClickPoint = mouseClickToWorldTransform(mouseX, mouseY);
		int closestPointIndex = -1;
		double closestDistance = Double.MAX_VALUE;
		for (int i = 0; i < this.spline.getControlPoints().size(); i++) {
			Vector2 point = this.spline.getControlPoints().get(i);
			double distance = Math.pow(mouseClickPoint.x - point.x, 2) + Math.pow(mouseClickPoint.y - point.y, 2);
			if (distance < closestDistance) {
				closestDistance = distance;
				closestPointIndex = i;
			}
		}
		if (Math.sqrt(closestDistance) < pixelToWorldDistance(VERTEX_RADIUS / 2.0)) {
			return closestPointIndex;
		};
		return -1;
	}

	/**
	 * Change the coordinates of the selected control point index to the new position of the mouse 
	 * @param mouseX, mouseY - X and Y coordinates of the mouse click relative to the screen, but on this panel
	 */
	public void updateSelectedWithMouse(int mouseX, int mouseY) {
		if (this.selected != -1) {
			Vector2 newPoint = mouseClickToWorldTransform(mouseX, mouseY);
			newPoint.x = Math.max(Math.min(1f, newPoint.x), -1f);
			newPoint.y = Math.max(Math.min(1f, newPoint.y), -1f);
			this.spline.setControlPoint(this.selected, newPoint);
		}
	}

	/**
	 * Unselect whichever control point was selected.
	 */
	public void unselect() {
		this.selected = -1;
	}

	/**
	 * Convert distance on the screen to world space
	 * @param dist The distance in pixels
	 * @return The distance in world space
	 */
	public double pixelToWorldDistance(double dist) {
		return dist / (SplinePanel.halfSide);
	}

	/**
	 * Change the mouse click to coordinates in the world space
	 * @param mouseX, mouseY - X and Y coordinates of the mouse click relative to the screen, but on this panel
	 */
	public Vector2 mouseClickToWorldTransform(int mouseX, int mouseY) {
		float relX = (mouseX - (this.index + 0.5f) * SplinePanel.panelWidth) / (SplinePanel.halfSide);
		float relY = (mouseY - 0.5f * SplinePanel.panelHeight) / (SplinePanel.halfSide);
		return new Vector2(relX, relY);
	}

	/**
	 * Change the mouse click to coordinates in the world space
	 * @param mouseClick A Vector2 object containing the position of the mouse click
	 */
	public Vector2 mouseClickToWorldTransform(Vector2 mouseClick) {
		return this.mouseClickToWorldTransform((int)mouseClick.x, (int)mouseClick.y);
	}
}
