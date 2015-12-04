package assignment06.entities;

import assignment06.renderEngine.DisplayManager;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Vector;

/**
 * Created by bryan on 28.10.2015.
 */
public class Camera {

    private static final float FOV = 70;

    private Matrix4f viewMatrix = new Matrix4f();
    private Matrix4f projectionMatrix = new Matrix4f();
    private Vector3f camPos = new Vector3f();
    private Vector3f lookDir = new Vector3f();
    private Vector3f up = new Vector3f();

    private float theta = (float)Math.PI * 0.4f;
    private float phi = (float)Math.PI * 1.0f;
    private float camDist = 28.0f;

    public Camera(){
        this.projectionMatrix = createProjectionMatrix(4.0f, 200.0f);
        this.camPos = new Vector3f(0, camDist, 0);
        this.lookDir = new Vector3f(0,camDist,0);

        Matrix3f mat = new Matrix3f();
        mat.rotateY(phi);
        mat.rotateZ(theta);
        this.camPos.mul(mat);
        this.lookDir.mul(mat);
        this.lookDir.negate();

        this.up = new Vector3f(0,1,0);
        this.updateViewMatrix();
    }

    public Matrix4f getProjectionMatrix(){
        return new Matrix4f(this.projectionMatrix);
    }

    public Matrix4f getViewMatrix(){
        return new Matrix4f(this.viewMatrix);
    }

    public void incrementTheta(float dTheta){
        this.theta -= dTheta;
    }

    public void incrementPhi(float dPhi){
        this.phi -= dPhi;
    }

    public void setDist(float dy){
        camDist = Math.max(camDist +(dy/5.0f), 3.0f);
    }

    public void moveForward(){
    	// TODO: Verschieben Sie camPos in Richtung von lookDir.
    }

    public void moveBackward(){
    	 // TODO: Verschieben Sie camPos in Gegenrichtung von lookDir.
    }

    public void updateViewMatrix(){
        // Compute new camera position //
        lookDir.x=0;
        lookDir.y=camDist;
        lookDir.z=0;
        
        // TODO: Bestimmen Sie lookDir anhand von phi und theta neu. Eventuell müssen Sie die Richtung von lookDir umkehren.
        // TODO: Außerdem müssen Sie einen lookAt Punkt aus camPos und lookDir berechnen, um die viewMatrix mit lookAt() berechnen zu können.        
        viewMatrix = new Matrix4f().lookAt(new Vector3f(28,6, 0), new Vector3f(0,0,0), new Vector3f(0,1,0));
    }

    private Matrix4f createProjectionMatrix(float near, float far){
        float aspectRatio= (float) DisplayManager.getWidth()/ (float) DisplayManager.getHeight() ;
        float y_scale = (float) (1f /Math.tan(Math.toRadians(FOV/2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far - near;

        Matrix4f m = new Matrix4f();
        m.m00 = x_scale;
        m.m11 = y_scale;
        m.m22 = -((far + near) / frustum_length);
        m.m23 = -1;
        m.m32 = -((2 * near * far) / frustum_length);
        m.m33 = 0;
        return m;
    }
}

