/*
 * Instructions:
 *  - IntelliJ on Mac: VM Options: -Djava.library.path=libs/LWJGL/native -XstartOnFirstThread
 *  - IntelliJ on Windows, Linux: VM Options: -Djava.library.path=libs/LWJGL/native
 */
package assignment01;

import org.joml.GeometryUtils;
import org.joml.Vector3f;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;




import java.text.DecimalFormat;

//import static org.joml.GeometryUtils.normal;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


public class HelloWorld {
 
    // references to callback function for error and keyboard handling
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
 
    // the window handle
    private long window;
    
    public static final DecimalFormat FLOAT_FORMAT = new DecimalFormat ("0.##");
 
    public void run() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
        
        triangleSolution(); // you need to provide the implementation for this function down below
 
        try {
        	init(); // initializes the window and OpenGL        
            renderingLoop(); // starts the rendering loop
 
            // release window and window callbacks when finished
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // terminate GLFW and release the GLFW errorcallback
            glfwTerminate();
            errorCallback.release();
        }
    }
    
        
    private void init() {
    	// init() contains everything that needs to be done only once at programstart
    	initWindow(); // initialize the window
        initKeyboardCallbacks(); // initialize the keyboard handling
        initOpenGLSettings(); // initialize OpenGL
    }


	private void initWindow() {
		// the initWindow() functions creates and displays an OpenGL window 
		// to display OpenGL's framebuffer
		
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
    	 glfwSetErrorCallback(errorCallback = createPrint((System.err)));
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
 
        int WIDTH = 300;
        int HEIGHT = 300;
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");        
 
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
        
    }
	
	
	private void initKeyboardCallbacks() {
	     // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
	}
 
	
	private void initOpenGLSettings() {
	    // This line is critical for LWJGL's interoperation with GLFW's OpenGL context
        GL.createCapabilities(); // valid for latest build
 
        // Set the clear color, in this case red //
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	}
	
    
    private void renderingLoop() {    
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            // the display function contains all your rendering calls for each frame
            display();
        	
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    
    
    private void display() {
    	// the display function should contain all your rendering calls
    	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer (color and depth)    	
        glfwSwapBuffers(window); // swap the color buffers
    }
    
    
    private void triangleSolution() {
    	/*TODO Implementieren Sie hier Ihre Lösung zu Aufgabe 1.4. Tipp: Informieren Sie sich über die 
    	 * Klassen org.joml.Vector3f sowie org.joml.GeometryUtils */
    	
    	
    	System.out.println("Aufgabe 1.4");
		Vector3f a = new Vector3f(4f, 1f, 0f);
		Vector3f b = new Vector3f(-1f, 3f, 2f);
		Vector3f c = new Vector3f(0, 2f, 1f);
		Vector3f normale = new Vector3f();
		Vector3f v = new Vector3f(1f, 1f, -3f);

		GeometryUtils.normal(a, b, c, normale);
		
		float radian = v.angle(normale);
		
		System.out.println("Normalenvektor des Dreiecks: " + normale.toString(FLOAT_FORMAT));
		System.out.println("Winkel zwischen Normalenvektor und Vektor v: " +  Math.toDegrees(radian) + "�");
	}
 
    
    public static void main(String[] args) {
        new HelloWorld().run();
    }
 
}