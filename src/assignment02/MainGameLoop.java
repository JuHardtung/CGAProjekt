/*
 * Instructions:
 *  - on Mac: VM Options: -Djava.library.path=libs/LWJGL/native -XstartOnFirstThread
 */
package assignment02;


import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.system.libffi.Closure;

import assignment02.entities.Entity;
import assignment02.renderEngine.*;
import assignment02.shaders.StaticShaderProgram;

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

		// Setup renderer and shaders
		StaticShaderProgram shader = new StaticShaderProgram();
		Renderer renderer          = new Renderer(shader);

		// Load model
		Loader loader = new Loader();
		Entity entity = loader.loadPyramid(
				new Vector3f(0, -0.4f, -2),
				new Vector3f(0, 0, 0),
				new Vector3f(1f, 1f, 1f));


		while(glfwWindowShouldClose(DisplayManager.getWindow()) == GL_FALSE){
			entity.increaseRotation(new Vector3f(0, 0.1f, 0));
			renderer.render(entity, shader);
			DisplayManager.updateDisplay();			
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
