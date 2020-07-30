package shapes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Cuboid {

	public void draw(GLAutoDrawable drawable, float x, float y, float z, float a, Boolean isSelected) {
		
		GL2 gl = drawable.getGL().getGL2();
		
		// current matrix on the stack
		gl.glPushMatrix();
		
		// x, y, z coordiantes of a translation vector
		gl.glTranslatef(x, y, z);
		
		gl.glScalef(a, a, a);
		
		// multiply the current matrix to get a rotation matrix
		gl.glRotatef(0, 0, 0, 0);

		// creating a cube
		gl.glBegin(GL2.GL_QUADS);
		
		if (!isSelected) {
        
        gl.glColor3f(0, 0, 0); // back
            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
            gl.glVertex3f(0.5f, -0.5f, -0.8f);
            gl.glVertex3f(0.5f, 0.5f, -0.8f);
            gl.glVertex3f(-0.5f, 0.5f, -0.8f);
        
         gl.glColor3f(0, 0, 0); // up
            gl.glVertex3f(+0.5f, +0.5f, +0.8f);
            gl.glVertex3f(+0.5f, +0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, +0.8f);
            
         gl.glColor3f(0, 0, 0); // down
            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
            gl.glVertex3f(+0.5f, -0.5f, -0.8f);
            gl.glVertex3f(+0.5f, -0.5f, +0.8f); 
         
         gl.glColor3f(0, 0, 0); // left
            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, +0.8f); 
         
         gl.glColor3f(0, 0, 0); // right
            gl.glVertex3f(+0.5f, -0.5f, +0.8f);
            gl.glVertex3f(+0.5f, -0.5f, -0.8f);
            gl.glVertex3f(+0.5f, +0.5f, -0.8f);
            gl.glVertex3f(+0.5f, +0.5f, +0.8f);  
          
          gl.glColor3f(0, 0, 0); // front
            gl.glVertex3f(+0.5f, +0.5f, +0.8f);
            gl.glVertex3f(+0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, +0.5f, +0.8f);
		} else {

	        gl.glColor3f(1, 0, 0); // back
	            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
	            gl.glVertex3f(0.5f, -0.5f, -0.8f);
	            gl.glVertex3f(0.5f, 0.5f, -0.8f);
	            gl.glVertex3f(-0.5f, 0.5f, -0.8f);
	        
	         gl.glColor3f(0, 1, 0); // up
	            gl.glVertex3f(+0.5f, +0.5f, +0.8f);
	            gl.glVertex3f(+0.5f, +0.5f, -0.8f);
	            gl.glVertex3f(-0.5f, +0.5f, -0.8f);
	            gl.glVertex3f(-0.5f, +0.5f, +0.8f);
	            
	         gl.glColor3f(0, 0, 1); // down
	            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
	            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
	            gl.glVertex3f(+0.5f, -0.5f, -0.8f);
	            gl.glVertex3f(+0.5f, -0.5f, +0.8f); 
	         
	         gl.glColor3f(1, 0.5f, 0); // left
	            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
	            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
	            gl.glVertex3f(-0.5f, +0.5f, -0.8f);
	            gl.glVertex3f(-0.5f, +0.5f, +0.8f); 
	         
	         gl.glColor3f(1, 0, 1); // right
	            gl.glVertex3f(+0.5f, -0.5f, +0.8f);
	            gl.glVertex3f(+0.5f, -0.5f, -0.8f);
	            gl.glVertex3f(+0.5f, +0.5f, -0.8f);
	            gl.glVertex3f(+0.5f, +0.5f, +0.8f);  
	          
	          gl.glColor3f(0.6f, 0, 1); // front
	            gl.glVertex3f(+0.5f, +0.5f, +0.8f);
	            gl.glVertex3f(+0.5f, -0.5f, +0.8f);
	            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
	            gl.glVertex3f(-0.5f, +0.5f, +0.8f);
		}
				
		gl.glEnd();
		
		gl.glPopMatrix();
	}

	public Cuboid() {}
}
