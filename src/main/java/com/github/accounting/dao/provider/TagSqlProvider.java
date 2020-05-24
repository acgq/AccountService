package com.github.accounting.dao.provider;

import com.github.accounting.model.persistence.TagInPersistence;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;


@Slf4j
public class TagSqlProvider {
    /**
     * generate dynamic sql statement.
     *
     * @param tag tag to be update.
     * @return dynamic sql.
     */
    public String updateTag(TagInPersistence tag) {
        return new SQL() {
            {
                UPDATE("as_tag");
                if (tag.getDescription() != null) {
                    SET("description = #{description}");
                }
                if (tag.getStatus() != null) {
                    SET("status = #{status}");
                }
                if (tag.getUserId() != null) {
                    SET("user_id = #{userId}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
