package pers.fairy.miusa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import pers.fairy.miusa.common.HostHolder;
import pers.fairy.miusa.service.MiusaGoodsService;
import pers.fairy.miusa.service.RedisService;
import pers.fairy.miusa.vo.GoodsVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 20:32
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private MiusaGoodsService miusaGoodsService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    private RedisService redisService;

    @GetMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String getGoodsList(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<GoodsVO> goodsList = miusaGoodsService.listMiusaGoods();
        model.addAttribute("user", hostHolder.getUser());
        model.addAttribute("goodsList", goodsList);
        // 不使用页面缓存
        // return "goods_list";
        // 页面缓存
        String html;
        // 从缓存中获取，如果缓存命中则返回。
        if ((html = redisService.getHtml("goods_list")) != null) {
            return html;
        }
        // 如果未命中，则使用 ThymeleafViewResolver 进行渲染，得到渲染后的页面。
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        // 渲染成功则将页面缓存并返回。
        if (html != null)
            redisService.setHtml("goods_list", html, 60);
        return html;
    }
}
