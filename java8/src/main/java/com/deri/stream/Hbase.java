package com.deri.stream;

import com.deri.stream.util.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Hbase
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/4/13 15:30
 * @Version: v1.0
 **/
@Slf4j
public class Hbase {
    private static final String USER_ID_PREFIX = "user-";
    private static Admin admin;
    public static void main(String[] args) {
        BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境。
        try {
            createTable("test:user_table", new String[] { "info", "contact" });
            User user = new User("001", "xiaoming", "123456", "man", "20", "13355550021", "1232821@csdn.com");
            insertData("test:user_table", user);
            User user2 = new User("002", "xiaohong", "654321", "female", "18", "18757912212", "214214@csdn.com");
            insertData("test:user_table", user2);
            log.info("--------------------插入两条数据后--------------------");
            List<User> list = getAllData("test:user_table");
            list.forEach(e->log.info("after first insert, user:{}",e));
            log.info("--------------------获取原始数据-----------------------");
            getNoDealData("test:user_table");
            log.info("--------------------根据rowKey查询--------------------");
            User user4 = getDataByRowKey("test:user_table", USER_ID_PREFIX+"001");
            log.info("getDataByRowKey 001:{}",user4);
            log.info("--------------------获取指定单条数据-------------------");
            String user_phone = getCellData("test:user_table", USER_ID_PREFIX+"001", "contact", "phone");
            log.info("getCellData 001 contact phone:{}",user_phone);
            User user5 = new User("test-003", "xiaoguang", "789012", "man", "22", "12312132214", "856832@csdn.com");
            insertData("test:user_table", user5);
            log.info("--------------------插入测试数据后--------------------");
            List<User> list2 = getAllData("test:user_table");
            list2.forEach(e->log.info("after insert test data, user:{}",e));
            deleteByRowKey("test:user_table", "user-test-003");
            List<User> list3 = getAllData("test:user_table");
            log.info("--------------------删除测试数据后--------------------");
            list3.forEach(e->log.info("after delete test data, user:{}",e));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 连接集群
     * @return
     * @throws IOException
     */
    public static Connection initHbase() throws IOException {

        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "192.168.41.128");
        configuration.setInt("hbase.rpc.timeout",2000);
        configuration.setInt("hbase.client.operation.timeout",3000);
        configuration.setInt("hbase.client.scanner.timeout.period",6000);
        //集群配置
       /* configuration.set("hbase.zookeeper.quorum", "192.168.5.135,192.168.5.136,192.168.5.137");
        configuration.set("hbase.master", "192.168.5.138:60000");*/
        Connection connection = ConnectionFactory.createConnection(configuration);
        return connection;
    }
    /**
     * 创建表
     * @param tableOriginName
     * @param cols
     * @throws IOException
     */
    public static void createTable(String tableOriginName, String[] cols) throws IOException {

        TableName tableName = TableName.valueOf(tableOriginName);
        admin = initHbase().getAdmin();
        if (admin.tableExists(tableName)) {
//            deleteTable(tableNmae);
            log.info("{} 表已存在！",tableOriginName);
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String col : cols) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
        }
    }

    /**
     * 插入数据
     * @param tableName
     * @param user
     * @throws IOException
     */
    public static void insertData(String tableName, User user) throws IOException {
        TableName tablename = TableName.valueOf(tableName);
        Put put = new Put((USER_ID_PREFIX + user.getId()).getBytes());
        //参数：1.列族名  2.列名  3.值
        put.addColumn("info".getBytes(), "username".getBytes(), user.getUsername().getBytes()) ;
        put.addColumn("info".getBytes(), "age".getBytes(), user.getAge().getBytes()) ;
        put.addColumn("info".getBytes(), "gender".getBytes(), user.getGender().getBytes()) ;
        put.addColumn("contact".getBytes(), "phone".getBytes(), user.getPhone().getBytes());
        put.addColumn("contact".getBytes(), "email".getBytes(), user.getEmail().getBytes());
        //HTable table = new HTable(initHbase().getConfiguration(),tablename);已弃用
        Table table = initHbase().getTable(tablename);
        table.put(put);
    }

    /**
     * 获取原始数据
     * @param tableName
     */
    public static void getNoDealData(String tableName){
        try {
            Table table= initHbase().getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            ResultScanner resutScanner = table.getScanner(scan);
            for(Result result: resutScanner){
                log.info("scan:  " + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据rowKey进行查询
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public static User getDataByRowKey(String tableName, String rowKey) throws IOException {

        Table table = initHbase().getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        User user = new User();
        user.setId(rowKey);
        //先判断是否有此条数据
        if(!get.isCheckExistenceOnly()){
            Result result = table.get(get);
            for (Cell cell : result.rawCells()){
                String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                if(colName.equals("username")){
                    user.setUsername(value);
                }
                if(colName.equals("age")){
                    user.setAge(value);
                }
                if (colName.equals("gender")){
                    user.setGender(value);
                }
                if (colName.equals("phone")){
                    user.setPhone(value);
                }
                if (colName.equals("email")){
                    user.setEmail(value);
                }
            }
        }
        return user;
    }


    /**
     * 查询指定单cell内容
     * @param tableName
     * @param rowKey
     * @param family
     * @param col
     * @return
     */
    public static String getCellData(String tableName, String rowKey, String family, String col){

        try {
            Table table = initHbase().getTable(TableName.valueOf(tableName));
            String result = null;
            Get get = new Get(rowKey.getBytes());
            if(!get.isCheckExistenceOnly()){
                get.addColumn(Bytes.toBytes(family),Bytes.toBytes(col));
                Result res = table.get(get);
                byte[] resByte = res.getValue(Bytes.toBytes(family), Bytes.toBytes(col));
                return result = Bytes.toString(resByte);
            }else{
                return result = "查询结果不存在";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "出现异常";
    }



    /**
     * 查询指定表名中所有的数据
     * @param tableName
     * @return
     */
    public static List<User> getAllData(String tableName){

        Table table = null;
        List<User> list = new ArrayList<User>();
        try {
            table = initHbase().getTable(TableName.valueOf(tableName));
            ResultScanner results = table.getScanner(new Scan());
            User user = null;
            for (Result result : results){
                String id = new String(result.getRow());
                log.info("用户名:" + new String(result.getRow()));
                user = new User();
                for(Cell cell : result.rawCells()){
                    String row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
                    //String family =  Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength());
                    String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                    String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    user.setId(row);
                    if(colName.equals("username")){
                        user.setUsername(value);
                    }
                    if(colName.equals("age")){
                        user.setAge(value);
                    }
                    if (colName.equals("gender")){
                        user.setGender(value);
                    }
                    if (colName.equals("phone")){
                        user.setPhone(value);
                    }
                    if (colName.equals("email")){
                        user.setEmail(value);
                    }
                }
                list.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 删除指定cell数据
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public static void deleteByRowKey(String tableName, String rowKey) throws IOException {

        Table table = initHbase().getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        //删除指定列
        //delete.addColumns(Bytes.toBytes("contact"), Bytes.toBytes("email"));
        table.delete(delete);
    }

    /**
     * 删除表
     * @param tableName
     */
    public static void deleteTable(String tableName){

        try {
            TableName tablename = TableName.valueOf(tableName);
            admin = initHbase().getAdmin();
            admin.disableTable(tablename);
            admin.deleteTable(tablename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
