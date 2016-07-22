package aurelienribon.ui.css.swing;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetManager;
import aurelienribon.ui.css.Style;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class SwingDeclarationSetManager implements DeclarationSetManager<Component> {
	@Override
	public void manage(Component target, Map<String, DeclarationSet> dss) {
		clearListeners(target);

		if (dss.isEmpty()) return;

		if ((dss.containsKey("") && dss.size() > 1) || (!dss.containsKey("") && !dss.isEmpty())) {
			TargetManager.manage(target, dss);

		} else if (dss.containsKey("")) {
			Style.apply(target, dss.get(""));
		}
	}

	private void clearListeners(Component target) {
		for (int i=target.getMouseListeners().length-1; i>=0; i--) {
			MouseListener lst = target.getMouseListeners()[i];
			if (lst instanceof TargetManager.HoverListener) target.removeMouseListener(lst);
			if (lst instanceof TargetManager.ActiveListener) target.removeMouseListener(lst);
		}

		for (int i=target.getFocusListeners().length-1; i>=0; i--) {
			FocusListener lst = target.getFocusListeners()[i];
			if (lst instanceof TargetManager.FocusListener) target.removeFocusListener(lst);
		}

		for (int i=target.getPropertyChangeListeners().length-1; i>=0; i--) {
			PropertyChangeListener lst = target.getPropertyChangeListeners()[i];
			if (lst instanceof TargetManager.PropertyListener) target.removePropertyChangeListener(lst);
		}
	}

	public static class TargetManager {
		public static void manage(Component target, Map<String, DeclarationSet> dss) {
			TargetManager tm = new TargetManager(target, dss);
			tm.apply();
		}

		private final Component target;
		private final Map<String, DeclarationSet> dss;
		private final Map<String, Boolean> states;

		private TargetManager(Component target, Map<String, DeclarationSet> dss) {
			this.target = target;
			this.dss = dss;
			this.states = new LinkedHashMap<String, Boolean>(dss.size());

			for (String pseudoClass : dss.keySet()) {
				if (!pseudoClass.equals(":disabled")) states.put(pseudoClass, Boolean.FALSE);
				if (pseudoClass.equals(":focus")) states.put(pseudoClass, target.isFocusOwner());

				if (pseudoClass.equals(":hover")) target.addMouseListener(new HoverListener());
				else if (pseudoClass.equals(":active")) target.addMouseListener(new ActiveListener());
				else if (pseudoClass.equals(":focus")) target.addFocusListener(new FocusListener());
				else if (pseudoClass.equals(":disabled")) target.addPropertyChangeListener("enabled", new DisabledListener());
				else if (!pseudoClass.equals("")) {
					assert pseudoClass.startsWith(":");
					assert pseudoClass.length() > 1;
					String prop = pseudoClass.substring(1);
					target.addPropertyChangeListener(prop, new PropertyListener(pseudoClass));
				}
			}
		}

		private void apply() {
			if (dss.containsKey("")) Style.apply(target, dss.get(""));

			if (target.isEnabled()) {
				for (String pseudoClass : states.keySet()) {
					if (states.get(pseudoClass) == true) Style.apply(target, dss.get(pseudoClass));
				}

			} else if (dss.containsKey(":disabled")) {
				Style.apply(target, dss.get(":disabled"));
			}
		}

		public class HoverListener extends MouseAdapter {
			@Override public void mouseEntered(MouseEvent e) {states.put(":hover", Boolean.TRUE); apply();}
			@Override public void mouseExited(MouseEvent e) {states.put(":hover", Boolean.FALSE); apply();}
		}

		public class ActiveListener extends MouseAdapter {
			@Override public void mousePressed(MouseEvent e) {states.put(":active", Boolean.TRUE); apply();}
			@Override public void mouseReleased(MouseEvent e) {states.put(":active", Boolean.FALSE); apply();}
		}

		public class FocusListener extends FocusAdapter {
			@Override public void focusGained(FocusEvent e) {states.put(":focus", Boolean.TRUE); apply();}
			@Override public void focusLost(FocusEvent e) {states.put(":focus", Boolean.FALSE); apply();}
		}

		public class DisabledListener implements PropertyChangeListener {
			@Override public void propertyChange(PropertyChangeEvent e) {apply();}
		}

		public class PropertyListener implements PropertyChangeListener {
			private final String pseudoClass;

			public PropertyListener(String pseudoClass) {
				this.pseudoClass = pseudoClass;
			}

			@Override public void propertyChange(PropertyChangeEvent e) {
				states.put(pseudoClass, (Boolean) e.getNewValue());
				apply();
			}
		}
	}
}
