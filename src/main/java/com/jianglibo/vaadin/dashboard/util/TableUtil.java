package com.jianglibo.vaadin.dashboard.util;

import org.springframework.context.MessageSource;

import com.jianglibo.vaadin.dashboard.annotation.VaadinTableColumn;
import com.jianglibo.vaadin.dashboard.annotation.TableColumns;
import com.jianglibo.vaadin.dashboard.annotation.VaadinTable;
import com.vaadin.server.Page;
import com.vaadin.ui.Table;

public class TableUtil {
	
	public static void decorateTable(Table table, MessageSource messageSource, VaadinTable vt, TableColumns tableColumns) {
		if (vt.fullSize()) {
			table.setSizeFull();
		}
		
		table.setSortEnabled(vt.sortable());
		
		for(String sn : vt.styleNames()) {
			table.addStyleName(sn);
		}
		table.setSelectable(vt.selectable());
		
		table.setColumnReorderingAllowed(vt.columnCollapsingAllowed());

		table.setColumnCollapsingAllowed(vt.columnCollapsingAllowed());
		
		table.setFooterVisible(vt.footerVisible());
		table.setMultiSelect(vt.multiSelect());
		
		for(VaadinTableColumn tc: tableColumns.getTcmap().values()) {
			table.setColumnCollapsible(tc.name(), tc.collapsible());
			table.setColumnAlignment(tc.name(), tc.alignment());
		}
		
		table.setVisibleColumns(tableColumns.getVisibleColumns());
		table.setColumnHeaders(tableColumns.getColumnHeaders(messageSource));
	}
	
	public static boolean autoCollapseColumnsNeedChangeState(Table table, TableColumns tc) {
		boolean result = true;
		for (String propertyId : tc.getAutoCollapseColumns()) {
			if (table.isColumnCollapsed(propertyId) == Page.getCurrent().getBrowserWindowWidth() < 800) {
				result = false;
			}
		}
		return result;
	}
}
