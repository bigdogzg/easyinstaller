package com.jianglibo.vaadin.dashboard.view.clustersoftware;

import org.springframework.context.MessageSource;

import com.google.common.collect.Lists;
import com.jianglibo.vaadin.dashboard.domain.Domains;
import com.jianglibo.vaadin.dashboard.domain.Software;
import com.jianglibo.vaadin.dashboard.uicomponent.grid.BaseGridFree;
import com.jianglibo.vaadin.dashboard.util.MsgUtil;
import com.jianglibo.vaadin.dashboard.util.StyleUtil;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class AllSoftwareGrid  extends BaseGridFree<Software, AllSoftwareContainer>{


	public AllSoftwareGrid(MessageSource messageSource, Domains domains) {
		super(messageSource, domains, Software.class, new String[]{"name", "ostype"});
	}

	@Override
	protected void setSummaryFooterCells(FooterRow footer) {
		
	}

	@Override
	protected void addGeneratedProperty(GeneratedPropertyContainer gpcontainer, String name) {
		
	}

	@Override
	protected void setupColumn(Column col, String cn) {
		
	}

	@Override
	protected void setColumnFiltering(Column col, String cn) {
		
	}

	@Override
	protected AllSoftwareContainer createContainer() {
		return new AllSoftwareContainer(getDomains(), 10, Lists.newArrayList());
	}

	@Override
	protected void setColumnFiltering() {
		
	}

	@Override
	protected void setupGrid() {
		HeaderRow groupingHeader = prependHeaderRow();
		HeaderCell namesCell = groupingHeader.join(
			    groupingHeader.getCell("name"),
			    groupingHeader.getCell("ostype"));
		HorizontalLayout hl = new HorizontalLayout();
		Label lb = new Label(MsgUtil.getMsgWithSubsReturnKeyOnAbsent(getMessageSource(), "view.boxsoftware.", "gridtitleall"));
		ComboBox cb = new ComboBox();
		cb.setEnabled(false);
		hl.addComponents(lb, cb);
		StyleUtil.setMarginBottomTwenty(hl);
		namesCell.setComponent(hl);
		
		setSelectionMode(SelectionMode.SINGLE);
		
		addSelectionListener(event -> {
			if (event.getSelected().size() == 1) {
				cb.setEnabled(true);
			}
		});
	}

}
