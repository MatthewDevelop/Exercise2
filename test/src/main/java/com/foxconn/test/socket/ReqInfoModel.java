package com.foxconn.test.socket;

import java.util.Date;

/**
 * 请求下载信息的报文格式
 * 
 * @author Janet 2013-3-28 下午2:18:19
 */
public class ReqInfoModel {

	private String fileName;
	private Date lastDlDate;
	private String typeId;
	private String vmId;

	public ReqInfoModel() {

	}

	public ReqInfoModel(String _fileName, Date _lastDlDate, String _typeId,
                        String _vmId) {
		fileName = _fileName;
		lastDlDate = _lastDlDate;
		typeId = _typeId;
		vmId = _vmId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getLastDlDate() {
		return lastDlDate;
	}

	public void setLastDlDate(Date lastDlDate) {
		this.lastDlDate = lastDlDate;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	@Override
	public String toString() {
		return "ReqInfoModel [fileName=" + fileName + ", lastDlDate="
				+ lastDlDate + ", typeId=" + typeId + ", vmId=" + vmId + "]";
	}
}
