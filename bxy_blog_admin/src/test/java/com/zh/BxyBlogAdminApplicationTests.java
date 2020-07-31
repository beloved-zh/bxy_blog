package com.zh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.zh.mapper.MenuMapper;
import com.zh.pojo.*;
import com.zh.service.*;
import com.zh.utils.BaseUtil;
import com.zh.utils.FastJsonUtil;
import com.zh.utils.QiNiuUtil;
import com.zh.utils.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.util.*;

@SuppressWarnings("ALL")
@SpringBootTest
class BxyBlogAdminApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRoleService menuRoleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RequestApiService requestApiService;

    @Autowired
    private RequestApiRoleService requestApiRoleService;

    /**
     * 添加用户
     */
    @Test
    void addUser(){
        User user = new User();
        user.setUserName("test");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEmail("1425279634@qq.com");
        user.setMobile("17609208151");
        user.setSource("手动创建测试用户");
        user.setAvatar("http://image.beloved.ink/Typora/23bdeddbf64660372b1664d6fda0c93.jpg");
        user.setLoginCount(0);
        user.setIsLock(false);
        user.setCommentStatus(false);
        user.setLastLoginTime(new Date());
        user.setLastLoginIp("127.0.0.1");

        userService.save(user);
    }

    /**
     * 添加角色
     */
    @Test
    void addRole(){
        Role role = new Role();
        role.setRoleName("ROLE_ADMIN");
        role.setSummary("超级管理员");

        roleService.save(role);
    }

    /**
     * 添加用户角色
     */
    @Test
    void addUserRole(){
        UserRole userRole = new UserRole();
        userRole.setUserId("e6efc4476e5f8bed65a6e76110746c21");
        userRole.setRoleId("f1a65fed20c34d54a61bfc79dfbd76c3");

        userRoleService.save(userRole);
    }

    /**
     * 添加菜单
     */
    @Test
    void addMenu(){
        Menu menu = new Menu();
        menu.setMenuTitle("路由管理");
        menu.setMenuIcon("el-icon-tickets");
        menu.setMenuUrl("/categoryMenu");
        menu.setComponent("/authority/categoryMenu");
        menu.setMenuId("5101bf615cfe449da6daa259b59ce24d");
        menu.setRedirect(null);
        menu.setSort(0);
        menu.setIsShow(false);
        menu.setMenuType(false);

        menuService.save(menu);
    }

    /**
     * 添加菜单角色
     */
    @Test
    void addMenuRole(){
        MenuRole menuRole = new MenuRole();
        menuRole.setRoleId("aa7261e290294089af6de21ff12e3af3");
        menuRole.setMenuId("2bd498c03d394d878d650cce09e864fb");

        menuRoleService.save(menuRole);
    }

    /**
     * 添加请求地址
     */
    @Test
    void addRequestApi(){
        RequestApi api = new RequestApi();
        api.setUrl("/addApi");
        api.setMethod("POST");
        api.setSummary("后台系统添加接口");
        api.setApiType(false);
        api.setRequestId("7e89f3e9ed2251cf5fdbfdab43acef55");

        requestApiService.save(api);

        RequestApiRole role = new RequestApiRole();
        role.setRApiId(api.getId());
        role.setRoleId("7d849898e5814f1bb2dad6f49961f888");

        requestApiRoleService.save(role);
    }

    /**
     * 添加请求角色
     */
    @Test
    void addRequestApiRole(){
        RequestApiRole role = new RequestApiRole();
        role.setRApiId("fbbf1f22ff9aadc551c3287cece91652");
        role.setRoleId("7d849898e5814f1bb2dad6f49961f888");

        requestApiRoleService.save(role);
    }

    @Autowired
    MenuMapper menuMapper;

    @Test
    void select(){

        IPage<User> user = userService.getUser(null,
                null,
                null,
                null,
                null,
                1,
                5);

        System.out.println(user);

    }

//    @Autowired
//    private BucketManager bucketManager;
//
//    @Autowired
//    private UploadManager uploadManager;
//
//    @Autowired
//    private QiNiu properties;
//
//    @Autowired
//    private Auth auth;
//
//    @Test
//    void test() throws QiniuException {
//        File file = new File("E:\\bxy\\bxy_blog_admin\\src\\main\\resources\\logo.png");
//
//        String token = auth.uploadToken(properties.getBucket());
//
//        Response response = uploadManager.put(file, "aaa", token);
//        DefaultPutRet putRet = FastJsonUtil.json2Bean(response.bodyString(), DefaultPutRet.class);
//        String return_path = properties.getPrefix() + "/" + putRet.key;
//        System.out.println(return_path);
//    }
//
//    @Test
//    void delete() throws QiniuException {
//        Response response = bucketManager.delete(properties.getBucket(), "logo");
//        System.out.println(response);
//    }
//
//    @Test
//    void refresh() throws QiniuException {
//        String [] urls = {"http://qd4zqd4qv.bkt.clouddn.com/logo"};
//        CdnManager c = new CdnManager(auth);
//        CdnResult.RefreshResult response = c.refreshUrls(urls);
//    }
//
//    @Test
//    void list(){
//        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(properties.getBucket(), null,1,null);
//        while (fileListIterator.hasNext()) {
//            //处理获取的file list结果
//            FileInfo[] items = fileListIterator.next();
//            for (FileInfo item : items) {
//                System.out.println(item.key);
//                System.out.println(item.putTime);
//            }
//        }
//    }

    @Autowired
    private SortService sortService;

    @Autowired
    private TagsService tagsService;

    @Test
    void test() throws QiniuException {
        String [] urls = {"http://qd4zqd4qv.bkt.clouddn.com/1c2b371a55b34051be0e522da3cdfb69"};
        CdnResult.RefreshResult refresh = QiNiuUtil.refresh(urls);
        System.out.println(refresh.code);
        System.out.println(refresh.error);
        System.out.println(refresh.requestId);
        System.out.println(refresh.invalidUrls);
        System.out.println(refresh.invalidDirs);
        System.out.println(refresh.urlQuotaDay);
        System.out.println(refresh.urlSurplusDay);
        System.out.println(refresh.dirQuotaDay);
        System.out.println(refresh.dirSurplusDay);
    }

    @Autowired
    private BlogService blogService;

    @Test
    void sort(){
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("blog_level");
        wrapper.groupBy("blog_level");
        List<Blog> list = blogService.list(wrapper);
        System.out.println(list);
    }


}
