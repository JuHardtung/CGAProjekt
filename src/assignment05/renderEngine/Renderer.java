package assignment05.renderEngine;

import assignment05.entities.Camera;
import assignment05.entities.Entity;
import assignment05.entities.Material;
import assignment05.entities.PointLight;
import assignment05.shaders.StaticShaderProgram;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.MatrixStack;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
    private Camera camera;
    private Entity cameraEntity;
    private Entity frustumEntity;

    private ArrayList<PointLight> lights;
    private Material material;

    public enum RenderState {
        CV, SV,
    }
    boolean rotateLights = false;

    public RenderState renderState;
	
	public Renderer(Camera camera, ArrayList<PointLight> lights, Material material){
		// set OpenGL parameters for rendering
		glClearColor(0, 0, 0, 1);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
    	glEnable(GL_DEPTH_TEST);

        this.camera = camera;
        this.renderState = RenderState.CV;
        this.lights = lights;
        this.material = material;
	}

	public void render(ArrayList<Entity> entities, ArrayList<PointLight> lights, StaticShaderProgram shader){

        // update camera viewmatrix //
        camera.updateViewMatrix();

		// use shader //
		shader.start();

        if(rotateLights) rotateLights(lights);

        shader.loadLights(lights);

        // setup rendering according to renderstate //
        switch(renderState){
            case CV:{
                shader.loadViewMatrix(camera.getViewMatrix());
                break;
            }
            case SV:{
                cameraEntity.setModelMatrix(camera.getViewMatrix().invert());
                frustumEntity.setModelMatrix(new Matrix4f(cameraEntity.getModelMatrix()).mul(camera.getProjectionMatrix().invert()));

                renderInSceneView(cameraEntity, shader, GL_TRIANGLES);
                renderInSceneView(frustumEntity, shader, GL_LINES);
                break;
            }
        }


		for (Entity e : entities){

            shader.loadMaterial(material);
            //shader.loadModelColor(e.getColor());

			// bind VAO and activate VBOs //
			RawModel model = e.getModel();
			glBindVertexArray(model.getVaoID());
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);

			// load model and projection matrix into shader //
			shader.loadModelMatrix(e.getModelMatrix());
            shader.loadProjectionMatrix(camera.getProjectionMatrix());

			// render model //
			glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);

			// a good programmer should clean up //
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glBindVertexArray(0);

		}
		shader.stop();
	}

    public void renderInSceneView(Entity entity, StaticShaderProgram shader, int mode){

        shader.loadViewMatrix(new Matrix4f().lookAt(new Vector3f(-15, 10, 0), new Vector3f(0, 0, 0), new Vector3f(0, 1, 0)));
        RawModel model = entity.getModel();

        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // load model and projection matrix into shader
        shader.loadModelMatrix(entity.getModelMatrix());
        shader.loadProjectionMatrix(camera.getProjectionMatrixSceneView());

        // render model
        glDrawElements(mode, model.getVertexCount(), GL_UNSIGNED_INT, 0);

        // a good programmer should clean up
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }


	public void animate(ArrayList<Entity> entities){

		MatrixStack s = new MatrixStack(4);

		Vector3f[] translate = {new Vector3f(1,-1.5f,0), new Vector3f(-1,-1.5f,0)};
		float[] rotate       = {(float)Math.toRadians(90.0), (float)Math.toRadians(-90.0)};
		int i=0;
        s.pushMatrix();

        for(int l=0; l<2;l++) {
            s.pushMatrix();
            s.translate(translate[l]);
            s.rotate(rotate[l], 0, 1, 0);
            s.scale(0.3f, 0.3f, 0.3f);
            entities.get(i).setModelMatrix(s.getDirect());
            s.popMatrix();
            i++;
        }
        s.popMatrix();
	}

    public void rotateLights(ArrayList<PointLight> lights){
        // TODO: Aufgabe 5.4: Lassen Sie die Lichter um die Y-Achse um die Szene rotieren
    }

    public void switchLight(int i){
        if (i < lights.size()) {
            lights.get(i).setEnabled(!lights.get(i).isEnabled());
        }
        for(i=0; i<lights.size(); i++){
            System.out.println("status of light "+ i + " "+lights.get(i).getName() +" "+ lights.get(i).isEnabled());
        }
        System.out.println("#######################\n");
    }


    // TODO: Aufgabe 5.5: Schreiben Sie eine Funktion, mit der Materialeigenschaften verändert werden können.

    public void setRenderState(RenderState r) {
        this.renderState = r;
    }

    public boolean isRotateLights() {
        return rotateLights;
    }

    public void setRotateLights(boolean rotateLights) {
        this.rotateLights = rotateLights;
    }

    public void setCameraEntity(Entity cameraEntity){
        this.cameraEntity = cameraEntity;
    }

    public void setFrustumEntity(Entity frustumEntity) {
        this.frustumEntity = frustumEntity;
    }
}

