package engine;

import java.util.PrimitiveIterator;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener
{
    private static KeyListener instance;
    private boolean keypressed[] = new boolean[350];

    private KeyListener(){

    }

    public static KeyListener get(){
        if (KeyListener.instance == null){
            KeyListener.instance = new KeyListener();
        }
        return KeyListener.instance;
    }

    public static void keycallback(long window, int key, int scancode, int action, int mods){
        if(action == GLFW_PRESS){
            get().keypressed[key] = true;
        } else if (action == GLFW_RELEASE){
            get().keypressed[key] = false;
        }
    }

    public static boolean iskeypressed(int keycode){
        return get().keypressed[keycode];
    }
}
