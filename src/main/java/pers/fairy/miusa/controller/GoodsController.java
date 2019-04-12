package pers.fairy.miusa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import pers.fairy.miusa.common.HostHolder;
import pers.fairy.miusa.common.Result;
import pers.fairy.miusa.entity.Goods;
import pers.fairy.miusa.service.MiusaGoodsService;
import pers.fairy.miusa.service.RedisService;
import pers.fairy.miusa.vo.GoodsDetailVO;
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

    /**
     *  获取商品列表，“/goods/to_list”
     */
    @GetMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String getGoodsList(Model model, HttpServletRequest request, HttpServletResponse response) {
        // 页面缓存
        String html;
        // 从缓存中获取，如果缓存命中则返回。
        if ((html = redisService.getHtml("goods_list")) != null) {
            return html;
        }
        List<GoodsVO> goodsList = miusaGoodsService.listMiusaGoods();
        model.addAttribute("goodsList", goodsList);
        // 如果未命中，则使用 ThymeleafViewResolver 进行渲染，得到渲染后的页面。
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        // 渲染成功则将页面缓存并返回。
        if (html != null)
            redisService.setHtml("goods_list", html, 60);
        return html;
    }

    /**
     *  根据商品id获取商品详情
     *
     * @param goodsId 商品id
     * @return 商品详情
     */
    @GetMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result getGoodsDetail(@PathVariable Long goodsId) {
        GoodsDetailVO goodsDetailVO= miusaGoodsService.getGoodsDetailVOByGoodsId(goodsId);
        return Result.SUCCESS(goodsDetailVO);
    }
}
