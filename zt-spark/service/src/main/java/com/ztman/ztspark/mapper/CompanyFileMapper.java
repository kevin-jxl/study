package com.ztman.ztspark.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.company.CompanyFile;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 文档管理表  mapper层
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:56
 */
public interface CompanyFileMapper extends BaseMapper<CompanyFile> {
    /**
     * 查询文档管理表信息
     *
     * @param id 文档管理表ID
     * @return 文档管理表信息
     */
    public CompanyFile selectCompanyFileById(Integer id);

    /**
     * 查询文档管理表列表
     *
     * @param companyFile 文档管理表信息
     * @return 文档管理表集合
     */
    public List<CompanyFile> selectCompanyFileList(CompanyFile companyFile);

    /**
     * 模糊查询文档管理表列表
     *
     * @param companyFile 文档管理表信息
     * @return 文档管理表集合
     */
    public List<CompanyFile> selectCompanyFileListByLike(CompanyFile companyFile);


    /**
     * 分页模糊查询文档管理表列表
     *
     * @param companyFile 文档管理表信息
     * @return 文档管理表集合
     */
    public List<CompanyFile> selectCompanyFileListByLike(Query query, CompanyFile companyFile);


    /**
     * 新增文档管理表
     *
     * @param companyFile 文档管理表信息
     * @return 结果
     */
    public int insertCompanyFile(CompanyFile companyFile);

    /**
     * 修改文档管理表
     *
     * @param companyFile 文档管理表信息
     * @return 结果
     */
    public int updateCompanyFile(CompanyFile companyFile);

    /**
     * 删除文档管理表
     *
     * @param id 文档管理表ID
     * @return 结果
     */
    public int deleteCompanyFileById(Integer id);

    /**
     * 批量删除文档管理表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCompanyFileByIds(String[] ids);


    CompanyFile selectCompanyFileByCode(@Param("code") String code);

    int insertCompanyFileData(@Param("list") List<Integer> imgs, @Param("id") Integer id);

    List<HashMap> selectCompanyFileDataById(Integer id);

    int deleteCompanyFileDataById(Integer id);
}
