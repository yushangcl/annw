package top.annwz.base.entity;

import java.util.Date;

public class BaLog {
    private Integer logId;

    private Integer relatedOrderId;

    private String relatedType;

    private String operationName;

    private Integer logUserId;

    private String logRemark;

    private Date logTime;

    private String logDny1;

    private String logDny2;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getRelatedOrderId() {
        return relatedOrderId;
    }

    public void setRelatedOrderId(Integer relatedOrderId) {
        this.relatedOrderId = relatedOrderId;
    }

    public String getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(String relatedType) {
        this.relatedType = relatedType == null ? null : relatedType.trim();
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName == null ? null : operationName.trim();
    }

    public Integer getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(Integer logUserId) {
        this.logUserId = logUserId;
    }

    public String getLogRemark() {
        return logRemark;
    }

    public void setLogRemark(String logRemark) {
        this.logRemark = logRemark == null ? null : logRemark.trim();
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getLogDny1() {
        return logDny1;
    }

    public void setLogDny1(String logDny1) {
        this.logDny1 = logDny1 == null ? null : logDny1.trim();
    }

    public String getLogDny2() {
        return logDny2;
    }

    public void setLogDny2(String logDny2) {
        this.logDny2 = logDny2 == null ? null : logDny2.trim();
    }
}