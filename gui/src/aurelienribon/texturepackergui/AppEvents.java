package aurelienribon.texturepackergui;

import java.util.EventListener;
import javax.swing.event.EventListenerList;

public class AppEvents {
	public interface BaseAppEventListener extends EventListener {
		public void onEvent(Object... args);
	}

	// -------------------------------------------------------------------------

	public interface PackDoneListener extends BaseAppEventListener {}
	public interface PreviousPageRequestedListener extends BaseAppEventListener {}
	public interface NextPageRequestedListener extends BaseAppEventListener {}

	// -------------------------------------------------------------------------

	private static final EventListenerList listenerList = new EventListenerList();

	public static <T extends AppEvents.BaseAppEventListener> void addEventListener(Class<T> listenerClass, T listener) {
		listenerList.add(listenerClass, listener);
	}

	public static <T extends AppEvents.BaseAppEventListener> void fireEventToListeners(Class<T> listenersClass, Object... args) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length-2; i>=0; i-=2)
			if (listeners[i] == listenersClass)
				((T)listeners[i+1]).onEvent(args);
	}
}
