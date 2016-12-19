package com.jianglibo.vaadin.dashboard.domain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.jianglibo.vaadin.dashboard.annotation.VaadinFormField;
import com.jianglibo.vaadin.dashboard.annotation.VaadinGrid;
import com.jianglibo.vaadin.dashboard.annotation.VaadinGridColumn;
import com.jianglibo.vaadin.dashboard.annotation.VaadinTable;
import com.jianglibo.vaadin.dashboard.annotation.VaadinTableColumn;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

@Entity
@Table(name = "pksource",uniqueConstraints = { @UniqueConstraint(columnNames = "fileMd5"), @UniqueConstraint(columnNames = "pkname") })
@VaadinGrid(multiSelect = true, messagePrefix = "domain.pksource.", footerVisible = true, styleNames = {
		ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_NO_HORIZONTAL_LINES,
		ValoTheme.TABLE_COMPACT }, selectable = true, fullSize = true, showCreatedAt=false,defaultSort="-updatedAt", selectMode = Grid.SelectionMode.MULTI)
@VaadinTable(multiSelect = true,
	messagePrefix="domain.pksource.",
	styleNames={ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_NO_HORIZONTAL_LINES, ValoTheme.TABLE_COMPACT},
	selectable=true,
	fullSize=true,
	footerVisible=true,
	sortable=true, showCreatedAt=false, defaultSort="-updatedAt")
public class PkSource extends BaseEntity implements HasUpdatedAt {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
    @Column(nullable = false)
	private String fileMd5;

	@VaadinTableColumn(order = 10)
	@VaadinFormField
	@VaadinGridColumn(order = 10, sortable = true, filterable = true)
    private String pkname;
    
	@VaadinFormField
	@VaadinGridColumn(order = 20)
    private String originFrom;
    
    @VaadinTableColumn(order = 30)
    @VaadinGridColumn(order = 30)
    private Long length;
    
    private String extNoDot;
    
    @VaadinTableColumn(order = 40)
    @VaadinGridColumn(order = 40)
    private String mimeType;
    
    
	@Temporal(TemporalType.TIMESTAMP)
	@VaadinTableColumn(order = 9995, sortable = true)
	@VaadinGridColumn(order = 9995, sortable = true)
	private Date updatedAt;

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public String getPkname() {
		return pkname;
	}

	public void setPkname(String pkname) {
		this.pkname = pkname;
	}

	public String getOriginFrom() {
		return originFrom;
	}

	public void setOriginFrom(String originFrom) {
		this.originFrom = originFrom;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getExtNoDot() {
		return extNoDot;
	}

	public void setExtNoDot(String extNoDot) {
		this.extNoDot = extNoDot;
	}
	
	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setLength(Long length) {
		this.length = length;
	}



	public static class PkSourceBuilder {
		private final String fileMd5;

	    private final String pkname;
	    
	    private final Long length;
	    
	    private final String extNoDot;
	    
	    private final String mimeType;
	    
	    private String originFrom;

		public PkSourceBuilder(String fileMd5, String pkname, Long length, String extNoDot, String mimeType) {
			super();
			this.fileMd5 = fileMd5;
			this.pkname = pkname;
			this.length = length;
			this.extNoDot = extNoDot;
			this.mimeType = mimeType;
		}
		
		public PkSourceBuilder(Path dst) throws IOException {
			super();
			this.fileMd5 = Files.asByteSource(dst.toFile()).hash(Hashing.md5()).toString();;
			this.pkname = dst.getFileName().toString();
			this.length = dst.toFile().length();
			this.extNoDot = Files.getFileExtension(dst.toFile().getName());
			this.mimeType = "";
		}
		
		public PkSource build() {
			PkSource ps = new PkSource();
			ps.setFileMd5(fileMd5);
			ps.setPkname(pkname);
			ps.setOriginFrom(originFrom);
			ps.setLength(length);
			ps.setExtNoDot(extNoDot);
			ps.setMimeType(mimeType);
			return ps;
		}

		public PkSourceBuilder setOriginFrom(String originFrom) {
			this.originFrom = originFrom;
			return this;
		}
	}

	@Override
	public String getDisplayName() {
		return getPkname();
	}
}
