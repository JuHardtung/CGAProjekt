/*
 * Instructions:
 *  - on Mac: VM Options: -Djava.library.path=libs/LWJGL/native -XstartOnFirstThread
 */
package assignment03;


import assignment03.entities.Entity;
import assignment03.renderEngine.DisplayManager;
import assignment03.renderEngine.Loader;
import assignment03.renderEngine.Renderer;
import assignment03.shaders.StaticShaderProgram;
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

		/* Setup window */
		DisplayManager.createDisplay();
		
		/* Setup renderer and shaders */
		StaticShaderProgram shader = new StaticShaderProgram();
		Renderer renderer          = new Renderer();
		
		/* Load model */
		Loader loader = new Loader();
		ArrayList<Entity> entities = new ArrayList<Entity>();

		Entity armadillo = loader.loadEntity("armadillo");


		entities.add(armadillo);


		/* TODO: Fügen Sie die benötigte Anzahl von Armadillos in die Entities-Liste ein.
		So können sie weitere Instanzen des Models erzeugen:
		Entity entity = new Entity(armadillo.getModel());
		*/
		
		Entity armadillo1 = new Entity(armadillo.getModel());
		Entity armadillo2 = new Entity(armadillo.getModel());
		Entity armadillo3 = new Entity(armadillo.getModel());
		Entity armadillo4 = new Entity(armadillo.getModel());
		Entity armadillo5 = new Entity(armadillo.getModel());
		Entity armadillo6 = new Entity(armadillo.getModel());
		Entity armadillo7 = new Entity(armadillo.getModel());

		entities.add(armadillo1);
		entities.add(armadillo2);
		entities.add(armadillo3);
		entities.add(armadillo4);
		entities.add(armadillo5);
		entities.add(armadillo6);
		entities.add(armadillo7);

		while(glfwWindowShouldClose(DisplayManager.getWindow()) == GL_FALSE){
			/* Clear framebuffer */
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			/* Animate all Entities in List */
			renderer.animate(entities);
			renderer.render(entities, shader);

			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}		
}
