package main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Camera {

    private double viewX, viewY, viewZ = 30;
    private double refPointX, refPointY, refPointZ;
    private double upx, upy = 1, upz;

    private double minLimitScreenX = -5, maxLimitScreenX = 5;
    private double minLimitScreenY = -5, maxLimitScreenY = 5;
    private double extendminimumX = -10, extendZmin = 10;
    private boolean orthographic;
    private boolean preserveAspect = true;

    private double realMinX, realMaxX, realMinY, realMaxY;
    private GLU glu = new GLU();

    private FollowMouse trackball;
    private Component setComp;

    public boolean getOrthographic() {
        return orthographic;
    }

    public void setOrthographic(boolean orthographic) {
        this.orthographic = orthographic;
    }

    public boolean getPreserveAspect() {
        return preserveAspect;
    }

    public void setPreserveAspect(boolean preserveAspect) {
        this.preserveAspect = preserveAspect;
    }

    public void setLimits(double minimumX, double maximumX, double minumY, double maximumY, double extendminimumX, double extendZmin) {
        minLimitScreenX = realMinX = minimumX;
        maxLimitScreenX = realMaxX = maximumX;
        minLimitScreenY = realMinY = minumY;
        maxLimitScreenY = realMaxY = maximumY;
        this.extendminimumX = extendminimumX;
        this.extendZmin = extendZmin;
    }

    public void setScale(double limit) {
        setLimits(-limit, limit, -limit, limit, -2 * limit, 2 * limit);
    }

    public double[] getLimits() {
        return new double[]{minLimitScreenX, maxLimitScreenX, minLimitScreenY, maxLimitScreenY, extendminimumX, extendZmin};
    }

    public double[] getActualXYLimits() {
        return new double[]{realMinX, realMaxX, realMinY, realMaxY};
    }

    public void lookAt(double viewX, double viewY, double viewZ,
            double viewCenterX, double viewCenterY, double viewCenterZ,
            double viewUpX, double viewUpY, double viewUpZ) {
        viewX = viewX;
        viewY = viewY;
        viewZ = viewZ;
        refPointX = viewCenterX;
        refPointY = viewCenterY;
        refPointZ = viewCenterZ;
        upx = viewUpX;
        upy = viewUpY;
        upz = viewUpZ;
    }

    public double[] getViewParameters() {
        return new double[]{viewX, viewY, viewZ, refPointX, refPointY, refPointZ, upx, upy, upz};
    }

    public void apply(GL2 gl) {

        int[] viewport = new int[4];
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        realMinX = minLimitScreenX;
        realMaxX = maxLimitScreenX;
        realMinY = minLimitScreenY;
        realMaxY = maxLimitScreenY;

        if (preserveAspect) {
            double viewWidth = viewport[2];
            double viewHeight = viewport[3];
            double windowWidth = realMaxX - realMinX;
            double windowHeight = realMaxY - realMinY;
            double aspect = viewHeight / viewWidth;

            double desired = windowHeight / windowWidth;
            if (desired > aspect) {
                double extra = (desired / aspect - 1.0) * (realMaxX - realMinX) / 2.0;
                realMinX -= extra;
                realMaxX += extra;
            } else if (aspect > desired) {
                double extra = (aspect / desired - 1.0) * (realMaxY - realMinY) / 2.0;
                realMinY -= extra;
                realMaxY += extra;
            }
        }

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        double viewDistance = normalizationOfVectors(new double[]{refPointX - viewX, refPointY - viewY, refPointZ - viewZ});
        if (orthographic) {
            gl.glOrtho(realMinX, realMaxX, realMinY, realMaxY, viewDistance - extendZmin, viewDistance - extendminimumX);
        } else {
            double near = viewDistance - extendZmin;
            if (near < 0.1) {
                near = 0.1;
            }
            double centerx = (realMinX + realMaxX) / 2;
            double centery = (realMinY + realMaxY) / 2;
            double newwidth = (near / viewDistance) * (realMaxX - realMinX);
            double newheight = (near / viewDistance) * (realMaxY - realMinY);
            double x1 = centerx - newwidth / 2;
            double x2 = centerx + newwidth / 2;
            double y1 = centery - newheight / 2;
            double y2 = centery + newheight / 2;
            gl.glFrustum(x1, x2, y1, y2, near, viewDistance - extendminimumX);
        }
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(viewX, viewY, viewZ, refPointX, refPointY, refPointZ, upx, upy, upz);
    }

    public void setTrack(Component c) {
        if (setComp != null && setComp != c) {
            setComp.removeMouseListener(trackball);
        }
        if (setComp == c) {
            return;
        }
        setComp = c;
        if (setComp == null) {
            return;
        }
        if (trackball == null) {
            trackball = new FollowMouse();
        }
        setComp.addMouseListener(trackball);
    }

    private double normalizationOfVectors(double[] v) {
        double normalizationOfVectors2 = v[0] * v[0] + v[1] * v[1] + v[2] * v[2];
        if (Double.isNaN(normalizationOfVectors2) || Double.isInfinite(normalizationOfVectors2) || normalizationOfVectors2 == 0) {
            throw new NumberFormatException("Vector length zero, undefined, or infinite.");
        }
        return Math.sqrt(normalizationOfVectors2);
    }

    private void normalizedVector(double[] v) {
        double normalizationOfVectors = normalizationOfVectors(v);
        v[0] /= normalizationOfVectors;
        v[1] /= normalizationOfVectors;
        v[2] /= normalizationOfVectors;
    }

    private void rotateVectors(double[] e1, double[] e2) {
        double[] directionOfZ = new double[]{viewX - refPointX, viewY - refPointY, viewZ - refPointZ};
        double viewDistance = normalizationOfVectors(directionOfZ);
        normalizedVector(directionOfZ);
        double[] directionOfY = new double[]{upx, upy, upz};
        double upLength = normalizationOfVectors(directionOfY);
        double proj = directionOfY[0] * directionOfZ[0] + directionOfY[1] * directionOfZ[1] + directionOfY[2] * directionOfZ[2];
        directionOfY[0] = directionOfY[0] - proj * directionOfZ[0];
        directionOfY[1] = directionOfY[1] - proj * directionOfZ[1];
        directionOfY[2] = directionOfY[2] - proj * directionOfZ[2];
        normalizedVector(directionOfY);
        double[] xDirection = new double[]{directionOfY[1] * directionOfZ[2] - directionOfY[2] * directionOfZ[1],
            directionOfY[2] * directionOfZ[0] - directionOfY[0] * directionOfZ[2],
            directionOfY[0] * directionOfZ[1] - directionOfY[1] * directionOfZ[0]};
        e1 = transformToViewCoords(e1, xDirection, directionOfY, directionOfZ);
        e2 = transformToViewCoords(e2, xDirection, directionOfY, directionOfZ);
        double[] e = new double[]{e1[0] + e2[0], e1[1] + e2[1], e1[2] + e2[2]};
        normalizedVector(e);
        double[] temp = new double[3];
        axisRReflections(e, directionOfZ, temp);
        axisRReflections(e1, temp, directionOfZ);
        axisRReflections(e, xDirection, temp);
        axisRReflections(e1, temp, xDirection);
        axisRReflections(e, directionOfY, temp);
        axisRReflections(e1, temp, directionOfY);
        viewX = refPointX + viewDistance * directionOfZ[0];
        viewY = refPointY + viewDistance * directionOfZ[1];
        viewZ = refPointZ + viewDistance * directionOfZ[2];
        upx = upLength * directionOfY[0];
        upy = upLength * directionOfY[1];
        upz = upLength * directionOfY[2];
    }

    private void axisRReflections(double[] axisR, double[] sourceR, double[] fDestination) {
        double s = 2 * (axisR[0] * sourceR[0] + axisR[1] * sourceR[1] + axisR[2] * sourceR[2]);
        fDestination[0] = s * axisR[0] - sourceR[0];
        fDestination[1] = s * axisR[1] - sourceR[1];
        fDestination[2] = s * axisR[2] - sourceR[2];
    }

    private double[] transformToViewCoords(double[] v, double[] x, double[] y, double[] z) {
        double[] w = new double[3];
        w[0] = v[0] * x[0] + v[1] * y[0] + v[2] * z[0];
        w[1] = v[0] * x[1] + v[1] * y[1] + v[2] * z[1];
        w[2] = v[0] * x[2] + v[1] * y[2] + v[2] * z[2];
        return w;
    }

    private class FollowMouse implements MouseListener, MouseMotionListener {

        private boolean rotationDrag;
        private double[] prevPostionXY;

        public void mousePressed(MouseEvent e) {
            if (rotationDrag) {
                return;
            }
            rotationDrag = true;
            prevPostionXY = pointToPosition(e.getX(), e.getY());
            setComp.addMouseMotionListener(this);
        }

        public void mouseReleased(MouseEvent e) {
            if (!rotationDrag) {
                return;
            }
            rotationDrag = false;
            setComp.removeMouseMotionListener(this);
        }

        public void mouseDragged(MouseEvent e) {
            if (!rotationDrag) {
                return;
            }
            double[] thisRay = pointToPosition(e.getX(), e.getY());
            rotateVectors(prevPostionXY, thisRay);
            prevPostionXY = thisRay;
            setComp.repaint();
        }

        private double[] pointToPosition(int x, int y) {
            double dx, dy, dz, normalizationOfVectors;
            int centerX = setComp.getWidth() / 2;
            int centerY = setComp.getHeight() / 2;
            double scale = 0.8 * Math.min(centerX, centerY);
            dx = (x - centerX);
            dy = (centerY - y);
            normalizationOfVectors = Math.sqrt(dx * dx + dy * dy);
            if (normalizationOfVectors >= scale) {
                dz = 0;
            } else {
                dz = Math.sqrt(scale * scale - dx * dx - dy * dy);
            }
            double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
            return new double[]{dx / length, dy / length, dz / length};
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }

    }

}