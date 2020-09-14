package com.ztman.ztspark.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.common.core.util.Query;
import com.ztman.tools.number.RandomCodeUtils;
import com.ztman.ztspark.entity.company.CompanyFile;
import com.ztman.ztspark.mapper.CompanyFileMapper;
import com.ztman.ztspark.service.CompanyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("companyFileService")
public class CompanyFileServiceImpl extends ServiceImpl<CompanyFileMapper, CompanyFile> implements CompanyFileService {

    @Autowired
    private CompanyFileMapper companyFileMapper;

    /**
     * 查询文档管理表信息
     *
     * @param id 文档管理表ID
     * @return 文档管理表信息
     */
    @Override
    public CompanyFile selectCompanyFileById(Integer id)
    {
        CompanyFile companyFile = companyFileMapper.selectCompanyFileById(id);
        List list = companyFileMapper.selectCompanyFileDataById(id);
        companyFile.setImgs(list);
        return companyFile;
    }

    /**
     * 查询文档管理表列表
     *
     * @param companyFile 文档管理表信息
     * @return 文档管理表集合
     */
    @Override
    public List<CompanyFile> selectCompanyFileList(CompanyFile companyFile)
    {
        return companyFileMapper.selectCompanyFileList(companyFile);
    }


    /**
     * 分页模糊查询文档管理表列表
     * @return 文档管理表集合
     */
    @Override
    public Page selectCompanyFileListByLike(Query query)
    {
        CompanyFile companyFile =  BeanUtil.mapToBean(query.getCondition(), CompanyFile.class,false);
        query.setRecords(companyFileMapper.selectCompanyFileListByLike(query,companyFile));
        return query;
    }

    /**
     * 新增文档管理表
     *
     * @param companyFile 文档管理表信息
     * @return 结果
     */
    @Override
    public int insertCompanyFile(CompanyFile companyFile)
    {
        companyFile.setCreateTime(new Date());
        companyFile.setDelFlag("1");

        CompanyFile cf = null;
        do {
            companyFile.setCode(RandomCodeUtils.getCode(6));
            cf = companyFileMapper.selectCompanyFileByCode(companyFile.getCode());
        } while (cf != null);

        return companyFileMapper.insertCompanyFile(companyFile);
    }

    /**
     * 修改文档管理表
     *
     * @param companyFile 文档管理表信息
     * @return 结果
     */
    @Override
    public int updateCompanyFile(CompanyFile companyFile)
    {
        companyFile.setUpdateTime(new Date());
        return companyFileMapper.updateCompanyFile(companyFile);
    }


    /**
     * 删除文档管理表
     *
     * @param id 文档管理表ID
     * @return 结果
     */
    public int deleteCompanyFileById(Integer id)
    {
        return companyFileMapper.deleteCompanyFileById( id);
    };


    /**
     * 批量删除文档管理表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCompanyFileByIds(String[] ids)
    {
        return companyFileMapper.deleteCompanyFileByIds( ids);
    }

    @Override
    public int insertCompanyFileData(CompanyFile companyFile) {
        List imgs = companyFile.getImgs();

        if (imgs.size() <= 0) {
            return 0;
        }

        companyFileMapper.deleteCompanyFileDataById(companyFile.getId());

        return companyFileMapper.insertCompanyFileData(imgs, companyFile.getId());
    }

}
