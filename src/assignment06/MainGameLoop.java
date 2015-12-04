/*
 * Instructions:
 *  - on Mac: VM Options: -Djava.library.path=libs/LWJGL/native -XstartOnFirstThread
 */
package assignment06;

import assignment06.entities.*;
import assignment06.textures.ModelTexture;
import assignment06.renderEngine.DisplayManager;
import assignment06.renderEngine.Loader;
import assignment06.renderEngine.Renderer;
import assignment06.shaders.StaticShaderProgram;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.system.libffi.Closure;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

public class MainGameLoop {

    @SuppressWarnings("unused")
    private static GLFWErrorCallback errorCallback;
    @SuppressWarnings("unused")
    private static GLFWKeyCallback keyCallback;
    @SuppressWarnings("unused")
    private static Closure debug;

    public static void main(String[] args) {

    	try{
        // Setup window
        DisplayManager.createDisplay();

        PointLight light = new PointLight(new Vector3f(20,20,0),
                new Vector3f(0.5f,0.5f,0.5f),
                new Vector3f(1.0f,1.0f,1.0f),
                new Vector3f(1.0f,1.0f,1.0f));

        Material whiteCeramic = new Material(new Vector3f(0,0,0),
                new Vector3f(0.1f,0.1f,0.1f),
                new Vector3f(0.0f,0.0f,0.0f),
                60.0f);

        // Setup renderer and shaders
        StaticShaderProgram shader = new StaticShaderProgram();
        Loader loader              = new Loader();
        Camera camera              = new Camera();
        Renderer renderer          = new Renderer(camera, light, whiteCeramic);

        DisplayManager.setCamera(camera);
        DisplayManager.setRenderer(renderer);

        ArrayList<Entity> entities = new ArrayList<Entity>();

        Entity ball = loader.loadEntity("ball");
        ModelTexture ballTexture = new ModelTexture(loader.loadTexture("ball"));
        TexturedEntity ballEntity = new TexturedEntity(ball.getModel(), ballTexture);

        Entity trashbin = loader.loadEntity("trashbin");
        ModelTexture trashbinTexture = new ModelTexture(loader.loadTexture("trashbin"));
        TexturedEntity trashbinEntity = new TexturedEntity(trashbin.getModel(), trashbinTexture);

        Entity floor = loader.loadEntity("floor");
        ModelTexture floorTexture = new ModelTexture(loader.loadTexture("floor"));
        TexturedEntity floorEntity = new TexturedEntity(floor.getModel(), floorTexture);

        entities.add(trashbinEntity);
        entities.add(new TexturedEntity(trashbin.getModel(), trashbinTexture));
        entities.add(new TexturedEntity(trashbin.getModel(), trashbinTexture));
        entities.add(new TexturedEntity(trashbin.getModel(), trashbinTexture));

        entities.add(ballEntity);
        entities.add(floorEntity);    	

        while(glfwWindowShouldClose(DisplayManager.getWindow()) == GL_FALSE){
            // Clear framebuffer
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Manipulate model matrices //
            renderer.animate(entities);

            // Render models //
            renderer.render(entities, light,  shader);

            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
        
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	finally{
    		
    	}
    }

}
