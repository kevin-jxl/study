package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.entity.Attachment;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 附件  mapper层
 *
 * @author zxx
 * @date 2020-08-29 11:05:32
 */
public interface AttachmentMapper extends BaseMapper<Attachment> {
    /**
     * 查询附件信息
     *
     * @param id 附件ID
     * @return 附件信息
     */
    public Attachment selectAttachmentById(Long id);

    /**
     * 查询附件列表
     *
     * @param attachment 附件信息
     * @return 附件集合
     */
    public List<Attachment> selectAttachmentList(Attachment attachment);

    /**
     * 模糊查询附件列表
     *
     * @param attachment 附件信息
     * @return 附件集合
     */
    public List<Attachment> selectAttachmentListByLike(Attachment attachment);


    /**
     * 分页模糊查询附件列表
     *
     * @param attachment 附件信息
     * @return 附件集合
     */
    public List<Attachment> selectAttachmentListByLike(Query query, Attachment attachment);


    /**
     * 新增附件
     *
     * @param attachment 附件信息
     * @return 结果
     */
    public int insertAttachment(Attachment attachment);

    /**
     * 修改附件
     *
     * @param attachment 附件信息
     * @return 结果
     */
    public int updateAttachment(Attachment attachment);

    /**
     * 删除附件
     *
     * @param id 附件ID
     * @return 结果
     */
    public int deleteAttachmentById(Long id);

    /**
     * 批量删除附件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAttachmentByIds(String[] ids);



}
