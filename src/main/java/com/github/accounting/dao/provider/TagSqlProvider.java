package com.github.accounting.dao.provider;

import com.github.accounting.model.persistence.TagInPersistence;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;


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

    /**
     * Generate sql to get tag list by a list of tag id.
     *
     * @param ids list of tag id.
     * @return sql string.
     */
    public String getTagListByIds(List<Long> ids) {
        return new SQL() {
            {
                SELECT("id, description, user_id, status, create_time, update_time ");
                FROM("as_tag ");
                WHERE(String.format("id in (%s)", Joiner.on(',').join(ids)));
            }
        }.toString();
    }
}
