package com.sun.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.crowd.constant.CrowdConstant;
import com.sun.crowd.entity.Admin;
import com.sun.crowd.entity.AdminExample;
import com.sun.crowd.exception.LoginFailedException;
import com.sun.crowd.mapper.AdminMapper;
import com.sun.crowd.mvc.config.LoginAcctAlreadyInUseException;
import com.sun.crowd.mvc.config.LoginAcctAlreadyInUseForUpdateException;
import com.sun.crowd.service.api.AdminService;
import com.sun.crowd.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author sun
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 根据id获取admin信息
     *
     * @param id adminId
     * @return admin信息
     */
    @Override
    public Admin getAdminById(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    /**
     * 更新admin信息
     *
     * @param admin 需要更新的admin信息
     */
    @Override
    public void updateAdmin(Admin admin) {

        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof DuplicateKeyException){
                logger.warn("异常类类名="+e.getClass().getName());
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    private Logger logger= LoggerFactory.getLogger(AdminServiceImpl.class);
    /**
     * 删除admin数据
     * @param adminId admin的id
     */
     @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    /**
     * 根据用户名和密码获取登录名
     * @param loginAcct 登录账号
     * @param userPswd  登录密码
     * @return 登录用户信息
     */
    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1. 根据登录账号查询Admin对象
        // 1.1 创建AdminExample对象
        AdminExample adminExample = new AdminExample();
        // 1.2 创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // 1.3 在Criteria中封装查询条件
        criteria.andLogAcctEqualTo(loginAcct);
        // 1.4 调用AdminMapper的方法执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);

        // 2. 判断Admin对象是否为null
        if (list == null || list.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if (list.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = list.get(0);

        // 3. 如果admin对象为null抛出异常
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4. 如果admin对象不为null则将数据库密码从Admin对象中取出
        String adminpswddb = admin.getUserPswd();

        // 5. 将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.md5(userPswd);

        // 6. 对密码进行比较
        if (!Objects.equals(adminpswddb, userPswdForm)) {
            // 7. 如果比较结果不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 8. 如果一致则返回Admin对象
        return admin;
    }

    /**
     * 根据关键词进行分页查询
     * @param keyword 关键词
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return PageInfo 信息
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1. 调用PageHelper 的静态方法开启分页功能
        // PageHelper的非侵入式设计：原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum, pageSize);

        // 2. 执行查询
        List<Admin> list = adminMapper.selectAdminByKeyWord(keyword);

        // 3. 封装到PageInfo对象中
        return new PageInfo<>(list);
    }

    @Override
    public void saveAdmin(Admin admin) {
        // 1. 密码加密
        String userPswd = admin.getUserPswd();
        userPswd = CrowdUtil.md5(userPswd);
        admin.setUserPswd(userPswd);

        // 2. 生成创建时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);
        // 3. 执行保存

        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("异常类类名="+e.getClass().getName());
            if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAll() {

        return adminMapper.selectByExample(new AdminExample());
    }

    /**
     * 保存admin对象所包含的角色
     *
     * @param adminId
     * @param roleIdList
     */
    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {

       // 1. 根据adminId删除旧的关联关系数据
        adminMapper.deletRelationship(adminId);

        // 2. 根据roleIdlist保存新的关联关系
        if (roleIdList != null && roleIdList.size() > 0) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }
}
