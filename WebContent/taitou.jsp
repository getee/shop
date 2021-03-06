<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %><!-- EL的函数库=jstl的函数 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--抬头开始-->
<c:if test="${cookie['username'].value != null}">
<c:if test="${sessionScope.User ==null }"> <!-- 有cokies且没登录 -->
    <c:redirect url="UserServlet?method=index"></c:redirect>
</c:if>
</c:if>
<div class="top">

<div class="top1">
<div class="top1_main">

	<c:if test="${sessionScope.User eq null}">
        <span class="dl">您好，欢迎光临本亲生活网！<a href="denglu.jsp"> [请登录]</a>  <a href="zhuce.jsp">[免费注册]</a></span>
	</c:if>
	<c:if test="${sessionScope.User != null}">
        <span class="dl">您好，欢迎<a href="hyzx.jsp"> <c:out value="${sessionScope.User.name}"></c:out></a>光临本亲生活网！<a href="UserServlet?method=signout">[退出登录]</a></span>
	</c:if>

<span class="yh_zx"><a href="hyzx.jsp">用户中心</a>  |  <a href="ddtj.jsp">购物车</a>  |  <a href="#">我的订单</a>  |  <a href="#">帮助中心</a></span>
</div>
</div>


<div class="top_logo">

<div class="logo"><a href="#"><img src="images/logo.jpg" width="338" height="113" / alt="本亲生活的logo标志"></a></div>
<div class="top_you">

	<div class="ss_1">
	
	<!-- 传递的参数(name,search) -->
	<form action="WineServlet" method="get"/>
	<input type="hidden"   name="method"  value="search"/>
	<input type="hidden"   name="page"  value="1"/>
	<input type="hidden"   name="count"  value="9"/>
	<input name="searchKey" type="text" id="key" value="请输入您要搜索的产品" size="30" 
	          onclick="if(value==defaultValue){value='';this.style.color='#898b8c'}"    
	          onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#999; line-height:26px;" / class="ssk1">
	</input>
	          <input  value="搜 索" type="submit"  class="button1">
	          </form>
	</div>

</div>

</div>

</div>

<!--抬头结束-->
<!--导航开始-->


<div class="headNav">
  <div class="navCon w1020">
    <div class="navCon-cate fl navCon_on">
      <div class="navCon-cate-title"> <a href="#">全部商品分类</a></div>
      <div class="cateMenu hide">
        <ul>
          <li style="border-top: none;">
            <div class="cate-tag"> <strong><a href="#">白酒</a></strong>
              <div class="listModel">
                <p> <a href="#">茅台</a> <a href="#">洋河</a> <a href="#">郎酒</a> <a href="#">古井贡酒</a> </p>
                <p> <a href="#">习酒</a> <a href="#">珍酒</a> <a href="#">红星</a> <a href="#">泸州老窖</a> </p>
                <p> <a href="#">沱牌</a> <a href="#">国台</a> <a href="#">五粮液</a> <a href="#">剑南春</a> </p>
              </div>
            </div>
            <div class="list-item hide">
              <ul class="itemleft">
                <dl>
                  <dt>品牌</dt>
                  <dd> <a href="#">茅台</a> <a href="#">五粮液</a> <a href="#">剑南春</a> <a href="#">水井坊</a> <a href="#">汾酒</a> <a href="#">洋河</a> <a href="#">泸州老窖</a> <a href="#">珍酒</a> <a href="#">郎酒</a> <a href="#">古井贡酒</a> <a href="#">西凤酒</a> <a href="#">董酒</a> <a href="#">酒鬼酒</a> <a href="#">红星</a> <a href="#">文君酒</a> <a href="#">牛栏山</a> <a href="#">四特酒</a> <a href="#">口子酒</a><a href="#">星河湾</a> <a href="#">百年传奇</a> </dd>
                </dl>
                <div class="fn-clear"></div>
                <dl>
                  <dt>香型</dt>
                  <dd> <a href="#">酱香型</a> <a href="#">浓香型</a> <a href="#">清香型</a> <a href="#">凤香型</a> <a href="#">馥郁香</a> <a href="#">董香型</a> <a href="#">特香型</a> <a href="#">芝麻香</a> <a href="#">兼香型</a> <a href="#">金门香型</a> <a href="#">老干白</a> <a href="#">绵柔型</a> <a href="#">柔和型</a> <a href="#">小曲型</a> <a href="#">生态竹香型</a> </dd>
                </dl>
                <div class="fn-clear"></div>
              </ul>
              <ul class="itemright">
                <dl>
                  <dt>促销信息</dt>
                </dl>
                <div class="news-list">
                  <p> <span class="red">[杜康]</span> <a href="#">酒体窖香幽雅、陈香舒适,只需156元，值得一试的好酒</a> </p>
                  <p> <span class="red">[红星]</span> <a href="#">经典红星153元热销千瓶，敢于全网誓比价！</a> </p>
                  <p> <span class="red">[太白]</span> <a href="#">中国第一诗酒，全场满200减50</a> </p>
                </div>
                <dl>
                  <dt>促销活动</dt>
                </dl>
                <div class="ad-list mt10"> <a href="#"><img src='images/c11.jpg' width='210' height='100' /></a> <a href="#"><img src='images/c12.jpg' width='210' height='100' /></a> <a href="#"><img src='images/c13.jpg' width='210' height='100' /></a> </div>
              </ul>
            </div>
          </li>
          <li >
            <div class="cate-tag"> <strong><a href="#">葡萄酒</a></strong>
              <div class="listModel">
                <p> <a href="#">中国</a> <a href="#">法国</a> <a href="#">智利</a> <a href="#">葡萄牙</a> </p>
                <p> <a href="#">意大利</a> <a href="#">澳大利亚</a> <a href="#">德国</a> <a href="#">南非</a> </p>
                <p> <a href="#">美国</a> <a href="#">阿根廷</a> <a href="#">西班牙</a> </p>
              </div>
            </div>
            <div class="list-item hide">
              <ul class="itemleft">
                <dl>
                  <dt>产区</dt>
                  <dd> <a href="#">澳大利亚</a> <a href="#">德国</a> <a href="#">法国</a> <a href="#">加拿大</a> <a href="#">美国</a> <a href="#">西班牙</a> <a href="#">智利</a> <a href="#">葡萄牙</a> <a href="#">俄罗斯</a> <a href="#">阿根廷</a> <a href="#">新西兰</a> <a href="#">南非</a> <a href="#">意大利</a> <a href="#">中国</a> <a href="#">希腊</a> </dd>
                </dl>
                <div class="fn-clear"></div>
                <dl>
                  <dt>类型</dt>
                  <dd> <a href="#">红葡萄酒</a> <a href="#">白葡萄酒</a> <a href="#">桃红葡萄酒</a> <a href="#">起泡酒/香槟</a> <a href="#">冰酒/贵腐/甜酒</a> </dd>
                </dl>
                <div class="fn-clear"></div>
                <dl>
                  <dt>品种</dt>
                  <dd> <a href="#">芭贝拉</a> <a href="#">长相思</a> <a href="#">解百纳</a> <a href="#">玛尔维萨</a> <a href="#">综合佳酿</a> <a href="#">美乐</a> </dd>
                </dl>
                <div class="fn-clear"></div>
              </ul>
              <ul class="itemright">
                <dl>
                  <dt>促销信息</dt>
                </dl>
                <div class="news-list">
                  <p> <span class="red">[加州乐事]</span> <a href="#">美国爆款红酒，超市89元，68元特价售</a> </p>
                  <p> <span class="red">[华尔兹]</span> <a href="#">德国经典款，39元继续热卖中</a> </p>
                  <p> <span class="red">[贵妇]</span> <a href="#">法国进口红酒，购满6瓶每瓶仅需38元，超值专享价</a> </p>
                </div>
                <dl>
                  <dt>促销活动</dt>
                </dl>
                <div class="ad-list mt10"> <a href="#"><img src='images/c21.jpg' width='210' height='100' /></a> <a href="#"><img src='images/c22.jpg' width='210' height='100' /></a> </div>
              </ul>
            </div>
          </li>
          <li >
            <div class="cate-tag"> <strong><a href="#">洋酒</a></strong>
              <div class="listModel">
                <p> <a href="#">格兰威特</a> <a href="#">杰克丹尼</a> <a href="#">轩尼诗</a> </p>
                <p> <a href="#">尊尼获加</a> <a href="#">皇家路易</a> <a href="#">百龄坛</a> </p>
              </div>
            </div>
            <div class="list-item hide">
              <ul class="itemleft">
                <dl>
                  <dt>品牌</dt>
                  <dd><a href="#">第林可</a> <a href="#">本尼维斯</a> <a href="#">奇滋</a> <a href="#">奥凯</a> <a href="#">剑威</a> <a href="#">帕夫利季斯</a> <a href="#">佩特雷城堡</a> <a href="#">斯多克</a> <a href="#">爱德华</a> <a href="#">格兰御鹿</a> <a href="#">萨隆公爵</a> </dd>
                </dl>
                <div class="fn-clear"></div>
                <dl>
                  <dt>酒种</dt>
                  <dd> <a href="#">白兰地</a> <a href="#">伏特加</a> <a href="#">朗姆酒</a> <a href="#">力娇酒</a> <a href="#">墨西哥烈酒</a> <a href="#">甜酒</a> <a href="#">威士忌</a> <a href="#">金酒</a> <a href="#">利口酒</a> </dd>
                </dl>
                <div class="fn-clear"></div>
              </ul>
              <ul class="itemright">
                <dl>
                  <dt>促销信息</dt>
                </dl>
                <div class="news-list">
                  <p> <span class="red">[威士忌]</span> <a href="#">芝华士，月销万瓶！</a> </p>
                </div>
                <dl>
                  <dt>促销活动</dt>
                </dl>
                <div class="ad-list mt10"> <a href="#"><img src='images/c31.jpg' width='210' height='100' /></a> <a href="#"><img src='images/c32.jpg' width='210' height='100' /></a> <a href="#"><img src='images/c33.jpg' width='210' height='100' /></a> </div>
              </ul>
            </div>
          </li>
          <li >
            <div class="cate-tag"> <strong><a href="#">黄酒</a></strong>
              <div class="listModel">
                <p> <a href="#">石库门</a> <a href="#">女儿红</a> <a href="#">古越龙山</a> </p>
              </div>
            </div>
            <div class="list-item hide">
              <ul class="itemleft">
                <dl>
                  <dt>品牌</dt>
                  <dd> <a href="#">石库门</a> <a href="#">和酒</a> <a href="#">古越龙山</a> <a href="#">女儿红</a> <a href="#">沙洲优黄</a> <a href="#">会稽山</a> <a href="#">塔牌</a> <a href="#">乌毡帽</a> <a href="#">唐宋黄酒</a> <a href="#">钱塘人家</a> <a href="#">咸亨黄酒</a> <a href="#">嘉善黄酒</a> <a href="#">绍兴师爷</a> <a href="#">梁祝</a> <a href="#">组合装</a> </dd>
                </dl>
                <div class="fn-clear"></div>
                <dl>
                  <dt>年份</dt>
                  <dd> <a href="#">100年</a> <a href="#">10年</a> <a href="#">12年</a> <a href="#">15年</a> <a href="#">18年</a> <a href="#">20年</a> <a href="#">25年</a> <a href="#">30年</a> <a href="#">3年</a> <a href="#">5年</a> <a href="#">6年</a> <a href="#">8年</a> <a href="#">9年</a> <a href="#">常规年份</a> </dd>
                </dl>
                <div class="fn-clear"></div>
              </ul>
              <ul class="itemright">
                <dl>
                  <dt>促销活动</dt>
                </dl>
                <div class="ad-list mt10"> <a href="#"><img src='images/c41.jpg' width='210' height='100' /></a> <a href="#"><img src='images/c42.jpg' width='210' height='100' /></a> </div>
              </ul>
            </div>
          </li>
          <li >
            <div class="cate-tag"> <strong><a href="#">保健酒与啤酒</a></strong>
              <div class="listModel">
                <p> <a href="#">百威</a> <a href="#">青岛</a> <a href="#">喜力</a> <a href="#">劲酒</a> </p>
              </div>
            </div>
            <div class="list-item hide">
              <ul class="itemleft">
                <dl>
                  <dt>品牌</dt>
                  <dd> <a href="#">百威</a> <a href="#">喜力</a> <a href="#">青岛</a> <a href="#">劲酒</a> <a href="#">黄金酒</a> <a href="#">蓝莓酒</a> <a href="#">银杏</a> <a href="#">春生堂</a> <a href="#">张裕酒</a> <a href="#">丹力</a> <a href="#">桂花酒</a> <a href="#">森林大猫</a> <a href="#">卡力特</a> <a href="#">麒麟</a> <a href="#">凯撒</a> <a href="#">修士</a> <a href="#">黑森马蹄</a> <a href="#">威兰仕</a> <a href="#">科罗娜</a> <a href="#">蓝带</a> <a href="#">布格福斯</a> <a href="#">博瑞克</a> <a href="#">力兹堡</a> <a href="#">嘉士伯</a> <a href="#">美名格</a> <a href="#">阿登堡</a> <a href="#">博德</a> <a href="#">德福堡</a> <a href="#">德力士</a> <a href="#">朗克</a> <a href="#">麦德奥黑</a> <a href="#">威欧</a> <a href="#">威斯玛</a> <a href="#">朝日</a> <a href="#">彼乐</a> <a href="#">白鹳</a> <a href="#">德博</a> <a href="#">庄园主</a> <a href="#">德拉克</a> <a href="#">西藏青稞</a> </dd>
                </dl>
                <div class="fn-clear"></div>
              </ul>
              <ul class="itemright">
                <dl>
                  <dt>促销活动</dt>
                </dl>
                <div class="ad-list mt10"> <a href="#"><img src='images/c51.jpg' width='210' height='100' /></a> </div>
              </ul>
            </div>
          </li>
          <li>
            <div class="float-list-dnav"> </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="navCon-menu fl">
      <ul>
        <li><a class="curMenu" href="index.jsp">商城首页</a></li>
        <li><a href="tplist.jsp">特色产品</a></li>
        <li><a href="tplist.jsp">VIP专区</a></li>
        <li><a href="#">服务支持</a></li>
        <li><a href="newslist.jsp">相关资讯</a></li>
        <li><a href="about.jsp">关于本亲</a></li>
      </ul>
    </div>
  </div>
</div>

<!--导航结束-->