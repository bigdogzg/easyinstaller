package com.jianglibo.vaadin.dashboard.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-18T19:00:40.818+0800")
@StaticMetamodel(Software.class)
public class Software_ extends BaseEntity_ {
	public static volatile SingularAttribute<Software, String> name;
	public static volatile SingularAttribute<Software, String> sversion;
	public static volatile SingularAttribute<Software, String> ostype;
	public static volatile SingularAttribute<Software, String> runas;
	public static volatile SingularAttribute<Software, String> possibleRoles;
	public static volatile SingularAttribute<Software, String> runner;
	public static volatile SingularAttribute<Software, String> timeouts;
	public static volatile SetAttribute<Software, TextFile> textfiles;
	public static volatile SingularAttribute<Software, Date> updatedAt;
	public static volatile SetAttribute<Software, String> filesToUpload;
	public static volatile SingularAttribute<Software, String> codeToExecute;
	public static volatile SingularAttribute<Software, String> codeFileExt;
	public static volatile SingularAttribute<Software, String> codeLineSeperator;
	public static volatile SingularAttribute<Software, String> configContent;
	public static volatile SingularAttribute<Software, String> preferredFormat;
	public static volatile SingularAttribute<Software, Person> creator;
	public static volatile SingularAttribute<Software, String> actions;
	public static volatile SingularAttribute<Software, String> actionDescriptions;
}
