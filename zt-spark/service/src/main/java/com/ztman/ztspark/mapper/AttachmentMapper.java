package com.ztman.ztspark.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.workorder.Attachment;

/**
 * 附件表  mapper层
 *
 * @author zxx
 * @date 2020-09-07 14:46:30
 */
public interface AttachmentMapper extends BaseMapper<Attachment> {
    /**
     * 查询附件表信息
     *
     * @param id 附件表ID
     * @return 附件表信息
     */
    public Attachment selectAttachmentById(Long id);

    /**
     * 查询附件表列表
     *
     * @param attachment 附件表信息
     * @return 附件表集合
     */
    public List<Attachment> selectAttachmentList(Attachment attachment);

    /**
     * 模糊查询附件表列表
     *
     * @param attachment 附件表信息
     * @return 附件表集合
     */
    public List<Attachment> selectAttachmentListByLike(Attachment attachment);


    /**
     * 分页模糊查询附件表列表
     *
     * @param attachment 附件表信息
     * @return 附件表集合
     */
    public List<Attachment> selectAttachmentListByLike(Query query, Attachment attachment);


    /**
     * 新增附件表
     *
     * @param attachment 附件表信息
     * @return 结果
     */
    public int insertAttachment(Attachment attachment);

    /**
     * 修改附件表
     *
     * @param attachment 附件表信息
     * @return 结果
     */
    public int updateAttachment(Attachment attachment);

    /**
     * 删除附件表
     *
     * @param id 附件表ID
     * @return 结果
     */
    public int deleteAttachmentById(Long id);

    /**
     * 批量删除附件表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAttachmentByIds(String[] ids);



}
