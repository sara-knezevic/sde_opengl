package shapes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Sphere {
	
	public Sphere(GLAutoDrawable drawable, float x, float y, float z, float a) {
		
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU();
		
		gl.glPushMatrix();
		
		// x, y, z coordiantes of a translation vector
		gl.glTranslatef(x, y, z);
		
		gl.glScalef(a, a, a);
		
		// multiply the current matrix to get a rotation matrix
		gl.glRotatef(0, 0, 0, 0);
		
		gl.glColor3f(0, 0, 1);
		GLUquadric quadric = glu.gluNewQuadric();
		glu.gluSphere(quadric, 1, 50, 15);
		glu.gluDeleteQuadric(quadric);
		
		gl.glPopMatrix();
	}
}
