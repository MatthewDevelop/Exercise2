package com.foxconn.test.test;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-29 上午9:58
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface IAndroidWebReportService {

    /**
     * 查询商品的销售量及单台平均销售量
     *
     * @param orgId      組織ID(Integer)(必填项)
     * @param cluId      聚落Id(String)
     * @param searchTime 销售开始时间(String)
     * @param page       页码(Integer)
     * @param pageSize   每页条数(Integer)
     * @return
     * spName:             商品名称(String)
     * spSaleCountNum:     商品销售量(Integer)
     * averageSaleNum:     机台平均销售量(Double)
     * <p>
     * 返回Json格式的字符串:
     * {"Rows":[{"spName":"","spSaleCountNum":,"averageSaleNum":}],"Total":}
     */
    String listSpSale(Integer orgId, String cluId, String searchTime, Integer page, Integer pageSize);

    /**
     * 单个贩卖机销售额和销售量（包括机台编号和机台位置）
     *
     * @param orgId      組織ID(Integer)(必填项)
     * @param cluId      聚落Id(String)
     * @param searchTime 销售开始时间(String)
     * @param page       页码(Integer)
     * @param pageSize   每页条数(Integer)
     * @return
     * address:              机台位置(String)
     * vmId:                机台编号(String)
     * spSaleCount:          机台总销售量(Int)
     * spSaleSum:           机台总销售额(double)
     * vmNum:             总机台数(Int)
     * allSpSaleCount:        所有机台总销售量(int)
     * allSpSaleSum:         所有机台总销售额(double)
     * <p>
     * 返回Json格式的字符串:
     * {"Rows":[{"address":"","vmId":"","spSaleCount":,"spSaleSum":}],
     * "vmNum":,"allSpSaleCount":,"allSpSaleSum":,"Total":}
     */
    String listVmSale(Integer orgId, String cluId, String searchTime, Integer page, Integer pageSize);

    /**
     * 每天销售日志
     *
     * @param vmId       机台编号(String)
     * @param searchTime 开始查询时间(String)
     * @param page       页码(Integer)
     * @param pageSize   每页条数(Integer)
     * @return
     * saleTime:         销售时间(String)
     * spName:          商品名称(String)
     * saleType:          销售类型(String)
     * vmId:            售货机编号(String)
     * searchTime:        查询日期(String)
     * <p>
     * 返回Json格式的字符串:
     * {"Rows":[{"saleTime":"","spName":"","saleType":""}],
     * "vmId":"","searchTime":"","Total":}
     */
    String listVmDailySale(String vmId, String searchTime, Integer page, Integer pageSize);

    /**
     * 近一个星期的全部销售额
     *
     * @param orgId      組織ID(Integer)(必填项)
     * @param cluId      聚落Id(String)
     * @param searchTime 开始查询时间(String)
     * @return
     * saleTime:          销售日期(String)
     * monthSum:         查询日期的当月销售额(double)
     * saleSum:           总销售额(double)
     * cashSum:           现金销售额(double)
     * onlineSum:          在线销售额(double)
     * saleNum:           总销售量(int)
     * cashNum:           现金销售量(int)
     * onlineNum:         在线销售量(int)
     * <p>
     * 返回Json格式的字符串:
     * {"Rows":[{"saleTime":"","monthSum":,"saleSum":,"cashSum":,
     * "onlineSum":,"saleNum":,"cashNum":,"onlineNum":}],"Total":}
     */
    String listWeekSale(Integer orgId, String cluId, String searchTime);
}
