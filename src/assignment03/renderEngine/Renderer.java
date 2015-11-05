package assignment03.renderEngine;

import assignment03.entities.Entity;
import assignment03.shaders.StaticShaderProgram;
import org.joml.Matrix4f;
import org.joml.MatrixStack;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;


public class Renderer {
	
	
	float angleGlobalArmadillo = 0.0f;
	float angleArmadillo = 0.0f;

    private Matrix4f projectionMatrix = new Matrix4f(
            0.8033f, 0, 0, 0,
            0, 1.326f, -0.3714f, -0.3714f,
            0, -0.5304f, -0.9285f, -0.9285f,
            0, -0.0000001702f, 5.365f, 5.385f);

    public Renderer() {
        // set OpenGL parameters for rendering
        glClearColor(0, 0, 0, 1);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glEnable(GL_DEPTH_TEST);
    }


    public void render(ArrayList<Entity> entities, StaticShaderProgram shader) {

        // start rendering //

        // use shader
        shader.start();

        for (Entity e : entities) {

            // bind VAO and activate vbo
            RawModel model = e.getModel();
            glBindVertexArray(model.getVaoID());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            // load model and projection matrix into shader
            shader.loadModelMatrix(e.getModelMatrix());

            Matrix4f view = new Matrix4f();
            view.translate(0, -1.5f, -2.5f);
            Matrix4f viewProj = new Matrix4f(projectionMatrix);
            viewProj.mul(view);

            shader.loadProjectionMatrix(viewProj);

            // render scene
            glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);

            // a good programmer should clean up
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glBindVertexArray(0);

        }

        shader.stop();
    }

    public void animate(ArrayList<Entity> entities) {
        // TODO: Aufgabe 1: Rendern Sie zwei Armadillos, die sich angucken    	

    	MatrixStack mv = new MatrixStack(3); 
    	
    	long intervalInMs = 1000;
    	long nextRun = System.currentTimeMillis() + intervalInMs;
    	dance(entities, mv);
    	if (nextRun < System.currentTimeMillis()) {
    		try {
				Thread.sleep(nextRun - System.currentTimeMillis());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    //pair 1	
    	mv.pushMatrix();
    	mv.translate(2,  0,  2);
    	mv.rotateY((float) Math.toRadians(angleArmadillo));
   			mv.pushMatrix();
    		mv.translate(1, 0, 0);
   			mv.rotateY((float) Math.toRadians(90));
   			Matrix4f current0 = new Matrix4f();
   			mv.get(current0);
   			mv.popMatrix();	
    		entities.get(0).setModelMatrix(current0);
    
    		mv.pushMatrix();
    		mv.translate(-1, 0, 0);
   			mv.rotateY((float) Math.toRadians(-90));
   			Matrix4f current1 = new Matrix4f();
   			mv.get(current1);
   			mv.popMatrix();  	
    		entities.get(1).setModelMatrix(current1);
    	mv.popMatrix();
    	
    //pair 2	
    	mv.pushMatrix();
    	mv.translate(2, 0, -2);
    	mv.rotateY((float) Math.toRadians(angleArmadillo));
    		mv.pushMatrix();
    		mv.translate(1, 0, 0);
    		mv.rotateY((float) Math.toRadians(90));
    		Matrix4f current2 = new Matrix4f();
    		mv.get(current2);
    		mv.popMatrix();
    		entities.get(2).setModelMatrix(current2);
    	    	
    		mv.pushMatrix();
    		mv.translate(-1, 0, 0);
    		mv.rotateY((float) Math.toRadians(-90));
    		Matrix4f current3 = new Matrix4f();
    		mv.get(current3);
    		mv.popMatrix(); 	
    		entities.get(3).setModelMatrix(current3);
    	mv.popMatrix();
    	
    //pair 3
    	mv.pushMatrix();
    	mv.translate(-2, 0, 2);
    	mv.rotateY((float) Math.toRadians(angleArmadillo));
    		mv.pushMatrix();
    		mv.translate(1, 0, 0);
    		mv.rotateY((float) Math.toRadians(90));
    		Matrix4f current4 = new Matrix4f();
    		mv.get(current4);
    		mv.popMatrix();  	
    		entities.get(4).setModelMatrix(current4);
    	    	
    		mv.pushMatrix();
    		mv.translate(-1, 0, 0);
    		mv.rotateY((float) Math.toRadians(-90));
    		Matrix4f current5 = new Matrix4f();
    		mv.get(current5);
    		mv.popMatrix();
    		entities.get(5).setModelMatrix(current5);
    	mv.popMatrix();
    		
    //pair 4
    	mv.pushMatrix();
    	mv.translate(-2, 0, -2);
    	mv.rotateY((float) Math.toRadians(angleArmadillo));
    		mv.pushMatrix();
    		mv.translate(1, 0, 0);
    		mv.rotateY((float) Math.toRadians(90));
    		Matrix4f current6 = new Matrix4f();
    		mv.get(current6);
    		mv.popMatrix();	
    		entities.get(6).setModelMatrix(current6);
    	    	
    		mv.pushMatrix();
    		mv.translate(-1, 0,0);
    		mv.rotateY((float) Math.toRadians(-90));
    		Matrix4f current7 = new Matrix4f();
    		mv.get(current7);
    		mv.popMatrix();
    		entities.get(7).setModelMatrix(current7);
    	mv.popMatrix();
    	
    	
        // TODO: Aufgabe 2: Lassen sie das Paar tanzen (rotieren um die Y-Achse)
    	
        // TODO: Aufgabe 3: Rendern sie 2x2 Paare (also insgesamt 8)  von Armadillos, die jeweils für sich tanzen

        // TODO: Aufgabe 4: Lassen Sie die gesamte Szene zusätzlich rotieren (ergibt einen schönen Walzer)
    }
    
    public void dance(ArrayList<Entity> entities, MatrixStack mv) {

    	mv.rotateY((float) Math.toRadians(angleArmadillo));

       	angleArmadillo += 1.08f;
       	angleGlobalArmadillo += 360/10;
    	
    	System.out.println("Angle: " + angleArmadillo);
    	System.out.println("Full rotations: " + angleArmadillo/360);
    	
    	Matrix4f current = new Matrix4f();
    	mv.get(current);
    	
    	for (Entity entity : entities) {
			entity.getModelMatrix().mul(mv.get(current));
		}
    }
}
