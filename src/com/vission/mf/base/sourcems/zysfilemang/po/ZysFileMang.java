package com.vission.mf.base.sourcems.zysfilemang.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import com.vission.mf.base.sourcems.zysfilemang.db.ZYS_FILE_MANG;

/**
 * 作者：lkj 描述：ZYS_FILE_MANG 模块Po 日期：2019-7-4 15:07:15 类型：Po文件
 */

@Entity
@Table(name = ZYS_FILE_MANG.TABLE_NAME)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler",
		"fieldHandler" })
public class ZysFileMang {

	private static final long serialVersionUID = 1L;

	// 主键
	private String pkId;

	@Id
	@GeneratedValue(generator = "uuid-gen")
	@GenericGenerator(name = "uuid-gen", strategy = "uuid")
	@Column(name = ZYS_FILE_MANG.PK_ID)
	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	// 文件类型
	private String fileType;

	@Column(name = ZYS_FILE_MANG.FILE_TYPE)
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	// 文件名称
	private String fileNm;

	@Column(name = ZYS_FILE_MANG.FILE_NM)
	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	// 文件路径
	private String filePath;

	@Column(name = ZYS_FILE_MANG.FILE_PATH, nullable = false)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	// 文件大小
	private Long fileSize;

	@Column(name = ZYS_FILE_MANG.FILE_SIZE, nullable = false)
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	// 文件说明
	private String fileRemark;

	@Column(name = ZYS_FILE_MANG.FILE_REMARK, nullable = false)
	public String getFileRemark() {
		return fileRemark;
	}

	public void setFileRemark(String fileRemark) {
		this.fileRemark = fileRemark;
	}

	// 压缩密码
	private String compPwd;

	@Column(name = ZYS_FILE_MANG.COMP_PWD, nullable = false)
	public String getCompPwd() {
		return compPwd;
	}

	public void setCompPwd(String compPwd) {
		this.compPwd = compPwd;
	}

	// 原价
	private String costPrice;

	@Column(name = ZYS_FILE_MANG.COST_PRICE, nullable = false)
	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	// 现价
	private String nowPrice;

	@Column(name = ZYS_FILE_MANG.NOW_PRICE, nullable = false)
	public String getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(String nowPrice) {
		this.nowPrice = nowPrice;
	}

	// 下载次数
	private int downloadCount;

	@Column(name = ZYS_FILE_MANG.DOWNLOAD_COUNT, nullable = false)
	public int getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	private String createUser;
	private String createTime;
	private String lastModUser;
	private String lastModTime;

	@Column(name = ZYS_FILE_MANG.CREATE_USER)
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = ZYS_FILE_MANG.CREATE_TIME)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = ZYS_FILE_MANG.LAST_MOD_USER)
	public String getLastModUser() {
		return lastModUser;
	}

	public void setLastModUser(String lastModUser) {
		this.lastModUser = lastModUser;
	}

	@Column(name = ZYS_FILE_MANG.LAST_MOD_TIME)
	public String getLastModTime() {
		return lastModTime;
	}

	public void setLastModTime(String lastModTime) {
		this.lastModTime = lastModTime;
	}

	public MultipartFile file;

	@Transient
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
