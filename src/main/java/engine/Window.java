package engine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    int width, height;
    String title;
    private long glfwwindow;
    private float r,g,b,a;
    private boolean fadetowhite = false;

    private static Window window = null;

    private Window(){
        this.width = 720;
        this.height = 480;
        this.title = "The Game";
        r = 0;
        g = 0;
        b = 0;
        a = 1;
    }

    public static Window get(){
        if(Window.window == null){
            window = new Window();
        }
        return  Window.window;
    }

    public void run(){
        System.out.println("Helo, this is LWJGL version " + Version.getVersion() );
        init();
        loop();

        // free memory
        glfwFreeCallbacks(glfwwindow);
        glfwDestroyWindow(glfwwindow);

        // terminate glfw and free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){
        // setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // init GLFW
        if(!glfwInit()){
            throw new IllegalStateException("gagal init GLFW");
        }

        // configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // create window
        glfwwindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(glfwwindow == NULL){
            throw new IllegalStateException("Gagal buat GLFW window");
        }

        glfwSetCursorPosCallback(glfwwindow, MouseListener :: mouseposCallback);
        glfwSetMouseButtonCallback(glfwwindow, MouseListener :: mousebuttoncallback);
        glfwSetScrollCallback(glfwwindow, MouseListener :: mousescrollcallback);
        glfwSetKeyCallback(glfwwindow, KeyListener :: keycallback);

        // opengl context
        glfwMakeContextCurrent(glfwwindow);
        //vsync
        glfwSwapInterval(1);

        // win visible
        glfwShowWindow(glfwwindow);
        GL.createCapabilities();
    }
    public void loop(){
        while(!glfwWindowShouldClose(glfwwindow)){
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (fadetowhite){
                r = Math.min(r + 0.01f, 1);
                g = Math.min(g + 0.01f, 1);
                b = Math.min(b + 0.01f, 1);
            }

            if(KeyListener.iskeypressed(GLFW_KEY_SPACE)){
                fadetowhite = true;
            }

            glfwSwapBuffers(glfwwindow);
        }
    }
}
