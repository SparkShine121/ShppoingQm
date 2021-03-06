package com.controller;

import com.chatpojo.Message;
import com.controller.testentity.GeoTest;
import com.controller.testentity.UserTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.WeChatService;
import com.utils.CodeUtil;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * es???????????????????????????????????????
 **/
@RestController
public class test {

    @Autowired
    RestHighLevelClient highLevelClient;

    @Autowired
    WeChatService weChatService;

    //????????????
    @GetMapping("/add")
    public void testAdd() throws IOException {
        Message messa = new Message();
        messa.setMessage("sssssddf");
        messa.setFromName("zzz");
        messa.setToName("fff");
        messa.setDateTime(new Date());
        weChatService.add(messa);
    }

    //????????????
    @GetMapping("/create")
    public void createIndex() throws IOException {
        //  CreateIndexRequest request = new CreateIndexRequest("goods_index_tmp2");
        CreateIndexRequest request = new CreateIndexRequest("es_geo");
        CreateIndexResponse createIndexResponse = highLevelClient.indices().create(request, RequestOptions.DEFAULT);
        if (createIndexResponse.isAcknowledged()) {
            System.out.println("??????index??????!");
        }
    }

    //??????????????????
    @GetMapping("/get")
    public void getIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("goods_index_tmp2");
        GetIndexResponse getIndexResponse = highLevelClient.indices().get(request, RequestOptions.DEFAULT);
        System.out.println(getIndexResponse.getAliases());
        System.out.println(getIndexResponse.getMappings());
    }

    //????????????
    @GetMapping("/deleteSuoYin")
    public void deleteSY() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("goods_index_tmp2");
        highLevelClient.indices().delete(request, RequestOptions.DEFAULT);

    }

    //????????????(?????????)
    @GetMapping("/insert_geo")
    public void insertGEO() throws IOException {
        IndexRequest request = new IndexRequest();
        request.index("es_geo").id("1002");
        GeoTest geoTest = new GeoTest();
        geoTest.setName("??????");
        geoTest.setAge(22);
        geoTest.setSex("???");
        GeoPoint geoPoint = new GeoPoint(190.23, 27.555);
        geoTest.setGeoPoint(geoPoint);
        ObjectMapper mapper = new ObjectMapper();
        String geojson = mapper.writeValueAsString(geoTest);
        request.source(geojson, XContentType.JSON);
        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
    }

    //????????????
    @GetMapping("/insert")
    public void insert() throws IOException {
        IndexRequest request = new IndexRequest();
        request.index("goods_index_tmp2").id("1001");
        UserTest user = new UserTest();
        user.setName("??????");
        user.setAge(22);
        user.setSex("???");
        ObjectMapper mapper = new ObjectMapper();
        String userjson = mapper.writeValueAsString(user);
        request.source(userjson, XContentType.JSON);
        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
    }

    //????????????
    @GetMapping("/update")
    public void update() throws IOException {
        UpdateRequest request = new UpdateRequest();
        request.index("goods_index_tmp2").id("1001");
        request.doc(XContentType.JSON, "sex", "???");
        UpdateResponse response = highLevelClient.update(request, RequestOptions.DEFAULT);
    }

    //????????????
    @GetMapping("/select")
    public ModelAndView select() throws IOException {
        GetRequest request = new GetRequest();
        request.index("goods_index_tmp2").id("1001");
        GetResponse response = highLevelClient.get(request, RequestOptions.DEFAULT);
        ModelAndView modelAndView = new ModelAndView("/hello");
        String a = response.getSourceAsString();
        modelAndView.addObject("age", a);
        System.out.println(response.getSourceAsString());

        //????????????

        return modelAndView;
    }

    //??????????????????
    @GetMapping("/geo")
    public void geo() throws IOException {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // ???????????????????????????????????????
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("geoPoint");
        distanceQueryBuilder.point(188.20, 28.555);
        // ???????????????????????????
        distanceQueryBuilder.distance(220, DistanceUnit.KILOMETERS);
        distanceQueryBuilder.geoDistance(GeoDistance.PLANE);
        boolQueryBuilder.filter(distanceQueryBuilder);
        SearchRequest request = new SearchRequest();
        request.indices("es_geo");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(boolQueryBuilder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit[] h = hits.getHits();
        for (SearchHit hit : h) {
            System.out.println(hit.getSourceAsMap());
        }

    }

    @GetMapping("/delete")
    public void delete() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.index("goods_index_tmp2").id("1001");
        DeleteResponse response = highLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        highLevelClient.close();

    }

    /**
     * ??????????????????
     */
    @GetMapping("/batchinsert")
    public void batchinsert() throws IOException {
        //??????????????????
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("goods_index_tmp2").id("1005").source(XContentType.JSON, "name", "zhangsan", "sex", "???", "age", 22));
        request.add(new IndexRequest().index("goods_index_tmp2").id("1006").source(XContentType.JSON, "name", "lisi", "sex", "???", "age", 26));
        request.add(new IndexRequest().index("goods_index_tmp2").id("1007").source(XContentType.JSON, "name", "wangwu", "sex", "???", "age", 29));
        BulkResponse response = highLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * ??????????????????
     */
    @GetMapping("/batchdelete")
    public void batchdelete() throws IOException {
        //??????????????????
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("goods_index_tmp2").id("1005"));
        request.add(new IndexRequest().index("goods_index_tmp2").id("1006"));
        request.add(new IndexRequest().index("goods_index_tmp2").id("1007"));
        BulkResponse response = highLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    //??????????????????????????????
    @GetMapping("/selectSJ")
    public void chaxun() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("goods_index_tmp2");
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    //????????????
    @GetMapping("/selectTJ")
    public void chaxunTJ() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("goods_index_tmp2");
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 22)));
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }


    //????????????
    @GetMapping("/selectFY")
    public void chaxunFY() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("goods_index_tmp2");
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.from(0);
        builder.size(2);
        request.source(builder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    //????????????
    @GetMapping("/selectPX")
    public void chaxunPX() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("goods_index_tmp2");
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //??????
        builder.sort("age", SortOrder.DESC);
        request.source(builder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    //????????????
    @GetMapping("/selectMH")
    public void chaxunMH() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("goods_index_tmp2");
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //???????????????Fuzziness.TWO????????????????????????????????????????????????
        builder.query(QueryBuilders.fuzzyQuery("name", "lis").fuzziness(Fuzziness.TWO));
        request.source(builder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    //????????????
    @GetMapping("/selectGLCX")
    public ModelAndView chaxunGLCX() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("goods_index_tmp2");

        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");
        highlightBuilder.field("sex");
        highlightBuilder.requireFieldMatch(false);
        builder.highlighter(highlightBuilder);
        builder.query(QueryBuilders.multiMatchQuery("??????", "name", "sex"));
        request.source(builder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        List<Map> list = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //??????????????????
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField field = highlightFields.get("name");
            HighlightField field1 = highlightFields.get("sex");
            if (field1 != null) {
                Text[] fragments = field1.fragments();
                String n_field1 = "";
                for (Text fragment : fragments) {
                    n_field1 += fragment;
                }
                //???????????????????????????
                sourceAsMap.put("sex", n_field1);
            }
            if (field != null) {
                Text[] fragments = field.fragments();
                String n_field = "";
                for (Text fragment : fragments) {
                    n_field += fragment;
                }
                //???????????????????????????
                sourceAsMap.put("name", n_field);
            }
            list.add(hit.getSourceAsMap());
        }
        ModelAndView modelAndView = new ModelAndView("/hello");
        modelAndView.addObject("ttt", list);
        return modelAndView;
    }

    @GetMapping("/selectJH")
    public void chaxunJH() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("goods_index_tmp2");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //????????????
//        AggregationBuilder aggregationBuilder= AggregationBuilders.max("maxAge").field("age") ;
//        builder.aggregation(aggregationBuilder);
        //????????????
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
        builder.aggregation(aggregationBuilder);
        request.source(builder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    @GetMapping("/selectGL")
    public void chaxunGL() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("goods_index_tmp2");
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //???????????????
        String[] excludes = {"age"};
        String[] includes = {};
        builder.fetchSource(includes, excludes);
        request.source(builder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    @GetMapping("/selectZH")
    public void chaxunZH() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("goods_index_tmp2");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        builder.query(boolQueryBuilder);
        //must???????????????should????????????
//        boolQueryBuilder.must(QueryBuilders.matchQuery("age",22));
//        boolQueryBuilder.must(QueryBuilders.matchQuery("sex","???"));
//        boolQueryBuilder.should(QueryBuilders.matchQuery("age",22));
//        boolQueryBuilder.should(QueryBuilders.matchQuery("age",30));
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        rangeQuery.gte(20);
        rangeQuery.lte(30);
        request.source(builder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    @RequestMapping("/hello")
    public ModelAndView houru() {
        ModelAndView modelAndView = new ModelAndView("/index");//????????????JSP???????????????
        //modelAndView.addObject("name", "miyue");
        return modelAndView;
    }


    @RequestMapping("/hello2")
    @ResponseBody
    public String hello(HttpServletRequest request) {
        if (!CodeUtil.checkVerifyCode(request)) {
            return "??????????????????";
        } else {
            return "hello,world";
        }
    }
}
