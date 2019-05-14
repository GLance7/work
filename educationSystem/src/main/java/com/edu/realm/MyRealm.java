package com.edu.realm;

import com.edu.entity.Tmenu;
import com.edu.entity.Trole;
import com.edu.entity.Tuser;
import com.edu.mapper.TmenuMapper;
import com.edu.mapper.TroleMapper;
import com.edu.mapper.TuserMapper;
import com.edu.mapper.TuserroleMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义的Realm
 * @author wl
 *
 */
public class MyRealm extends AuthorizingRealm{

    @Resource
    private TuserMapper tuserMapper;

    @Resource
    private TroleMapper troleMapper;

    @Resource
    private TuserroleMapper tuserroleMapper;

    @Resource
    private TmenuMapper tmenuMapper;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName=(String) SecurityUtils.getSubject().getPrincipal();


        //根据用户名查询出用户记录
        Example tuserExample=new Example(Tuser.class);
        tuserExample.or().andEqualTo("userName",userName);
        Tuser user=tuserMapper.selectByExample(tuserExample).get(0);


        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();


        List<Trole> roleList = troleMapper.selectRolesByUserId(user.getId());

        Set<String> roles=new HashSet<String>();
        if(roleList.size()>0){
            for(Trole role:roleList){
                roles.add(role.getName());

                System.out.println("哈哈哈  "+role);
                //根据角色id查询所有资源
                List<Tmenu> menuList=tmenuMapper.selectMenusByRoleId(role.getId());
                for(Tmenu menu:menuList){
                    info.addStringPermission(menu.getName()); // 添加权限
                    System.out.println("授权测试输出  "+menu);
                }
            }
        }
        info.setRoles(roles);
        return info;
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName=(String)token.getPrincipal();

        Example tuserExample=new Example(Tuser.class);

        System.out.println("uuuuu   "+tuserExample);
        System.out.println("uuuuu   "+userName+"  "+tuserExample.getSelectColumns());
        tuserExample.or().andEqualTo("userName",userName);

        Tuser user=tuserMapper.selectByExample(tuserExample).get(0);

        if(user!=null){
            AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"xxx");
            System.out.println("权限认证中测试输出 "+user.getUserName());
            return authcInfo;
        }else{
            return null;
        }
    }

}
