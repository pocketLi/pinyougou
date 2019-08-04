package cn.itcast.es;

import cn.itcast.es.dao.ItemDao;
import com.pinyougou.pojo.TbItem;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-es.xml")
public class ElasticSearchTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Autowired
    private ItemDao itemDao;

    //创建索引库和映射
    @Test
    public void createIndexAndMapping(){
        //创建索引库
        esTemplate.createIndex(TbItem.class);
        //创建映射
        esTemplate.putMapping(TbItem.class);
    }

    //新增
    @Test
    public void save(){
        TbItem item = new TbItem();
        item.setId(100003344497L);
        item.setTitle("一加 OnePlus 7 Pro 2K+90Hz 流体屏 骁龙855旗舰 4800万超广角三摄 8GB+256GB 曜岩灰 全面屏拍照游戏手机");
        item.setPrice(4499.0);
        item.setGoodsId(1L);
        item.setImage("https://img12.360buyimg.com/n1/s450x450_jfs/t29932/142/1355094658/179246/af2020dd/5cdd0ce1Nf84c0c4d.jpg");
        item.setSeller("一加");
        item.setBrand("OnePlus");
        item.setCategory("手机");
        item.setUpdateTime(new Date());


        Map<String, String> specMap = new HashMap<>();
        specMap.put("机身内存", "64G");
        specMap.put("屏幕尺寸", "5.5");
        item.setSpecMap(specMap);

        itemDao.save(item);
    }

    //查询全部
    @Test
    public void findAll(){
        Iterable<TbItem> items = itemDao.findAll();
        for (TbItem item : items) {
            System.out.println(item);
        }
    }

    //分页查询
    @Test
    public void findAllByPage(){
        /**
         * 参数1：页号（从0开始）
         * 参数2：页大小
         */
        PageRequest pageRequest = PageRequest.of(0, 2);

        Iterable<TbItem> items = itemDao.findAll(pageRequest);
        for (TbItem item : items) {
            System.out.println(item);
        }
    }

    //删除
    @Test
    public void delete(){
        itemDao.deleteById(100003344497L);
    }

    //通配符搜索，不分词
    @Test
    public void wildCardQuery(){

        //创建查询创建对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withQuery(QueryBuilders.wildcardQuery("title", "手机"));

        //创建查询对象
        NativeSearchQuery query = queryBuilder.build();
        //查询
        AggregatedPage<TbItem> items = esTemplate.queryForPage(query, TbItem.class);
        System.out.println("总记录数为：" + items.getTotalElements());
        System.out.println("总页数为：" + items.getTotalPages());
        for (TbItem item : items) {
            System.out.println(item);
        }
    }

    //分词搜索
    @Test
    public void matchQuery(){

        //创建查询创建对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "一加"));

        //创建查询对象
        NativeSearchQuery query = queryBuilder.build();
        //查询
        AggregatedPage<TbItem> items = esTemplate.queryForPage(query, TbItem.class);
        System.out.println("总记录数为：" + items.getTotalElements());
        System.out.println("总页数为：" + items.getTotalPages());
        for (TbItem item : items) {
            System.out.println(item);
        }
    }

    //分词搜索-复制域
    @Test
    public void copyToQuery(){

        //创建查询创建对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withQuery(QueryBuilders.matchQuery("keywords", "手机"));

        //创建查询对象
        NativeSearchQuery query = queryBuilder.build();
        //查询
        AggregatedPage<TbItem> items = esTemplate.queryForPage(query, TbItem.class);
        System.out.println("总记录数为：" + items.getTotalElements());
        System.out.println("总页数为：" + items.getTotalPages());
        for (TbItem item : items) {
            System.out.println(item);
        }
    }

    //分词搜索-嵌套域
    @Test
    public void nestedQuery(){

        //创建查询创建对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //组合查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        //基本查询条件
        boolQuery.must(QueryBuilders.matchQuery("keywords", "手机"));

        //进行规格过滤查询
        /**
         * 参数1：实体类中对应的域名称
         * 参数2：查询对象（完整域名称，查询值）
         * 参数3：得分模式：当符合这些查询条件的数据有多条，每一条文档都有一个得分；本次查询的得分以什么为主
         */
        NestedQueryBuilder queryBuilder1 =
                new NestedQueryBuilder("specMap", QueryBuilders.wildcardQuery("specMap.机身内存.keyword", "64G"), ScoreMode.Max);
        boolQuery.filter(queryBuilder1);

        NestedQueryBuilder queryBuilder2 =
                new NestedQueryBuilder("specMap", QueryBuilders.wildcardQuery("specMap.屏幕尺寸.keyword", "5.5"), ScoreMode.Max);
        boolQuery.filter(queryBuilder2);

        queryBuilder.withQuery(boolQuery);

        //创建查询对象
        NativeSearchQuery query = queryBuilder.build();
        //查询
        AggregatedPage<TbItem> items = esTemplate.queryForPage(query, TbItem.class);
        System.out.println("总记录数为：" + items.getTotalElements());
        System.out.println("总页数为：" + items.getTotalPages());
        for (TbItem item : items) {
            System.out.println(item);
        }
    }

}
