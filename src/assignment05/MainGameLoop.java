/*
 * Instructions:
 *  - on Mac: VM Options: -Djava.library.path=libs/LWJGL/native -XstartOnFirstThread
 */
package assignment05;

import assignment05.entities.Camera;
import assignment05.entities.Entity;
import assignment05.entities.Material;
import assignment05.entities.PointLight;
import assignment05.renderEngine.DisplayManager;
import assignment05.renderEngine.Loader;
import assignment05.renderEngine.Renderer;
import assignment05.shaders.StaticShaderProgram;
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

		// Setup window
		DisplayManager.createDisplay();

		PointLight light0 = new PointLight("white",
				new Vector3f(10,10,10),
				new Vector3f(1.0f,1.0f,1.0f),
				new Vector3f(1.0f,1.0f,1.0f),
				new Vector3f(1.0f,1.0f,1.0f),
				true);

		PointLight light1 = new PointLight("red",
				new Vector3f(10,10,0),
				new Vector3f(1.0f,1.0f,1.0f),
				new Vector3f(1.0f,0.0f,0.0f),
				new Vector3f(1.0f,1.0f,1.0f),
				false);

		PointLight light2 = new PointLight("green",
				new Vector3f(0,10,10),
				new Vector3f(1.0f,1.0f,1.0f),
				new Vector3f(0.0f,1.0f,0.0f),
				new Vector3f(1.0f,1.0f,1.0f),
				false);

		PointLight light3 = new PointLight("teal",
				new Vector3f(-10,10,10),
				new Vector3f(1.0f,1.0f,1.0f),
				new Vector3f(0.0f,0.5f,0.5f),
				new Vector3f(1.0f,1.0f,1.0f),
				false);

        PointLight light4 = new PointLight("blue",
				new Vector3f(-10,10,0),
                new Vector3f(1.0f,1.0f,1.0f),
                new Vector3f(0.0f,0.0f,1.0f),
                new Vector3f(1.0f,1.0f,1.0f),
				false);

        PointLight light5 = new PointLight("fuchsia",
				new Vector3f(-10,10,-10),
                new Vector3f(1.0f,1.0f,1.0f),
                new Vector3f(1.0f,0.0f,1.0f),
                new Vector3f(1.0f,1.0f,1.0f),
				false);

        PointLight light6 = new PointLight("yellow",
				new Vector3f(10,10,-10),
                new Vector3f(1.0f,1.0f,1.0f),
                new Vector3f(1.0f,1.0f,0.5f),
                new Vector3f(1.0f,1.0f,1.0f),
				false);

        PointLight light7 = new PointLight("purple",
				new Vector3f(10,10,-10),
                new Vector3f(1.0f,1.0f,1.0f),
                new Vector3f(0.8f,0.0f,0.8f),
                new Vector3f(1.0f,1.0f,1.0f),
				false);

		ArrayList<PointLight> lights = new ArrayList<>(8);

		lights.add(light0);
		lights.add(light1);
		lights.add(light2);
		lights.add(light3);
        lights.add(light4);
        lights.add(light5);
        lights.add(light6);
        lights.add(light7);

		Material whiteCeramic = new Material(new Vector3f(0,0,0),
				new Vector3f(0.0f,0.0f,0.0f),
				new Vector3f(1.0f,1.0f,1.0f),
				new Vector3f(1.0f,1.0f,1.0f),
				60.0f);

//		Material bluePlastic = new Material(new Vector3f(0,0,0),
//				new Vector3f(0.0f,0.0f,0.1f),
//				new Vector3f(0.1f,0.1f,1.0f),
//				new Vector3f(1.0f,1.0f,1.0f),
//				40.0f);
//
//		Material redMetal = new Material(new Vector3f(0,0,0),
//				new Vector3f(0.0f,0,0),
//				new Vector3f(1.0f,0.0f,0.0f),
//				new Vector3f(1,1,1),
//				10.0f);
//
//		Material flipFlopGreen = new Material(new Vector3f(0,0,0),
//				new Vector3f(0,0,0),
//				new Vector3f(0.0f,1.0f,0.0f),
//				new Vector3f(0,0,1),
//				20.0f);

		// Setup renderer and shaders
		StaticShaderProgram shader = new StaticShaderProgram();
        Loader loader              = new Loader();
        Camera camera              = new Camera(loader);
        Renderer renderer          = new Renderer(camera, lights, whiteCeramic);

        DisplayManager.setCamera(camera);
        DisplayManager.setRenderer(renderer);

        renderer.setCameraEntity(loader.loadEntity("camera"));
        renderer.setFrustumEntity(new Entity(camera.getFrustumModel()));

		ArrayList<Entity> entities = new ArrayList<Entity>();


        // load models //
		Entity ying = loader.loadEntity("dragon");
        Entity yang = new Entity(ying.getModel());

        // add models to list //
        entities.add(ying);
        entities.add(yang);

		while(glfwWindowShouldClose(DisplayManager.getWindow()) == GL_FALSE){
			// Clear framebuffer
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Manipulate model matrices //
			renderer.animate(entities);

            // Render models //
			renderer.render(entities, lights,  shader);

			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
