//import java.util.*;

public interface ReceiveListener {
    public void ReceiveData(boolean LeftOrRight, int x, int y);

}
/*

class Initiater {
        private List<RecieveListener> listeners = new ArrayList<RecieveListener>();

        public void addListener(RecieveListener toAdd) {
            listeners.add(toAdd);
        }
        public void Receaved() {
            // Notify everybody that may be interested.
            for (RecieveListener hl : listeners)
            hl.RecieveData();
        }
}

//////////////////////////

import java.util.EventListener;
import java.util.EventObject;
import javax.swing.event.EventListenerList;

class MyEvent extends EventObject {
    public MyEvent(Object source) {
        super(source);
    }
}

interface RecieveListener extends EventListener {
    public void RecieveData(MyEvent evt);
}

class MyClass {
    protected EventListenerList listenerList = new EventListenerList();

    public void addMyEventListener(RecieveListener listener) {
        listenerList.add(RecieveListener.class, listener);
    }
    public void removeMyEventListener(RecieveListener listener) {
        listenerList.remove(RecieveListener.class, listener);
    }
    void fireMyEvent(MyEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == RecieveListener.class) {
                ((RecieveListener) listeners[i+1]).RecieveData(evt);
            }
        }
    }
}

public class Main {
    public static void main(String[] argv) throws Exception {
        MyClass c = new MyClass();
        c.addMyEventListener(new RecieveListener() {
            public void RecieveData(MyEvent evt) {
                System.out.println("fired");
            }
        });

    }
}*/