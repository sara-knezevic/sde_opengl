package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.awt.TextRenderer;

import shapes.Cone;
import shapes.Cube;
import shapes.Cuboid;
import shapes.Cylinder;
import shapes.Pyramid;
import shapes.Sphere;

@SuppressWarnings("serial")
public class Blueprint extends JPanel implements GLEventListener, ActionListener {

//	private static String TITLE = "Blueprint";
//	private static final int WINDOW_WIDTH = 640;
//	private static final int WINDOW_HEIGHT = 640;
//	private static final int FPS = 60;
//	private GLWindow window;
//	private FPSAnimator animator;
	
	private static final int windowWidth = 900;
	private static final int windowHeight = 900;
	
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private static final char PRESS_A = 'A';
	private static final char PRESS_D = 'D';
	private static final char PRESS_S = 'S';
	private static final char PRESS_W = 'W';
	private static final char PRESS_N = 'N';
	private static final char PRESS_M = 'M';
	private static final char PRESS_H = 'H';
	private static final char PRESS_ENTER = 'E';
	private static int rotation_y = 0;
	private static int rotation_x = 0;
	
	private static int userSelectedShape = -1;
	
	ButtonGroup radioButtons = new ButtonGroup();
	JRadioButton cuboidButton;
	
	Random rand = new Random();
	List<String> shapes = Arrays.asList("Cube", "Cuboid", "Sphere", "Cylinder", "Pyramid", "Cone", "None");
	
	private int[] shapesIds = new int[7];
	private int nSides = rand.nextInt(4) + 3;
	private float[][] shapePositions = {{0, 0, 0, 1},
										{-1, 0, 0, 1},
										{1, 0, 0, 1},
										{0, 1, 0, 1},
										{0, -1, 0, 1},
										{0, 0, 1, 1},
										{0, 0, -1, 1}};
	
	private int[] userShapes = {-1, -1, -1, -1, -1, -1, -1};
								
	private int userLocation, userShape = -1;
	private Boolean submitCurrent = false;
	private Boolean help = true;
	private static Boolean isPlaying = true;
	
	private Boolean win = true;

	private TextRenderer txtRenderer;
	
	List<Boolean> isSelected = Arrays.asList(true, false, false, false, false, false, false);
	
	public Blueprint() {
		
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout(20, 20));
//		top.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		GLJPanel drawable = new GLJPanel();
        drawable.setPreferredSize(new Dimension(windowWidth, windowHeight));
        drawable.addGLEventListener(this);
        setLayout(new BorderLayout());
        add(drawable, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);
        
        JPanel east = new JPanel(new GridLayout(7,0));
        JRadioButton cubeButton = new JRadioButton( "Cube");
        cubeButton.addActionListener(this);
        JRadioButton cuboidButton = new JRadioButton( "Cuboid");
        cuboidButton.addActionListener(this);
        JRadioButton coneButton = new JRadioButton( "Cone");
        coneButton.addActionListener(this);
        JRadioButton cylinderButton = new JRadioButton( "Cylinder");
        cylinderButton.addActionListener(this);
        JRadioButton pyramidButton = new JRadioButton( "Pyramid");
        pyramidButton.addActionListener(this);
        JRadioButton sphereButton = new JRadioButton("Sphere");
        sphereButton.addActionListener(this);
        JRadioButton noneButton = new JRadioButton("None", true);
        noneButton.addActionListener(this);
        
        radioButtons.add(cubeButton);
        radioButtons.add(cuboidButton);
        radioButtons.add(coneButton);
        radioButtons.add(cylinderButton);
        radioButtons.add(pyramidButton);
        radioButtons.add(sphereButton);
        radioButtons.add(noneButton);
        
        east.add(cubeButton);
        east.add(cuboidButton);
        east.add(coneButton);
        east.add(cylinderButton);
        east.add(pyramidButton);
        east.add(sphereButton);
        east.add(noneButton);
        add(east, BorderLayout.EAST);
        
        
        top.getInputMap(IFW).put(KeyStroke.getKeyStroke("A"), PRESS_A);
        top.getActionMap().put(PRESS_A, new Keys('A'));
        
        top.getInputMap(IFW).put(KeyStroke.getKeyStroke("D"), PRESS_D);
        top.getActionMap().put(PRESS_D, new Keys('D'));
        
        top.getInputMap(IFW).put(KeyStroke.getKeyStroke("W"), PRESS_W);
        top.getActionMap().put(PRESS_W, new Keys('W'));
        
        top.getInputMap(IFW).put(KeyStroke.getKeyStroke("S"), PRESS_S);
        top.getActionMap().put(PRESS_S, new Keys('S'));
        
        top.getInputMap(IFW).put(KeyStroke.getKeyStroke("N"), PRESS_N);
        top.getActionMap().put(PRESS_N, new Keys('N'));
        
        top.getInputMap(IFW).put(KeyStroke.getKeyStroke("M"), PRESS_M);
        top.getActionMap().put(PRESS_M, new Keys('M'));
        
        top.getInputMap(IFW).put(KeyStroke.getKeyStroke("ENTER"), PRESS_ENTER);
        top.getActionMap().put(PRESS_ENTER, new Keys('E'));
        
        top.getInputMap(IFW).put(KeyStroke.getKeyStroke("H"), PRESS_H);
        top.getActionMap().put(PRESS_H, new Keys('H'));
	}
	
	private class Keys extends AbstractAction {
		char k;
		
		Keys(char k) {
			this.k = k;
		}
		
		@Override
        public void actionPerformed(ActionEvent e) {
			switch (k) {
			case 'W':
				rotation_x += 5;
				break;
			case 'A':
				rotation_y -= 5;
				break;
			case 'S':
				rotation_x -= 5;
				break;
			case 'D':
				rotation_y += 5;
				break;
			case 'N':
				selectShape('N');
				break;
			case 'M':
				selectShape('M');
				break;
			case 'E':
				submitCurrent = true;
				break;
			case 'H':
				if (help) {
					help = false;
				} else {
					help = true;
				}
				break;
			}
			
			repaint();
        }
	}
	
	public void helpScreen() {
		int positionX = windowWidth / 4;
		int positionY = windowHeight;
		
		txtRenderer.beginRendering(windowWidth, windowHeight);
		txtRenderer.setColor(1.0f, 0.0f, 1.0f, 1.0f);
		txtRenderer.draw("W/A/S/D - translate scene", positionX, positionY - 30);
		txtRenderer.draw("N/M - previous/next shape", positionX, positionY - 50);
		txtRenderer.draw("ENTER - submit current shape (or none)", positionX, positionY - 70);
		txtRenderer.draw("H - toggle help", positionX, positionY - 90);
		
		txtRenderer.endRendering();
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
		
		if (!isPlaying) {
			this.init(drawable);
		}
		
		setProjection(drawable);
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glTranslatef(0.0f,0.0f,-5.0f);
		gl.glRotatef(rotation_y, 0, 1, 0);
		gl.glRotatef(rotation_x, 0, 0, 1);
		
		// wireframe for blueprints 
//		gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_LINE );
		gl.glEnable(GL2.GL_POLYGON_OFFSET_LINE);
		gl.glPolygonOffset(-1,-1);
		
		gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_LINE );
		
		// BLUEPRINT
		generateObject(drawable);
		
		// iterate over selected shape
		
		gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
		
		// INSERTED OBJECTS 

		userLocation = isSelected.indexOf(true); // location chosen
		userShape = userSelectedShape; // shape chosen
		
		objectPicker(drawable, shapePositions[userLocation], userShape, true); // draw current browsing
		
		if (submitCurrent) { // submit current shape
			submitShape(drawable, userLocation, userShape);
		}
		
		if (help) {
			helpScreen();
		}
		
		win = gameWin(drawable);
		
		gl.glFlush();
	}
	
	public void submitShape(GLAutoDrawable drawable, int location, int shape) {
			userShapes[location] = shape;
			submitCurrent = false;
	}
	
	public void submittedShapes(GLAutoDrawable drawable) {
		for (int i = 0; i < userShapes.length; i++) { // draw submitted shapes
			objectPicker(drawable, shapePositions[i], userShapes[i], true);
		}
	}
	
	public Boolean gameWin(GLAutoDrawable drawable) {

		submittedShapes(drawable);
		
		for (int i = 0; i < userShapes.length; i++) {
			
			// check if shapes are chosen well
			if (userShapes[i] != shapesIds[i] && shapesIds[i] != 6) {
				win = false;
				break;
			} else {
				win = true;
			}
		}
		
		if (win) {
			isPlaying = false;
			
			txtRenderer.beginRendering(windowWidth, windowHeight);
			txtRenderer.setColor(0.4f, 0.19f, 0.12f, 1.0f);
			txtRenderer.draw("YOU DEFEATED!", windowWidth / 3, windowHeight / 4);
			txtRenderer.draw("Press any key to continue.", windowWidth / 3 - 20, windowHeight / 4 - 30);
			txtRenderer.endRendering();
		}
		
		return win;
	}
	
	public void selectShape(char direction) {

		int selectedShape = isSelected.indexOf(true);
		int nextSelect;
		
		if (direction == 'M') {
			
			nextSelect = checkArrayEnd(selectedShape, isSelected.size(), 1);
	
			while (shapesIds[nextSelect] == 6) {
				nextSelect = checkArrayEnd(nextSelect, shapesIds.length, 1);
			}
			
			isSelected.set(nextSelect, true);
		
		} else {
			
			nextSelect = checkArrayEnd(selectedShape, isSelected.size(), 0);
			
			while (shapesIds[nextSelect] == 6) {
				nextSelect = checkArrayEnd(nextSelect, shapesIds.length, 0);
			}
			
			isSelected.set(nextSelect, true);
			
		}
		
		isSelected.set(selectedShape, false);
	}
	
	public int checkArrayEnd(int i, int length, int direction) {
		
		if (direction == 1) { // front
			if (i == length - 1) {
				i = 0;
			} else {
				i += 1;
			}
		} else { 			  // back
			if (i == 0) {
				i = length - 1;
			} else {
				i -= 1;
			}
		}
		
		return i;
	}
	
	public void objectPicker(GLAutoDrawable drawable, float[] positions, int id, Boolean isSelected) {
		switch(id) {
		// Cube
		case 0:
			Cube cube = new Cube();
			cube.draw(drawable, positions[0], positions[1], positions[2], positions[3], isSelected);
			break;
		// Cuboid
		case 1:
			Cuboid cuboid = new Cuboid();
			cuboid.draw(drawable, positions[0], positions[1], positions[2], positions[3], isSelected);
			break;
		// Sphere
		case 2:
			Sphere sphere = new Sphere();
			sphere.draw(drawable, positions[0], positions[1], positions[2], positions[3], isSelected);
			break;
		// Cylinder
		case 3:
			Cylinder cylinder = new Cylinder();
			cylinder.draw(drawable, positions[0], positions[1], positions[2], positions[3], isSelected);
			break;
		// Pyramid
		case 4:
			Pyramid pyramid = new Pyramid(nSides);
			pyramid.draw(drawable, positions[0], positions[1], positions[2], positions[3], isSelected);
			break;
		// Cone
		case 5:
			Cone cone = new Cone();
			cone.draw(drawable, positions[0], positions[1], positions[2], positions[3], isSelected);
			break;
		default:
			break;
		}
	}
	
	public void generateObject(GLAutoDrawable drawable) {
		objectPicker(drawable, shapePositions[0], shapesIds[0], isSelected.get(0));
		
		// FUJCINA
		objectPicker(drawable, shapePositions[1], shapesIds[1], isSelected.get(1));
		objectPicker(drawable, shapePositions[2], shapesIds[2], isSelected.get(2));
		objectPicker(drawable, shapePositions[3], shapesIds[3], isSelected.get(3));
		objectPicker(drawable, shapePositions[4], shapesIds[4], isSelected.get(4));
		objectPicker(drawable, shapePositions[5], shapesIds[5], isSelected.get(5));
		objectPicker(drawable, shapePositions[6], shapesIds[6], isSelected.get(6));
	}
	
	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		txtRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
		
		// RGB, without alpha channel
		gl.glClearColor(1, 1, 1, 0);
		
		// smooth shading/colors
		gl.glShadeModel(GL2.GL_SMOOTH);
		
		// polygon mode
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		
		// depth test before drawing
		gl.glEnable(GL2.GL_DEPTH_TEST);
		
		gl.glLineWidth(3f);

//		gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_LINE );
		
		shapesIds[0] = rand.nextInt(3);
		
		for (int i = 1; i < shapesIds.length; i++) {
			shapesIds[i] = rand.nextInt(7);
		}
		
		isPlaying = true;
		
		for (int i = 0; i < userShapes.length; i++) {
			userShapes[i] = -1;
		}
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		switch (event.getActionCommand()) {
			case "Cube":
				userSelectedShape = 0;
				break;
			case "Cuboid":
				userSelectedShape = 1;
				break;
			case "Sphere":
				userSelectedShape = 2;
				break;
			case "Cylinder":
				userSelectedShape = 3;
				break;
			case "Pyramid":
				userSelectedShape = 4;
				break;
			case "Cone":
				userSelectedShape = 5;
				break;
			case "None":
				userSelectedShape = -1;
				break;
		}
		
		repaint();
		
	}
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Blueprint");
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Blueprint bp = new Blueprint();
		
		window.setContentPane(bp);
		window.pack();
		window.setLocation(50, 50);
		window.setVisible(true);
	}
}
