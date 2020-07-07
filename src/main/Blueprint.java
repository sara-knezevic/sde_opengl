package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import shapes.Cone;
import shapes.Cube;
import shapes.Cuboid;
import shapes.Cylinder;
import shapes.Pyramid;
import shapes.Sphere;

public class Blueprint implements GLEventListener, KeyListener {

	private static String TITLE = "Blueprint";
	private static final int WINDOW_WIDTH = 640;
	private static final int WINDOW_HEIGHT = 640;
	private static final int FPS = 60;
	private GLWindow window;
	private FPSAnimator animator;
	
	private static int rotation_y = 0;
	private static int rotation_x = 0;
//	private Camera camera;
	
	public Blueprint() {
		
//		JPanel top = new JPanel();
//		top.setLayout(new BorderLayout(20, 20));
//		top.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
//		
//		GLJPanel drawable = new GLJPanel();
//		drawable.setPreferredSize(new Dimension(640, 640));
//        drawable.addGLEventListener(this);
        
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
//        setLayout(new BorderLayout());
//        add(drawable, BorderLayout.CENTER);
//        add(top, BorderLayout.NORTH);
		
		caps.setAlphaBits(8);
		caps.setDepthBits(24);
		caps.setDoubleBuffered(true);

//        camera = new Camera();
//        camera.setLimits(-10, 10, -10, 10, -10, 10);
//        camera.setTrack(this);
//		
		window = GLWindow.create(caps);
		animator = new FPSAnimator(window, FPS, true);
		
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyNotify(WindowEvent arg0) {
				new Thread() {
					@Override
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}.start();
			}
		});
		
		window.addGLEventListener(this);
		window.addKeyListener(this);
		
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setTitle(TITLE);
		window.setVisible(true);
		animator.start();
	}
	
	public void setProjection(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		// matrix projection - active matrix
		gl.glMatrixMode(GL2.GL_PROJECTION);
		
		// replace current matrix with identity matrix
		gl.glLoadIdentity();
		
		// switch current projection
		// orthographics OR perspective
		gl.glFrustum(-1, 1, -1, 1, 1, 20);
//		gl.glOrtho(-5, 5, -5, 5, 1, 11);
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		
		setProjection(drawable);
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
//		camera.apply(gl);

		gl.glClearColor(0.3f, 3f, 5f, 1f);
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glTranslatef(0.0f,0.0f,-10f);
		gl.glRotatef(rotation_y, 0, 1, 0);
		gl.glRotatef(rotation_x, 0, 0, 1);
		 
		// wireframe for blueprints 
//		gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_LINE );
		
		// code for drawing shapes
		new Cube(drawable, 0, 0, 1, 1);
		new Cylinder(drawable, 0, 1, 1, 1);
		new Cone(drawable, 0, 2, 1, 1);
		new Cube(drawable, 1, 0, -4, 1);
		new Cuboid(drawable, -1, -2, -4, 1);
		new Pyramid(drawable, -1, 0, -4, 1, 6);
		new Cylinder(drawable, 2, -1, -4, 1);
		new Cone(drawable, -1, 3, -4, 1);
		new Sphere(drawable, 0, 2, -4, 1);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				new Thread() {
					@Override
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}.start();
				break;
			case KeyEvent.VK_W:
				rotation_x += 5;
				break;
			case KeyEvent.VK_A:
				rotation_y -= 5;
				break;
			case KeyEvent.VK_S:
				rotation_x -= 5;
				break;
			case KeyEvent.VK_D:
				rotation_y += 5;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		// RGB, without alpha channel
		gl.glClearColor(1, 1, 1, 0);
		
		// smooth shading/colors
		gl.glShadeModel(GL2.GL_SMOOTH);
		
		// polygon mode
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		
		// depth test before drawing
		gl.glEnable(GL2.GL_DEPTH_TEST);
		
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}
}
