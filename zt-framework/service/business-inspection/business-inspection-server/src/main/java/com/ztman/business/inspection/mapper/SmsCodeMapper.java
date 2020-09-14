package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.entity.SmsCode;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 短信验证码  mapper层
 *
 * @author zxx
 * @date 2020-08-29 11:11:37
 */
public interface SmsCodeMapper extends BaseMapper<SmsCode> {
    /**
     * 查询短信验证码信息
     *
     * @param id 短信验证码ID
     * @return 短信验证码信息
     */
    public SmsCode selectSmsCodeById(Long id);

    /**
     * 查询短信验证码列表
     *
     * @param smsCode 短信验证码信息
     * @return 短信验证码集合
     */
    public List<SmsCode> selectSmsCodeList(SmsCode smsCode);

    /**
     * 模糊查询短信验证码列表
     *
     * @param smsCode 短信验证码信息
     * @return 短信验证码集合
     */
    public List<SmsCode> selectSmsCodeListByLike(SmsCode smsCode);


    /**
     * 分页模糊查询短信验证码列表
     *
     * @param smsCode 短信验证码信息
     * @return 短信验证码集合
     */
    public List<SmsCode> selectSmsCodeListByLike(Query query, SmsCode smsCode);


    /**
     * 新增短信验证码
     *
     * @param smsCode 短信验证码信息
     * @return 结果
     */
    public int insertSmsCode(SmsCode smsCode);

    /**
     * 修改短信验证码
     *
     * @param smsCode 短信验证码信息
     * @return 结果
     */
    public int updateSmsCode(SmsCode smsCode);

    /**
     * 删除短信验证码
     *
     * @param id 短信验证码ID
     * @return 结果
     */
    public int deleteSmsCodeById(Long id);

    /**
     * 批量删除短信验证码
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSmsCodeByIds(String[] ids);



}
