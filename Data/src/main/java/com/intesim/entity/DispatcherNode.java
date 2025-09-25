package com.intesim.entity;

import com.intesim.cont.NodeStatusConst;
import com.intesim.cont.ViewTypeConst;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Data
public class DispatcherNode implements Comparable<DispatcherNode> {

    public static final String RDP_RUN = "RdpRun";

    /**
     * 任务类型
     */
    private String taskType;
    /**
     * 任务主键
     */
    private String taskId;
    /**
     * 计算节点ID
     */
    private String nodeId;
    /**
     * 节点类型，默认工作流节点类型
     */
    private String nodeType;
    /**
     * 指定运算host地址
     */
    private String hostIp;
    /**
     * 错误运算信息
     */
    private String errorRunMsg;
    /**
     * 前序节点
     */
    private List<String> preNodeIds;
    /**
     * 模板前序文件Id集合
     */
    private List<String> preFileIds;
    /**
     * 结果文件名称集合
     */
    private List<String> resultFileNames;
    /**
     * 结果文件ID集合
     */
    private List<String> resultFileIds;
    /**
     * 脚本ID
     */
    private String scriptFileId;
    /**
     * 辅脚本文件Id集合
     */
    private List<String> scriptSub;
    /**
     * 工作路径
     */
    private String workPath;
    /**
     * 使用的商软
     */
    private AnalysisTool tool;
    /**
     * 节点状态
     */
    private Integer nodeStatus = NodeStatusConst.PRE_NOT_COMPLETED;
    /**
     * 认证token
     */
    private String token;
    /**
     * 分库信息
     */
    private String thingCode;
    /**
     * 前序节点完成数量
     */
    private Integer preNodeCompleteNum;
    /**
     * 优先级
     */
    private Integer level;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 执行计算IP
     */
    private String runHostIp;
    /**
     * 是否暂停
     */
    private Boolean beSuspend;
    /**
     * 工况次节点ID
     */
    private Integer timesNodeId;
    /**
     * 是否可视化节点，如不为空则分配可视化节点
     */
    private String viewType = ViewTypeConst.NO_VIEW;
    /**
     * 远程登录信息
     */
    private RdpAuthEntity rdpAuth;
    /**
     * 文件内容读取规则
     */
    private String fileContentReadingRule;

    /**
     * 商软信息
     */
    private String message;

    /**
     * 前序节点文件Id
     */
    private Map<String, List<String>> preNodeFileIds;
    /**
     * host执行人
     */
    private String hostExecuteUserName;
    /**
     * host安装目录
     */
    private String hostInstallPath;
    /**
     * host执行路径
     */
    private String hostExecutePath;
    /**
     * host文件服务端口
     */
    private String hostFileServerPort;
    /**
     * mq日志队列
     */
    private String uuId;
    /**
     * 队列jobId
     */
    private String jobId;
    /**
     *
     */
    private Integer logicDesignType;
    /**
     *
     */
    List<DispatcherNodeLogicDesign> logicDesigns;
    /**
     * 脚本替换字符串
     */
    private Map<String, Map<String, String>> scriptFileReplace;
    /**
     * 判断是否为可视化节点
     *
     * @return false不是，true是
     */
    public Boolean getViewFlag() {
        return !ViewTypeConst.NO_VIEW.equals(viewType);
    }
    /**
     * 完成数量增加
     *
     */
    public void addCompleteNum() {
        preNodeCompleteNum++;
    }
    /**
     * 前序节点完成判断
     *
     * @return true已完成
     */
    public boolean preNodeIsOk() {
        if (preNodeIds == null) {
            return true;
        }
        return preNodeIds.size() == preNodeCompleteNum;
    }

    @Override
    public int compareTo(@NonNull DispatcherNode node) {
        if (equals(node)) {
            return 0;
        }
        if (level == null) {
            level = NodeStatusConst.MIN_LEVEL;
        }
        if (node.getLevel() == null) {
            node.setLevel(NodeStatusConst.MIN_LEVEL);
        }
        int result = level.compareTo(node.getLevel());
        if (result != 0) {    // 先判断优先级
            return result;
        } else {    // 优先级别相同判断创建时间
            if (createTime == null) {
                createTime = new Date();
            }
            if (node.getCreateTime() == null) {
                node.setCreateTime(new Date());
            }
            result = createTime.compareTo(node.getCreateTime());
            return result == 0 ? 1 : result; //创建时间相同，按加入顺序排列
        }
    }
}