package assignment03.renderEngine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.libffi.Closure;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayManager {

    private static int width = 1280;
    private static int height = 720;
    private static float aspect = 16.0f/9.0f;

    private static GLFWKeyCallback keyCallback;

    @SuppressWarnings("unused")
    private static GLFWErrorCallback errorCallback;  // starke Referenz auf den errorCallback ist wegen Garbage Collection nötig
    @SuppressWarnings("unused")
    private static GLFWWindowSizeCallback windowCallback;
    @SuppressWarnings("unused")
    private static Closure debug;                     // starke Referenz auf debug ist wegen Garbage Collection nötig
    // The window handle
    private static long window;

    public static long getWindow() {
        return window;
    }

    public static void createDisplay() {

        glfwSetErrorCallback(errorCallback = createPrint((System.err)));

        initOpenGL();

        initWindow();
                
        initCallbackFunctions(); // set mouse and keyboard interaction  
        
        debug = GLUtil.setupDebugMessageCallback(); // after
        // GL.createCapabilities()
        System.out.println("Your OpenGL version is " + glGetString(GL_VERSION));
    }

	private static void initWindow() {
		// Das Fenster erzeugen.
        window = glfwCreateWindow(width, height, "Exercise 02 - Play with the bunny (Armadillo)", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Auflösung des primären Displays holen.
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        // Fenster zentrieren
        glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);
        
        // Den GLFW Kontext aktuell machen.
        glfwMakeContextCurrent(window);         
        
        // GL Kontext unter Berücksichtigung des Betriebssystems erzeugen.
        GL.createCapabilities();

        // Synchronize to refresh rate
        glfwSwapInterval(0);
        
        // Das Fenster sichtbar machen.
        glfwShowWindow(window);
	}

	private static void initCallbackFunctions() {
		// Key-Callback aufsetzen. Wird jedes mal gerufen, wenn eine Taste
        // gedrückt oder losgelassen wird.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, GL_TRUE);
            }
        });
        
        glfwSetWindowSizeCallback(window, windowCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                updateWidthHeight(width, height);

            }
        });
	}

	private static void initOpenGL() {
		// GLFW Initialisieren. Die meisten GLFW-Funktionen funktionieren vorher nicht.
        if (glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        // Konfigurieren des Fensters
        glfwDefaultWindowHints(); // optional, die aktuellen Window-Hints sind bereits Standardwerte
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // Das Fenster bleibt nach dem Erzeugen versteckt.
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // Die Fenstergröße lässt sich verändern.
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE); // Windowhint für den Debug Kontext
	}

    public static void updateDisplay() {
        glfwPollEvents();
        glViewport(0, 0, width, height);
        glfwSwapBuffers(window);
    }
    private static void updateWidthHeight(int w, int h) {

        width = w;
        height = (int)(w/aspect);
        glfwSetWindowSize (window, width, height);
    }

    public static void closeDisplay() {
        glfwDestroyWindow(window);
        keyCallback.release();

    }

}
