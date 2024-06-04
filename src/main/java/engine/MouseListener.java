package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double Xpos, Ypos,lastX, lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isdragging;

    private MouseListener(){
        this.scrollX = 0;
        this.scrollY = 0;
        this.Xpos = 0;
        this.Ypos = 0;
        this.lastX = 0;
        this.lastY = 0;
    }

    public static MouseListener get(){
        if (MouseListener.instance == null){
            MouseListener.instance = new MouseListener();
        }

        return MouseListener.instance;
    }

    public static void mouseposCallback(long window, double xpos, double ypos){
        get().lastX = get().Xpos;
        get().lastY = get().Ypos;
        get().Xpos = xpos;
        get().Ypos = ypos;
        get().isdragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    public static void mousebuttoncallback(long window, int button, int action, int mods){
        if(action == GLFW.GLFW_PRESS) {
            if(button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = true;
            }
        }else if(action == GLFW.GLFW_RELEASE){
            if(button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().isdragging = false;
            }
        }
    }

    public static void mousescrollcallback(long windows, double xoffset, double yoffset){
        get().scrollX = xoffset;
        get().scrollY = yoffset;
        get().lastX = get().Xpos;
        get().lastY = get().Ypos;
    }

    public static float getX(){
        return (float)get().Xpos;
    }

    public static float getY(){
        return (float)get().Ypos;
    }

    public static float getDx(){
        return (float)(get().lastX - get().Xpos);
    }

    public static float getDy(){
        return (float)(get().lastY - get().Ypos);
    }

    public static float getscrollX(){
        return (float)get().scrollX;
    }

    public static float getscrollY(){
        return (float)get().scrollY;
    }

    public static boolean isdragging(){
        return get().isdragging;
    }

    public static boolean mousebuttondown(int button){
        if (button < get().mouseButtonPressed.length){
            return get().mouseButtonPressed[button];
        }else{
            return false;
        }
    }
}
