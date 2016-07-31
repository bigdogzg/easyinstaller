package com.jianglibo.vaadin.dashboard.view.installationpackages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jianglibo.vaadin.dashboard.domain.PkSource;
import com.jianglibo.vaadin.dashboard.event.ui.DashboardEventBus;
import com.jianglibo.vaadin.dashboard.event.ui.DashboardEvent.ProfileUpdatedEvent;
import com.jianglibo.vaadin.dashboard.event.view.HistoryBackEvent;
import com.jianglibo.vaadin.dashboard.repositories.PkSourceRepository;
import com.jianglibo.vaadin.dashboard.uicomponent.viewheader.HeaderLayout;
import com.jianglibo.vaadin.dashboard.util.ItemViewFragmentBuilder;
import com.jianglibo.vaadin.dashboard.util.StyleUtil;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;


@SpringView(name = InstallationPackageEditView.VIEW_NAME)
public class InstallationPackageEditView  extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(InstallationPackageEditView.class);
	
	private final MessageSource messageSource;
	
	private final PkSourceRepository pkSourceRepository;

	public static final String VIEW_NAME = "installationPackage/edit";

	public static final FontAwesome ICON_VALUE = FontAwesome.FILE_ARCHIVE_O;

	private ApplicationContext applicationContext;
		
	private EventBus eventBus;
	
    private BeanFieldGroup<PkSource> fieldGroup;
	
    private static final String fileMd5FieldName = "fileMd5";
    
	@PropertyId(fileMd5FieldName)
	private TextField fileMd5Field;
	
	private static final String pknameFieldName = "pkname";

	@PropertyId(pknameFieldName)
    private TextField pknameField;
    
	private static final String originFromFieldName = "originFrom";

	@PropertyId(originFromFieldName)
    private TextField originFromField;
	
	
	private static final String mimeTypeFieldName = "mimeType";
    
	@PropertyId(mimeTypeFieldName)
    private TextField mimeTypeField;
	
	
	private	PkSource pkSource;
	
	private String getMsg(String key) {
		return messageSource.getMessage("fieldname.pksource." + key, null, UI.getCurrent().getLocale());
	}
	
    @SuppressWarnings("serial")
	private Component buildProfileTab() {

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        
        details.addShortcutListener(new ShortcutListener("submit", null, KeyCode.ENTER) {
			@Override
			public void handleAction(Object sender, Object target) {
				save();
			}
		});
        
        details.addShortcutListener(new ShortcutListener("submit", null, KeyCode.ESCAPE) {
			@Override
			public void handleAction(Object sender, Object target) {
				eventBus.post(new HistoryBackEvent());
			}
		});

        fileMd5Field = new TextField(getMsg(fileMd5FieldName));
        details.addComponent(fileMd5Field);
        
        pknameField = new TextField(getMsg(pknameFieldName));
        details.addComponent(pknameField);

        originFromField = new TextField(getMsg(originFromFieldName));
        details.addComponent(originFromField);
        originFromField.setNullRepresentation("");

        mimeTypeField = new TextField(getMsg(mimeTypeFieldName));
        details.addComponent(mimeTypeField);
        
        fieldGroup = new BeanFieldGroup<PkSource>(PkSource.class);
        fieldGroup.bindMemberFields(this);

//        sexField = new OptionGroup("Sex");
//        sexField.addItem(Boolean.FALSE);
//        sexField.setItemCaption(Boolean.FALSE, "Female");
//        sexField.addItem(Boolean.TRUE);
//        sexField.setItemCaption(Boolean.TRUE, "Male");
//        sexField.addStyleName("horizontal");
//        details.addComponent(sexField);
//
//        Label section = new Label("Contact Info");
//        section.addStyleName(ValoTheme.LABEL_H4);
//        section.addStyleName(ValoTheme.LABEL_COLORED);
//        details.addComponent(section);
//
//        emailField = new TextField("Email");
//        emailField.setWidth("100%");
//        emailField.setRequired(true);
//        emailField.setNullRepresentation("");
//        details.addComponent(emailField);
//
//        locationField = new TextField("Location");
//        locationField.setWidth("100%");
//        locationField.setNullRepresentation("");
//        locationField.setComponentError(new UserError(
//                "This address doesn't exist"));
//        details.addComponent(locationField);
//
//        phoneField = new TextField("Phone");
//        phoneField.setWidth("100%");
//        phoneField.setNullRepresentation("");
//        details.addComponent(phoneField);
//
//        newsletterField = new OptionalSelect<Integer>();
//        newsletterField.addOption(0, "Daily");
//        newsletterField.addOption(1, "Weekly");
//        newsletterField.addOption(2, "Monthly");
//        details.addComponent(newsletterField);
//
//        section = new Label("Additional Info");
//        section.addStyleName(ValoTheme.LABEL_H4);
//        section.addStyleName(ValoTheme.LABEL_COLORED);
//        details.addComponent(section);
//
//        websiteField = new TextField("Website");
//        websiteField.setInputPrompt("http://");
//        websiteField.setWidth("100%");
//        websiteField.setNullRepresentation("");
//        details.addComponent(websiteField);
//
//        bioField = new TextArea("Bio");
//        bioField.setWidth("100%");
//        bioField.setRows(4);
//        bioField.setNullRepresentation("");
//        details.addComponent(bioField);
        StyleUtil.setMarginTopTwenty(details);
        return details;
    }
    
    private HeaderLayout header;
    
    private ItemViewFragmentBuilder ifb;
	
	@Autowired
	public InstallationPackageEditView(PkSourceRepository pkSourceRepository, MessageSource messageSource,
			ApplicationContext applicationContext) {
		this.messageSource = messageSource;
		this.applicationContext = applicationContext;
		this.pkSourceRepository = pkSourceRepository;
		this.eventBus = new EventBus(this.getClass().getName());
		eventBus.register(this);
		setSizeFull();
		addStyleName("transactions");
		// DashboardEventBus.register(this);
		setMargin(true);
		
		header = applicationContext.getBean(HeaderLayout.class).afterInjectionWithBackBtn(eventBus, "");
		
		addComponent(header);
		Component fl = buildProfileTab();
//		fl.setWidth(80f, Unit.PERCENTAGE);
		addComponent(fl);
		addComponent(buildFooter());
//		setComponentAlignment(fl, Alignment.MIDDLE_CENTER);
		setExpandRatio(fl, 1);
	}
	
	private void save() {
        try {
            fieldGroup.commit();
            pkSourceRepository.save(pkSource);
            Notification success = new Notification(messageSource.getMessage("shared.msg.savesuccess", null, UI.getCurrent().getLocale()));
            success.setDelayMsec(2000);
            success.setStyleName("bar success small");
            success.setPosition(Position.BOTTOM_CENTER);
            success.show(Page.getCurrent());
            DashboardEventBus.post(new ProfileUpdatedEvent());
        } catch (CommitException e) {
            Notification.show(messageSource.getMessage("shared.msg.savefailed",null, UI.getCurrent().getLocale()),
                    Type.ERROR_MESSAGE);
        }
	}
	
    @SuppressWarnings("serial")
	private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button(messageSource.getMessage("shared.btn.save", null, UI.getCurrent().getLocale()));
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	save();
            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }
    

	@Override
	public void detach() {
		super.detach();
		// A new instance of TransactionsView is created every time it's
		// navigated to so we'll need to clean up references to it on detach.
		// DashboardEventBus.unregister(this);
	}
	
	@Subscribe
	public void onBackBtnClicked(HistoryBackEvent hbe) {
		UI.getCurrent().getNavigator().navigateTo(ifb.getPreviousView());
	}
	

	@Override
	public void enter(ViewChangeEvent event) {
		LOGGER.info("parameter string is: {}", event.getParameters());
		ifb = new ItemViewFragmentBuilder(event);
		long bid = ifb.getBeanId();
		if (bid == 0) {
			pkSource = new PkSource();
		} else {
			pkSource = pkSourceRepository.findOne(bid);
			header.setLabelTxt(pkSource.getPkname());
		}
        fieldGroup.setItemDataSource(pkSource);
	}
}