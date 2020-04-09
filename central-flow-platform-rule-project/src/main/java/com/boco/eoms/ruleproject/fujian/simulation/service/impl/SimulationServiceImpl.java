package com.boco.eoms.ruleproject.fujian.simulation.service.impl;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.boco.eoms.ruleproject.base.paging.ReturnData;
import com.boco.eoms.ruleproject.base.util.ExcelUtil2007;
import com.boco.eoms.ruleproject.base.util.HttpClientServlet;
import com.boco.eoms.ruleproject.base.util.ReturnJsonUtil;
import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.boco.eoms.ruleproject.base.util.UUIDHexGenerator;
import com.boco.eoms.ruleproject.fujian.gateway.mapper.CwmRuleGatewayMapper;
import com.boco.eoms.ruleproject.fujian.simulation.mapper.SimulationDataMapper;
import com.boco.eoms.ruleproject.fujian.simulation.mapper.SimulationDataParamMapper;
import com.boco.eoms.ruleproject.fujian.simulation.mapper.SimulationExecuteMapper;
import com.boco.eoms.ruleproject.fujian.simulation.mapper.SimulationMapper;
import com.boco.eoms.ruleproject.fujian.simulation.model.SheetCondition;
import com.boco.eoms.ruleproject.fujian.simulation.model.Simulation;
import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationCondition;
import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationData;
import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationDataParam;
import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationExecute;
import com.boco.eoms.ruleproject.fujian.simulation.model.SimulationParam;
import com.boco.eoms.ruleproject.fujian.simulation.service.ISimulationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service("simulationService")
public class SimulationServiceImpl implements ISimulationService {

    @Autowired
    private Environment env;
    @Autowired
    private SimulationMapper simulationMapper;
    @Autowired
    private SimulationDataMapper simulationDataMapper;
    @Autowired
    private SimulationDataParamMapper simulationDataParamMapper;
    @Autowired
    private SimulationExecuteMapper simulationExecuteMapper;
	//注入人工复检id
	@Value("${gatewayId.reCheckGatewayId}")
	private String reCheckGatewayId;
    //注入人工复检id
    @Value("${ruleRun.num}")
    private int ruleNum;
    @Autowired
    private CwmRuleGatewayMapper cwmRuleGatewayMapper;
    private Logger logger = LoggerFactory.getLogger(SimulationServiceImpl.class);

    /**
     * 网关仿真数据文件导入
     * 导入方式操作性决定数据量不会很多
     * daoru 
     * @param file
     * @param gatewayIds
     * @return
     */
    @Transactional
    public JSONObject upload(MultipartFile file, String gatewayIds, HttpServletRequest req) throws Exception{
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        //获取第一个sheet数据
        Sheet sheet = workbook.getSheetAt(0);
        //获取多少行
        int rows = sheet.getPhysicalNumberOfRows();
        //获取列数
        int colunms = sheet.getRow(0).getLastCellNum();
        //创建仿真数据
        Simulation simulation = new Simulation();
        String simulationId = UUIDHexGenerator.getInstance().getID();
        simulation.setId(simulationId);
        //设置操作人
        simulation.setOperator(StaticMethod.nullObject2String(req.getSession().getAttribute("userId")));
        simulation.setOperationButton("upload");
        simulation.setGatewayIds(gatewayIds);
        simulation.setSheetTotal(String.valueOf(rows-1));
        
        //遍历每一行，注意：第 0 行为标题 
        if(rows>1){
            Row titleRow = sheet.getRow(0);
            Map fieldMap = getFieldinfo(gatewayIds);
            //excel导入校验
            List<String> fileTitle = new ArrayList<>();
            List<String> titleList = new ArrayList<String>(fieldMap.values());
            for (int i = 0; i < colunms; i++) {
                Cell title = titleRow.getCell(i);
                String fieldValue = ExcelUtil2007.getCellValue(title);
                fileTitle.add(fieldValue);
            }
            //判断标题内容是否一致
            boolean isEqual = StaticMethod.isEqual(titleList,fileTitle);
            if(!isEqual){
                return ReturnJsonUtil.returnFailInfo("上传文件字段和网关相关的字段不匹配");
            }
            //仿真数据表数据
            List<SimulationData> simulationDatas = new ArrayList<SimulationData>();
            //仿真数据参数表数据
            List<SimulationDataParam> simulationDataParams = new ArrayList<SimulationDataParam>();
            for (int j = 1; j < rows; j++) {
                //获得第 j 行
                Row row = sheet.getRow(j);
                //仿真数据对象设置
                SimulationData simulationData = new SimulationData();
                String simulationDataId = UUIDHexGenerator.getInstance().getID();
                simulationData.setId(simulationDataId);
                simulationData.setSimulationId(simulationId);
                for (int i = 0; i < colunms; i++) {
                    //字段值
                    Cell fieldValueCell = row.getCell(i);
                    String fieldValue = ExcelUtil2007.getCellValue(fieldValueCell);
                    //字段中文名的cell
                    Cell fieldCnName = titleRow.getCell(i);
                    String fieldEnName = StaticMethod.getKey(fieldMap,fieldCnName);
                    if("sheetId".contains(fieldEnName)){
                        simulationData.setSheetId(fieldValue);
                    }else if("title".contains(fieldEnName)){
                        simulationData.setSheetTitle(fieldValue);
                    }else if("mainId".contains(fieldEnName)){
                        simulationData.setMainId(fieldValue);
                    }else{
                        //仿真数据参数对象设置
                        SimulationDataParam simulationDataParam = new SimulationDataParam();
                        simulationDataParam.setSimulationDataId(simulationDataId);
                        simulationDataParam.setInputParamEnname(fieldEnName);
                        simulationDataParam.setInputParamCnname(fieldCnName.getStringCellValue());
                        simulationDataParam.setInputParamValue(fieldValue);
                        simulationDataParams.add(simulationDataParam);
                    }
                }
                simulationDatas.add(simulationData);
                if(simulationDatas.size()>=500){
                    //仿真数据对象批量保存
                    simulationDataMapper.batchInsert(simulationDatas);
                    //仿真数据参数对象批量保存
                    simulationDataParamMapper.batchInsert(simulationDataParams);
                    simulationDatas.clear();
                    simulationDataParams.clear();
                }
            }
            if(!simulationDatas.isEmpty()){
                //仿真数据对象批量保存
                simulationDataMapper.batchInsert(simulationDatas);
                //仿真数据参数对象批量保存
                simulationDataParamMapper.batchInsert(simulationDataParams);
            }
        }
        //仿真对象保存
        simulationMapper.insert(simulation);
        JSONObject simulationData = new JSONObject();
        simulationData.put("simulationId",simulationId);
        return ReturnJsonUtil.returnSuccessInfo(simulationData,"仿真数据导入成功");
    }

    /**
     * 仿真数据导出
     * @param gatewayIds
     */
    public void download(String gatewayIds, HttpServletResponse response) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建一个Excel表单,参数为sheet的名字
        XSSFSheet sheet = workbook.createSheet("仿真字段");
        //获取excel表头数据
        Map fieldMap = getFieldinfo(gatewayIds);
        //初始化集合
        List<String> titleList = new ArrayList<String>(fieldMap.values());
        //创建表头
        ExcelUtil2007.setTitle(workbook, sheet,titleList);
        //解决文件名乱码
        String fileName = new String(("仿真数据模板").getBytes("gb2312"), "ISO-8859-1") + ".xlsx";
        //清空response
        response.reset();
        //设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename="+ fileName);
        response.setHeader("Access-Control-Allow-Origin", "*");
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/vnd.ms-excel;charset=gb2312");
        //将excel写入到输出流中
        workbook.write(os);
        os.flush();
        os.close();
    }

    /**
     * 根据父节点名称获取地区信息
     * @param parentAreaId
     * @return
     */
    public JSONObject getCitys(String parentAreaId) {
        String cityUrl = env.getProperty("interfaceInfo.cityUrl");
        JSONObject propertyObj = new JSONObject();
        if("".equals(parentAreaId)){
            parentAreaId = env.getProperty("interfaceParam.parentAreaId");
        }
        propertyObj.put("parentAreaId", parentAreaId);
        String entityStr = HttpClientServlet.httpPostRaw(cityUrl,propertyObj.toString(),null,null);
        JSONObject entity = JSON.parseObject(entityStr);
        if("success".equals(entity.get("result"))){
            JSONArray cityArr = entity.getJSONArray("data");
            return ReturnJsonUtil.returnSuccessInfo(cityArr,"地市获取成功");
        }else{
            return ReturnJsonUtil.returnFailInfo("地市获取异常");
        }
    }

    /**
     * 仿真数据批量保存
     * 去除事务控制，做到进度查询
     * @param param
     * @return
     */
    public JSONObject saveSimulationData(JSONObject param) throws Exception{
        if(param!=null){
            //仿真id
            String simulationId = StaticMethod.nullObject2String(param.getString("simulationId"));
            if("".equals(simulationId))return ReturnJsonUtil.returnFailInfo("数据错误");
            //根据仿真id查询数据
            String gatewayIds = StaticMethod.nullObject2String(simulationMapper.selectById(simulationId).getGatewayIds());
            JSONArray fieldArr =  param.getJSONArray("fieldInfo");
            if(!fieldArr.isEmpty()){
                //仿真数据表数据
                List<SimulationData> simulationDatas = new ArrayList<SimulationData>();
                //仿真数据参数表数据
                List<SimulationDataParam> simulationDataParams = new ArrayList<SimulationDataParam>();
                Map fieldMap = getFieldinfo(gatewayIds);
                for (int i = 0; i < fieldArr.size(); i++) {
                    JSONObject fieldObj = fieldArr.getJSONObject(i);
                    //仿真数据对象设置
                    SimulationData simulationData = new SimulationData();
                    String simulationDataId = UUIDHexGenerator.getInstance().getID();
                    simulationData.setId(simulationDataId);
                    simulationData.setSimulationId(simulationId);
                    for(Map.Entry<String, Object> entry : fieldObj.entrySet()) {
                        String fieldEnName = entry.getKey();
                        String fieldValue = entry.getValue().toString();
                        if("sheetId".contains(fieldEnName)){
                            simulationData.setSheetId(fieldValue);
                        }else if("title".contains(fieldEnName)){
                            simulationData.setSheetTitle(fieldValue);
                        }else if("mainId".contains(fieldEnName)){
                            simulationData.setMainId(fieldValue);
                        }else{
                            //仿真数据参数对象设置
                            SimulationDataParam simulationDataParam = new SimulationDataParam();
                            simulationDataParam.setSimulationDataId(simulationDataId);
                            simulationDataParam.setInputParamEnname(fieldEnName);
                            simulationDataParam.setInputParamCnname(fieldMap.get(fieldEnName).toString());
                            simulationDataParam.setInputParamValue(fieldValue);
                            simulationDataParams.add(simulationDataParam);
                        }
                    }
                    simulationDatas.add(simulationData);

                    //数据进度明显性，百条数据批量插入一次
                    if(simulationDatas.size()>=100){
                        //仿真数据对象批量保存
                        simulationDataMapper.batchInsert(simulationDatas);
                        //仿真数据参数对象批量保存
                        simulationDataParamMapper.batchInsert(simulationDataParams);
                        simulationDatas.clear();
                        simulationDataParams.clear();
                    }
                }
                if(!simulationDatas.isEmpty()){
                    //仿真数据对象批量保存
                    simulationDataMapper.batchInsert(simulationDatas);
                }
                if(!simulationDataParams.isEmpty()){
                    //仿真数据参数对象批量保存
                    simulationDataParamMapper.batchInsert(simulationDataParams);
                }
                return ReturnJsonUtil.returnSuccessInfo("保存成功");
            }else{
                return ReturnJsonUtil.returnFailInfo("数据为空");
            }
        }else{
            return ReturnJsonUtil.returnFailInfo("字段信息为空");
        }
    }

    /**
     * 仿真运行数据修改
     * @param param
     * @return
     * @throws Exception
     */
    public JSONObject updateSimulationRunData(JSONObject param) throws Exception {
        if(param!=null) {
            //仿真id
            String simulationId = StaticMethod.nullObject2String(param.getString("simulationId"));
            //是否最后一个网关
            String isLastGateWay = StaticMethod.nullObject2String(param.getString("isLastGateWay"));
            //网关id
            String gatewayId = StaticMethod.nullObject2String(param.getString("gatewayId"));
            //仿真运行数据
            JSONArray simulationRunArr =  param.getJSONArray("simulationRunArr");
            //批量修改
            List<SimulationExecute> simulationExecutes = new ArrayList<>();
            if(!simulationRunArr.isEmpty()){
                //仿真执行数据
                List<SimulationExecute> simulationRunDatas = new ArrayList<SimulationExecute>();
                for (int i = 0; i < simulationRunArr.size(); i++) {
                    JSONObject simulationRunObj = simulationRunArr.getJSONObject(i);
                    //仿真执行对象设置
                    SimulationExecute simulationExecute = new SimulationExecute();
                    simulationExecute.setId(simulationRunObj.getString("simulationExecuteId"));
                    simulationExecute.setSimulationExecuteDuration(simulationRunObj.getIntValue("simulationExecuteDuration"));
                    simulationExecute.setSimulationOutputParam(simulationRunObj.getString("simulationOutputParam"));
                    simulationExecute.setSimulationError(simulationRunObj.getString("simulationError"));
                    simulationExecute.setAiSimilarScore(simulationRunObj.getString("aiSimilarScore"));
                    String executeResult = StaticMethod.nullObject2String(simulationRunObj.getString("executeResult"));
                    //设置反向
                    if(!gatewayId.equals(reCheckGatewayId)){
                        if("true".equals(executeResult)){
                            simulationExecute.setSimulationResult("false");
                        }else{
                            simulationExecute.setSimulationResult("true");
                        }
                    }else{
                        simulationExecute.setSimulationResult(executeResult);
                    }
                    simulationExecutes.add(simulationExecute);
                }
                //仿真数据对象批量修改
                simulationExecuteMapper.batchUpdate(simulationExecutes);
                //更新工单得仿真结果
                if ("true".equals(isLastGateWay)) {
                    //根据仿真id查询仿真数据
                    List<SimulationData> simulationDataList = simulationDataMapper.getListBySimulationId(simulationId);
                    boolean isExistData = false;//判断是否需要修改,默认不需要
                    for (int i = 0; i < simulationDataList.size(); i++) {
                        List<SimulationExecute> simulationExecuteList =
                                simulationExecuteMapper.getListBySimDataId(simulationDataList.get(i).getId());
                        String executeResult = "true";
                        int executeDuration = 0;
                        if(simulationExecuteList==null||simulationExecuteList.size()==0){
                            continue;
                        }
                        isExistData = true;
                        for (int i1 = 0; i1 < simulationExecuteList.size(); i1++) {
                            SimulationExecute simulationExecute = simulationExecuteList.get(i1);
                            String runGatewayId = simulationExecute.getGatewayId();
                            String simulationError = StaticMethod.nullObject2String(simulationExecute.getSimulationError());
                            if(!"".equals(simulationError)){
                                executeResult = "exception";
                            }
                            if(!"exception".equals(executeResult)&&!reCheckGatewayId.equals(runGatewayId)
                                    &&"true".equals(executeResult)&&!"true".equals(simulationExecute.getSimulationResult())){
                                executeResult = "false";
                            }
                            executeDuration+=simulationExecute.getSimulationExecuteDuration();
                        }
                        simulationDataList.get(i).setExecuteResult(executeResult);
                        simulationDataList.get(i).setExecuteDuration(executeDuration);
                    }
                    //批量更新仿真结果
                    if(isExistData){
                        simulationDataMapper.batchUpdate(simulationDataList);
                    }
                }
                return ReturnJsonUtil.returnSuccessInfo("修改成功");
            }else{
                return ReturnJsonUtil.returnFailInfo("数据为空");
            }
        }else{
            return ReturnJsonUtil.returnFailInfo("参数信息为空");
        }
    }

    @Override
    public JSONObject getDicts(String parentDictId) {
        String dictUrl = env.getProperty("interfaceInfo.dictUrl");
        JSONObject propertyObj = new JSONObject();
        if("".equals(parentDictId)){
            parentDictId = env.getProperty("interfaceParam.parentDictId");
        }
        propertyObj.put("parentDictId", parentDictId);
        String entityStr = HttpClientServlet.httpPostRaw(dictUrl,propertyObj.toString(),null,null);
        JSONObject entity = JSON.parseObject(entityStr);
        if("success".equals(entity.get("result"))){
            JSONArray cityArr = entity.getJSONArray("data");
            return ReturnJsonUtil.returnSuccessInfo(cityArr,"字典获取成功");
        }else{
            return ReturnJsonUtil.returnFailInfo("字典获取异常");
        }
    }

    /**
     * 去除事务，保证先记录仿真操作，然后更新工单量
     * @param simulationParam
     * @return
     * @throws Exception
     */
    public JSONObject getSimulationData(SimulationParam simulationParam,HttpServletRequest req) throws Exception{
        //仿真表的保存
        String simulationId = UUIDHexGenerator.getInstance().getID();
        Simulation simulation = new Simulation();
        simulation.setId(simulationId);
        //设置操作人,待修改
        simulation.setOperator(StaticMethod.nullObject2String(req.getSession().getAttribute("userId")));
        simulation.setOperationButton("autoObtain");
        //获取网关标识
        String[] gateway = simulationParam.getGateWayIds();
        String gatewayIds = StringUtils.join(gateway, ",");
        simulation.setGatewayIds(gatewayIds);

        //获取字段信息
        Map fieldMap = getFieldinfo(gatewayIds);
        //初始化集合
        List<String> fieldList = new ArrayList<String>(fieldMap.keySet());
        JSONArray fieldArr= JSONArray.parseArray(JSON.toJSONString(fieldList));
        //获取仿真数据获取接口地址
        String simulationDataUrl = env.getProperty("interfaceInfo.simulationDataUrl");
        JSONObject propertyObj = new JSONObject(true);
        String sheetStartTime = StaticMethod.dateToString(simulationParam.getSheetStartTime());
        String sheetEndTime = StaticMethod.dateToString(simulationParam.getSheetEndTime());
        propertyObj.put("city", StaticMethod.nullObject2String(simulationParam.getCity()));
        propertyObj.put("networkSheetOne",StaticMethod.nullObject2String(simulationParam.getNetworkSheetOne()) );
        propertyObj.put("networkSheetTwo",StaticMethod.nullObject2String(simulationParam.getNetworkSheetTwo()) );
        propertyObj.put("sheetStartTime",sheetStartTime );
        propertyObj.put("sheetEndTime",sheetEndTime);
        propertyObj.put("simulationId", simulationId);
        propertyObj.put("fieldInfo", fieldArr);

        simulation.setCity(StaticMethod.nullObject2String(simulationParam.getCityName()));
        simulation.setNetworkSheetOne(StaticMethod.nullObject2String(simulationParam.getNetworkSheetOneName()));
        simulation.setNetworkSheetTwo(StaticMethod.nullObject2String(simulationParam.getNetworkSheetTwoName()));
        simulation.setSheetStartTime(StaticMethod.stringToDate(sheetStartTime,"yyyy-MM-dd HH:mm:ss"));
        simulation.setSheetEndTime(StaticMethod.stringToDate(sheetEndTime,"yyyy-MM-dd HH:mm:ss"));
        //仿真得保存
        simulationMapper.insert(simulation);

        //获取仿真数据
        String entityStr = HttpClientServlet.httpPostRaw(simulationDataUrl,propertyObj.toString(),null,null);
        JSONObject entity = JSON.parseObject(entityStr);
        if("success".equals(entity.get("result"))){
            JSONObject simulationData = entity.getJSONObject("data");
            simulationData.put("simulationId",simulationId);
            String sheetTotal =  simulationData.getString("sheetTotal");
            //根据仿真id添加工单量
            simulationMapper.updateSheetTotal(simulationId,sheetTotal);
            return ReturnJsonUtil.returnSuccessData(simulationData,"仿真数据获取成功");
        }else{
            logger.info("仿真数据获取异常,仿真失败");
            return ReturnJsonUtil.returnFailInfo("仿真数据获取异常");
        }
    }

    /**
     * 根据网关标识仿真规则
     * 注：去除事务控制，需要做到进度查询
     * @param gatewayId
     * @param gatewayName
     * @param simulationId
     * @return
     * @throws Exception
     */
    public JSONObject excuteRule(String gatewayId,String gatewayName,String simulationId,String isLastGateWay) throws Exception{
        //仿真执行记录
        List<SimulationExecute>  simulationExecutesList = new ArrayList<>();
        //根据仿真id查询仿真数据
        List<SimulationData> simulationDataList = simulationDataMapper.getListBySimulationId(simulationId);
        //检查是否已仿真过
        int simulationSheetNum = simulationExecuteMapper.simulationSheetNum(gatewayId,simulationId);
        if(simulationSheetNum>0){
            return ReturnJsonUtil.returnFailInfo("该仿真已执行过.");
        }
        //仿真规则
        String ip = env.getProperty("ruleReloadInfo.ip");
        String port = env.getProperty("ruleReloadInfo.port");
        String version = env.getProperty("ruleReloadInfo.version");
        String url = "http://"+ip+":"+port+"/"+version+"/rule/"+gatewayId+"/asyncExcuteRule";
        if(simulationDataList==null||simulationDataList.size()==0){
            return ReturnJsonUtil.returnFailInfo("规则仿真失败");
        }
        //仿真数据入库
        JSONObject paramObj = new JSONObject(true);
        paramObj.put("isLastGateWay",isLastGateWay);
        paramObj.put("simulationId",simulationId);
        paramObj.put("gatewayId",gatewayId);
        JSONArray dataArr = new JSONArray();
        for (int i = 0; i < simulationDataList.size(); i++) {
            //仿真执行
            JSONObject simulationObj = new JSONObject(true);
            SimulationData simulationData = simulationDataList.get(i);
            String simulationExecuteId = UUIDHexGenerator.getInstance().getID();
            simulationObj.put("simulationExecuteId",simulationExecuteId);
            simulationObj.put("sheetId",simulationData.getSheetId());
            simulationObj.put("title",simulationData.getSheetTitle());
            simulationObj.put("mainId",simulationData.getMainId());
            String simulationDataId = simulationData.getId();
            //根据仿真数据id查询仿真参数信息
            List<SimulationDataParam> simulationDataParamList=
                    simulationDataParamMapper.getListBySimulationDataId(simulationDataId);
            if(simulationDataParamList!=null&&simulationDataParamList.size()>0){
                for (int i1 = 0; i1 < simulationDataParamList.size(); i1++) {
                    SimulationDataParam simulationDataParam = simulationDataParamList.get(i1);
                    simulationObj.put(simulationDataParam.getInputParamEnname(),simulationDataParam.getInputParamValue());
                }
            }
            dataArr.add(simulationObj);
            //仿真执行记录
            SimulationExecute simulationExecute = new SimulationExecute();
            simulationExecute.setId(simulationExecuteId);
            simulationExecute.setSimulationDataId(simulationData.getId());
            simulationExecute.setGatewayId(gatewayId);
            simulationExecute.setGatewayName(gatewayName);
            simulationExecutesList.add(simulationExecute);
            if(dataArr.size()>=ruleNum){
                //保证在调用前数据已插入,防止回调修改失败
                simulationExecuteMapper.batchInsert(simulationExecutesList);
                simulationExecutesList.clear();
                paramObj.put("simulationRunArr",dataArr);
                String entityStr =HttpClientServlet.httpPostRaw(url,paramObj.toString(),null,null);
                logger.info("规则执行调用接口相应结果为:"+entityStr);
                //清除数据
                dataArr.clear();
                logger.info("仿真批量执行完成.");
            }
        }
        //仿真记录入库
        if(!simulationExecutesList.isEmpty()){
            //保存
            simulationExecuteMapper.batchInsert(simulationExecutesList);
        }
        //批量执行剩余规则
        if(!dataArr.isEmpty()){
            paramObj.put("simulationRunArr",dataArr);
            String entityStr =HttpClientServlet.httpPostRaw(url,paramObj.toString(),null,null);
            logger.info("规则执行调用接口相应结果为:"+entityStr);
        }
        return ReturnJsonUtil.returnSuccessInfo("规则执行成功");
    }

    /**
     * 仿真进度获取
     * @param simulationId
     * @return
     */
    public JSONObject getSimulationDataProgess(String simulationId) {
        //查询仿真数据获取进度
        Simulation simulation = simulationMapper.selectById(simulationId);
        //工单总数
        int sheetTotal = StaticMethod.nullObject2int(simulation.getSheetTotal());
        int sheetNum = simulationDataMapper.sheetNum(simulationId);
        Double result = 1.0;
        if(sheetTotal!=0){
            BigDecimal b = new BigDecimal((float)sheetNum/sheetTotal);
            //四舍五入保留2位
            result = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        JSONObject simulationProgessObj = new JSONObject();
        simulationProgessObj.put("simulationProgess",result.toString());
        return ReturnJsonUtil.returnSuccessInfo(simulationProgessObj,"获取仿真进度成功");
    }

    /**
     * 仿真执行进度获取
     * @param gatewayId 网关id
     * @param simulationId 仿真id
     * @return
     */
    public JSONObject simulationExecuteProgess(String gatewayId, String simulationId) {
        //查询的仿真工单量
        int sheetTotal = simulationDataMapper.sheetNum(simulationId);
        //指定网关下仿真执行的工单量
        int simulationSheetNum = simulationExecuteMapper.simulationSheetNum(gatewayId,simulationId);
        Double result = 1.0;
        if(sheetTotal!=0){
            BigDecimal b = new BigDecimal((float)simulationSheetNum/sheetTotal);
            //四舍五入保留2位
            result = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        JSONObject simulationExecuteProgess = new JSONObject();
        simulationExecuteProgess.put("simulationExecuteProgess",result);
        return ReturnJsonUtil.returnSuccessInfo(simulationExecuteProgess,"获取仿真进度成功");
    }

    @Override
    public ReturnData<Simulation> getSimulations(SimulationCondition simulationCondition) throws Exception {
        //当前页
        Integer curPage = simulationCondition.getCurPage();
        //页的大小
        Integer pageSize = simulationCondition.getPageSize();
        long total = 0;
        //仿真搜索开始时间
        Date simulationStartTime = simulationCondition.getSimulationStartTime();
        //仿真搜索结束时间
        Date simulationEndTime = simulationCondition.getSimulationEndTime();
        Page page = PageHelper.startPage(curPage,pageSize);
        List<Simulation> simulationList = simulationMapper.getSimulations(simulationStartTime,simulationEndTime);
        //封装数据
        total = page.getTotal();
        ReturnData<Simulation> simulationData = new ReturnData<Simulation>();
        simulationData.setTotal(total);
        simulationData.setRows(simulationList);
        return simulationData;
    }

    /**
     * 条件查询工单数据
     * @param sheetCondition
     * @return
     * @throws Exception
     */
    public ReturnData<SimulationData> getSimulationDatas(SheetCondition sheetCondition) throws Exception {
        //当前页
        Integer curPage = sheetCondition.getCurPage();
        //页的大小
        Integer pageSize = sheetCondition.getPageSize();
        long total = 0;
        //仿真id
        String simulationId = sheetCondition.getSimulationId();
        //AI质检是否通过
        String executeResult = sheetCondition.getExecuteResult();
        Page page = PageHelper.startPage(curPage,pageSize);
        List<SimulationData> simulationDataList = simulationDataMapper.getSimulationDatas(simulationId,executeResult);
        //封装数据
        total = page.getTotal();
        ReturnData<SimulationData> simulationSheetData = new ReturnData<SimulationData>();
        simulationSheetData.setTotal(total);
        simulationSheetData.setRows(simulationDataList);
        return simulationSheetData;
    }

    /**
     * 获取工单详情数据
     * @param simulationDataId
     * @return
     */
    public JSONObject getSheetDetail(String simulationDataId) throws Exception{
        //工单详情json
        JSONObject sheetDetail = new JSONObject(true);
        //根据id查询仿真数据
        SimulationData simulationData = simulationDataMapper.getSimulationDataById(simulationDataId);
        //工单输入
        JSONObject sheetInput = new JSONObject(true);
        sheetInput.put("工单主题",simulationData.getSheetTitle());
        sheetInput.put("工单流水号",simulationData.getSheetId());
        sheetInput.put("executeResult",simulationData.getExecuteResult());
        List<SimulationDataParam> simulationDataParams = simulationDataParamMapper.getListBySimulationDataId(simulationDataId);
        if(simulationDataParams!=null&&simulationDataParams.size()>0){
            for (int i = 0; i < simulationDataParams.size(); i++) {
                SimulationDataParam simulationDataParam = simulationDataParams.get(i);
                sheetInput.put(simulationDataParam.getInputParamCnname(),simulationDataParam.getInputParamValue());
            }
        }
        //工单输出
        JSONArray sheetOutput = new JSONArray();
        List<SimulationExecute>  simulationExecutes = simulationExecuteMapper.getListBySimDataId(simulationDataId);
        if(simulationExecutes!=null&&simulationExecutes.size()>0){
            for (int i = 0; i < simulationExecutes.size(); i++) {
                SimulationExecute simulationExecute = simulationExecutes.get(i);
                String simulationExecuteId = UUIDHexGenerator.getInstance().getID();
                JSONObject ruleExecute = new JSONObject(true);
                ruleExecute.put("index",i);
                ruleExecute.put("id",simulationExecuteId);
                ruleExecute.put("pId",0);
                ruleExecute.put("gatewayName",simulationExecute.getGatewayName());
                //规则id
                String ruleId = simulationExecute.getGatewayId();
                String groupDescription = cwmRuleGatewayMapper.getGatewayById(ruleId);
                ruleExecute.put("groupDescription",groupDescription);
                ruleExecute.put("executeDuration",simulationExecute.getSimulationExecuteDuration());
                String simulationResult = StaticMethod.nullObject2String(simulationExecute.getSimulationResult());
                String simulationError = StaticMethod.nullObject2String(simulationExecute.getSimulationError());
                if(reCheckGatewayId.equals(ruleId)){
                    if("true".equals(simulationResult)){
                        ruleExecute.put("simulationResult","进入");
                    }else{
                        ruleExecute.put("simulationResult","未进入");
                    }
                }else{
                    if("".equals(simulationError)){
                        ruleExecute.put("simulationResult",simulationResult);
                    }else {
                        ruleExecute.put("simulationResult","exception");
                    }
                }
                //工单输出设置
                sheetOutput.add(ruleExecute);
                //网关对应的参数
                Map fieldMap = getFieldinfo(ruleId);
                //初始化集合
                List<String> fieldList = new ArrayList<String>(fieldMap.keySet());
                if(simulationDataParams!=null&&simulationDataParams.size()>0){
                    for (int i1 = 0; i1 < simulationDataParams.size(); i1++) {
                        SimulationDataParam simulationDataParam = simulationDataParams.get(i1);
                        if(fieldList.contains(StaticMethod.nullObject2String(simulationDataParam.getInputParamEnname()))){
                            JSONObject paramObj = new JSONObject(true);
                            paramObj.put("id",UUIDHexGenerator.getInstance().getID());
                            paramObj.put("pId",simulationExecuteId);
                            paramObj.put("inputParamCnname",simulationDataParam.getInputParamCnname());
                            paramObj.put("inputParamValue",simulationDataParam.getInputParamValue());
                            sheetOutput.add(paramObj);
                        }
                    }
                }
            }
            sheetDetail.put("sheetInput",sheetInput);
            sheetDetail.put("sheetOutput",sheetOutput);
        }
        return sheetDetail;
    }

    /**
     * 根据网关集合获取字段信息
     * @param gatewayIds
     * @return
     * @throws Exception
     */
    public Map getFieldinfo(String gatewayIds) throws  Exception{
        //获取网关的字段
        String ip = env.getProperty("ruleReloadInfo.ip");
        String port = env.getProperty("ruleReloadInfo.port");
        String version = env.getProperty("ruleReloadInfo.version");
        String url = "http://"+ip+":"+port+"/"+version+"/rule/getCnAndEnInputParams?ruleId="+gatewayIds;
        JSONObject returnObj = HttpClientServlet.httpPost(url);
        String status = StaticMethod.nullObject2String(returnObj.get("status"));
        if("200".equals(status)){
            JSONObject paramsResultObj = returnObj.getJSONObject("paramsResult");
            return JSONObject.parseObject(paramsResultObj.toJSONString(),
                    new TypeReference<Map<String, String>>(){});
        }else{
            throw new Exception();
        }
    }

    /**
     * 获取AI相似度数据
     */
	@Override
	public JSONObject simulationSimilarData(String simulationId) {
		JSONObject json = new JSONObject();
		List<Map> list = simulationExecuteMapper.getSimulationSimilarData(simulationId);
		if(list != null && list.size() > 0) {
			for(Map map : list) {
				String key = StaticMethod.nullObject2String(map.get("SCORE"));
				String value = StaticMethod.nullObject2String(map.get("NUM"));
				json.put(key, value);
			}
			return ReturnJsonUtil.returnSuccessData(json, "获取AI相似度数据成功");
		}else {
			return ReturnJsonUtil.returnFailInfo("获取AI相似度数据失败");
		}
	}

	/**
	 * 仿真数据统计
	 */
	@Override
	public JSONObject simulationStatictisData(String simulationId) {
		JSONObject json = new JSONObject();
		try {
			int totalSimulationTask = simulationExecuteMapper.totalSimulationTask(simulationId);//总任务数
			int totalComplateSimulationTask = simulationExecuteMapper.totalComplateSimulationTask(simulationId);//已完成仿真数
			int totalSimulationReCheckTask = simulationExecuteMapper.totalSimulationReCheckTask(simulationId, reCheckGatewayId);//进入人工复检数
			long totalSimulationExcuteTime = simulationExecuteMapper.totalSimulationExcuteTime(simulationId);//仿真执行总时长
			int totalSimulationNotReCheckTask = simulationExecuteMapper.totalSimulationNotReCheckTask(simulationId, reCheckGatewayId);//非人工复检总任务数
			int totalSimulationNotReCheckTaskPass = simulationExecuteMapper.totalSimulationNotReCheckTaskPass(simulationId, reCheckGatewayId);//非人工复检通过任务数
			//计算完成百分比
			NumberFormat nt = NumberFormat.getPercentInstance();
			//百分号
			String complatePercent = nt.format((double)totalComplateSimulationTask/totalSimulationTask);
			//质检通过率
			String passRate = "";
			//若只选择人工复检网关
			if(totalSimulationNotReCheckTask != 0) {
				passRate = nt.format((double)totalSimulationNotReCheckTaskPass/totalSimulationNotReCheckTask);
			}else {
				passRate = "0%";
			}
			json.put("totalTask", totalSimulationTask);//总任务数
			json.put("complateTask", totalComplateSimulationTask);//已完成仿真数
			json.put("complatePercent", complatePercent);//已完成百分比
			json.put("coverRate", "100%");//覆盖率
			json.put("reCheckTask", totalSimulationReCheckTask);//进入人工复检数
			json.put("average", totalSimulationExcuteTime/totalSimulationTask);//平均耗时
			json.put("passRate", passRate);//质检通过率
			return ReturnJsonUtil.returnSuccessData(json, "获取统计数据成功");
		} catch (Exception e) {
			logger.error("获取统计数据失败",e);
			return ReturnJsonUtil.returnFailInfo("获取统计数据失败");
		}
	}
}
