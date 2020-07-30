package shapes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Sphere {
	
	public Sphere() {}
	
	public void draw(GLAutoDrawable drawable, float x, float y, float z, float a, Boolean isSelected) {
		
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU();
		
		gl.glPushMatrix();
		
		// x, y, z coordiantes of a translation vector
		gl.glTranslatef(x, y, z);
		
		gl.glScalef(a, a, a);
		
		// multiply the current matrix to get a rotation matrix
		gl.glRotatef(0, 0, 0, 0);
		
		if (!isSelected) {
			gl.glColor3f(0, 0, 0);
		} else {
			gl.glColor3f(0, 0, 1);
		}
		
		GLUquadric quadric = glu.gluNewQuadric();
		glu.gluSphere(quadric, 0.5, 10, 10);
		glu.gluDeleteQuadric(quadric);
		
		gl.glPopMatrix();
	}
}
