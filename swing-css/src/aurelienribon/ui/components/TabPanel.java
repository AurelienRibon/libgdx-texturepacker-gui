package aurelienribon.ui.components;

import aurelienribon.ui.components.TabPanelModel.TabModel;
import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;
import aurelienribon.ui.css.Selector;
import aurelienribon.ui.css.Style;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class TabPanel extends JPanel {
	// -------------------------------------------------------------------------
	// Attributes + ctor
	// -------------------------------------------------------------------------

	public static final int LAYOUT_STACK = 0;
	public static final int LAYOUT_GRID = 1;

	private final Map<TabModel, String> cardsMap = new HashMap<TabModel, String>();
	private final TabPanelHeaderPanel headerPanel = new TabPanelHeaderPanel(LAYOUT_STACK, new TabHeaderPanelCallbackImpl());
	private final CardLayout cardLayout = new CardLayout();
	private final JPanel cardPanel = new TransparentPanel(cardLayout);
	private TabPanelModel model;
	private int cnt = 0;

	private Style style;
	private List<Selector.Atom> styleStack;

	public TabPanel() {
		setPreferredSize(new Dimension(50, 50));

		setLayout(new BorderLayout());
		add(headerPanel, BorderLayout.NORTH);
		add(cardPanel, BorderLayout.CENTER);

		setOpaque(false);
		setModel(new TabPanelModel());

		Style.registerCssClasses(cardPanel, ".-ar-content");
		Style.registerCssClasses(headerPanel, ".-ar-header");
	}

	// -------------------------------------------------------------------------
	// Public API
	// -------------------------------------------------------------------------

	public void setHeaderLayout(int layout) {
		if (layout < 0 || layout > 1) return;
		headerPanel.setTabsLayout(layout);
	}

	public int getHeaderLayout() {
		return headerPanel.getTabsLayout();
	}

	public void setModel(final TabPanelModel model) {
		cardPanel.removeAll();
		headerPanel.removeAllTabs();
		this.model = model;

		model.setCallback(new TabPanelModel.Callback() {
			@Override public void tabAdded(TabModel tm, int idx) {
				cardsMap.put(tm, "card" + (cnt++));
				cardPanel.add(tm.component, cardsMap.get(tm));
				headerPanel.addTab(tm);
				if (model.getSelectedTabModel() == null) selectLastModel();

				Style.registerCssClasses(tm.component, ".-ar-tabcontent");
				Style.apply(tm.component, style, styleStack);
			}

			@Override public void tabRemoved(TabModel tm, int idx) {
				cardsMap.remove(tm);
				cardPanel.remove(tm.component);
				headerPanel.removeTab(tm);
				if (model.getSelectedTabModel() == null) selectLastModel();

				Style.unregisterCssClasses(tm.component, ".-ar-tabcontent");
			}

			@Override public void selectionChanged(TabModel tm, int idx) {
				cardLayout.show(cardPanel, cardsMap.get(tm));
				headerPanel.reload();
				if (tm != null) tm.component.requestFocusInWindow();

				for (TabPanelHeaderSubPanel subPanel : headerPanel.getSubPanels()) {
					if (subPanel.getModel() == tm) {
						subPanel.firePropertyChange("selected", false, true);
					} else {
						subPanel.firePropertyChange("selected", true, false);
					}
				}
			}
		});

		for (TabModel tm : model.getTabModels()) {
			cardsMap.put(tm, "card" + (cnt++));
			cardPanel.add(tm.component, cardsMap.get(tm));
			headerPanel.addTab(tm);
		}

		if (model.getSelectedTabModel() == null) model.setSelection(0);
	}

	public TabPanelModel getModel() {
		return model;
	}

	@Override
	public void setBackground(Color bg) {
		if (cardPanel != null) cardPanel.setBackground(bg);
	}

	@Override
	public void setBorder(Border border) {
		if (cardPanel != null) cardPanel.setBorder(border);
	}

	// -------------------------------------------------------------------------
	// Callback impl.
	// -------------------------------------------------------------------------

	private class TabHeaderPanelCallbackImpl implements TabPanelHeaderPanel.Callback {
		@Override
		public void selectTabRequested(TabModel tm) {
			selectModel(tm);
		}

		@Override
		public void closeTabRequested(TabModel tm) {
			if (tm.closable) model.remove(tm.component);
		}

		@Override
		public void closeAllTabsRequested() {
			for (int i=model.getTabModels().size()-1; i>=0; i--) {
				TabModel tm = model.getTabModels().get(i);
				if (tm.closable) model.remove(tm.component);
			}
		}

		@Override
		public void closeOtherTabsRequested(TabModel tm) {
			for (int i=model.getTabModels().size()-1; i>=0; i--) {
				TabModel tm2 = model.getTabModels().get(i);
				if (tm2.closable && tm2 != tm) model.remove(tm2.component);
			}
		}
	};

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private void selectModel(TabModel tm) {
		model.setSelection(tm != null ? tm.component : null);
	}

	private void selectLastModel() {
		if (model.getTabModels().isEmpty()) selectModel(null);
		else selectModel(model.getTabModels().get(model.getTabModels().size()-1));
	}

	// -------------------------------------------------------------------------
	// StyleProcessor
	// -------------------------------------------------------------------------

	public static class Processor implements DeclarationSetProcessor<TabPanel>, ArProperties {
		@Override
		public void process(TabPanel target, DeclarationSet ds) {
			target.style = ds.getStyle();
			target.styleStack = new ArrayList<Selector.Atom>(Style.getLastStack());
		}
	}
}
