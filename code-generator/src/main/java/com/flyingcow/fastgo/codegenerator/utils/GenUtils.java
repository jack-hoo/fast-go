package com.flyingcow.fastgo.codegenerator.utils;

import com.flyingcow.fastgo.codegenerator.entity.ColumnEntity;
import com.flyingcow.fastgo.codegenerator.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenUtils {

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Dao.java.vm");
        templates.add("template/Dao.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
//        templates.add("template/MyController.java.vm");
//        templates.add("template/list.html.vm");
//        templates.add("template/list.js.vm");
        templates.add("template/api.js.vm");
        templates.add("template/Table.vue.vm");
        templates.add("template/TableAdmin.vue.vm");
        templates.add("template/authorities.sql.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();

        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

//			try {
            //添加到zip
            String fileName = getFileName(template, tableEntity.getClassName(), config.getString("package"));
            String fileLocation = "";
            //数据库权限语句
            if (fileName.contains("authorities.sql")) {
                fileLocation = System.getProperty("user.dir") + "\\server\\src\\main\\resources\\sql\\" + fileName;
            }
            //mapper.xml语句
            else if (fileName.contains(("Dao.xml"))) {
                fileLocation = System.getProperty("user.dir") + "\\server\\src\\main\\resources\\mapper\\" + fileName;
            }
            //前端vue代码生成
            else if (fileName.contains("Api.js")) {
                fileLocation = System.getProperty("user.dir") + "\\vue-admin\\src\\api\\" + fileName;
            } else if (fileName.contains(".vue") && !fileName.contains("Admin")) {
                fileLocation = System.getProperty("user.dir") + "\\vue-admin\\src\\views\\common-pages\\" + fileName;
            } else if (fileName.contains("Admin.vue")) {
                fileLocation = System.getProperty("user.dir") + "\\vue-admin\\src\\views\\admin-pages\\" + fileName;
            }
            //后端代码生成

            else {
                fileLocation = System.getProperty("user.dir") + "\\server\\src\\main\\java\\com\\flyingcow\\fastgo\\server\\" +fileName;
            }
            Path path = Paths.get(fileLocation);
            if (!Files.exists(path)) {
                try {
                    Files.createFile(path);
                    Files.write(path, sw.toString().getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*try {
                zip.putNextEntry(new ZipEntry(fileName));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }*/
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RRException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("Entity.java.vm")) {
//            return packagePath + "entity" + File.separator + className + "Entity.java";
            return "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Dao.java.vm")) {
//            return packagePath + "dao" + File.separator + className + "Dao.java";
            return "dao" + File.separator + className + "Dao.java";
        }

        if (template.contains("Service.java.vm")) {
//            return packagePath + "service" + File.separator + className + "Service.java";
            return "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
//            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
            return "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        /*if (template.contains("MyController.java.vm")) {
            return packagePath + "myController" + File.separator + className + "MyController.java";
        }*/

        if (template.contains("Controller.java.vm")) {
//            return packagePath + "controller" + File.separator + className + "Controller.java";
            return "controller" + File.separator + className + "Controller.java";
        }


        if (template.contains("Dao.xml.vm")) {
//            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + className + "Dao.xml";
            return className + "Dao.xml";
        }

        /*if (template.contains("list.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "views" + File.separator
                    + "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".html";
        }*/

        if (template.contains("TableAdmin.vue.vm")) {
//            return "main" + File.separator + "resources" + File.separator + "views" + File.separator
//                    + "modules" + File.separator + "generator" + File.separator + className + "Admin.vue";
            return className + "Admin.vue";
        }

        if (template.contains("Table.vue.vm")) {
            /*return "main" + File.separator + "resources" + File.separator + "views" + File.separator
                    + "modules" + File.separator + "generator" + File.separator + className + ".vue";*/
            return className + ".vue";
        }


        /*if (template.contains("list.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".js";
        }*/

        if (template.contains("api.js.vm")) {
//            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
//                    + "modules" + File.separator + "generator" + File.separator + className + "Api.js";
            return className + "Api.js";
        }

        if (template.contains("authorities.sql.vm")) {
            return className.toLowerCase() + "_authorities.sql";
        }

        return null;
    }
}
