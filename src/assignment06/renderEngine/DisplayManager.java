package assignment06.renderEngine;

import assignment06.entities.Camera;
import org.lwjgl.glfw.*;
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

    private static float mouseX = 0;
    private static float mouseY = 0;

    private static GLFWKeyCallback keyCallback;

    @SuppressWarnings("unused")
    private static GLFWCursorPosCallback cursorPosCallback;
    @SuppressWarnings("unused")
    private static GLFWErrorCallback errorCallback;
    @SuppressWarnings("unused")
    private static GLFWWindowSizeCallback windowCallback;
    @SuppressWarnings("unused")
    private static GLFWScrollCallback scrollCallback;
    @SuppressWarnings("unused")
    private static Closure debug;
    // The window handle
    private static long window;

    private static Camera camera;
    private static Renderer renderer;

    public static long getWindow() {
        return window;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void createDisplay() {

        glfwSetErrorCallback(errorCallback = createPrint((System.err)));

        initOpenGL();

        initWindow();
                
        initCallbackFunctions(); // set mouse and keyboard interaction  
        
        debug = GLUtil.setupDebugMessageCallback(); // after
        System.out.println("Your OpenGL version is " + glGetString(GL_VERSION));
    }

	private static void initWindow() {
		// Das Fenster erzeugen.
        window = glfwCreateWindow(width, height, "Exercise 06 - Ghetto Soccer", NULL, NULL);
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

        // Synchronize to refresh rate.
        glfwSwapInterval(0);

        glfwSetInputMode(window,  GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        
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
                if (key == GLFW_KEY_W  && (action == GLFW_PRESS || action == GLFW_REPEAT))
                    camera.moveForward();
                if (key == GLFW_KEY_S  && (action == GLFW_PRESS || action == GLFW_REPEAT))
                    camera.moveBackward();
                if (key == GLFW_KEY_A  && (action == GLFW_PRESS || action == GLFW_REPEAT))
                    camera.moveLeft();
                if (key == GLFW_KEY_D && (action == GLFW_PRESS || action == GLFW_REPEAT))
                    camera.moveRight();
            }
        });

        glfwSetWindowSizeCallback(window, windowCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                updateWidthHeight(width, height);
            }
        });

        glfwSetScrollCallback(window, scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xOffset, double dw) {
                camera.setDist((float)dw);
            }
        });

        glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback(){
            @Override
            public void invoke(long window, double mx, double my) {
                camera.incrementPhi(((float) mx - mouseX) * 0.001f);
                camera.incrementTheta(((float) my - mouseY) * 0.001f);
                mouseX = (float)mx;
                mouseY = (float)my;
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
        height = (int)((float)w/aspect);
        glfwSetWindowSize(window, width, height);
    }

    public static void closeDisplay() {
        glfwDestroyWindow(window);
        keyCallback.release();

    }

    public static void setCamera(Camera c){
        camera = c;
    }

    public static void setRenderer(Renderer r){
        renderer = r;
    }
}
