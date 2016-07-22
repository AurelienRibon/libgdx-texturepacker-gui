package aurelienribon.ui.components;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class TabPanelModel {
	private final List<TabModel> tabModels = new ArrayList<TabModel>();
	private Callback callback;

	// -------------------------------------------------------------------------
	// Public API
	// -------------------------------------------------------------------------

	public void add(Component cmp, String title) {
		add(cmp, title, null, true);
	}

	public void add(Component cmp, String title, ImageIcon icon) {
		add(cmp, title, icon, true);
	}

	public void add(Component cmp, String title, ImageIcon icon, boolean closable) {
		TabModel tm = new TabModel(cmp, title, icon, closable);
		tabModels.add(tm);
		if (callback != null) callback.tabAdded(tm, tabModels.indexOf(tm));
	}

	public void remove(Component cmp) {
		remove(tabModels.indexOf(findTabModel(cmp)));
	}

	public void remove(int idx) {
		if (idx >= 0 && idx < tabModels.size()) {
			TabModel tm = tabModels.remove(idx);
			if (callback != null) callback.tabRemoved(tm, idx);
		}
	}

	public int getTabsCount() {
		return tabModels.size();
	}

	public void setSelection(Component cmp) {
		setSelection(tabModels.indexOf(findTabModel(cmp)));
	}

	public void setSelection(int idx) {
		for (TabModel tm : tabModels) tm.selected = false;
		if (idx >= 0 && idx < tabModels.size()) {
			TabModel tm = tabModels.get(idx);
			tm.selected = true;
			if (callback != null) callback.selectionChanged(tm, idx);
		} else {
			if (callback != null) callback.selectionChanged(null, -1);
		}
	}

	// -------------------------------------------------------------------------
	// Package API
	// -------------------------------------------------------------------------

	List<TabModel> getTabModels() {
		return Collections.unmodifiableList(tabModels);
	}

	void setCallback(Callback callback) {
		this.callback = callback;
	}

	TabModel getSelectedTabModel() {
		for (TabModel tm : tabModels) if (tm.selected) return tm;
		return null;
	}

	class TabModel {
		public final Component component;
		public String title;
		public ImageIcon icon;
		public boolean closable;
		public boolean selected = false;

		public TabModel(Component cmp, String title, ImageIcon icon, boolean closable) {
			this.component = cmp;
			this.title = title;
			this.icon = icon;
			this.closable = closable;
		}
	}

	interface Callback {
		public void tabAdded(TabModel tm, int idx);
		public void tabRemoved(TabModel tm, int idx);
		public void selectionChanged(TabModel tm, int idx);
	}

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private TabModel findTabModel(Component cmp) {
		for (TabModel tm : tabModels) if (tm.component == cmp) return tm;
		return null;
	}
}
