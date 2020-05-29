package com.sun.crowd.mvc.handler;

import com.sun.crowd.entity.Menu;
import com.sun.crowd.service.api.MenuService;
import com.sun.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class MenuHandler {

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTreeNew() {
        // 1. 查询全部Menu对象
        List<Menu> menuList = menuService.getAll();
        // 2. 声明一个变量来存储找到的节点
        Menu root = null;

        //3. 创建Map对象用来存储id和Menu对象对应关系便于查询父节点
        Map<Integer, Menu> menuMap = new HashMap<>();

        // 4. 遍历menulist ，将将id存入map中
        menuList.forEach(menu -> menuMap.put(menu.getId(), menu));

        // 5. 遍历mentlist，将带有父节点的menu绑定到父节点上
        for (Menu menu : menuList) {
            // 6. 获取当前menu对象的pid属性
            Integer pid = menu.getPid();

            // 7. 如果pid 为null ，判定为根节点
            if (pid == null) {
                root = menu;
                continue;
            }
            // 8. 如果不为null，则添加到父节点
            menuMap.get(pid).getChildren().add(menu);
        }
        // 9. 经过运算，将根节点返回
        return ResultEntity.successWithData(root);
    }

    public ResultEntity<Menu> getWholeTreeOld() {

        // 1. 查询全部Menu对象
        List<Menu> menuList = menuService.getAll();
        // 2. 声明一个变量来存储找到的节点
        Menu root = null;

        // 3. 遍历MenuList
        for (Menu menu : menuList) {
            // 4. 获取当前对象的pit
            Integer pid = menu.getPid();
            // 5. 如果pid 为null，则把menu对象赋值给父节点
            if (pid == null) {
                // 6. 把当前遍历的menu对象赋值给root
                root = menu;
                // 7. 停止本次循环，继续下一次
                continue;
            }
            // 8. 如果pid不为null 则找到父节点，将本节点作为子节点进行组装
            for (Menu confirmMenu : menuList) {
                // 9. 获取父id的值
                Integer id = confirmMenu.getId();
                // 10. 如果pid 和 查找的menu的id相同，则为父节点
                if (Objects.equals(id, pid)) {
                    // 11. 将子节点存入父节点
                    confirmMenu.getChildren().add(menu);
                    // 12. 退出循环
                    break;
                }
            }
        }
        // 13. 将组装好的树形结构返回给根节点
        return ResultEntity.successWithData(root);
    }
}
