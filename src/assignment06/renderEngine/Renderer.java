package assignment06.renderEngine;

import assignment06.entities.*;
import assignment06.shaders.StaticShaderProgram;
import org.joml.MatrixStack;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
    private Camera camera;
    private PointLight light;
    private Material material;

	public Renderer(Camera camera, PointLight light, Material material){
		// set OpenGL parameters for rendering
		glClearColor(0.5f, 0.8f, 1.0f, 1);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
    	glEnable(GL_DEPTH_TEST);

        this.camera = camera;
        this.light = light;
        this.material = material;
	}

	public void render(ArrayList<Entity> entities,PointLight light, StaticShaderProgram shader){

        // update camera viewmatrix //
        camera.updateViewMatrix();

		// use shader //
		shader.start();
        shader.loadTexture();
        shader.loadLight(light);
        shader.loadViewMatrix(camera.getViewMatrix());

		for (Entity e : entities){
            renderTexturedEntity(shader, (TexturedEntity) e);
        }
		shader.stop();
		
		//System.out.println("Rendering finished.");
	}

    public void renderTexturedEntity(StaticShaderProgram shader, TexturedEntity e){
        shader.loadMaterial(material);
        //shader.loadModelColor(e.getColor());

        // bind VAO and activate VBOs //
        RawModel model = e.getModel();
        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        
        // TODO: Die Texturkoordinaten sind im VAO an Index 2 gespeichert. Es muss vorm Rendern aktiviert werden.
        // TODO: und nach dem Rendern deaktiviert werden.
        glEnableVertexAttribArray(2);
        // TODO: Legen Sie die aktive Textur in die Texture Unit GL_TEXTURE0.
        
        glActiveTexture(GL_TEXTURE0);
        // TODO: Binden Sie die Textur der Entity e mit glBindTexture anhand der textureID ihrer Textur.

        glBindTexture(GL_TEXTURE_2D, e.getTexture().getTextureID());
        // load model and projection matrix into shader //
        shader.loadModelMatrix(e.getModelMatrix());
        shader.loadProjectionMatrix(camera.getProjectionMatrix());

        // render model //
        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);

        // a good programmer should clean up //
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        // TODO: ...und nach dem Rendern deaktiviert werden.        
        glDisableVertexAttribArray(2);


        glBindVertexArray(0);
    }

	public void animate(ArrayList<Entity> entities){

		MatrixStack s = new MatrixStack(4);

		Vector3f[] translate = {new Vector3f(-4,-1.5f,-12), new Vector3f(4,-1.5f,-12), new Vector3f(-4,-1.5f,12), new Vector3f(4,-1.5f,12), new Vector3f(-1,-1f,0)};
		float[] rotate       = {(float)Math.toRadians(12.0), (float)Math.toRadians(86.0), (float)Math.toRadians(42.0), (float)Math.toRadians(123.0), (float)Math.toRadians(-90.0)};
		int i=0;
        s.pushMatrix();

        for(int l=0; l<5;l++) {
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
}

