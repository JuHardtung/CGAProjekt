package assignment06.entities;

import assignment06.renderEngine.DisplayManager;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.text.DecimalFormat;
import java.util.Vector;

/**
 * Created by bryan on 28.10.2015.
 */
public class Camera {

    private static final float FOV = 70;

    private static final Vector3f DEFAULT_UP = new Vector3f(0, 1, 0);
    private static final DecimalFormat FORMAT = new DecimalFormat("#0.0000");
    private static final float MOVE_FACTOR = 0.5f;
    
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

    public void moveForward() {
        System.out.println("Forward");
        // Berechnung der Bewegungsrichtung
        Vector3f moveDirection = getCurrentLookDirection();
        moveDirection = moveDirection.mul(MOVE_FACTOR);
        
        if(camPos.y < 4.0f){
        	camPos.y = 4.0f;
        }
        camPos = camPos.normalize().add(moveDirection);
        System.out.println();
        System.out.println("MoveDirection: " + moveDirection.toString(FORMAT));
        System.out.println("CamPos: " + camPos.toString(FORMAT));

        
}

private Vector3f getCurrentLookDirection() {
        return new Vector3f((float) (Math.sin(phi) * Math.cos(theta)), (float) (Math.sin(theta)),
                        (float) (Math.cos(phi) * Math.cos(theta)));
}


public void moveBackward() {
        System.out.println();
        System.out.println("Backward");
        
        Vector3f moveDirection = getCurrentLookDirection();
        moveDirection = moveDirection.normalize().mul(MOVE_FACTOR * -1);
        camPos = camPos.add(moveDirection);

        System.out.println("MoveDirection: " + moveDirection.toString(FORMAT));
        System.out.println("CamPos: " + camPos.toString(FORMAT));
}


public void moveLeft(){
    System.out.println();
    System.out.println("Left");
    
	Vector3f moveDirection = getCurrentLookDirection();
	Vector3f leftDirection = new Vector3f(moveDirection).cross(DEFAULT_UP).negate();
	leftDirection = leftDirection.normalize().mul(MOVE_FACTOR);
	camPos = camPos.add(leftDirection);
	
    System.out.println("MoveDirection: " + moveDirection.toString(FORMAT));
    System.out.println("CamPos: " + camPos.toString(FORMAT));
}

public void moveRight(){
    System.out.println();
    System.out.println("Rigth");
    
	Vector3f moveDirection = getCurrentLookDirection();
	Vector3f rightDirection = new Vector3f(moveDirection).cross(DEFAULT_UP);
	rightDirection = rightDirection.normalize().mul(MOVE_FACTOR);
	camPos = camPos.add(rightDirection);
	
    System.out.println("MoveDirection: " + moveDirection.toString(FORMAT));
    System.out.println("CamPos: " + camPos.toString(FORMAT));
}



public void updateViewMatrix() {

        // TODO: Bestimmen Sie lookDir anhand von phi und theta neu. Eventuell
        // Ehemals OriginToCamera
	
        lookDir = getCurrentLookDirection().negate();
        Vector3f lookAt = new Vector3f(camPos).add(new Vector3f(new Vector3f(lookDir).mul(camDist)).negate());
        viewMatrix = new Matrix4f().lookAt(camPos, lookAt, DEFAULT_UP);

        // TODO: Außerdem müssen Sie einen lookAt Punkt aus camPos und lookDir

//         System.out.println();
//         System.out.println("CamPos: " + camPos.toString(FORMAT));
//         System.out.println("LookDir: " + lookDir.toString(FORMAT));
//         System.out.println("LookAt: " + lookAt.toString(FORMAT));
//         berechnen, um die viewMatrix mit lookAt() berechnen zu können.
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

