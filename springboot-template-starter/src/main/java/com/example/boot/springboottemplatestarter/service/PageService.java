package com.example.boot.springboottemplatestarter.service;

import com.example.boot.springboottemplatedomain.page.payload.CreatePagePLO;
import com.example.boot.springboottemplatedomain.page.payload.FindAllPagePLOAbstract;
import com.example.boot.springboottemplatedomain.page.payload.ModifyPagePLO;
import com.example.boot.springboottemplatedomain.page.persistent.PagePermissionRef;
import com.example.boot.springboottemplatedomain.page.persistent.SystemPage;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * write this class description...
 *
 * @author Chang
 * @date 2019/7/28 15:04
 */
public interface PageService {

    Page<SystemPage> findAllPage(FindAllPagePLOAbstract plo);

    SystemPage getPageById(Long pageId);

    List<PagePermissionRef> getPagePermissionListById(Long pageId);

    void createPage(CreatePagePLO plo);

    void modifyPage(Long pageId, ModifyPagePLO plo);

    void deletePage(Long pageId);

    List<PagePermissionRef> getPagePermissionListByIds(List<Long> refIds);
}
