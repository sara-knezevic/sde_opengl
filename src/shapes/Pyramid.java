package shapes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Pyramid {
	
	public Pyramid(GLAutoDrawable drawable, float x, float y, float z, float a, int sides) {
		
		GL2 gl = drawable.getGL().getGL2();
		
		// current matrix on the stack
		gl.glPushMatrix();
		
		// x, y, z coordiantes of a translation vector
		gl.glTranslatef(x, y, z);
		
		// multiply the current matrix to get a rotation matrix
		gl.glRotatef(0, 0, 0, 0);

		gl.glScalef(a, a, a);
		
		gl.glColor3f(0, 0, 1);
		
		switch (sides) {
		case 3:
			// base
			gl.glBegin(GL2.GL_TRIANGLES);
				gl.glVertex3f(-0.5f, 0, -0.29f);
				gl.glVertex3f(0f, 0, 0.58f);
				gl.glVertex3f(0.5f, 0, -0.29f);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
				gl.glColor3f(1, 0, 1);
				
				gl.glVertex3f(-0.5f, 0, -0.29f);
				gl.glVertex3f(0f, 0, 0.58f);
				gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
				gl.glColor3f(1f, 0.3f, 1);
				
				gl.glVertex3f(0f, 0, 0.58f);
				gl.glVertex3f(0.5f, 0, -0.29f);
				gl.glVertex3f(0.0f, 0.5f, 0);
			gl.glEnd();
		
			gl.glBegin(GL2.GL_TRIANGLES);
				gl.glColor3f(0.2f, 0.4f, 1);
				
				gl.glVertex3f(-0.5f, 0, -0.29f);
				gl.glVertex3f(0.5f, 0, -0.29f);
				gl.glVertex3f(0.0f, 0.5f, 0);
			gl.glEnd();
			break;
		case 4:
			// base
			gl.glBegin(GL2.GL_QUADS);
	            gl.glVertex3f(-0.5f, 0, -0.5f);
	            gl.glVertex3f(0.5f, 0, -0.5f);
	            gl.glVertex3f(0.5f, 0, 0.5f);
	            gl.glVertex3f(-0.5f, 0, 0.5f);
	        gl.glEnd();
	        
	        gl.glBegin(GL2.GL_TRIANGLES);
	        gl.glColor3f(1f, 0.3f, 1);
			
			gl.glVertex3f(-0.5f, 0, -0.5f);
			gl.glVertex3f(0.5f, 0, -0.5f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(0.2f, 0.4f, 1);
			
			gl.glVertex3f(0.5f, 0, -0.5f);
			gl.glVertex3f(0.5f, 0, 0.5f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(0.1f, 0.3f, 0.8f);
			
			gl.glVertex3f(0.5f, 0, 0.5f);
			gl.glVertex3f(-0.5f, 0, 0.5f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(1, 0.98f, 0.6f);
			
			gl.glVertex3f(-0.5f, 0, 0.5f);
			gl.glVertex3f(-0.5f, 0, -0.5f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			break;
		case 5:
			// base
			gl.glBegin(GL2.GL_POLYGON);
				gl.glVertex3f(-0.6f, 0f, 0.2f);
				gl.glVertex3f(-0.4f, 0f, -0.5f);
				gl.glVertex3f(0.4f, 0.0f, -0.5f);
				gl.glVertex3f(0.6f, 0.0f, 0.2f);
				gl.glVertex3f(0.0f, 0.0f, 0.6f);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(1, 0.98f, 0.6f);
			
			gl.glVertex3f(-0.6f, 0f, 0.2f);
			gl.glVertex3f(-0.4f, 0f, -0.5f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(0.35f, 0.1f, 0.48f);
			
			gl.glVertex3f(-0.4f, 0f, -0.5f);
			gl.glVertex3f(0.4f, 0.0f, -0.5f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(0.32f, 0.9f, 0.1f);
			
			gl.glVertex3f(0.4f, 0.0f, -0.5f);
			gl.glVertex3f(0.6f, 0.0f, 0.2f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(0.2f, 0.4f, 0.2f);
			
			gl.glVertex3f(0.6f, 0.0f, 0.2f);
			gl.glVertex3f(0.0f, 0.0f, 0.6f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(0.76f, 0.99f, 0.001f);
			
			gl.glVertex3f(0.0f, 0.0f, 0.6f);
			gl.glVertex3f(-0.6f, 0f, 0.2f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			break;
		case 6:
			// base
			gl.glBegin(GL2.GL_POLYGON);
				gl.glVertex3f(-0.5f, 0.0f, 1.0f);
				gl.glVertex3f( 0.5f, 0.0f, 1.0f);
				gl.glVertex3f(1.0f, 0.0f, 0.0f);
				gl.glVertex3f(0.5f, 0.0f, -1.0f);
				gl.glVertex3f(-0.5f, 0.0f, -1.0f);
				gl.glVertex3f(-1.0f, 0.0f, 0.0f);
				gl.glVertex3f(-0.5f, 0.0f, 1.0f);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
	        gl.glColor3f(0.035f, 0.0125f, 0);
			
			gl.glVertex3f(-0.5f, 0.0f, 1.0f);
			gl.glVertex3f( 0.5f, 0.0f, 1.0f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(0.76f, 0.99f, 0.001f);
			
			gl.glVertex3f( 0.5f, 0.0f, 1.0f);
			gl.glVertex3f(1.0f, 0.0f, 0.0f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(0.2f, 0.4f, 0.2f);
			
			gl.glVertex3f(1.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.5f, 0.0f, -1.0f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(0.32f, 0.9f, 0.1f);
			
			gl.glVertex3f(0.5f, 0.0f, -1.0f);
			gl.glVertex3f(-0.5f, 0.0f, -1.0f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(0.35f, 0.1f, 0.48f);
			
			gl.glVertex3f(-0.5f, 0.0f, -1.0f);
			gl.glVertex3f(-1.0f, 0.0f, 0.0f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glColor3f(1, 0.98f, 0.6f);
			
			gl.glVertex3f(-1.0f, 0.0f, 0.0f);
			gl.glVertex3f(-0.5f, 0.0f, 1.0f);
			gl.glVertex3f(0f, 0.5f, 0);
			gl.glEnd();
			
			break;
		}
		
		gl.glPopMatrix();
	}
}
