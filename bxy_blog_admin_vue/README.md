# 富文本编辑器
- 安装依赖
    ```npm
    npm i @kangc/v-md-editor -S
    ```

剪切板
npm install --save v-clipboard

mysql插入表情需要修改数据库，对应表，对应字段的字符编码为utf8mb4
```sql
CREATE TABLE `blog`  (
  `content_html` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'html格式内容',
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客表' ROW_FORMAT = Compact;
```
