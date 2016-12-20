package com.jianglibo.vaadin.dashboard.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import com.google.common.base.Splitter;
import com.jianglibo.vaadin.dashboard.annotation.VaadinFormFieldWrapper;
import com.jianglibo.vaadin.dashboard.annotation.VaadinTableColumnWrapper;
import com.jianglibo.vaadin.dashboard.annotation.VaadinTableWrapper;
import com.jianglibo.vaadin.dashboard.config.ComboItem;
import com.vaadin.ui.UI;

public class MsgUtil {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MsgUtil.class); 
	
	public static Splitter dotSplitter = Splitter.on('.').trimResults();
	
	public static String getDynaMenuMsg(MessageSource messageSource, String menuid) {
		return getMsgFallbackToSelf(messageSource,"dynmenu.", menuid);
	}
	
	public static String getDropDownMenuMsg(MessageSource messageSource, String menuid) {
		return getMsgFallbackToSelf(messageSource,"dmenu.dropdown.", menuid);
	}
	
	public static String getComboItemMsg(MessageSource messageSource,String comboKey, ComboItem ci) {
		String key = "comboitem." + comboKey + "." + ci.getCaption();
		String msg = null;
		try {
			msg = messageSource.getMessage(key, null, UI.getCurrent().getLocale());
		} catch (NoSuchMessageException e) {
			LOGGER.info("field {} has no localized message", key);
		}
		return msg == null ? ci.getCaption() : msg;
	}
	
//	public static String getBottomNotifiMsg(MessageSource messageSource, String mname) {
//		String key = "bottom.notifi." + mname;
//		String msg = null;
//		try {
//			msg = messageSource.getMessage(key, null, UI.getCurrent().getLocale());
//		} catch (NoSuchMessageException e) {
//			LOGGER.info("field {} has no localized message", key);
//		}
//		return msg == null ? key : msg;
//	}
	
	public static String getViewMenuMsg(MessageSource messageSource, String viewName) {
		String key = "mainmenu." + viewName;
		String msg = null;
		try {
			msg = messageSource.getMessage(key, null, UI.getCurrent().getLocale());
		} catch (NoSuchMessageException e) {
			LOGGER.info("field {} has no localized message", key);
		}
		return msg == null ? key : msg;
	}
	
	public static String getListViewTitle(MessageSource messageSource, String domainName, String...substitudes) {
		String key = "view." + domainName + ".list.title";
		String msg = null;
		try {
			msg = messageSource.getMessage(key, substitudes, UI.getCurrent().getLocale());
		} catch (NoSuchMessageException e) {
			LOGGER.info("field {} has no localized message", key);
		}
		return msg == null ? key : msg;
	}
	
	public static String getViewMsg(MessageSource messageSource, String domainName, String...substitudes) {
		String key = "view." + domainName;
		String msg = null;
		try {
			msg = messageSource.getMessage(key, substitudes, UI.getCurrent().getLocale());
		} catch (NoSuchMessageException e) {
			LOGGER.info("field {} has no localized message", key);
		}
		return msg == null ? key : msg;
	}
	
	public static String getFieldMsg(MessageSource messageSource, VaadinTableWrapper vtw, VaadinTableColumnWrapper ttcw) {
		return getFieldMsg(messageSource, vtw.getVt().messagePrefix(), ttcw);
	}

	
	public static String getFieldMsg(MessageSource messageSource, VaadinTableWrapper vtw, VaadinFormFieldWrapper vffw) {
		return getFieldMsg(messageSource, vtw.getVt().messagePrefix(), vffw);
	}
	
	public static String getFieldMsg(MessageSource messageSource, String prefix, VaadinTableColumnWrapper ttcw) {
		String fieldName = ttcw.getVtc().columnHeader();
		if (fieldName.isEmpty()) {
			fieldName = ttcw.getName();
		}
		return getFieldMsg(messageSource, prefix, fieldName);
	}
	
	public static String getFieldMsg(MessageSource messageSource, String prefix, VaadinFormFieldWrapper vffw) {
		String fieldName = vffw.getVff().caption();
		if (fieldName.isEmpty()) {
			fieldName = vffw.getName();
		}
		return getFieldMsg(messageSource, prefix, fieldName);
	}
	
	public static String getFieldDescription(MessageSource messageSource, String prefix, VaadinFormFieldWrapper vffw) {
		String fieldName = vffw.getVff().caption();
		if (fieldName.isEmpty()) {
			fieldName = vffw.getName();
		}
		return getFieldDescription(messageSource, prefix, fieldName);
	}
	
	public static String getFieldDescription(MessageSource messageSource, String prefix, String fieldName) {
		String msg = null;
		String key = "";
		try {
			key = prefix + "field.desc." + fieldName;
			msg = messageSource.getMessage(key, null, UI.getCurrent().getLocale());
			return msg;
		} catch (NoSuchMessageException e) {
			return "";
		}
	}

	public static String getFieldMsg(MessageSource messageSource, String prefix, String fieldName) {
		String msg = null;
		String key = "";
		try {
			key = prefix + "field." + fieldName;
			msg = messageSource.getMessage(key, null, UI.getCurrent().getLocale());
		} catch (NoSuchMessageException e) {
			LOGGER.info("field {} has no localized message", key);
		}
		if (msg == null) {
			try {
				key = "domain.shared.field." + fieldName;
				msg = messageSource.getMessage(key, null, UI.getCurrent().getLocale());
			} catch (NoSuchMessageException e) {
				LOGGER.info("field {} has no localized message", key);
			}
		}
		if (msg == null) {
			return fieldName;
		} else {
			return msg;
		}
	}
	
	public static String getMsgFallbackToSelf(MessageSource messageSource, String prefix, String name) {
		String key = prefix + name;
		try {
			return messageSource.getMessage(key, null, UI.getCurrent().getLocale());
		} catch (NoSuchMessageException e) {
			LOGGER.info("field {} has no localized message", key);
		}
		return name;
	}
	
	public static String getMsgWithSubsReturnKeyOnAbsent(MessageSource messageSource,String prefix, String key, String...substitudes) {
		try {
			return messageSource.getMessage(prefix + key, substitudes, UI.getCurrent().getLocale());
		} catch (NoSuchMessageException e) {
			LOGGER.info("field {} has no localized message",prefix + key);
		}
		return key;
	}

	public static String getMsgWithSubsReturnLastKeyPartOnAbsent(MessageSource messageSource, String key, String...substitudes) {
		try {
			return messageSource.getMessage(key, substitudes, UI.getCurrent().getLocale());
		} catch (NoSuchMessageException e) {
			LOGGER.info("field {} has no localized message", key);
		}
		List<String> kps = dotSplitter.splitToList(key); 
		return kps.get(kps.size() - 1);
	}

}
