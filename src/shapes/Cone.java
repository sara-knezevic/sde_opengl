package shapes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Cone {
	
	public Cone(GLAutoDrawable drawable, float x, float y, float z, float a) {
		
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU();
		
		float height = 1f;
		float width_base = 0.5f;
		float width_top = 0.0f;
		
		// current matrix on the stack
		gl.glPushMatrix();
		
		// x, y, z coordiantes of a translation vector
		gl.glTranslatef(x, y - height / 2 , z);
		
		gl.glScalef(a, a, a);
		
		// multiply the current matrix to get a rotation matrix
		gl.glRotatef(-90, 1, 0, 0);
		
		gl.glColor3f(0, 1, 0);
		GLUquadric quadric = glu.gluNewQuadric();
		glu.gluCylinder(quadric, width_base, width_top, height, 50, 50);
		draw_circle(gl, 0.0f, 0.0f, 0.0f, width_base);
//		draw_circle(gl, 0.0f, 0.0f, height, width_base);
		
		gl.glPopMatrix();
	}
	
	void draw_circle(GL2 gl, float x, float y, float z, float radius) {
	    gl.glTranslatef(x, y, z);
	    
	    int circle_points = 100;
	    float angle = 2.0f * 3.1416f / circle_points;

	    gl.glBegin(GL2.GL_POLYGON);
	    gl.glColor3f(1, 0, 0);
	    
	    double angle1=0.0;
	    gl.glVertex2d(radius * Math.cos(0.0) , radius * Math.sin(0.0));
	    int i;
	    for (i=0; i<circle_points; i++)
	    {       
	    	gl.glVertex2d(radius * Math.cos(angle1), radius * Math.sin(angle1));
	        angle1 += angle;
	    }
	    gl.glEnd();
	}
}
